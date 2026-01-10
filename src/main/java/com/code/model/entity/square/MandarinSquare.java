package com.code.model.entity.square;

import com.code.config.GameConstants;

public class MandarinSquare extends Square {

    protected MandarinSquare(int id, int stones) {
        super(id, stones, false); 
    }

    @Override
    public int getScoreValue() {
        return GameConstants.MANDARIN_VALUE; 
    }
}
