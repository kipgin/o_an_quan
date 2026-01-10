package com.code.model.entity.player;

import com.code.config.GameConstants;
import com.code.model.enums.PlayerSide;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, PlayerSide side) {
        super(name, side);
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
}
