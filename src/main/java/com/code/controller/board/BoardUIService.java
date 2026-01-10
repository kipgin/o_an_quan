package com.code.controller.board;

import com.code.config.GameConstants;
import com.code.model.game.OAnQuanGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import java.util.function.Predicate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BoardUIService {
    private final GridPane gridBoard;
    private final Map<Integer, SquareController> squareControllerMap;
    private Integer currentArrowSquareId = null;

    public BoardUIService(GridPane gridBoard) {
        this.gridBoard = gridBoard;
        this.squareControllerMap = new HashMap<>();
    }

    public void setupBoardUI(Consumer<Integer> onSquareClick, Consumer<Boolean> onArrowClick) {
        try {
            loadAndAddSquare(GameConstants.MANDARIN_BOX_2, true, 0, 0,
                    GameConstants.MANDARIN_COLSPAN, GameConstants.MANDARIN_ROWSPAN, onSquareClick, onArrowClick);
            loadAndAddSquare(GameConstants.MANDARIN_BOX_1, true, 6, 0,
                    GameConstants.MANDARIN_COLSPAN, GameConstants.MANDARIN_ROWSPAN, onSquareClick, onArrowClick);

            for (int i = 0; i < GameConstants.CITIZENS_PER_SIDE; i++) {
                int squareId = 10 - i;
                loadAndAddSquare(squareId, false, i + 1, 0,
                        GameConstants.CITIZEN_COLSPAN, GameConstants.CITIZEN_ROWSPAN, onSquareClick, onArrowClick);
            }

           
            for (int i = 0; i < GameConstants.CITIZENS_PER_SIDE; i++) {
                int squareId = i; // P1 squares: 0, 1, 2, 3, 4
                loadAndAddSquare(squareId, false, i + 1, 1,
                        GameConstants.CITIZEN_COLSPAN, GameConstants.CITIZEN_ROWSPAN, onSquareClick, onArrowClick);
            }
        } catch (IOException e) {
            System.err.println("Error: Cannot load Square.fxml - " + e.getMessage());
            throw new RuntimeException("Failed to initialize board UI", e);
        }
    }

    private void loadAndAddSquare(int id, boolean isMandarin, int col, int row, int colSpan, int rowSpan,
            Consumer<Integer> onSquareClick, Consumer<Boolean> onArrowClick) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GameConstants.FXML_PATH + "Square.fxml"));
        Parent squareNode = loader.load();

        SquareController sqCtrl = loader.getController();
        sqCtrl.setup(id, isMandarin);

        if (id == GameConstants.MANDARIN_BOX_2) {
            sqCtrl.setMirrored(true);
        }

        
        squareNode.setOnMouseClicked(e -> {
            if (!(e.getTarget() instanceof ImageView)) {
                onSquareClick.accept(id);
            }
        });

        sqCtrl.setOnArrowClick(onArrowClick::accept);

        gridBoard.add(squareNode, col, row, colSpan, rowSpan);
        squareControllerMap.put(id, sqCtrl);
    }

    public void updateBoardStones(OAnQuanGame gameModel) {
        for (Map.Entry<Integer, SquareController> entry : squareControllerMap.entrySet()) {
            entry.getValue().setStones(gameModel.getSquareStones(entry.getKey()));
        }
    }

    public void setStones(int squareId, int stones) {
        SquareController ctrl = squareControllerMap.get(squareId);
        if (ctrl != null) {
            ctrl.setStones(stones);
        } else {
            System.err.println("Warning: Attempted to set stones for unknown square: " + squareId);
        }
    }

    public void highlightSquare(int squareId, boolean highlight) {
        SquareController ctrl = squareControllerMap.get(squareId);
        if (ctrl != null) {
            StackPane root = ctrl.getRoot();
            if (highlight) {
                if (!root.getStyleClass().contains("square-selected")) {
                    root.getStyleClass().add("square-selected");
                }
            } else {
                root.getStyleClass().remove("square-selected");
            }
        }
    }

    public Map<Integer, SquareController> getAllControllers() {
        return squareControllerMap;
    }

    public void showArrows(int squareId, boolean show) {
        if (show && currentArrowSquareId != null && currentArrowSquareId != squareId) {
            SquareController prevCtrl = squareControllerMap.get(currentArrowSquareId);
            if (prevCtrl != null) {
                prevCtrl.showArrows(false);
            }
        }

        if (squareControllerMap.containsKey(squareId)) {
            squareControllerMap.get(squareId).showArrows(show);
            currentArrowSquareId = show ? squareId : null;
        }
    }

    public void clearArrows() {
        if (currentArrowSquareId != null) {
            SquareController ctrl = squareControllerMap.get(currentArrowSquareId);
            if (ctrl != null) {
                ctrl.showArrows(false);
            }
            currentArrowSquareId = null;
        }
    }

    public Parent getSquareRootNode(int squareId) {
        SquareController ctrl = squareControllerMap.get(squareId);
        if (ctrl != null) {
            return ctrl.getRoot();
        }
        return null;
    }

    public void updateSquareHoverability(OAnQuanGame gameModel) {
        for (Map.Entry<Integer, SquareController> entry : squareControllerMap.entrySet()) {
            int id = entry.getKey();
            SquareController ctrl = entry.getValue();

            if (id == GameConstants.MANDARIN_BOX_1 || id == GameConstants.MANDARIN_BOX_2) {
                ctrl.setHoverEnabled(false);
            } else {
                boolean isCurrentPlayerSquare = gameModel.isCurrentPlayerOwnsSquare(id);
                boolean hasStones = gameModel.getSquareStones(id) > 0;
                ctrl.setHoverEnabled(isCurrentPlayerSquare && hasStones);
            }
        }
    }

    public void checkAndHighlightHover(Bounds handBoundsInScene,
            Predicate<Integer> isValidValidator) {
        boolean isLocked = false;

        for (Map.Entry<Integer, SquareController> entry : squareControllerMap.entrySet()) {
            int squareId = entry.getKey();
            SquareController ctrl = entry.getValue();
            StackPane squareRoot = ctrl.getRoot();
            boolean shouldHighlight = false;

            if (!isLocked) {
                Bounds squareBounds = squareRoot.localToScene(squareRoot.getBoundsInLocal());
                if (handBoundsInScene.intersects(squareBounds)) {
                    if (isValidValidator.test(squareId)) {
                        shouldHighlight = true;
                        isLocked = true;
                    }
                }
            }

            if (shouldHighlight) {
                if (!squareRoot.getStyleClass().contains("square-hover")) {
                    squareRoot.getStyleClass().add("square-hover");
                }
            } else {
                squareRoot.getStyleClass().remove("square-hover");
            }
        }
    }
}
