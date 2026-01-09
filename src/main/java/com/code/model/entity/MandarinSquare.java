package com.code.model.entity;

public class MandarinSquare extends Square {
    private static final int MANDARIN_VALUE = 5;

    public MandarinSquare(int id, int stones) {
        super(id, stones, false);
    }

    @Override
    public int getScoreValue() {
        return MANDARIN_VALUE;
    }
}