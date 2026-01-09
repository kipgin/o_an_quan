package com.code.controller.managers;

import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import java.util.HashMap;
import java.util.Map;

public class GameButtonManager {
    
    private final Map<Button, Bounds> boundsCache = new HashMap<>();
    
    public void handleButtonHover(Button btn, Bounds handBounds, HandCursorManager cursorManager) {
        if (btn == null)
            return;

        if (!boundsCache.containsKey(btn)) {
            boundsCache.put(btn, btn.localToScene(btn.getBoundsInLocal()));
        }

        Bounds btnBounds = boundsCache.get(btn);

        if (handBounds.intersects(btnBounds)) {
            if (!btn.getStyleClass().contains("button-hover")) {
                btn.getStyleClass().add("button-hover");
                cursorManager.setHandClosed();
            }
        } else {
            btn.getStyleClass().remove("button-hover");
        }
    }

    public void clearCache() {
        boundsCache.clear();
    }
}
