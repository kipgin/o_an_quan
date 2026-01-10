package com.code.model.game;

public class MoveStep {
    private final int squareId;
    private final int stones;

    public MoveStep(int squareId, int stones) {
        this.squareId = squareId;
        this.stones = stones;
    }

    public int getSquareId() {
        return squareId;
    }

    public int getStones() {
        return stones;
    }
}