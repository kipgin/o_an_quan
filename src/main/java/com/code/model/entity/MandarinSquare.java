package com.code.model.entity;
import com.code.model.domain.MoveDecision;
public class MandarinSquare extends Square {
    private static final int MANDARIN_VALUE = 5;

    protected MandarinSquare(int id, int stones) {
        super(id, stones, false);
    }

    @Override
    public MoveDecision decideMove() {
        return MoveDecision.STOP_AT_MANDARIN;
    }

    @Override
    public int getScoreValue() {
        return MANDARIN_VALUE;
    }
}