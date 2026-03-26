from ndtkit_api.ndtkit_socket_connection import gateway
from pathlib import Path
import json
import sys
from logger_config import AppLogger

_logger = None


def get_logger():
    global _logger
    if _logger:
        return _logger
    # Initialize the Logger System
    # This automatically handles the path, file creation, and print redirection
    app_log_system = AppLogger(log_filename="PythonPlugin.log")
    _logger = app_log_system.get_logger()
    return _logger

def get_port_from_config(default_port=1212) -> int:
    """
    Reads the 'port' key from a JSON configuration file located in the same directory.
    Returns default_port if file is missing, invalid, or key is not found.
    """
    try:
        file_path = gateway.jvm.agi.ndtkit.api.NIFileConstants.USER_CONFIGURATION + "/python-plugin.json"

        if not Path(file_path).exists():
            get_logger().info(f"Config file '{file_path}' not found. Using default port: {default_port}")
            return default_port

        with open(file_path, 'r', encoding='utf-8') as f:
            config = json.load(f)

        # Extract port, defaulting to the provided value if key is missing
        port = int(config.get('port', default_port))

        get_logger().info(f"Port {port} loaded from '{file_path}'")
        return port

    except json.JSONDecodeError:
        get_logger().info(f"'{file_path}' is not valid JSON. Using default port: {default_port}")
        return default_port
    except ValueError:
        get_logger().info(f"Port value in JSON is not a valid number. Using default: {default_port}")
        return default_port
    except Exception as e:
        get_logger().info(f"Unexpected error reading config: {e}. Using default port: {default_port}")
        return default_port


current_lang = None


def is_launched_from_executable():
    return getattr(sys, 'frozen', False)


def load_locales(folder_name='locales'):
    data = {}
    # GET ABSOLUTE PATH relative to this python file
    if is_launched_from_executable():
        if hasattr(sys, '_MEIPASS'):
            base_path = Path(sys._MEIPASS)  # one file exe file
        else:
            base_path = Path(sys.executable).parent  # exe file + _include dir
    else:
        base_path = Path(__file__).parent

    path = base_path / folder_name

    # Check if folder exists
    if not path.exists():
        # Just print warning, don't crash yet
        get_logger().info(f"Warning: Locales folder not found at: {path}")
        return {}

    for file in path.glob('*.json'):
        lang_code = file.stem
        try:
            with open(file, 'r', encoding='utf-8') as f:
                data[lang_code] = json.load(f)
        except Exception as e:
            get_logger().info(f"Error loading {file}: {e}")

    return data


# Load translations once at startup
all_translations = load_locales()


def get_java_lang():
    global current_lang
    # Using py4j for accessing a Java variable is slower than accessing a Python variable, so it is memorized
    if current_lang:
        return current_lang
    try:
        current_lang = gateway.jvm.java.util.Locale.getDefault().getLanguage()  # Memorized only if value comes from NDTkit
        return current_lang
    except:
        return 'en'


def t(key):
    """
    Retrieves value from loaded JSON based on current language.
    Falls back to the Key string if translation is missing.
    """
    # 1. Try to get language dict
    lang_dict = all_translations.get(get_java_lang(), {})
    # 2. Try to get key, otherwise fallback to English, then to Key itself
    return lang_dict.get(key, all_translations.get('en', {}).get(key, f"[{key}]"))
