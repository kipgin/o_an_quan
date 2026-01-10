package com.code.util.managers;

import com.code.config.GameConstants;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameControlManager {
    private final Button btnStop;
    private final GameTimerManager timerManager;

    public GameControlManager(Button btnStop, GameTimerManager timerManager) {
        this.btnStop = btnStop;
        this.timerManager = timerManager;
        setupActions();
    }

    private void setupActions() {
        btnStop.setOnAction(e -> handlePauseResume());
    }

    private void handlePauseResume() {
        if (timerManager.isPaused()) {
            timerManager.resume();
            setButtonImage(btnStop, GameConstants.IMG_STOP);
        } else {
            timerManager.pause();
            setButtonImage(btnStop, GameConstants.IMG_CONTINUE);
        }
    }

    private void setButtonImage(Button btn, String path) {
        try {
            ImageView view = (ImageView) btn.getGraphic();
            view.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            System.err.println("Failed to set button image: " + path + " - " + e.getMessage());
        }
    }

    public Button getStopButton() {
        return btnStop;
    }

    public void setEnabled(boolean enabled) {
        btnStop.setDisable(!enabled);
    }

    public void resetToDefaultState() {
        setButtonImage(btnStop, GameConstants.IMG_STOP);
    }
}
