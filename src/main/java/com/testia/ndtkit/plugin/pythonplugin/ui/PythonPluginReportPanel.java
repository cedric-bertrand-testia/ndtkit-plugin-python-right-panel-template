package com.testia.ndtkit.plugin.pythonplugin.ui;

import agi.ndtkit.modules.actions.configuration.NINoReportConfiguration;
import agi.ndtkit.modules.actions.gui.fx.NIFxInfoPanel;
import javafx.scene.layout.Pane;

public class PythonPluginReportPanel extends NIFxInfoPanel<NINoReportConfiguration> {

    @Override
    public void bind(NINoReportConfiguration arg0) {
        // Nothing
    }

    @Override
    protected FxPanelUiConfiguration createFxUiConfiguration(boolean arg0) {
        return new FxPanelUiConfiguration(new ManualConfiguration(new Pane()));
    }

}
