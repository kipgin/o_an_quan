package com.code.controller.board;

import com.code.config.GameConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import java.util.function.Consumer;
import javafx.scene.Node;

public class SquareController {

    @FXML
    private StackPane rootPane;
    @FXML
    private FlowPane stoneContainer;
    @FXML
    private Label lblStones;
    @FXML
    private ImageView arrowLeft;
    @FXML
    private ImageView arrowRight;

    private int squareId;
    private boolean isMandarin;
    private int currentStoneCount = -1;

    private static Image imgStone;
    private static Image imgBigStone;

    static {
        try {
            imgStone = new Image(SquareController.class.getResourceAsStream(GameConstants.SMALL_STONE));
            imgBigStone = new Image(SquareController.class.getResourceAsStream(GameConstants.BIG_STONE));
        } catch (Exception e) {
            // debug
            System.err.println("No image found, using CSS shape instead.");
        }
    }

    public void setup(int squareId, boolean isMandarin) {
        this.squareId = squareId;
        this.isMandarin = isMandarin;

        rootPane.getStyleClass().clear();
        if (isMandarin) {
            rootPane.getStyleClass().add("mandarin-square");
            rootPane.getStyleClass()
                    .add(squareId == GameConstants.MANDARIN_LEFT_ID ? "mandarin-left" : "mandarin-right");
        } else {
            rootPane.getStyleClass().add("citizen-square");
        }
    }

    public void setStones(int amount) {
        lblStones.setText(String.valueOf(amount));

        if (amount != currentStoneCount) {
            renderVisualStones(amount);
            currentStoneCount = amount;
        }
    }

    public void setMirrored(boolean mirrored) {
        if (mirrored) {
            rootPane.setScaleX(-1);
            lblStones.setScaleX(-1);
        } else {
            rootPane.setScaleX(1);
            lblStones.setScaleX(1);
        }
    }

    private void renderVisualStones(int amount) {
        stoneContainer.getChildren().clear();

        if (isMandarin && amount >= GameConstants.MANDARIN_VALUE) {
            int bigStones = 1;
            int smallStones = amount - GameConstants.MANDARIN_VALUE;

            for (int i = 0; i < bigStones; i++) {
                stoneContainer.getChildren().add(createBigStoneNode());
            }

            for (int i = 0; i < smallStones; i++) {
                stoneContainer.getChildren().add(createStoneNode());
            }
        } else {
            int displayAmount = Math.min(amount, GameConstants.MAX_VISIBLE_STONES);
            for (int i = 0; i < displayAmount; i++) {
                stoneContainer.getChildren().add(createStoneNode());
            }
        }
    }

    private Node createStoneNode() {
        if (imgStone != null) {
            ImageView iv = new ImageView(imgStone);
            iv.setFitWidth(15);
            iv.setFitHeight(15);
            return iv;
        } else {
            Circle c = new Circle(6);
            c.getStyleClass().add("stone-shape");
            return c;
        }
    }

    private Node createBigStoneNode() {
        if (imgBigStone != null) {
            ImageView iv = new ImageView(imgBigStone);
            iv.setFitWidth(25);
            iv.setFitHeight(25);
            iv.getStyleClass().add("big-stone-image");
            return iv;
        } else {
            Circle c = new Circle(12);
            c.getStyleClass().add("big-stone");
            return c;
        }
    }

    public void showArrows(boolean isVisible) {
        arrowLeft.setVisible(isVisible);
        arrowRight.setVisible(isVisible);
    }

    public void setOnArrowClick(Consumer<Boolean> onDirectionSelected) {

        arrowRight.setOnMouseEntered(e -> {
            arrowRight.getStyleClass().add("arrow-hover");
        });
        arrowRight.setOnMouseExited(e -> {
            arrowRight.getStyleClass().remove("arrow-hover");
        });

        arrowLeft.setOnMouseEntered(e -> {
            arrowLeft.getStyleClass().add("arrow-hover");
        });
        arrowLeft.setOnMouseExited(e -> {
            arrowLeft.getStyleClass().remove("arrow-hover");
        });
        arrowRight.setOnMouseClicked(e -> {
            e.consume();
            onDirectionSelected.accept(true);
        });

        arrowLeft.setOnMouseClicked(e -> {
            e.consume();
            onDirectionSelected.accept(false);
        });
    }

    public double getLayoutX() {
        return rootPane.getLayoutX();
    }

    public double getLayoutY() {
        return rootPane.getLayoutY();
    }

    public double getWidth() {
        return rootPane.getWidth();
    }

    public double getHeight() {
        return rootPane.getHeight();
    }

    public ImageView getArrowLeft() {
        return this.arrowLeft;
    }

    public ImageView getArrowRight() {
        return this.arrowRight;
    }

    public StackPane getRoot() {
        return rootPane;
    }

    public void setHoverEnabled(boolean enabled) {
        rootPane.setOnMouseEntered(enabled ? e -> {
            if (!rootPane.getStyleClass().contains("square-hover")) {
                rootPane.getStyleClass().add("square-hover");
            }
        } : null);

        rootPane.setOnMouseExited(enabled ? e -> {
            rootPane.getStyleClass().remove("square-hover");
        } : null);
    }
}