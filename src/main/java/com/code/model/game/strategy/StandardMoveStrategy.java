package com.code.model.game.strategy;

import com.code.model.domain.CaptureChain;
import com.code.model.domain.MoveOutcome;
import com.code.model.domain.SquareId;
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

        SquareId startId = SquareId.of(startSquareId);

        MoveOutcome outcome = board.executeMove(startId, direction);
        moveHistory.addAll(outcome.getSteps());

        if (moveHistory.isEmpty())
            return;
        MoveStep lastStep = moveHistory.get(moveHistory.size() - 1);
        SquareId lastSquareId = SquareId.of(lastStep.getSquareId());

        SquareId nextId = lastSquareId.next(direction);
        Square nextSq = board.getSquare(nextId);

        if (nextSq.isEmpty()) {
            CaptureChain chain = board.executeCaptureChain(nextId, direction);
            if (chain.getTotalPoints() > 0) {
                currentPlayer.earnScore(chain.getTotalPoints());
                moveHistory.addAll(chain.getSteps());
            }
        }

    }
}
