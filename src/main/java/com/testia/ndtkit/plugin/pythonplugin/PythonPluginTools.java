package com.testia.ndtkit.plugin.pythonplugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.testia.ndtkit.plugin.pythonplugin.actions.PythonPluginAction;
import com.testia.ndtkit.plugin.pythonplugin.i18n.I18n;
import com.testia.ndtkit.plugin.pythonplugin.ui.PythonPluginPane;

import agi.ndtkit.api.common.ui.sidebar.NISidebarManager;
import agi.ndtkit.api.common.ui.sidebar.NISidebarTab;
import agi.ndtkit.modules.features.tools.NIToolBase;

public class PythonPluginTools extends NIToolBase {
    /** The LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PythonPluginTools.class);
    /** The viewPane. */
    private NISidebarTab viewPane;

    @Override
    public void initialize() {
        // This tool should always be enabled.
        getState().setActive(true);
    }

    @Override
    public void onActivate() {
        LOGGER.debug("activate PythonPluginTool");

        viewPane = new NISidebarTab(I18n.instance().getLocalizedString("python.plugin.action.name"), PythonPluginPane.getInstance(), 300);
        viewPane.setClosable(false);

        NISidebarManager.addTab(PythonPluginAction.ACTION_ID, viewPane);
    }

    @Override
    public void onDeactivate() {
        NISidebarManager.removeTab(viewPane);
    }

}
