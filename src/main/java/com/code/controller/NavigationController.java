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

    private NavigationController() {}

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
//            System.out.println("CAN RUN INTO HERE ===================");
            
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            // scene.getStylesheets().add(...);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể load file FXML: " + fxmlPath);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
//    private void switchScene(String fxmlPath, String title) {
//        try {
//            URL url = getClass().getResource(fxmlPath);
//            System.out.println("Trying to load FXML from: " + fxmlPath);
//            System.out.println("URL result: " + url);
//
//            if (url == null) {
//                System.err.println("ERROR: FXML file not found at path: " + fxmlPath);
//                System.err.println("Available resources:");
//                // List all resources in com/code/view/
//                try {
//                    var paths = getClass().getResource("/com/code/view/");
//                    System.err.println("Resources folder: " + paths);
//                } catch (Exception e) {
//                    System.err.println("Cannot list resources");
//                }
//                return;
//            }
//
//            FXMLLoader loader = new FXMLLoader(url);
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//
//            stage.setScene(scene);
//            stage.setTitle(title);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Không thể load file FXML: " + fxmlPath);
//        }
//    }


    public void showMainMenu() {
        switchScene("/com/code/view/MainMenu.fxml", "Ô Ăn Quan - Menu");
    }

    public void showHelpScreen() {
        switchScene("/com/code/view/HelpScreen.fxml", "Ô Ăn Quan - Hướng dẫn");
    }

    public void showGameScreen() {
        switchScene("/com/code/view/GameScreen.fxml", "Ô Ăn Quan - Đang chơi");
    }
}