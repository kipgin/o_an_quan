package com.code.controller;

import com.code.model.entity.Square;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;

import com.code.model.game.OAnQuanGame;
import com.code.model.game.MoveStep;


public class GameController implements Initializable {
	
	@FXML private AnchorPane mainRoot;
    @FXML private Label lblScoreP1;
    @FXML private Label lblScoreP2;
    @FXML private Label lblTurnInfo;
    @FXML private GridPane gridBoard;
    @FXML private ImageView handCursor;

    private OAnQuanGame gameModel;
    
    private Map<Integer, SquareController> squareControllerMap;
    private boolean isAnimating = false;
    private Integer selectedSquareId = null;
    private Image imgHandOpen, imgHandClosed;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameModel = new OAnQuanGame();
        squareControllerMap = new HashMap<>();
        
        setupBoardUI();
//        updateUI();
        updateInfoLabels(); 
        updateBoardStonesImmediate();
        loadHands();

        mainRoot.setOnMouseMoved(this::handleMouseMove);

        
        
        gridBoard.setFocusTraversable(true);
        gridBoard.setOnKeyPressed(e -> {
            if (selectedSquareId != null && !isAnimating) {
                if (e.getCode() == KeyCode.LEFT) {
                    executeMove(false); 
                } else if (e.getCode() == KeyCode.RIGHT) {
                    executeMove(true);  
                }
            }
        });
    }
    
    private void loadHands() {
        try {
            // Tạm thời nếu bạn chưa có ảnh, dùng null. Code logic sẽ check null.
             imgHandOpen = new Image(getClass().getResourceAsStream("/image/open_hand_second.png"));
             imgHandClosed = new Image(getClass().getResourceAsStream("/image/open_hand_second.png"));
//            if(imgHandOpen == null) {
//            	System.out.println("ERROR HERE----");
//            }
            // Set ảnh mặc định ban đầu
            if (imgHandOpen != null) handCursor.setImage(imgHandOpen);
            handCursor.setVisible(true);
            handCursor.setMouseTransparent(true); 
        } catch (Exception e) {
            System.out.println("No image of hand!!!");
        }
    }
    
    private void handleMouseMove(MouseEvent event) {
        // Chỉ di chuyển theo chuột khi KHÔNG chạy animation
        if (!isAnimating) {
            updateHandPosition(event.getX(), event.getY());
            
            // Reset trạng thái tay mở
            if (imgHandOpen != null) handCursor.setImage(imgHandOpen);
        }
    }

    private void updateHandPosition(double x, double y) {
        // Căn chỉnh để mũi trỏ tay nằm đúng vị trí chuột (thường là lệch x, y một chút)
        handCursor.setLayoutX(x - 10); 
        handCursor.setLayoutY(y - 10);
    }
    
    

    private void setupBoardUI() {
        try {
            loadAndAddSquare(11, true, 0, 0, 1, 2);
            loadAndAddSquare(5, true, 6, 0, 1, 2);

            for (int i = 0; i < 5; i++) {
                loadAndAddSquare(10 - i, false, i + 1, 0, 1, 1);
            }

            for (int i = 0; i < 5; i++) {
                loadAndAddSquare(i, false, i + 1, 1, 1, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Cannot load file Square.fxml");
        }
    }


    private void loadAndAddSquare(int id, boolean isMandarin, int col, int row, int colSpan, int rowSpan) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/code/view/Square.fxml"));
        Parent squareNode = loader.load();

        SquareController sqCtrl = loader.getController();
        sqCtrl.setup(id, isMandarin);
        
        squareNode.setOnMouseClicked(e -> handleSquareClick(id));
        sqCtrl.setOnArrowClick(isRightArrow -> executeMove(isRightArrow));
        
//        squareNode.setOnMouseClicked(e -> handleSquareAction(id));

        gridBoard.add(squareNode, col, row, colSpan, rowSpan);

        squareControllerMap.put(id, sqCtrl);
    }
    
    private void handleSquareClick(int squareId) {
        if (isAnimating || gameModel.isGameOver()) return;
        
        // Ẩn mũi tên ô cũ
        if (selectedSquareId != null && squareControllerMap.containsKey(selectedSquareId)) {
            squareControllerMap.get(selectedSquareId).showArrows(false);
        }
        
        // Đổi hình bàn tay nắm
        if (imgHandClosed != null) handCursor.setImage(imgHandClosed);

        // SỬA: Gọi hàm kiểm tra luật (Đúng phe, có sỏi, không phải Quan)
        if (isValidSelection(squareId)) {
             selectedSquareId = squareId;
             squareControllerMap.get(squareId).showArrows(true);
             
             // Quan trọng: Focus vào bàn cờ để nhận sự kiện bàn phím (Left/Right arrow)
             gridBoard.requestFocus(); 
        } else {
            selectedSquareId = null;
        }
    }
    
    private void handleSquareAction(int squareId) {
        if (isAnimating || gameModel.isGameOver()) return;
        boolean isClockwise = showDirectionDialog();
        boolean success = gameModel.play(squareId, isClockwise);

        if (success) {
            runMoveAnimation(gameModel.getLastMoveHistory());
        } else {
            new Alert(Alert.AlertType.WARNING, "Nước đi không hợp lệ!").show();
        }
    }
    
    
    private boolean isValidSelection(int squareId) {
        
        if (gameModel.getBoard().getSquare(squareId).getStones() == 0) return false;

        int side = gameModel.getCurrentPlayer().getSide().ordinal() + 1; // 1 or 2
        if (side == 1 && (squareId < 0 || squareId > 4)) return false;
        if (side == 2 && (squareId < 6 || squareId > 10)) return false;
        
        return true;
    }
    
    private void executeMove(boolean isRightDirection) {
        if (selectedSquareId == null) return;

        squareControllerMap.get(selectedSquareId).showArrows(false);
        
        boolean isClockwise;
        int side = gameModel.getCurrentPlayer().getSide().ordinal() + 1; // 1=Bottom, 2=Top

        if (side == 1) { 
             isClockwise = isRightDirection; 
        } else { 
             isClockwise = !isRightDirection; 
        }

        boolean success = gameModel.play(selectedSquareId, isClockwise);

        if (success) {
            runMoveAnimation(gameModel.getLastMoveHistory());
            selectedSquareId = null; // Reset selection
        } else {
            new Alert(Alert.AlertType.WARNING, "Nước đi lỗi!").show();
        }
    }
    
    private boolean showDirectionDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Chọn hướng");
        alert.setHeaderText("Chọn chiều rải quân:");
        ButtonType right = new ButtonType("Chiều kim đồng hồ");
        ButtonType left = new ButtonType("Ngược chiều");
        alert.getButtonTypes().setAll(right, left);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == right;
    }
    
    
    private void runMoveAnimation(List<MoveStep> history) {
        isAnimating = true;
        if (imgHandClosed != null) handCursor.setImage(imgHandClosed); // Tay nắm lại rải quân

        Timeline timeline = new Timeline();
        double delayTime = 0;
        double stepDuration = 400; // Chậm lại chút để nhìn tay bay

        for (MoveStep step : history) {
            KeyFrame kf = new KeyFrame(Duration.millis(delayTime), e -> {
                // 1. Update số sỏi
                if (squareControllerMap.containsKey(step.squareId)) {
                    squareControllerMap.get(step.squareId).setStones(step.stones);
                    
                    // 2. Di chuyển bàn tay đến ô đang rải
                    moveHandToSquare(step.squareId);
                }
            });
            timeline.getKeyFrames().add(kf);
            delayTime += stepDuration;
        }

        timeline.setOnFinished(e -> {
            isAnimating = false;
            if (imgHandOpen != null) handCursor.setImage(imgHandOpen); // Mở tay ra
            updateInfoLabels();
            if (gameModel.isGameOver()) showWinnerDialog();
        });
        timeline.play();
    }
    
    private void moveHandToSquare(int squareId) {
        SquareController ctrl = squareControllerMap.get(squareId);
        if (ctrl == null) return;

        // 1. Lấy Node giao diện (rootPane) từ Controller của ô
        // Bạn cần đảm bảo class SquareController có hàm: public Parent getRoot() { return rootPane; }
        Parent squareNode = ctrl.getRoot(); 

        // 2. Lấy tọa độ của ô cờ so với Scene (màn hình game)
        javafx.geometry.Point2D point = squareNode.localToScene(0.0, 0.0);

        // 3. Tính toán vị trí mới cho bàn tay
        // Lấy tọa độ ô + một nửa kích thước ô để tay vào giữa
        double targetX = point.getX() + squareNode.getBoundsInLocal().getWidth() / 2;
        double targetY = point.getY() + squareNode.getBoundsInLocal().getHeight() / 2;

        // Trừ đi toạ độ của mainRoot (nếu mainRoot không full màn hình) 
        // và trừ 1/2 kích thước ảnh bàn tay để tâm bàn tay trùng tâm ô
        double handWidth = handCursor.getFitWidth() > 0 ? handCursor.getFitWidth() : handCursor.getImage().getWidth();
        double handHeight = handCursor.getFitHeight() > 0 ? handCursor.getFitHeight() : handCursor.getImage().getHeight();

        handCursor.setLayoutX(targetX - handWidth / 2);
        handCursor.setLayoutY(targetY - handHeight / 2);
    }
    
    
    private void updateInfoLabels() {
        lblScoreP1.setText("Score: " + gameModel.getPlayer1().getScore());
        lblScoreP2.setText("Score: " + gameModel.getPlayer2().getScore());
        lblTurnInfo.setText("Lượt: " + gameModel.getCurrentPlayer().getName());
    }
    
    private void updateBoardStonesImmediate() {
        for (Map.Entry<Integer, SquareController> entry : squareControllerMap.entrySet()) {
            entry.getValue().setStones(gameModel.getBoard().getSquare(entry.getKey()).getStones());
        }
    }
    
    private void showWinnerDialog() {
         String msg = "Kết thúc! P1: " + gameModel.getPlayer1().getScore() + " - P2: " + gameModel.getPlayer2().getScore();
         new Alert(Alert.AlertType.INFORMATION, msg).show();
    }
    
    @FXML
    public void handleBackToMenu() {
        NavigationController.getInstance().showMainMenu();
    }
    
  
}