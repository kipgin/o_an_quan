package com.code.model.game;

import com.code.model.entity.Board;
import com.code.model.entity.player.Player;
import com.code.model.entity.player.HumanPlayer;
import com.code.model.enums.Direction;
import com.code.model.enums.PlayerSide;
import com.code.model.rules.GameRule;
import com.code.model.rules.StandardRule;
import com.code.config.GameConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OAnQuanGame {
    private final Board board;
    private final Player player1; 
    private final Player player2; 
    private Player currentPlayer;
    private final GameRule rule;
    private boolean isGameOver;
    private List<MoveStep> moveHistory;

    public OAnQuanGame() {
        this(new StandardRule());
    }

    public OAnQuanGame(GameRule rule) {
        this.rule = rule;
        this.board = new Board();
        this.player1 = new HumanPlayer("Player 1", PlayerSide.BOTTOM_SIDE);
        this.player2 = new HumanPlayer("Player 2", PlayerSide.TOP_SIDE);
        this.currentPlayer = player1;
        this.isGameOver = false;
        this.moveHistory = new ArrayList<>();
    }

    public boolean play(int squareId, boolean isClockwise) {
        if (isGameOver) {
            return false;
        }

        if (!rule.isValidMove(board, squareId, currentPlayer)) {
            return false;
        }

        moveHistory.clear();
        Direction direction = Direction.fromBoolean(isClockwise);
        MoveResult result = board.executeCompleteMove(squareId, direction);

        moveHistory.addAll(result.getAllSteps());
        currentPlayer.earnScore(result.getTotalScore());

        if (rule.isGameOver(board)) {
            isGameOver = true;
            calculateFinalScore();
        } else {
            switchTurn();
            checkAndRefillEmptySquares();
        }
        return true;
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void checkAndRefillEmptySquares() {
        int start, end;
        if (currentPlayer.getSide() == PlayerSide.BOTTOM_SIDE) {
            start = GameConstants.P1_START_INDEX;
            end = GameConstants.P1_END_INDEX;
        } else {
            start = GameConstants.P2_START_INDEX;
            end = GameConstants.P2_END_INDEX;
        }

        if (board.isRegionEmpty(start, end)) {
            if (currentPlayer instanceof HumanPlayer) {
                HumanPlayer human = (HumanPlayer) currentPlayer;
                if (human.canBorrowStones()) {
                    human.borrowStones(GameConstants.BORROW_AMOUNT);
                    board.distributeStonesToRegion(start, end);
                }
            }
            // mo rong cho AIPlayer
        }
    }

    private void calculateFinalScore() {
        int p1Points = board.collectStones(GameConstants.P1_START_INDEX, GameConstants.P1_END_INDEX);
        player1.earnScore(p1Points);
        int p2Points = board.collectStones(GameConstants.P2_START_INDEX, GameConstants.P2_END_INDEX);
        player2.earnScore(p2Points);
    }

    public void forceTimeoutSwitchTurn() {
        switchTurn();
        checkAndRefillEmptySquares();
    }

    public int getPlayer1Score() {
        return player1.getScore();
    }

    public int getPlayer2Score() {
        return player2.getScore();
    }

    public String getPlayer1Name() {
        return player1.getName();
    }

    public String getPlayer2Name() {
        return player2.getName();
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    public boolean isPlayer1Turn() {
        return currentPlayer == player1;
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
        return Collections.unmodifiableList(moveHistory);
    }

    public int getSquareStones(int squareId) {
        return board.getSquare(squareId).getStones();
    }

    public boolean isCurrentPlayerOwnsSquare(int squareId) {
        if (currentPlayer.getSide() == PlayerSide.BOTTOM_SIDE) {
            return squareId >= GameConstants.P1_START_INDEX && squareId <= GameConstants.P1_END_INDEX;
        } else {
            return squareId >= GameConstants.P2_START_INDEX && squareId <= GameConstants.P2_END_INDEX;
        }
    }
}