package com.code.model.domain;

public class Score {
    private final int value;

    private Score(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.value = value;
    }

    public static Score of(int value) {
        return new Score(value);
    }

    public int getValue() {
        return value;
    }

    public Score add(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to add must be non-negative");
        }
        return new Score(this.value + points);
    }

    public Score subtract(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to subtract must be non-negative");
        }
        return new Score(Math.max(0, this.value - points));
    }

    public boolean isGreaterThanOrEqual(int threshold) {
        return this.value >= threshold;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
