package com.code.view.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import com.code.model.enums.StoneType;

import java.util.Random;

public class VisualStone extends ImageView {
    
    private static final Random random = new Random();
    
    private static Image imgSmall;
    private static Image imgBig;

    static {
        try {
            // imgSmall = new Image(VisualStone.class.getResourceAsStream("/images/stone.png"));
            // imgBig = new Image(VisualStone.class.getResourceAsStream("/images/big_stone.png"));
        } catch (Exception e) { }
    }

    public VisualStone(StoneType type) {
        setupVisuals(type);
        randomizeAppearance();
    }

    private void setupVisuals(StoneType type) {
        if (type == StoneType.BIG) {
            if (imgBig != null) setImage(imgBig);
            setFitWidth(40); 
            setFitHeight(40);
        } else {
            if (imgSmall != null) setImage(imgSmall);
            else {
                // Nếu chưa có ảnh, code tạm logic vẽ bằng CSS hoặc placeholder ở controller
                // Ở đây giả sử controller sẽ set image nếu class này chỉ extend ImageView
            }
            setFitWidth(20);
            setFitHeight(20);
        }
        
        setPreserveRatio(true);
       
        setEffect(new DropShadow(3, Color.rgb(0,0,0,0.4)));
    }

    private void randomizeAppearance() {
        setRotate(random.nextInt(360));

        setTranslateX(random.nextInt(10) - 5); 
        setTranslateY(random.nextInt(10) - 5);
    }
}