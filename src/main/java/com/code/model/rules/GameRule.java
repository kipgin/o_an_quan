package com.code.model.rules;

import com.code.model.entity.Board;
import com.code.model.entity.player.Player;

public interface GameRule {
    boolean isValidMove(Board board, int squareId, Player player);
    boolean isGameOver(Board board);
}