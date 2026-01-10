package com.code.model.entity.player;

import com.code.config.GameConstants;
import com.code.model.enums.PlayerSide;

public class AIPlayer extends Player {
    private final int difficulty;

    public AIPlayer(String name, PlayerSide side, int difficulty) {
        super(name, side);
        this.difficulty = difficulty;
    }

    public boolean canBorrowStones() {
        return score >= GameConstants.SCORE_TO_BORROW;
    }

    public void borrowStones(int amount) {
        if (amount != GameConstants.BORROW_AMOUNT) {
            throw new IllegalArgumentException("Invalid borrow amount");
        }
        this.score = Math.max(0, this.score - amount);
    }

    public int getDifficulty() {
        return difficulty;
    }

    // public int selectBestMove(Board board) { ... }
}
