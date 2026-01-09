package com.code.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        System.exit(0);
    }
}