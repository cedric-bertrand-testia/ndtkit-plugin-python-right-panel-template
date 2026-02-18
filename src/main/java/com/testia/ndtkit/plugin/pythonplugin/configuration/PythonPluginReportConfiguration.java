package com.testia.ndtkit.plugin.pythonplugin.configuration;

import agi.ndtkit.modules.actions.configuration.NIReportConfiguration;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pythonPluginReportConfiguration")
@XmlType(name = "PythonPluginReportConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class PythonPluginReportConfiguration extends NIReportConfiguration {

}
