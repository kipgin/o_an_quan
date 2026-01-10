package com.code.controller.animation;

import com.code.config.GameConstants;
import com.code.controller.board.BoardUIService;
import com.code.model.game.MoveStep;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.geometry.Point2D;

import java.util.List;

public class AnimationService {

    private final ImageView handCursor;
    private final BoardUIService boardUIService;
    private boolean isAnimating = false;
    private Image imgHandOpen;
    private Image imgHandClosed;

    public AnimationService(ImageView handCursor, BoardUIService boardUIService) {
        this.handCursor = handCursor;
        this.boardUIService = boardUIService;
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
                boardUIService.setStones(step.getSquareId(), step.getStones());
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
        Parent squareNode = boardUIService.getSquareRootNode(squareId);
        if (squareNode == null)
            return;

        Point2D point = squareNode.localToScene(0.0, 0.0);

        double targetX = point.getX() + squareNode.getBoundsInLocal().getWidth() / 2;
        double targetY = point.getY() + squareNode.getBoundsInLocal().getHeight() / 2;

        double handWidth = handCursor.getFitWidth() > 0 ? handCursor.getFitWidth() : handCursor.getImage().getWidth();
        double handHeight = handCursor.getFitHeight() > 0 ? handCursor.getFitHeight()
                : handCursor.getImage().getHeight();

        handCursor.setLayoutX(targetX - handWidth / 2);
        handCursor.setLayoutY(targetY - handHeight / 2);
    }

    public void runMoveAnimation(List<MoveStep> history, Runnable onFinishedCallback) {
        animateMove(history, onFinishedCallback);
    }
}
