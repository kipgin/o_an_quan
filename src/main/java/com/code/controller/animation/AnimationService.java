package com.code.controller.animation;

import com.code.config.GameConstants;
import com.code.controller.board.BoardUIService;
import com.code.controller.managers.HandCursorManager;
import com.code.model.game.MoveStep;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.geometry.Point2D;

import java.util.List;

public class AnimationService {

    private final ImageView handCursor;
    private final BoardUIService boardUIService;
    private final HandCursorManager handCursorManager;
    private boolean isAnimating = false;

    public AnimationService(ImageView handCursor, BoardUIService boardUIService,
            HandCursorManager handCursorManager) {
        this.handCursor = handCursor;
        this.boardUIService = boardUIService;
        this.handCursorManager = handCursorManager;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void runMoveAnimation(List<MoveStep> history, Runnable onFinishedCallback) {
        if (isAnimating)
            return;
        isAnimating = true;

        // Use closed hand during animation
        handCursorManager.setHandClosed();

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
            handCursorManager.setHandOpen();

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
}
