package com.code.model.entity;

import com.code.config.GameConstants;
import com.code.model.domain.Score;
import com.code.model.enums.PlayerSide;

public class Player {
    private final String name;
    private Score score;
    private final PlayerSide side;

    public Player(String name, PlayerSide side) {
        this.name = name;
        this.score = Score.of(0);
        this.side = side;
    }

    public void earnScore(int points) {
        this.score = this.score.add(points);
    }

    public void addScore(int points) {
        earnScore(points);
    }

    public void minusScore(int points) {
        this.score = this.score.subtract(points);
    }

    public boolean canBorrowStones() {
        return score.isGreaterThanOrEqual(GameConstants.SCORE_TO_BORROW);
    }

    public void borrowStones(int amount) {
        if (amount != GameConstants.BORROW_AMOUNT) {
            throw new IllegalArgumentException("Invalid borrow amount");
        }
        this.score = this.score.subtract(amount);
    }

    public int getScore() {
        return score.getValue();
    }

    public PlayerSide getSide() {
        return side;
    }

    public String getName() {
        return name;
    }
}