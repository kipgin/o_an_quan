package com.code.util.ui;

import com.code.config.GameConstants;
import com.code.model.game.MoveStep;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.geometry.Point2D;
import javafx.geometry.Bounds;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.List;

public class AnimationService {

    private final ImageView handCursor;
    private final Function<Integer, Node> nodeProvider;
    private final BiConsumer<Integer, Integer> stoneUpdater;
    private boolean isAnimating = false;
    private Image imgHandOpen;
    private Image imgHandClosed;

    public AnimationService(ImageView handCursor,
            Function<Integer, Node> nodeProvider,
            BiConsumer<Integer, Integer> stoneUpdater) {
        this.handCursor = handCursor;
        this.nodeProvider = nodeProvider;
        this.stoneUpdater = stoneUpdater;
        loadHandImages();
    }

    private void loadHandImages() {
        try {
            imgHandOpen = new Image(getClass().getResourceAsStream(GameConstants.IMG_HAND_OPEN));
            imgHandClosed = new Image(getClass().getResourceAsStream(GameConstants.IMG_HAND_CLOSED));
        } catch (Exception e) {
            System.err.println("Could not load hand images: " + e.getMessage());
        }
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void animateMove(List<MoveStep> history, Runnable onFinishedCallback) {
        if (isAnimating || history == null || history.isEmpty())
            return;

        isAnimating = true;

        handCursor.setVisible(true);
        if (imgHandClosed != null) {
            handCursor.setImage(imgHandClosed);
        }

        Timeline timeline = new Timeline();
        double delayTime = 0;

        for (MoveStep step : history) {
            KeyFrame kf = new KeyFrame(Duration.millis(delayTime), e -> {
                stoneUpdater.accept(step.getSquareId(), step.getStones());
                moveHandToSquare(step.getSquareId());
            });
            timeline.getKeyFrames().add(kf);
            delayTime += GameConstants.ANIMATION_STEP_DURATION_MS;
        }

        timeline.setOnFinished(e -> {
            isAnimating = false;
            if (imgHandOpen != null) {
                handCursor.setImage(imgHandOpen);
            }
            handCursor.setVisible(false);

            if (onFinishedCallback != null) {
                onFinishedCallback.run();
            }
        });
        timeline.play();
    }

    private void moveHandToSquare(int squareId) {
        Node squareNode = nodeProvider.apply(squareId);
        if (squareNode == null) {
            System.err.println("Warning: Square node not found for ID: " + squareId);
            return;
        }

        if (squareNode.getScene() == null) {
            System.err.println("Warning: Square node not in scene for ID: " + squareId);
            return;
        }

        Bounds boundsInScene = squareNode.localToScene(squareNode.getBoundsInLocal());


        double targetX = boundsInScene.getMinX() + boundsInScene.getWidth() / 2;
        double targetY = boundsInScene.getMinY() + boundsInScene.getHeight() / 2;

        double handWidth = handCursor.getFitWidth() > 0 ? handCursor.getFitWidth()
                : (handCursor.getImage() != null ? handCursor.getImage().getWidth() : 50);
        double handHeight = handCursor.getFitHeight() > 0 ? handCursor.getFitHeight()
                : (handCursor.getImage() != null ? handCursor.getImage().getHeight() : 50);

        handCursor.setLayoutX(targetX - handWidth / 2);
        handCursor.setLayoutY(targetY - handHeight / 2);
    }

    public void runMoveAnimation(List<MoveStep> history, Runnable onFinishedCallback) {
        animateMove(history, onFinishedCallback);
    }
}
