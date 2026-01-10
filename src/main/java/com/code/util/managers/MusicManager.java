package com.code.util.managers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicManager {
    private static MusicManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;
    private final List<Button> attachedButtons = new ArrayList<>();

    private Image imgMusic;
    private Image imgMute;

    private MusicManager() {
        loadResources();
        initMediaPlayer();
    }

    public static synchronized MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    private void loadResources() {
        try {
            imgMusic = new Image(getClass().getResourceAsStream("/image/music.png"));
            imgMute = new Image(getClass().getResourceAsStream("/image/mute.png"));
        } catch (Exception e) {
            System.err.println("Error loading music icons: " + e.getMessage());
        }
    }

    private void initMediaPlayer() {
        try {
            URL resource = getClass().getResource("/music/lofi-chill-background-461490.mp3");
            if (resource != null) {
                Media media = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            } else {
                System.err.println("Music file not found!");
            }
        } catch (Exception e) {
            System.err.println("Error initializing media player: " + e.getMessage());
        }
    }

    public void attachMusicButton(Button btn) {
        if (btn == null)
            return;
        if (!attachedButtons.contains(btn)) {
            attachedButtons.add(btn);
        }

        updateButtonIcon(btn);
        btn.setOnAction(e -> toggleMute());
    }

    private void toggleMute() {
        isMuted = !isMuted;
        if (mediaPlayer != null) {
            mediaPlayer.setMute(isMuted);
        }
        updateAllButtons();
    }

    private void updateAllButtons() {
        for (Button btn : attachedButtons) {
            updateButtonIcon(btn);
        }
    }

    private void updateButtonIcon(Button btn) {
        ImageView icon = new ImageView(isMuted ? imgMute : imgMusic);
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        icon.setPreserveRatio(true);
        btn.setGraphic(icon);
        btn.setText(""); 
    }
}
