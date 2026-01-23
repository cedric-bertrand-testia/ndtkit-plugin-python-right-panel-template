package com.testia.ndtkit.plugin.pythonplugin.ui;

import java.net.URL;

import com.testia.ndtkit.plugin.pythonplugin.configuration.PythonPluginConfiguration;
import com.testia.ndtkit.plugin.pythonplugin.i18n.I18n;
import com.testia.ndtkit.plugin.pythonplugin.pf4j.PythonPlugin;
import com.testia.ndtkit.plugin.pythonplugin.ui.controller.PythonPluginAutomationController;

import agi.ndtkit.modules.actions.gui.fx.NIFxInfoPanel;

public class PythonPluginPanel extends NIFxInfoPanel<PythonPluginConfiguration> {

    /** The GUI controller. */
    PythonPluginAutomationController controller;
    /** The FXML GUI description URL. */
    URL fxmlURL;

    /**
     * Constructor.
     */
    public PythonPluginPanel() {
        fxmlURL = PythonPlugin.get().getResourceLoader().loadAsURL("fxml/automationConfiguration.fxml");
    }

    @Override
    public void bind(PythonPluginConfiguration configuration) {
        controller.initializeGUIFromConfiguration(configuration);
    }

    protected FxPanelUiConfiguration createFxUiConfiguration(final boolean isReadonly) {
        controller = new PythonPluginAutomationController();
        I18n.instance();
        return new FxPanelUiConfiguration(//
                new FxmlConfiguration(//
                        controller, //
                        fxmlURL, //
                        I18n.getBundle()));
    }
}
