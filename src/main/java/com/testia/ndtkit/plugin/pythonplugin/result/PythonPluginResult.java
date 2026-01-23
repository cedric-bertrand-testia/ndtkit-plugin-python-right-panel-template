package com.testia.ndtkit.plugin.pythonplugin.result;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Drawing;

import com.agi.ndtkit.common.io.excel.ExcelStyleManager;

import agi.ndtkit.api.reporting.word.NIDocumentNode;
import agi.ndtkit.modules.actions.configuration.NIReportConfiguration;
import agi.ndtkit.modules.actions.result.NIExportableResultV1;

@XmlRootElement(name = "pythonPluginResult")
@XmlType(name = "PythonPluginResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class PythonPluginResult extends NIExportableResultV1<NIReportConfiguration> {

    public PythonPluginResult() {
    }

    @Override
    public List<NIDocumentNode> writeToWord(final NIReportConfiguration niReportConfiguration) {
        return new ArrayList<>();
    }

    @Override
    public void writeToExcel(final NIReportConfiguration niReportConfiguration, final Drawing drawing, final Cell cell, final ExcelStyleManager excelStyleManager) {
    }
}
