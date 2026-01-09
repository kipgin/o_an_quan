package com.code.model.game.strategy;

import com.code.model.entity.Board;
import com.code.model.entity.Player;
import com.code.model.entity.Square;
import com.code.model.enums.Direction;
import com.code.model.game.MoveStep;

import java.util.List;

public class StandardMoveStrategy implements MoveStrategy {

    @Override
    public void performMove(Board board, int startSquareId, Direction direction, Player currentPlayer,
            List<MoveStep> moveHistory) {
        int currentId = startSquareId;
        Square currentSq = board.getSquare(currentId);
        int hand = currentSq.pickUpStones();

        while (hand > 0) {
            currentId = board.getNextIndex(currentId, direction);
            board.getSquare(currentId).addStones(1);
            hand--;

            moveHistory.add(new MoveStep(currentId, board.getSquare(currentId).getStones()));

            if (hand == 0) {
                int nextId = board.getNextIndex(currentId, direction);
                Square nextSq = board.getSquare(nextId);

                if (nextSq.canBeMoved()) {
                    hand = nextSq.pickUpStones();
                    currentId = nextId;
                    moveHistory.add(new MoveStep(currentId, 0));
                } else if (nextSq.isEmpty()) {
                    handleCapture(board, nextId, direction, currentPlayer, moveHistory);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    private void handleCapture(Board board, int emptySquareId, Direction direction, Player currentPlayer,
            List<MoveStep> moveHistory) {
        int currentEmptyId = emptySquareId;

        while (true) {
            int targetId = board.getNextIndex(currentEmptyId, direction);
            Square targetSq = board.getSquare(targetId);

            if (!targetSq.isEmpty()) {
                int points = targetSq.pickUpStones() + targetSq.getScoreValue();
                currentPlayer.addScore(points);

                moveHistory.add(new MoveStep(targetId, 0));

                int nextOfTarget = board.getNextIndex(targetId, direction);
                if (board.getSquare(nextOfTarget).isEmpty()) {
                    currentEmptyId = nextOfTarget;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }
}
