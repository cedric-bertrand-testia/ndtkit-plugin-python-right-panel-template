package com.testia.ndtkit.plugin.pythonplugin.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import agi.ndtkit.api.NIFileConstants;
import agi.ndtkit.modules.actions.configuration.NIActionConfiguration;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pythonPluginConfiguration")
@XmlType(name = "PythonPluginConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class PythonPluginConfiguration extends NIActionConfiguration {

    private static String UI_URL = null;

    public PythonPluginConfiguration() {
    }

    public static String getUserInterfaceURL() {
        if (UI_URL != null) {
            return UI_URL;
        }
        return UI_URL = "http://127.0.0.1:" + getServerPort() + "/";
    }

    /**
     * Reads the 'port' key from a JSON configuration file located on the filesystem.
     * Uses a regex approach to avoid external JSON parsing libraries.
     * * @return The port number found in the file, or DEFAULT_PORT if any error occurs.
     */
    public static int getServerPort() {
        int defaultPort = 1212;
        String filename = NIFileConstants.USER_CONFIGURATION + "/python-plugin.json";
        // Path relative to the Current Working Directory (where the application is executed)
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            System.out.println("[INFO] Config file '" + filename + "' not found on filesystem. Using default port: " + defaultPort);
            return defaultPort;
        }

        try {
            // Read the entire file content into a String (Java 11+ required for Files.readString)
            String jsonContent = Files.readString(path).trim();

            // Group 1 captures the digits
            Pattern pattern = Pattern.compile("\"port\"\\s*:\\s*(\\d+)");
            Matcher matcher = pattern.matcher(jsonContent);

            if (matcher.find()) {
                // Group 1 is the captured digit string
                String portStr = matcher.group(1);
                int port = Integer.parseInt(portStr);

                System.out.println("[INFO] Port " + port + " loaded from " + filename + ".");
                return port;
            } else {
                System.out.println("[WARN] Key 'port' not found in JSON. Using default: " + defaultPort);
            }

        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Port value in JSON is not a valid number. Using default: " + defaultPort);
        } catch (IOException e) {
            // Catches file access errors (read permissions, disk issues, etc.)
            System.err.println("[ERROR] Failed to read config file " + filename + ": " + e.getMessage() + ". Using default: " + defaultPort);
        }

        return defaultPort;
    }

}
