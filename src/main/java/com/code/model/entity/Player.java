package com.code.model.entity;

import com.code.model.enums.PlayerSide;

public class Player {
    private final String name;
    private int score;
    private final PlayerSide side;

    public Player(String name, PlayerSide side) {
        this.name = name;
        this.score = 0;
        this.side = side;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void minusScore(int points) {
        this.score -= points;
    }

    // Getters
    public String getName() { return name; }
    public int getScore() { return score; }
    public PlayerSide getSide() { return side; }
}