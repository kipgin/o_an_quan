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
    private int currentStoneCount = -1; // Track for optimization

    private static Image imgStone;
    private static Image imgBigStone;

    static {
        try {
            imgStone = new Image(SquareController.class.getResourceAsStream("/image/small_stone.png"));
            imgBigStone = new Image(SquareController.class.getResourceAsStream("/image/big_stone.png"));
        } catch (Exception e) {
            System.err.println("Chưa có ảnh sỏi, dùng hình vẽ CSS thay thế.");
        }
    }

    public void setup(int id, boolean isMandarin) {
        this.squareId = id;
        this.isMandarin = isMandarin;

        rootPane.getStyleClass().clear();
        if (isMandarin) {
            rootPane.getStyleClass().add(id == GameConstants.MANDARIN_LEFT_ID ? "mandarin-left" : "mandarin-right");
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

    private void renderVisualStones(int amount) {
        int displayAmount = Math.min(amount, GameConstants.MAX_VISIBLE_STONES);
        int currentChildren = stoneContainer.getChildren().size();

        // Optimization: Adjust rather than clear and recreate
        if (displayAmount > currentChildren) {
            // Add more stones
            for (int i = currentChildren; i < displayAmount; i++) {
                stoneContainer.getChildren().add(createStoneNode());
            }
        } else if (displayAmount < currentChildren) {
            // Remove excess stones
            stoneContainer.getChildren().remove(displayAmount, currentChildren);
        }
    }

    private javafx.scene.Node createStoneNode() {
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
}