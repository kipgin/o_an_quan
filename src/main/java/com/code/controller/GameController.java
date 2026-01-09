package com.code.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.code.config.GameConstants;

import com.code.controller.managers.HandCursorManager;
import com.code.controller.managers.MenuManager;
import com.code.controller.managers.GameTimerManager;
import com.code.controller.managers.PlayerInfoManager;
import com.code.controller.managers.GameControlManager;
import com.code.controller.managers.GameButtonManager;
import com.code.controller.board.BoardUIService;
import com.code.controller.animation.AnimationService;
import com.code.controller.input.GameInputHandler;
import com.code.controller.input.GameInputListener;
import com.code.controller.managers.MusicManager;
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

    // New UI Fields
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
    private HandCursorManager handCursorManager;
    private MenuManager menuManager;
    private GameTimerManager gameTimerManager;
    private PlayerInfoManager playerInfoManager;
    private GameControlManager gameControlManager;
    private GameButtonManager gameButtonManager;
    private GameInputListener gameInputListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Initialize Core Services
        gameModel = new OAnQuanGame();
        boardUIService = new BoardUIService(gridBoard);
        handCursorManager = new HandCursorManager(mainRoot, handCursor);
        handCursor.setVisible(true); // Fix: Ensure cursor is visible immediately

        menuManager = new MenuManager(btnMenu, handCursorManager, this::handleBackToMenu);
        MusicManager.getInstance().attachMusicButton(btnMusic);
        animationService = new AnimationService(handCursor, boardUIService, handCursorManager);
        inputHandler = new GameInputHandler(gameModel, boardUIService, handCursorManager, this::onMoveExecuted);

        // 2. Initialize New Managers
        playerInfoManager = new PlayerInfoManager(lblScoreP1, lblScoreP2, boxPlayer1, boxPlayer2,
                lblPlayerName1, lblPlayerName2);
        gameTimerManager = new GameTimerManager();
        gameButtonManager = new GameButtonManager();
        gameControlManager = new GameControlManager(btnStop, gameTimerManager);

        gameInputListener = new GameInputListener(
                handCursorManager, boardUIService, menuManager,
                inputHandler, gameTimerManager,
                gameButtonManager, btnMusic, btnStop);

        // 3. Setup Timer Bindings & Logic
        lblTimer.textProperty().bind(gameTimerManager.timeStringProperty());
        gameTimerManager.setOnTimeout(this::handleTimeout);
        gameTimerManager.setOnTick(this::handleTimerTick);
        gameTimerManager.start();

        // 4. Setup Board Callbacks
        boardUIService.setupBoardUI(
                (squareId) -> {
                    if (!animationService.isAnimating() && !gameTimerManager.isPaused()) {
                        inputHandler.handleSquareClick(squareId);
                        gridBoard.requestFocus();
                    }
                },
                (isRightArrow) -> {
                    if (!animationService.isAnimating() && !gameTimerManager.isPaused()) {
                        inputHandler.handleDirectionSelection(isRightArrow);
                    }
                });

        // 5. Initial UI Sync
        playerInfoManager.updateScores(gameModel.getPlayer1(), gameModel.getPlayer2());
        playerInfoManager.updateActivePlayerHighlight(gameModel);
        boardUIService.updateBoardStones(gameModel);

        setupGlobalInputs();
    }

    private void setupGlobalInputs() {
        mainRoot.setOnMouseMoved(gameInputListener::handleMouseMove);
        gridBoard.setFocusTraversable(true);
        gridBoard.setOnKeyPressed(gameInputListener::handleKeyPressed);
    }

    private void handleTimeout() {
        lblTimer.textProperty().unbind();
        lblTimer.setText("Time out!!!");
        paneTimer.setStyle(GameConstants.EFFECT_TIMEOUT);

        PauseTransition pt = new PauseTransition(Duration.seconds(GameConstants.TIMEOUT_DISPLAY_DURATION_SECONDS));
        pt.setOnFinished(e -> {
            paneTimer.setStyle("");
            gameModel.switchTurn();
            gameTimerManager.reset();
            lblTimer.textProperty().bind(gameTimerManager.timeStringProperty());
            playerInfoManager.updateScores(gameModel.getPlayer1(), gameModel.getPlayer2());
            playerInfoManager.updateActivePlayerHighlight(gameModel);
        });
        pt.play();
    }

    private void handleTimerTick() {
        paneTimer.setStyle(GameConstants.EFFECT_TIMER_TICK);
        PauseTransition pt = new PauseTransition(Duration.millis(GameConstants.TIMER_TICK_EFFECT_DURATION_MS));
        pt.setOnFinished(e -> {
            if (!lblTimer.getText().equals("Time out!!!"))
                paneTimer.setStyle("");
        });
        pt.play();
    }

    private void onMoveExecuted() {
        gameTimerManager.pause();
        gameInputListener.setAnimating(true); // Block input

        runMoveAnimation(gameModel.getLastMoveHistory());
    }

    private void runMoveAnimation(List<MoveStep> history) {
        animationService.runMoveAnimation(history, () -> {
            gameInputListener.setAnimating(false); // Enable input
            gameTimerManager.reset();
            playerInfoManager.updateScores(gameModel.getPlayer1(), gameModel.getPlayer2());
            playerInfoManager.updateActivePlayerHighlight(gameModel);
            if (gameModel.isGameOver())
                showWinnerDialog();
        });
    }

    private void showWinnerDialog() {
        String msg = "Kết thúc! P1: " + gameModel.getPlayer1().getScore() + " - P2: "
                + gameModel.getPlayer2().getScore();
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    public void handleBackToMenu() {
        gameTimerManager.stop();
        NavigationController.getInstance().showMainMenu();
    }
}
