package com.code.model.game;

public class MoveStep {
    public int squareId;
    public int stones; 

    public MoveStep(int squareId, int stones) {
        this.squareId = squareId;
        this.stones = stones;
    }
}