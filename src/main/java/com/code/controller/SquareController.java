//package com.code.controller;
//
//import javafx.scene.control.Label;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polygon;
//import javafx.scene.shape.Rectangle;
//
//import java.util.function.Consumer;
//public class SquareController {
//
//    @FXML private StackPane rootPane;
//    @FXML private Rectangle bgShape;
//    @FXML private Label lblStones;
//    @FXML private Polygon arrowLeft;
//    @FXML private Polygon arrowRight;
//
//    private int squareId;
//
//    public void setup(int id, boolean isMandarin) {
//        this.squareId = id;
//        if (isMandarin) {
//            bgShape.setFill(Color.LIGHTGOLDENRODYELLOW);
//            bgShape.setStrokeWidth(2);
//        } else {
//            bgShape.setFill(Color.LIGHTGRAY);
//        }
//    }
//
//    public void setStones(int amount) {
//        lblStones.setText(String.valueOf(amount));
//    }
//
//    public void showArrows(boolean isVisible) {
//        arrowLeft.setVisible(isVisible);
//        arrowRight.setVisible(isVisible);
//    }
//
//    public void setOnArrowClick(Consumer<Boolean> onDirectionSelected) {
//        arrowRight.setOnMouseClicked(e -> {
//            e.consume(); 
//            onDirectionSelected.accept(true); 
//        });
//        
//        arrowLeft.setOnMouseClicked(e -> {
//            e.consume();
//            onDirectionSelected.accept(false);
//        });
//    }
//    
//    private void renderVisualStones(int amount) {
//        stoneContainer.getChildren().clear();
//        if (isMandarin && amount > 0) {
//            stoneContainer.getChildren().add(new VisualStone(StoneType.BIG));
//            amount--; 
//        }
//
//        int displayAmount = Math.min(amount, 25); 
//        
//        for (int i = 0; i < displayAmount; i++) {
//            stoneContainer.getChildren().add(new VisualStone(StoneType.SMALL));
//        }
//    }
//    public int getSquareId() { return squareId; }
//}

package com.code.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import java.util.function.Consumer;

public class SquareController {

    @FXML private StackPane rootPane;
    @FXML private FlowPane stoneContainer;
    @FXML private Label lblStones;
    @FXML private javafx.scene.shape.Polygon arrowLeft;
    @FXML private javafx.scene.shape.Polygon arrowRight;

    private int squareId;
    private boolean isMandarin;

    // Load ảnh 1 lần (static) để tối ưu bộ nhớ
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

        // Áp dụng CSS class dựa trên loại ô
        rootPane.getStyleClass().clear();
        if (isMandarin) {
            if (id == 11) rootPane.getStyleClass().add("mandarin-left"); // Quan trái
            else rootPane.getStyleClass().add("mandarin-right"); // Quan phải
        } else {
            rootPane.getStyleClass().add("citizen-square");
        }
    }

    public void setStones(int amount) {
        lblStones.setText(String.valueOf(amount));
        renderVisualStones(amount);
    }

    private void renderVisualStones(int amount) {
        stoneContainer.getChildren().clear();

        // Giới hạn số lượng hiển thị để không bị tràn ô (VD: max 15 viên hình ảnh)
        // Nếu nhiều hơn thì chỉ hiện tượng trưng
        int displayAmount = Math.min(amount, 20); 

        for (int i = 0; i < displayAmount; i++) {
            if (imgStone != null) {
                // Dùng ảnh
                ImageView iv = new ImageView(imgStone);
                iv.setFitWidth(15); 
                iv.setFitHeight(15);
                stoneContainer.getChildren().add(iv);
            } else {
                // Dùng CSS Shape (Hình tròn)
                Circle c = new Circle(6); // Bán kính 6
                c.getStyleClass().add("stone-shape");
                stoneContainer.getChildren().add(c);
            }
        }
        
        // Nếu là ô Quan và có điểm > 0 (giả sử quan trị giá > 0 hoặc logic riêng)
        // Vẽ thêm viên Quan to nếu cần. Ở đây logic đơn giản hóa theo số đá.
        if (isMandarin && amount > 0 && amount % 5 == 0) { // Ví dụ logic hiển thị quan
             // Thêm hình quan to...
        }
    }

    public void showArrows(boolean isVisible) {
        arrowLeft.setVisible(isVisible);
        arrowRight.setVisible(isVisible);
    }

    public void setOnArrowClick(Consumer<Boolean> onDirectionSelected) {
        arrowRight.setOnMouseClicked(e -> { e.consume(); onDirectionSelected.accept(true); });
        arrowLeft.setOnMouseClicked(e -> { e.consume(); onDirectionSelected.accept(false); });
    }
    
    // API để lấy toạ độ của ô trên màn hình (dùng cho bàn tay bay đến)
    public double getLayoutX() { return rootPane.getLayoutX(); }
    public double getLayoutY() { return rootPane.getLayoutY(); }
    public double getWidth() { return rootPane.getWidth(); }
    public double getHeight() { return rootPane.getHeight(); }
    public StackPane getRoot() {
        return rootPane;
    }
}