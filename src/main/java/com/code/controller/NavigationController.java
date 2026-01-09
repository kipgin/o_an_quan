package com.code.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationController {
    private static NavigationController instance;
    private Stage stage;

    private NavigationController() {
    }

    public static NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void switchScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể load file FXML: " + fxmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMainMenu() {
        switchScene("/fxml/MainMenu.fxml", "Ô Ăn Quan - Menu");
    }

    public void showHelpScreen() {
        switchScene("/fxml/HelpScreen.fxml", "Ô Ăn Quan - Instruction");
    }

    public void showGameScreen() {
        switchScene("/fxml/GameScreen.fxml", "Ô Ăn Quan - Playing...");
    }
}