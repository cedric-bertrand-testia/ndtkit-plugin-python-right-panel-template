package com.testia.ndtkit.plugin.pythonplugin.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import agi.ndtkit.modules.actions.configuration.NIReportConfiguration;

@XmlRootElement(name = "pythonPluginReportConfiguration")
@XmlType(name = "PythonPluginReportConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class PythonPluginReportConfiguration extends NIReportConfiguration {

}
