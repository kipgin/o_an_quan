package com.code.model.entity;

public abstract class Square {
    protected final int id;
    protected int stones;
    protected final boolean movable;

    public Square(int id, int stones, boolean movable) {
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

    public boolean canBeMoved() {
        return movable && !isEmpty();
    }

    public abstract int getScoreValue();
}
