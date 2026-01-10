package com.code.model.entity.square;

import com.code.model.enums.MoveDecision;
import com.code.config.GameConstants;

public abstract class Square {
    protected final int id;
    protected int stones;
    protected final boolean movable;

    public static Square createCitizen(int id) {
        return new CitizenSquare(id, GameConstants.INITIAL_CITIZEN_STONES);
    }

    public static Square createMandarin(int id) {
        return new MandarinSquare(id, GameConstants.INITIAL_MANDARIN_STONES);
    }

    protected Square(int id, int stones, boolean movable) {
        this.id = id;
        this.stones = stones;
        this.movable = movable;
    }

    public int getId() {
        return id;
    }

    public int getStones() {
        return stones;
    }

    public void addStones(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative stones");
        }
        this.stones += amount;
    }

    public int pickUpStones() {
        int temp = this.stones;
        this.stones = 0;
        return temp;
    }

    public boolean isEmpty() {
        return this.stones == 0;
    }

    public MoveDecision decideMove() {
        if (!movable) {
            return MoveDecision.STOP_AT_MANDARIN;
        }
        return isEmpty() ? MoveDecision.STOP_AT_EMPTY : MoveDecision.CONTINUE;
    }

    public abstract int getScoreValue();
}
