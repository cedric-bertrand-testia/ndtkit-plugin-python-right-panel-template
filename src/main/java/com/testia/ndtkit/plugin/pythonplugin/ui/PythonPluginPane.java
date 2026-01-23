package com.testia.ndtkit.plugin.pythonplugin.ui;

import com.testia.ndtkit.plugin.pythonplugin.configuration.PythonPluginConfiguration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class PythonPluginPane extends VBox {

    /** This instance. */
    private static PythonPluginPane instance;
    /** The main panel where UI is displayed. */
    private final PythonPluginPanel mainPanel = new PythonPluginPanel();

    /** The URL to load. */
    private final String targetUrl = PythonPluginConfiguration.getUserInterfaceURL();

    /** Timer for retrying the connection. */
    private Timeline retryTimeline;

    public PythonPluginPane() {
        initMainPane();
        setSpacing(5);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(5, 0, 5, 0));
    }

    /**
     * Initialize the main area pane.
     */
    private void initMainPane() {
        try {
            mainPanel.createUi(false);

            Parent root = mainPanel.getMainScene().getRoot();
            getChildren().add(root);
            loadUIFromURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUIFromURL() {
        Platform.runLater(() -> {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);

            // 1. Create a Timeline that triggers every second
            retryTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                Label debugLabel = mainPanel.controller.getDebugLabel();
                if (debugLabel.getText().endsWith("...")) {
                    debugLabel.setText(debugLabel.getText().substring(0, debugLabel.getText().length() - 3));
                } else {
                    debugLabel.setText(debugLabel.getText() + ".");
                }
                engine.load(targetUrl);
            }));
            // Set to run indefinitely until we stop it manually
            retryTimeline.setCycleCount(Timeline.INDEFINITE);

            // 2. Listen to the Worker State (SUCCEEDED, FAILED, RUNNING, etc.)
            engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

                if (newState == Worker.State.SUCCEEDED) {
                    // Connection successful: Stop the retry timer
                    System.out.println("[SUCCESS] URL is now reachable. PythonPlugin UI Loaded successfully.");
                    if (retryTimeline.getStatus() == javafx.animation.Animation.Status.RUNNING) {
                        retryTimeline.stop();
                    }
                    // Add WebView to the VBox immediately
                    getChildren().remove(mainPanel.getMainScene().getRoot());
                    getChildren().add(webView);

                } else if (newState == Worker.State.FAILED) {
                    if (retryTimeline.getStatus() != javafx.animation.Animation.Status.RUNNING) {
                        retryTimeline.play();
                    }
                }
            });

            // 3. Initial attempt to load the URL
            engine.load(targetUrl);
        });
    }

    public static PythonPluginPane getInstance() {
        if (instance == null) {
            instance = new PythonPluginPane();
        }
        return instance;
    }
}