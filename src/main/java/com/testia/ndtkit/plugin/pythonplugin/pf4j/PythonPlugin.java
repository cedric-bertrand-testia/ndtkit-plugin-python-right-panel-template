package com.testia.ndtkit.plugin.pythonplugin.pf4j;

import java.io.IOException;

import org.pf4j.PluginWrapper;

import com.testia.ndtkit.plugin.pythonplugin.actions.PythonPluginAction;

import agi.ndtkit.api.NIFileConstants;
import agi.ndtkit.apiplugin.services.NDTkitServicesManager;
import agi.ndtkit.apiplugin.services.NIActionPlugin;

public class PythonPlugin extends NIActionPlugin {

    private static PythonPlugin currentPlugin;

    public static PythonPlugin get() {
        return currentPlugin;
    }

    public PythonPlugin(PluginWrapper wrapper) {
        super(wrapper);
        currentPlugin = this;
    }

    @Override
    public void start() {
        boolean licenseValid = true;

        // RESERVED FOR TESTIA: this following line allows to protect the plugin with a license. It means that the plugin
        // will be loaded only if the "python-plugin" information is discovered in the license extra information
        // licenseValid = NDTkitServicesManager.getInstance().getSecurityService().isLicenseValid("python-plugin", "ALL");

        if (licenseValid) {
            NDTkitServicesManager.getInstance().getActionService().addAction(PythonPluginAction.ACTION_ID, PythonPluginAction.class);
            // Run the server the soonest as possible in order to render the UI the soonest as possible
            try {
                Runtime.getRuntime().exec(NIFileConstants.USER_CONFIGURATION + "/plugins/python-plugin/python-plugin.exe", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM python-plugin.exe", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
