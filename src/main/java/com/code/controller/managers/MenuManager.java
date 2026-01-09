package com.code.controller.managers;

import javafx.geometry.Bounds;
import javafx.scene.control.Button;

public class MenuManager {
    private final Button btnMenu;
    private final Runnable onMenuAction;
    private final HandCursorManager handCursorManager;

    public MenuManager(Button btnMenu, HandCursorManager handCursorManager, Runnable onMenuAction) {
        this.btnMenu = btnMenu;
        this.handCursorManager = handCursorManager;
        this.onMenuAction = onMenuAction;
        setup();
    }

    private void setup() {
        btnMenu.setOnMouseClicked(e -> {
            if (onMenuAction != null) {
                onMenuAction.run();
            }
        });
    }

    public void handleHover(javafx.geometry.Bounds handBounds) {
        Bounds btnBounds = btnMenu.localToScene(btnMenu.getBoundsInLocal());
        if (handBounds.intersects(btnBounds)) {
            if (!btnMenu.getStyleClass().contains("button-hover")) {
                btnMenu.getStyleClass().add("button-hover");
                handCursorManager.setHandClosed();
            }
        } else {
            if (btnMenu.getStyleClass().contains("button-hover")) {
                btnMenu.getStyleClass().remove("button-hover");
            }
        }
    }
}
