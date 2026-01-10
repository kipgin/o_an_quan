package com.code.controller.managers;

import javafx.scene.control.Button;

public class GameButtonManager {

    public void setupButtonHover(Button btn) {
        if (btn == null)
            return;

        btn.setOnMouseEntered(e -> {
            if (!btn.getStyleClass().contains("button-hover")) {
                btn.getStyleClass().add("button-hover");
            }
        });

        btn.setOnMouseExited(e -> {
            btn.getStyleClass().remove("button-hover");
        });
    }
}
