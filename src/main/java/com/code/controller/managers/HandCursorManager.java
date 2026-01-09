package com.code.controller.managers;

import com.code.config.GameConstants;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HandCursorManager {
    private final ImageView handCursor;
    private final Node rootNode;
    private Image imgHandOpen;
    private Image imgHandClosed;

    public HandCursorManager(Node rootNode, ImageView handCursor) {
        this.rootNode = rootNode;
        this.handCursor = handCursor;
        setup();
    }

    private void setup() {
        handCursor.setVisible(true);
        handCursor.setMouseTransparent(true);
        loadHandImages();
        setHandOpen();

        // Hide system cursor
        rootNode.setCursor(javafx.scene.Cursor.NONE);
    }

    private void loadHandImages() {
        try {
            imgHandOpen = new Image(getClass().getResourceAsStream(GameConstants.IMG_HAND_OPEN));
            imgHandClosed = new Image(getClass().getResourceAsStream(GameConstants.IMG_HAND_CLOSED));
        } catch (Exception e) {
            System.err.println("Could not load hand images: " + e.getMessage());
        }
    }

    public void updatePosition(double x, double y) {
        handCursor.setLayoutX(x - GameConstants.CURSOR_OFFSET_X);
        handCursor.setLayoutY(y - GameConstants.CURSOR_OFFSET_Y);
    }

    private boolean isOpen = true;

    public void setHandOpen() {
        if (!isOpen && imgHandOpen != null) {
            handCursor.setImage(imgHandOpen);
            isOpen = true;
        }
    }

    public void setHandClosed() {
        if (isOpen && imgHandClosed != null) {
            handCursor.setImage(imgHandClosed);
            isOpen = false;
        }
    }

    public Bounds getBoundsInScene() {
        return handCursor.localToScene(handCursor.getBoundsInLocal());
    }
}
