package com.code.model.entity;

import com.code.config.GameConstants;
import com.code.model.enums.Direction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<Square> squares;

    public Board() {
        squares = new ArrayList<>();
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < GameConstants.TOTAL_SQUARES; i++) {
            if (i == GameConstants.MANDARIN_BOX_1 || i == GameConstants.MANDARIN_BOX_2) {
                squares.add(new MandarinSquare(i, GameConstants.INITIAL_MANDARIN_STONES));
            } else {
                squares.add(new CitizenSquare(i, GameConstants.INITIAL_CITIZEN_STONES));
            }
        }
    }

    public Square getSquare(int id) {
        if (id < 0 || id >= GameConstants.TOTAL_SQUARES)
            return null;
        return squares.get(id);
    }

    public int getNextIndex(int currentIndex, Direction direction) {
        if (direction == Direction.CLOCKWISE) {
            return (currentIndex + 1) % GameConstants.TOTAL_SQUARES;
        } else {
            return (currentIndex - 1 + GameConstants.TOTAL_SQUARES) % GameConstants.TOTAL_SQUARES;
        }
    }

    public boolean areMandarinsEmpty() {
        return squares.get(GameConstants.MANDARIN_BOX_1).isEmpty() &&
                squares.get(GameConstants.MANDARIN_BOX_2).isEmpty();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }
}