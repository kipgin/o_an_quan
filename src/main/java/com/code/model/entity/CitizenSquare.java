package com.code.model.entity;
import com.code.model.domain.MoveDecision;
public class CitizenSquare extends Square {
    protected CitizenSquare(int id, int stones) {
        super(id, stones, true);
    }

    @Override
    public MoveDecision decideMove() {
        return isEmpty()
                ? MoveDecision.STOP_AT_EMPTY
                : MoveDecision.CONTINUE;
    }

    @Override
    public int getScoreValue() {
        return 0;
    }
}