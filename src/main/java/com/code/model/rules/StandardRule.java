package com.code.model.rules;

import com.code.model.entity.Board;
import com.code.model.entity.Player;
import com.code.model.enums.PlayerSide;
import com.code.config.GameConstants;

public class StandardRule implements GameRule {

    @Override
    public boolean isValidMove(Board board, int squareId, Player player) {
        if (player.getSide() == PlayerSide.BOTTOM_SIDE
                && (squareId < GameConstants.P1_START_INDEX || squareId > GameConstants.P1_END_INDEX))
            return false;
        if (player.getSide() == PlayerSide.TOP_SIDE
                && (squareId < GameConstants.P2_START_INDEX || squareId > GameConstants.P2_END_INDEX))
            return false;

        return board.getSquare(squareId).canBeMoved();
    }

    @Override
    public boolean isGameOver(Board board) {
        return board.areMandarinsEmpty();
    }
}