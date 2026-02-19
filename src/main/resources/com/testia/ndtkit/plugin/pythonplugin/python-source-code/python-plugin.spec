# -*- mode: python ; coding: utf-8 -*-
import os
from PyInstaller.utils.hooks import collect_all

# Read the name passed from the build script, or fallback to a default
plugin_name = os.environ.get('NDTKIT_PLUGIN_NAME', 'python-plugin')

# --- Collect everything for NiceGUI ---
ng_datas, ng_binaries, ng_hiddenimports = collect_all('nicegui')

block_cipher = None

# --- HIDDEN IMPORTS LIST ---
hidden_imports_list = [
    'py4j.java_collections',
    'py4j.java_gateway',
    'py4j.protocol',
    'py4j.finalizer',
    'uvicorn.logging',
    'uvicorn.loops',
    'uvicorn.loops.auto',
    'uvicorn.protocols',
    'uvicorn.protocols.http',
    'uvicorn.protocols.http.auto',
    'uvicorn.lifespan',
    'uvicorn.lifespan.on',
    'engineio.async_drivers.threading',
    'json',
]

hidden_imports_list.extend(ng_hiddenimports)

a = Analysis(
    ['main.py'],
    pathex=[],
    # --- Add NiceGUI binaries ---
    binaries=ng_binaries,
    # --- Append NiceGUI data files (CSS, JS, etc.) ---
    datas=[
        ('locales', 'locales'),
    ] + ng_datas,
    hiddenimports=hidden_imports_list,
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    cipher=block_cipher,
    noarchive=False,
)

pyz = PYZ(a.pure, a.zipped_data, cipher=block_cipher)

# --- EXE CONFIGURATION (Launcher only) ---
exe = EXE(
    pyz,
    a.scripts,
    [],
    exclude_binaries=True, # Make the exe file lighter
    name=plugin_name,
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=False,
    console=False,
    disable_windowed_traceback=False,
    argv_emulation=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
)

# --- COLLECT CONFIGURATION (The Directory) ---
coll = COLLECT(
    exe,
    a.binaries,
    a.zipfiles,
    a.datas,
    strip=False,
    upx=False,
    upx_exclude=[],
    name=plugin_name,
)