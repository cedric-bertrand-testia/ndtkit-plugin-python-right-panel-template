package com.testia.ndtkit.plugin.pythonplugin.preferences;

import agi.ndtkit.modules.preferences.NIPreferencesAccessor;

public class PythonPluginPreferencesAccessor extends NIPreferencesAccessor<PythonPluginProperties> {

    @Override
    public PythonPluginProperties getPreferenceValue() {
        PythonPluginProperties properties = new PythonPluginProperties();
        return properties;
    }

    @Override
    public void setPreferenceValue(final PythonPluginProperties preferenceValue) {
    }
}
