package com.code.model.entity.player;

import com.code.model.enums.PlayerSide;

public abstract class Player {
    protected final String name;
    protected int score;
    protected final PlayerSide side;

    protected Player(String name, PlayerSide side) {
        this.name = name;
        this.score = 0;
        this.side = side;
    }

    public void earnScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points must be non-negative");
        }
        this.score += points;
    }

    public void minusScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points must be non-negative");
        }
        this.score = Math.max(0, this.score - points);
    }
    
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public PlayerSide getSide() {
        return side;
    }
}
