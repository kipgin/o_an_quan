package com.code.model.entity;

import com.code.config.GameConstants;
import com.code.model.entity.square.Square;
import com.code.model.enums.Direction;
import com.code.model.enums.PlayerSide;
import com.code.model.game.MoveStep;
import com.code.model.game.MoveResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final List<Square> squares;

    public Board() {
        squares = new ArrayList<>();
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < GameConstants.TOTAL_SQUARES; i++) {
            if (i == GameConstants.MANDARIN_BOX_1 || i == GameConstants.MANDARIN_BOX_2) {
                squares.add(Square.createMandarin(i));
            } else {
                squares.add(Square.createCitizen(i));
            }
        }
    }

    public Square getSquare(int id) {
        if (id < 0 || id >= GameConstants.TOTAL_SQUARES) {
            throw new IllegalArgumentException("Invalid square id: " + id);
        }
        return squares.get(id);
    }

    public int getNextId(int currentId, Direction direction) {
        if (direction == Direction.CLOCKWISE) {
            return (currentId + 1) % GameConstants.TOTAL_SQUARES;
        } else {
            return (currentId - 1 + GameConstants.TOTAL_SQUARES) % GameConstants.TOTAL_SQUARES;
        }
    }

    public boolean areMandarinsEmpty() {
        return squares.get(GameConstants.MANDARIN_BOX_1).isEmpty() &&
                squares.get(GameConstants.MANDARIN_BOX_2).isEmpty();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }

    public int collectStones(int startId, int endId) {
        int total = 0;
        for (int i = startId; i <= endId; i++) {
            total += getSquare(i).pickUpStones();
        }
        return total;
    }

    public boolean isRegionEmpty(int startId, int endId) {
        for (int i = startId; i <= endId; i++) {
            if (!getSquare(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void distributeStonesToRegion(int startId, int endId) {
        for (int i = startId; i <= endId; i++) {
            getSquare(i).addStones(1);
        }
    }

    public boolean isPlayerRegionEmpty(PlayerSide side) {
        int start = (side == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_START_INDEX : GameConstants.P2_START_INDEX;
        int end = (side == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_END_INDEX : GameConstants.P2_END_INDEX;
        return isRegionEmpty(start, end);
    }

    public void refillPlayerRegion(PlayerSide side) {
        int start = (side == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_START_INDEX : GameConstants.P2_START_INDEX;
        int end = (side == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_END_INDEX : GameConstants.P2_END_INDEX;
        distributeStonesToRegion(start, end);
    }
    public int getSquareStones(int squareId) {
        return getSquare(squareId).getStones();
    }
}