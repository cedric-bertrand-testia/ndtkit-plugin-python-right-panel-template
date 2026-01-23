package com.testia.ndtkit.plugin.pythonplugin.actions;

import com.testia.ndtkit.plugin.pythonplugin.PythonPluginModule;
import com.testia.ndtkit.plugin.pythonplugin.configuration.PythonPluginConfiguration;
import com.testia.ndtkit.plugin.pythonplugin.i18n.I18n;
import com.testia.ndtkit.plugin.pythonplugin.result.PythonPluginResult;
import com.testia.ndtkit.plugin.pythonplugin.ui.PythonPluginPanel;
import com.testia.ndtkit.plugin.pythonplugin.ui.PythonPluginReportPanel;

import agi.ndtkit.api.common.localization.NILocalizedString;
import agi.ndtkit.api.model.frame.NICartographyFrame;
import agi.ndtkit.modules.actions.configuration.NIAutomationConfiguration;
import agi.ndtkit.modules.actions.configuration.NINoReportConfiguration;
import agi.ndtkit.modules.actions.definition.NIActionDeclareModules;
import agi.ndtkit.modules.actions.definition.NIActionDefinition;
import agi.ndtkit.modules.actions.definition.NIActionInGroup;
import agi.ndtkit.modules.actions.definition.NIActionIsAutomationCapableAndConfigurable;
import agi.ndtkit.modules.actions.gui.NIInfoPanel;
import agi.ndtkit.modules.actions.result.NIResult;
import agi.ndtkit.modules.definition.NIModuleDefinition;

public class PythonPluginAction extends NIActionDefinition
        implements NIActionDeclareModules, NIActionInGroup,
        NIActionIsAutomationCapableAndConfigurable<PythonPluginConfiguration, NIResult, NINoReportConfiguration> {

    public static String ACTION_ID = "PythonPlugin";

    @Override
    public NIModuleDefinition[] getDeclaredModules() {
        return new NIModuleDefinition[]{PythonPluginModule.getInstance()};
    }

    @Override
    public String getId() {
        return ACTION_ID;
    }

    @Override
    public Class<? extends PythonPluginConfiguration> getConfigClass() {
        return PythonPluginConfiguration.class;
    }

    @Override
    public String getConfigurationFilename() {
        return "python-plugin.cfg";
    }

    @Override
    public Class<? extends NIInfoPanel<PythonPluginConfiguration>> getConfigPanelClass() {
        return PythonPluginPanel.class;
    }

    @Override
    public NILocalizedString getName() {
        return I18n.getProvider().getString("python.plugin.action.name");
    }

    @Override
    public Class<? extends NIResult> getResultClass() {
        return PythonPluginResult.class;
    }

    @Override
    public NILocalizedString getGroupName() {
        return getName();
    }

    @Override
    public NIAutomationConfiguration<NINoReportConfiguration> getAutomationConfiguration() {
        NIAutomationConfiguration<NINoReportConfiguration> config = NIAutomationConfiguration.create();
        config.setConfigurationPanel(NINoReportConfiguration.class, PythonPluginReportPanel.class);
        return config;
    }

    @Override
    public PythonPluginResult executeAction(final PythonPluginConfiguration pythonpluginConfiguration, final boolean b, final NICartographyFrame... niCartographyFrames) {
        return new PythonPluginResult();
    }
}


