package com.code.model.entity;

import com.code.config.GameConstants;
import com.code.model.domain.*;
import com.code.model.enums.Direction;
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

    public Square getSquare(SquareId id) {
        return squares.get(id.getValue());
    }

    public Square getSquare(int id) {
        if (id < 0 || id >= GameConstants.TOTAL_SQUARES)
            return null;
        return squares.get(id);
    }

    public SquareId getNextId(SquareId currentId, Direction direction) {
        return currentId.next(direction);
    }

    public MoveOutcome executeMove(SquareId startId, Direction direction) {
        MoveOutcome outcome = new MoveOutcome();

        Square startSquare = getSquare(startId);
        int hand = startSquare.pickUpStones();

        SquareId currentId = startId;

        while (hand > 0) {
            currentId = currentId.next(direction);
            Square currentSquare = getSquare(currentId);

            currentSquare.addStones(1);
            hand--;

            outcome.addStep(currentId.getValue(), currentSquare.getStones());

            if (hand == 0) {
                SquareId nextId = currentId.next(direction);
                Square nextSquare = getSquare(nextId);

                MoveDecision decision = nextSquare.decideMove();

                if (decision == MoveDecision.CONTINUE) {
                    hand = nextSquare.pickUpStones();
                    currentId = nextId;
                    outcome.addStep(currentId.getValue(), 0);
                }

            }
        }
        return outcome;
    }

    public CaptureChain executeCaptureChain(SquareId startEmptyId, Direction direction) {
        CaptureChain chain = new CaptureChain();
        SquareId currentEmptyId = startEmptyId;

        while (true) {
            SquareId targetId = currentEmptyId.next(direction);
            Square targetSq = getSquare(targetId);

            CaptureResult result = targetSq.capture();

            if (result.isSuccess()) {
                chain.addCapture(targetId, result.getPoints());

                SquareId nextOfTarget = targetId.next(direction);
                if (getSquare(nextOfTarget).isEmpty()) {
                    currentEmptyId = nextOfTarget;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return chain;
    }

    public int getNextIndex(int currentIndex, Direction direction) {
        return SquareId.of(currentIndex).next(direction).getValue();
    }

    public boolean areMandarinsEmpty() {
        return squares.get(GameConstants.MANDARIN_BOX_1).isEmpty() &&
                squares.get(GameConstants.MANDARIN_BOX_2).isEmpty();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }

    public void distributeStone(SquareId id) {
        getSquare(id).addStones(1);
    }

    public int collectStones(int startId, int endId) {
        int total = 0;
        for (int i = startId; i <= endId; i++) {
            total += getSquare(SquareId.of(i)).pickUpStones();
        }
        return total;
    }

    public boolean isRegionEmpty(int startId, int endId) {
        for (int i = startId; i <= endId; i++) {
            if (!getSquare(SquareId.of(i)).isEmpty())
                return false;
        }
        return true;
    }

    public void distributeStonesToRegion(int startId, int endId) {
        for (int i = startId; i <= endId; i++) {
            getSquare(SquareId.of(i)).addStones(1);
        }
    }
}