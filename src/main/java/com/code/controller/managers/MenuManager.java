package com.code.controller.managers;

import javafx.scene.control.Button;

public class MenuManager {
    private final Button btnMenu;
    private final Runnable onMenuAction;

    public MenuManager(Button btnMenu, Runnable onMenuAction) {
        this.btnMenu = btnMenu;
        this.onMenuAction = onMenuAction;
        setup();
    }

    private void setup() {
        btnMenu.setOnAction(e -> onMenuAction.run());
    }
}
