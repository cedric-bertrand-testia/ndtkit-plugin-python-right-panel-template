package com.testia.ndtkit.plugin.pythonplugin.ui.controller;

import com.testia.ndtkit.plugin.pythonplugin.configuration.PythonPluginConfiguration;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PythonPluginAutomationController {
    @FXML
    private Label debugLabel;
    private PythonPluginConfiguration configuration;

    public void initializeGUIFromConfiguration(final PythonPluginConfiguration configuration) {
        this.configuration = configuration;
        debugLabel.setText("Debug mode: waiting for user interface on port " + PythonPluginConfiguration.getServerPort());
    }

    public Label getDebugLabel() {
        return debugLabel;
    }
}
