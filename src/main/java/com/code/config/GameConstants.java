package com.code.config;


public final class GameConstants {

    private GameConstants() {
    } // Prevent instantiation

    // ==================== TIMER ====================
    public static final int DEFAULT_TIMER_SECONDS = 60;
    public static final int TIMEOUT_DISPLAY_DURATION_SECONDS = 2;
    public static final int TIMER_TICK_EFFECT_DURATION_MS = 200;

    // ==================== ANIMATION ====================
    public static final double ANIMATION_STEP_DURATION_MS = 400.0;

    // ==================== CURSOR ====================
    public static final double CURSOR_OFFSET_X = 10.0;
    public static final double CURSOR_OFFSET_Y = 10.0;

    // ==================== BOARD LAYOUT ====================
    public static final int MANDARIN_COLSPAN = 1;
    public static final int MANDARIN_ROWSPAN = 2;
    public static final int CITIZEN_COLSPAN = 1;
    public static final int CITIZEN_ROWSPAN = 1;

    public static final int TOTAL_SQUARES = 12;
    public static final int CITIZENS_PER_SIDE = 5;

    // Square IDs
    public static final int MANDARIN_LEFT_ID = 0;
    public static final int MANDARIN_RIGHT_ID = 6;

    // ==================== STONES ====================
    public static final int MAX_VISIBLE_STONES = 20;
    public static final int INITIAL_CITIZEN_STONES = 5;
    public static final int INITIAL_MANDARIN_STONES = 0;

    // ==================== GAME MODEL CONSTANTS ====================
    // Board structure
    public static final int BOARD_SIZE = 12; // 0-11
    public static final int P1_START_INDEX = 0;
    public static final int P1_END_INDEX = 4;
    public static final int MANDARIN_BOX_1 = 5; // Right mandarin
    public static final int P2_START_INDEX = 6;
    public static final int P2_END_INDEX = 10;
    public static final int MANDARIN_BOX_2 = 11; // Left mandarin

    // Game rules
    public static final int SCORE_TO_BORROW = 5;
    public static final int BORROW_AMOUNT = 5;
    public static final int MANDARIN_VALUE = 5;
    // public static final int CITIZEN_VALUE = 1;

    // ==================== UI EFFECTS ====================
    public static final String EFFECT_ACTIVE_PLAYER = "-fx-effect: dropshadow(gaussian, #00FF00, 15, 0.5, 0, 0);";
    public static final String EFFECT_TIMEOUT = "-fx-effect: dropshadow(gaussian, red, 20, 0.8, 0, 0);";
    public static final String EFFECT_TIMER_TICK = "-fx-effect: dropshadow(gaussian, cyan, 10, 0.5, 0, 0);";
    public static final double INACTIVE_PLAYER_OPACITY = 0.5;
    public static final double ACTIVE_PLAYER_OPACITY = 1.0;

    // ==================== PLAYER SIDES ====================
    public static final int SIDE_BOTTOM = 1;
    public static final int SIDE_TOP = 2;

    // ==================== RESOURCES ====================
    public static final String IMAGE_PATH = "/image/";
    public static final String FXML_PATH = "/fxml/";
    public static final String CSS_PATH = "/css/";

    // Hand cursor images
    public static final String IMG_HAND_OPEN = IMAGE_PATH + "open_hand_third.png";
    public static final String IMG_HAND_CLOSED = IMAGE_PATH + "open_hand_third.png";

    // Button images
    public static final String IMG_STOP = IMAGE_PATH + "stop.png";
    public static final String IMG_CONTINUE = IMAGE_PATH + "continue.png";
    public static final String IMG_MUSIC = IMAGE_PATH + "music.png";
    public static final String IMG_MUTE = IMAGE_PATH + "mute.png";
}
