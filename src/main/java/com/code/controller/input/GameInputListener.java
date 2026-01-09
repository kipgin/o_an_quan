package com.code.controller.input;

import com.code.controller.board.BoardUIService;
import com.code.controller.managers.GameButtonManager;
import com.code.controller.managers.GameTimerManager;
import com.code.controller.managers.HandCursorManager;
import com.code.controller.managers.MenuManager;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameInputListener {
    private final HandCursorManager handCursorManager;
    private final BoardUIService boardUIService;
    private final MenuManager menuManager;
    private final GameInputHandler gameInputHandler;
    private final GameTimerManager timerManager;
    private final GameButtonManager buttonManager;
    private final Button btnMusic;
    private final Button btnStop;

    private boolean isAnimating = false;

    public GameInputListener(HandCursorManager handCursorManager,
            BoardUIService boardUIService,
            MenuManager menuManager,
            GameInputHandler gameInputHandler,
            GameTimerManager timerManager,
            GameButtonManager buttonManager,
            Button btnMusic,
            Button btnStop) {
        this.handCursorManager = handCursorManager;
        this.boardUIService = boardUIService;
        this.menuManager = menuManager;
        this.gameInputHandler = gameInputHandler;
        this.timerManager = timerManager;
        this.buttonManager = buttonManager;
        this.btnMusic = btnMusic;
        this.btnStop = btnStop;
    }

    public void setAnimating(boolean isAnimating) {
        this.isAnimating = isAnimating;
    }

    public void handleMouseMove(MouseEvent event) {
        if (isAnimating)
            return;

        handCursorManager.updatePosition(event.getX(), event.getY());
        handCursorManager.setHandOpen();

        Bounds handBounds = handCursorManager.getBoundsInScene();

        // Board Interaction (blocked if paused - only board, not UI buttons)
        if (!timerManager.isPaused()) {
            boardUIService.checkAndHighlightHover(handBounds, gameInputHandler::isValidSelection);
        }

        // UI Buttons remain interactive during pause
        menuManager.handleHover(handBounds);
        buttonManager.handleButtonHover(btnStop, handBounds, handCursorManager);
        buttonManager.handleButtonHover(btnMusic, handBounds, handCursorManager);
    }

    public void handleKeyPressed(KeyEvent e) {
        if (timerManager.isPaused())
            return;

        if (gameInputHandler.getSelectedSquareId() != null && !isAnimating) {
            if (e.getCode() == KeyCode.LEFT) {
                gameInputHandler.handleDirectionSelection(false);
            } else if (e.getCode() == KeyCode.RIGHT) {
                gameInputHandler.handleDirectionSelection(true);
            }
        }
    }
}
