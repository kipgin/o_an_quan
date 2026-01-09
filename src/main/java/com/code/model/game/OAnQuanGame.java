package com.code.model.game;

import com.code.model.entity.Board;
import com.code.model.entity.Player;
import com.code.model.entity.Square;
import com.code.model.enums.Direction;
import com.code.model.enums.PlayerSide;
import com.code.model.rules.GameRule;
import com.code.model.rules.StandardRule;
import com.code.model.game.strategy.MoveStrategy;
import com.code.model.game.strategy.StandardMoveStrategy;

import java.util.ArrayList;
import java.util.List;

public class OAnQuanGame {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameRule rule;
    private boolean isGameOver;

    private List<MoveStep> moveHistory;

    public OAnQuanGame() {
        initGame();
    }

    public void initGame() {
        this.board = new Board();
        this.player1 = new Player("Player 1", PlayerSide.BOTTOM_SIDE);
        this.player2 = new Player("Player 2", PlayerSide.TOP_SIDE);
        this.currentPlayer = player1;
        this.rule = new StandardRule();
        this.isGameOver = false;
        this.moveHistory = new ArrayList<>();
    }

    public boolean play(int squareId, boolean isClockwise) {
        if (isGameOver)
            return false;

        if (!rule.isValidMove(board, squareId, currentPlayer)) {
            return false;
        }

        moveHistory.clear();

        Direction direction = Direction.fromBoolean(isClockwise);

        performMoveLogic(squareId, direction);

        if (rule.isGameOver(board)) {
            isGameOver = true;
            calculateFinalScore();
        } else {
            switchTurn();
            checkAndRefillEmptySquares();
        }

        return true;
    }

    private void performMoveLogic(int startId, Direction direction) {
        int currentId = startId;
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
                    handleCapture(nextId, direction);
                    break;
                }

                else {
                    break;
                }
            }
        }
    }

    private void handleCapture(int emptySquareId, Direction direction) {
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

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void checkAndRefillEmptySquares() {
        boolean allEmpty = true;
        int start = (currentPlayer.getSide() == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_START_INDEX
                : GameConstants.P2_START_INDEX;
        int end = (currentPlayer.getSide() == PlayerSide.BOTTOM_SIDE) ? GameConstants.P1_END_INDEX
                : GameConstants.P2_END_INDEX;

        for (int i = start; i <= end; i++) {
            if (!board.getSquare(i).isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        if (allEmpty) {

            if (currentPlayer.getScore() >= GameConstants.SCORE_TO_BORROW) {
                currentPlayer.minusScore(GameConstants.BORROW_AMOUNT);
                for (int i = start; i <= end; i++) {
                    board.getSquare(i).addStones(1);
                }
            } else {

                currentPlayer.minusScore(GameConstants.BORROW_AMOUNT);
                for (int i = start; i <= end; i++) {
                    board.getSquare(i).addStones(1);
                }
            }
        }
    }

    private void calculateFinalScore() {

        for (int i = GameConstants.P1_START_INDEX; i <= GameConstants.P1_END_INDEX; i++) {
            player1.addScore(board.getSquare(i).pickUpStones());
        }
        for (int i = GameConstants.P2_START_INDEX; i <= GameConstants.P2_END_INDEX; i++) {
            player2.addScore(board.getSquare(i).pickUpStones());
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public List<MoveStep> getLastMoveHistory() {
        return moveHistory;
    }
}