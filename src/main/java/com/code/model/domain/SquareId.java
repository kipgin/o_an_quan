package com.code.model.domain;

import com.code.config.GameConstants;
import com.code.model.enums.Direction;

public class SquareId {
    private final int value;

    private SquareId(int value) {
        if (value < 0 || value >= GameConstants.TOTAL_SQUARES) {
            throw new IllegalArgumentException("Invalid square ID: " + value);
        }
        this.value = value;
    }

    public static SquareId of(int value) {
        return new SquareId(value);
    }

    public int getValue() {
        return value;
    }

    public SquareId next(Direction direction) {
        int nextValue = direction == Direction.CLOCKWISE
                ? (value + 1) % GameConstants.TOTAL_SQUARES
                : (value - 1 + GameConstants.TOTAL_SQUARES) % GameConstants.TOTAL_SQUARES;
        return new SquareId(nextValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SquareId squareId = (SquareId) o;
        return value == squareId.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
