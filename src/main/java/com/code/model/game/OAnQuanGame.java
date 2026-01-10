package com.code.model.game;

import com.code.model.entity.Board;
import com.code.model.entity.player.Player;
import com.code.model.entity.square.Square;
import com.code.model.entity.player.HumanPlayer;
import com.code.model.enums.Direction;
import com.code.model.enums.PlayerSide;
import com.code.model.rules.GameRule;
import com.code.model.rules.StandardRule;
import com.code.config.GameConstants;
import java.util.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        this(new HumanPlayer("Player 1", PlayerSide.BOTTOM_SIDE),
                new HumanPlayer("Player 2", PlayerSide.TOP_SIDE),
                new StandardRule());
    }

    public OAnQuanGame(Player player1, Player player2, GameRule rule) {
        this.rule = rule;
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
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
        MoveResult result = executeCompleteMove(squareId, direction);

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
    
    public MoveResult executeCompleteMove(int startId, Direction direction) {
        List<MoveStep> allSteps = new ArrayList<>();

        List<MoveStep> moveSteps = executeMove(startId, direction);
        allSteps.addAll(moveSteps);

        int totalScore = 0;
        if (!moveSteps.isEmpty()) {
            MoveStep lastStep = moveSteps.get(moveSteps.size() - 1);
            int lastSquareId = lastStep.getSquareId();

            int nextId = board.getNextId(lastSquareId, direction);
            Square nextSquare = board.getSquare(nextId);

            if (nextSquare.isEmpty() && nextSquare.isMovable()) {
                List<MoveStep> captureSteps = new ArrayList<>();
                totalScore = executeCaptureChain(nextId, direction, captureSteps);
                allSteps.addAll(captureSteps);
            }
        }

        return new MoveResult(allSteps, totalScore);
    }

    private List<MoveStep> executeMove(int startId, Direction direction) {
        List<MoveStep> steps = new ArrayList<>();

        Square startSquare = board.getSquare(startId);
        int hand = startSquare.pickUpStones();
        steps.add(new MoveStep(startId, 0));

        int currentId = startId;

        while (hand > 0) {
            currentId = board.getNextId(currentId, direction);
            Square currentSquare = board.getSquare(currentId);

            currentSquare.addStones(1);
            hand--;

            steps.add(new MoveStep(currentId, currentSquare.getStones()));

            if (hand == 0) {
                int nextId = board.getNextId(currentId, direction);
                Square nextSquare = board.getSquare(nextId);

                if (!nextSquare.isMovable()) {
                    break;
                }

                if (!nextSquare.isEmpty()) {
                    hand = nextSquare.pickUpStones();
                    currentId = nextId;
                    steps.add(new MoveStep(currentId, 0));
                } else {
                    break;
                }
            }
        }

        return steps;
    }

    private int executeCaptureChain(int emptySquareId, Direction direction, List<MoveStep> captureSteps) {
        int totalPoints = 0;
        int emptyIdx = emptySquareId;

        while (true) {
            int targetIdx = board.getNextId(emptyIdx, direction);
            Square targetSquare = board.getSquare(targetIdx);

            if (targetSquare.isEmpty()) {
                break;
            }

            int captured = targetSquare.pickUpStones();
            totalPoints += captured;
            captureSteps.add(new MoveStep(targetIdx, 0));

            int checkIdx = board.getNextId(targetIdx, direction);
            Square checkSquare = board.getSquare(checkIdx);

            if (!checkSquare.isMovable()) {
                break;
            }

            if (!checkSquare.isEmpty()) {
                break;
            }

            emptyIdx = checkIdx;
        }

        return totalPoints;
    }
    
    private void checkAndRefillEmptySquares() {
        if (board.isPlayerRegionEmpty(currentPlayer.getSide())) {
            int borrowAmount = GameConstants.BORROW_AMOUNT;
            currentPlayer.minusScore(borrowAmount);
            board.refillPlayerRegion(currentPlayer.getSide());
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

    

    public boolean canSelectSquare(int squareId) {
        if (isGameOver)
            return false;
        if (!rule.isValidMove(board, squareId, currentPlayer)) return false;
        return true;

    }

    public Map<Integer, Integer> getBoardSnapshot() {
        Map<Integer, Integer> snapshot = new HashMap<>();
        for (int i = 0; i < GameConstants.TOTAL_SQUARES; i++) {
            snapshot.put(i, board.getSquareStones(i));
        }
        return snapshot;
    }

    public boolean isCurrentPlayerOwnsSquare(int squareId) {
        if (currentPlayer.getSide() == PlayerSide.BOTTOM_SIDE) {
            return squareId >= GameConstants.P1_START_INDEX && squareId <= GameConstants.P1_END_INDEX;
        } else {
            return squareId >= GameConstants.P2_START_INDEX && squareId <= GameConstants.P2_END_INDEX;
        }
    }
}