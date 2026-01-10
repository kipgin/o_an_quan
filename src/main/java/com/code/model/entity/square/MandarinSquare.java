package com.code.model.entity.square;

import com.code.config.GameConstants;

public class MandarinSquare extends Square {

    private boolean mandarinCaptured = false;

    protected MandarinSquare(int id, int stones) {
        super(id, stones, false);
    }

    @Override
    public int pickUpStones() {
        int result = super.pickUpStones();
        if (this.stones == 0) {
            mandarinCaptured = true; 
        }
        return result;
    }

    public boolean isMandarinCaptured() {
        return mandarinCaptured;
    }
}
