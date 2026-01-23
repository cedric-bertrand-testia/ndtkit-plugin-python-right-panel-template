package com.testia.ndtkit.plugin.pythonplugin;

import com.testia.ndtkit.plugin.pythonplugin.i18n.I18n;

import agi.ndtkit.modules.definition.NIModuleDefinition;
import agi.ndtkit.modules.definition.NIModuleHasTools;
import agi.ndtkit.modules.features.tools.NIToolDefinition;

public class PythonPluginModule extends NIModuleDefinition implements NIModuleHasTools {

    /** The instance of singleton. */
    private static PythonPluginModule instance = null;

    private PythonPluginModule() {
        // nothing to do
    }

    /**
     * Singleton instance.
     * @return instance
     */
    public static PythonPluginModule getInstance() {
        if (instance == null) {
            instance = new PythonPluginModule();
        }
        return instance;
    }

    @Override
    public NIToolDefinition[] getTools() {
        NIToolDefinition definition = new NIToolDefinition("PythonPlugin", new PythonPluginTools());
        return new NIToolDefinition[]{definition};
    }

    @Override
    public String getModuleInternalName() {
        return I18n.getProvider().getString("python.plugin.action.name").getValueForCurrentLocale();
    }

}
