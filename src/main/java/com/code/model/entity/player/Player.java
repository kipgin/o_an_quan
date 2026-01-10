package com.code.model.entity.player;

import com.code.model.enums.PlayerSide;
import com.code.config.GameConstants;

public abstract class Player {
    private final String name;
    private int score;
    private final PlayerSide side;

    protected Player(String name, PlayerSide side) {
        this.name = name;
        this.score = 0;
        this.side = side;
    }

    public void earnScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points must be non-negative");
        }
        this.score += points;
    }

    public void minusScore(int points) {
        this.score -= points;
    }

    public boolean canBorrowStones() {
        return score >= GameConstants.SCORE_TO_BORROW;
    }

    public void borrowStones(int amount) {
        if (amount != GameConstants.BORROW_AMOUNT) {
            throw new IllegalArgumentException("Invalid borrow amount");
        }
        this.score -= amount;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public PlayerSide getSide() {
        return side;
    }
}
