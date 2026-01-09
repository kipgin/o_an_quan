package com.code.model.game.strategy;

import com.code.model.entity.Board;
import com.code.model.entity.Player;
import com.code.model.enums.Direction;
import com.code.model.game.MoveStep;

import java.util.List;

public interface MoveStrategy {
    /**
     * Executes the move logic and populates history.
     */
    void performMove(Board board, int startSquareId, Direction direction, Player currentPlayer,
            List<MoveStep> moveHistory);
}
