package com.code.controller.input;

import com.code.config.GameConstants;
import com.code.controller.board.BoardUIService;
import com.code.controller.managers.HandCursorManager;
import com.code.model.game.OAnQuanGame;
import javafx.scene.control.Alert;

public class GameInputHandler {
    private final OAnQuanGame gameModel;
    private final BoardUIService boardUIService;
    private final HandCursorManager handCursorManager;
    private final Runnable onMoveExecuted;

    private Integer selectedSquareId = null;

    public GameInputHandler(OAnQuanGame gameModel, BoardUIService boardUIService,
            HandCursorManager handCursorManager, Runnable onMoveExecuted) {
        this.gameModel = gameModel;
        this.boardUIService = boardUIService;
        this.handCursorManager = handCursorManager;
        this.onMoveExecuted = onMoveExecuted;
    }

    public Integer getSelectedSquareId() {
        return selectedSquareId;
    }

    public void setSelectedSquareId(Integer id) {
        this.selectedSquareId = id;
    }

    public void handleSquareClick(int squareId) {
        if (gameModel.isGameOver())
            return;

        if (selectedSquareId != null) {
            boardUIService.showArrows(selectedSquareId, false);
        }

        handCursorManager.setHandClosed();

        if (isValidSelection(squareId)) {
            selectedSquareId = squareId;
            boardUIService.showArrows(squareId, true);
        } else {
            selectedSquareId = null;
        }
    }

    public void handleDirectionSelection(boolean isRightDirection) {
        if (selectedSquareId == null)
            return;

        boardUIService.showArrows(selectedSquareId, false);

        boolean isClockwise;
        int side = gameModel.getCurrentPlayer().getSide().ordinal() + 1;

        if (side == GameConstants.SIDE_BOTTOM) {
            isClockwise = isRightDirection;
        } else {
            isClockwise = !isRightDirection;
        }

        performMove(selectedSquareId, isClockwise);
        selectedSquareId = null;
    }

    private void performMove(int squareId, boolean isClockwise) {
        boolean success = gameModel.play(squareId, isClockwise);

        if (success) {
            if (onMoveExecuted != null) {
                onMoveExecuted.run();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Nước đi lỗi!").show();
        }
    }

    public boolean isValidSelection(int squareId) {
        if (gameModel.getSquareStones(squareId) == 0) {
            return false;
        }

        return gameModel.isCurrentPlayerOwnsSquare(squareId);
    }
}
