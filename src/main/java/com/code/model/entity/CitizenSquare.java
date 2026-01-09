package com.code.model.entity;

public class CitizenSquare extends Square {
    public CitizenSquare(int id, int stones) {
        super(id, stones, true);
    }

    @Override
    public int getScoreValue() {
        return 0;
    }
}