package com.code.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.code.config.GameConstants;
import com.code.util.managers.MenuManager;
import com.code.util.managers.GameTimerManager;
import com.code.util.managers.PlayerInfoManager;
import com.code.util.managers.GameControlManager;
// import com.code.util.managers.GameButtonManager;
import com.code.util.managers.MusicManager;
import com.code.util.ui.BoardUIService;
import com.code.util.ui.AnimationService;
import com.code.util.ui.GameInputHandler;
import com.code.model.game.OAnQuanGame;
import com.code.model.game.MoveStep;

public class GameController implements Initializable {

    @FXML
    private AnchorPane mainRoot;
    @FXML
    private Label lblScoreP1;
    @FXML
    private Label lblScoreP2;
    @FXML
    private GridPane gridBoard;
    @FXML
    private ImageView handCursor;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnMusic;
    @FXML
    private Button btnStop;
    @FXML
    private HBox boxPlayer1;
    @FXML
    private HBox boxPlayer2;
    @FXML
    private StackPane paneTimer;
    @FXML
    private Label lblTimer;
    @FXML
    private Label lblPlayerName1;
    @FXML
    private Label lblPlayerName2;

    private OAnQuanGame gameModel;
    private BoardUIService boardUIService;
    private AnimationService animationService;
    private GameInputHandler inputHandler;
    private MenuManager menuManager;
    private GameTimerManager gameTimerManager;
    private PlayerInfoManager playerInfoManager;
    private GameControlManager gameControlManager;
    // private GameButtonManager gameButtonManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupManagers();
        setupEventHandlers();
        updateGameUI();
    }

    private void setupManagers() {
        gameModel = new OAnQuanGame();
        boardUIService = new BoardUIService(gridBoard);
        handCursor.setVisible(false);

        menuManager = new MenuManager(btnMenu, this::handleBackToMenu);
        MusicManager.getInstance().attachMusicButton(btnMusic);

        animationService = new AnimationService(
                handCursor,
                boardUIService::getSquareRootNode,
                boardUIService::setStones);

        inputHandler = new GameInputHandler(gameModel, boardUIService, this::onMoveExecuted);

        playerInfoManager = new PlayerInfoManager(lblScoreP1, lblScoreP2, boxPlayer1, boxPlayer2,
                lblPlayerName1, lblPlayerName2);
        gameTimerManager = new GameTimerManager();

        gameControlManager = new GameControlManager(btnStop, gameTimerManager);

        lblTimer.textProperty().bind(gameTimerManager.timeStringProperty());

        boardUIService.setupBoardUI(
                (squareId) -> {
                    if (isInputAllowed()) {
                        inputHandler.handleSquareClick(squareId);
                        gridBoard.requestFocus();
                    }
                },
                (isRightArrow) -> {
                    if (isInputAllowed()) {
                        inputHandler.handleDirectionSelection(isRightArrow);
                    }
                });
    }

    private void setupEventHandlers() {
        gameTimerManager.setOnTimeout(this::handleTimeout);
        gameTimerManager.setOnTick(this::handleTimerTick);
        gameTimerManager.start();
        setupGlobalInputs();
    }

    private void setupGlobalInputs() {
        mainRoot.setOnKeyPressed(e -> {
            if (isInputAllowed())
                inputHandler.handleKeyPress(e);
        });
    }

    private boolean isInputAllowed() {
        return !animationService.isAnimating() && !gameTimerManager.isPaused();
    }

    private void updateGameUI() {
        boardUIService.updateBoardStones(gameModel.getBoardSnapshot());
        boardUIService.updateMandarinCapturedState(gameModel.getBoard());
        boardUIService.updateSquareHoverability(gameModel::canSelectSquare);
        playerInfoManager.updateScores(gameModel.getPlayer1(), gameModel.getPlayer2());
        playerInfoManager.updateActivePlayerHighlight(gameModel);
    }

    private void onMoveExecuted() {
        pauseTimerDuringAnimation();
        gameControlManager.setEnabled(false);

        List<MoveStep> moveHistory = gameModel.getLastMoveHistory();
        // checkGameOver();

        animationService.animateMove(moveHistory, () -> {
            updateGameUI();
            gameTimerManager.reset();
            gameControlManager.setEnabled(true);
            gameControlManager.resetToDefaultState();
            Platform.runLater(() -> checkGameOver());
        });
        // checkGameOver();
    }

    private void pauseTimerDuringAnimation() {
        if (!gameTimerManager.isPaused()) {
            gameTimerManager.pause();
        }
    }
    // private void resumeTimerAfterAnimation() {
    // if (gameTimerManager.isPaused()) {
    // gameTimerManager.resume();
    // }
    // }
    // private void ti

    private void checkGameOver() {
        boolean gameOver = gameModel.isGameOver();
        System.out.println("DEBUG: checkGameOver called, isGameOver = " + gameOver);

        if (gameOver) {
            System.out.println("DEBUG: Game is over! Showing dialog...");
            gameTimerManager.stop();
            gameControlManager.setEnabled(false);
            inputHandler.setLocked(true);
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        int scoreP1 = gameModel.getPlayer1Score();
        int scoreP2 = gameModel.getPlayer2Score();
        String player1Name = gameModel.getPlayer1Name();
        String player2Name = gameModel.getPlayer2Name();

        String winner;
        int winningScore;

        if (scoreP1 > scoreP2) {
            winner = player1Name;
            winningScore = scoreP1;
        } else if (scoreP2 > scoreP1) {
            winner = player2Name;
            winningScore = scoreP2;
        } else {
            winner = "Draw";
            winningScore = scoreP1;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(" " + winner + " wins!");
        alert.setContentText(
                "Winner: " + winner + "\n" +
                        "Winning Score: " + winningScore + "\n\n" +
                        "Final Scores:\n" +
                        player1Name + ": " + scoreP1 + "\n" +
                        player2Name + ": " + scoreP2);

        ButtonType btnBackHome = new ButtonType("Back to Menu");
        ButtonType btnClose = new ButtonType("Close");
        alert.getButtonTypes().setAll(btnBackHome, btnClose);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnBackHome) {
            handleBackToMenu();
        }
    }

    private void handleBackToMenu() {
        NavigationController.getInstance().showMainMenu();
    }

    private void handleTimeout() {
        setMessageText("Time out!!!", true);
        inputHandler.setLocked(true);
        gameControlManager.setEnabled(false);

        PauseTransition pause = new PauseTransition(Duration.seconds(GameConstants.TIMEOUT_DISPLAY_DURATION_SECONDS));
        pause.setOnFinished(e -> {
            try {
                setMessageText(null, false);
                inputHandler.setLocked(false);

                gameModel.forceTimeoutSwitchTurn();
                updateGameUI();

                gameTimerManager.reset();
                lblTimer.textProperty().bind(gameTimerManager.timeStringProperty());

                gameControlManager.setEnabled(true);
                gameControlManager.resetToDefaultState();

                checkGameOver();
            } catch (Exception ex) {
                System.err.println("Error during timeout handling: " + ex.getMessage());
                ex.printStackTrace();
                lblTimer.setText("00:00");
                inputHandler.setLocked(false);
                gameTimerManager.reset();
                lblTimer.textProperty().bind(gameTimerManager.timeStringProperty());
                gameControlManager.setEnabled(true);
                gameControlManager.resetToDefaultState();
            }
        });
        pause.play();
    }

    private void setMessageText(String text, boolean isTimeoutStyle) {
        if (text != null) {
            lblTimer.textProperty().unbind();
            lblTimer.setText(text);
            if (isTimeoutStyle) {
                if (!lblTimer.getParent().getStyleClass().contains("timer-timeout")) {
                    lblTimer.getParent().getStyleClass().add("timer-timeout");
                }
            }
        } else {
            lblTimer.getParent().getStyleClass().remove("timer-timeout");
        }
    }

    private void handleTimerTick() {
    }
}
