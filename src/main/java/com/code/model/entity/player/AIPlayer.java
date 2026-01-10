package com.code.model.entity.player;

import com.code.config.GameConstants;
import com.code.model.enums.PlayerSide;

public class AIPlayer extends Player {
    private final int difficulty;

    public AIPlayer(String name, PlayerSide side, int difficulty) {
        super(name, side);
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    // public int selectBestMove(Board board) { ... }
}
