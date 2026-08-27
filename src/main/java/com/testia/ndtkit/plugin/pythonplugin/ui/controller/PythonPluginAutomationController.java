package com.testia.ndtkit.plugin.pythonplugin.ui.controller;

import com.testia.ndtkit.plugin.pythonplugin.configuration.PythonPluginConfiguration;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PythonPluginAutomationController {
    @FXML
    private Label debugLabel;
    private PythonPluginConfiguration configuration;

    @FXML
    public void initialize() {
        updateLabelText();
    }

    private void updateLabelText() {
        if (debugLabel != null) {
            debugLabel.setText("Waiting for user interface on port " + PythonPluginConfiguration.getServerPort());
        }
    }

    public void initializeGUIFromConfiguration(final PythonPluginConfiguration configuration) {
        this.configuration = configuration;
        updateLabelText();
    }

    public Label getDebugLabel() {
        return debugLabel;
    }
}
