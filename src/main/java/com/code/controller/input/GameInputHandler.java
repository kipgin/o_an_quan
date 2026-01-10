package com.code.controller.input;

import com.code.config.GameConstants;
import com.code.controller.board.BoardUIService;
import com.code.model.game.OAnQuanGame;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class GameInputHandler {
    private final OAnQuanGame gameModel;
    private final BoardUIService boardUIService;
    private final Runnable onMoveExecuted;

    private Integer selectedSquareId = null;
    private boolean locked = false;

    public GameInputHandler(OAnQuanGame gameModel, BoardUIService boardUIService, Runnable onMoveExecuted) {
        this.gameModel = gameModel;
        this.boardUIService = boardUIService;
        this.onMoveExecuted = onMoveExecuted;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        if (locked) {
            selectedSquareId = null;
            boardUIService.clearArrows();
        }
    }

    public void handleSquareClick(int squareId) {
        if (locked || !isValidSelection(squareId)) {
            return;
        }

        selectedSquareId = squareId;
        boardUIService.showArrows(squareId, true);
    }

    public void handleDirectionSelection(boolean isRightArrow) {
        if (selectedSquareId == null) {
            return;
        }

        boolean clockwise = resolveDirection(selectedSquareId, isRightArrow);
        boolean success = gameModel.play(selectedSquareId, clockwise);

        if (success) {
            boardUIService.showArrows(selectedSquareId, false);
            onMoveExecuted.run();
            selectedSquareId = null;
        }
    }

    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            if (selectedSquareId != null) {
                handleDirectionSelection(false);
            }
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (selectedSquareId != null) {
                handleDirectionSelection(true);
            }
        }
    }

    private boolean isValidSelection(int squareId) {
        if (squareId == GameConstants.MANDARIN_BOX_1 || squareId == GameConstants.MANDARIN_BOX_2) {
            return false;
        }

        if (gameModel.getSquareStones(squareId) == 0) {
            return false;
        }

        return gameModel.isCurrentPlayerOwnsSquare(squareId);
    }

    private boolean resolveDirection(int squareId, boolean isRightArrow) {
        boolean isPlayer2Square = squareId >= GameConstants.P2_START_INDEX && squareId <= GameConstants.P2_END_INDEX;
        return isPlayer2Square ? !isRightArrow : isRightArrow;
    }
}
