import sys
import logging
from pathlib import Path


class StreamToLogger:
    """
    Fake file-like stream object that redirects writes to a logger instance.
    Includes a recursion guard and Uvicorn compatibility.
    """

    def __init__(self, logger, log_level=logging.INFO):
        self.logger = logger
        self.log_level = log_level
        self.linebuf = ''
        # RECURSION GUARD: Prevents the logger from logging its own errors
        self.logging_active = False

    def write(self, buf):
        # 1. Safety check: If we are already logging, DO NOT log again.
        if self.logging_active:
            return

        # 2. Mark as active
        self.logging_active = True

        try:
            # Only log non-empty lines
            for line in buf.rstrip().splitlines():
                # We interpret the string to assign the correct level
                level = self.log_level
                self.logger.log(level, line.rstrip())
        except Exception:
            # If an error occurs WHILE logging, we must swallow it
            # otherwise we trigger the infinite recursion loop again.
            pass
        finally:
            # 3. Release lock
            self.logging_active = False

    def flush(self):
        pass

    # --- Uvicorn Compatibility ---
    def isatty(self):
        return False

    @property
    def encoding(self):
        return 'utf-8'


class AppLogger:
    def __init__(self, log_filename="app_log.txt", level=logging.INFO):
        # 1. Save original streams cautiously
        self.original_stdout = sys.__stdout__
        self.original_stderr = sys.__stderr__

        self.log_path = self._get_log_path(log_filename)
        self.level = level
        self.logger = logging.getLogger("AppLogger")

        # Configure only if not already configured
        if not self.logger.handlers:
            self._setup_logging()
            self._redirect_stdout_stderr()

    def _get_log_path(self, filename):
        if getattr(sys, 'frozen', False):
            # Exe
            application_path = Path(sys.executable).parent
        else:
            # Script
            application_path = Path(__file__).parent.absolute()

        return application_path / filename

    def _setup_logging(self):
        formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')

        # 1. File Handler (Always safe)
        try:
            file_handler = logging.FileHandler(self.log_path, mode='a', encoding='utf-8')
            file_handler.setFormatter(formatter)
            self.logger.addHandler(file_handler)
        except Exception as e:
            # If we can't write to file, we are in trouble, but don't crash the app
            pass

        # 2. Stream Handler (Console) - CRITICAL FIX
        # We only add the console handler if there is a REAL console attached.
        # In a .exe with --noconsole, self.original_stdout is usually None or invalid.
        if self.original_stdout is not None:
            try:
                # Test if we can actually write to it
                self.original_stdout.fileno()
                stream_handler = logging.StreamHandler(stream=self.original_stdout)
                stream_handler.setFormatter(formatter)
                self.logger.addHandler(stream_handler)
            except (AttributeError, OSError, ValueError):
                # No real console exists (e.g., GUI app), so we skip adding this handler.
                # This prevents the OSError: [Errno 22]
                pass

        self.logger.setLevel(self.level)
        self.logger.info(f"Logging initialized. Path: {self.log_path}")

    def _redirect_stdout_stderr(self):
        """Redirects print() and crashes to the log file."""
        if sys.stdout != self.original_stdout and not isinstance(sys.stdout, StreamToLogger):
            pass

        sys.stdout = StreamToLogger(self.logger, logging.INFO)
        sys.stderr = StreamToLogger(self.logger, logging.ERROR)

    def get_logger(self):
        return self.logger
