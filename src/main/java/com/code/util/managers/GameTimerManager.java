package com.code.util.managers;

import com.code.config.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameTimerManager {
    private int timeSeconds = GameConstants.DEFAULT_TIMER_SECONDS;
    private Timeline timeline;
    private final StringProperty timeString = new SimpleStringProperty(formatTime(GameConstants.DEFAULT_TIMER_SECONDS));
    private Runnable onTimeout;
    private Runnable onTick;
    private boolean isPaused = false;
    private boolean timeoutHandled = false;

    public GameTimerManager() {
        setupTimeline();
    }

    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timeSeconds > 0) {
                timeSeconds--;
                updateTimeString();
                if (onTick != null)
                    onTick.run();
            } else if (!timeoutHandled) {
                timeoutHandled = true;
                updateTimeString(); 
                if (onTimeout != null)
                    onTimeout.run();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setOnTimeout(Runnable onTimeout) {
        this.onTimeout = onTimeout;
    }

    public void setOnTick(Runnable onTick) {
        this.onTick = onTick;
    }

    public void start() {
        if (timeline != null) {
            timeline.play();
        }
    }

    public void pause() {
        if (timeline != null) {
            timeline.pause();
            isPaused = true;
        }
    }

    public void resume() {
        if (timeline != null) {
            timeline.play();
            isPaused = false;
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void reset() {
        stop();
        timeSeconds = GameConstants.DEFAULT_TIMER_SECONDS;
        timeoutHandled = false;
        updateTimeString();
        isPaused = false;
        start();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public StringProperty timeStringProperty() {
        return timeString;
    }

    private void updateTimeString() {
        timeString.set(formatTime(timeSeconds));
    }

    private static String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }
}
