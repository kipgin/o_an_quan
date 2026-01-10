package com.code.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MainMenuController {

    @FXML
    private Button btnStart;
    @FXML
    private Button btnHelp;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnMusic;

    @FXML
    public void initialize() {
        com.code.controller.managers.MusicManager.getInstance().attachMusicButton(btnMusic);

        btnStart.setOnAction(e -> handleStartGame());
        btnHelp.setOnAction(e -> handleHelp());
        btnExit.setOnAction(e -> handleExit());
    }

    private void handleStartGame() {
        NavigationController.getInstance().showGameScreen();
    }

    private void handleHelp() {
        NavigationController.getInstance().showHelpScreen();
    }

    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit game");
        alert.setHeaderText("Are you sure to exit the game?");
        alert.setContentText("Choose Yes to exit the game");

        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnYes) {
            System.exit(0);
        }
    }
}