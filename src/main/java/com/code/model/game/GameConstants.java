package com.code.model.game;

public class GameConstants {
    public static final int BOARD_SIZE = 12; // 0-11
    public static final int P1_START_INDEX = 0;
    public static final int P1_END_INDEX = 4;
    public static final int MANDARIN_BOX_1 = 5;
    public static final int P2_START_INDEX = 6;
    public static final int P2_END_INDEX = 10;
    public static final int MANDARIN_BOX_2 = 11;

    public static final int INITIAL_CITIZEN_STONES = 5;
    public static final int INITIAL_MANDARIN_STONES = 0; // Usually 0 or 10 depending on rules, user code had 0 in
                                                         // loops? No, actually typically 10, but let's check init code.

    // Based on StandardRule logica
    public static final int SCORE_TO_BORROW = 5;
    public static final int BORROW_AMOUNT = 5;

    // Mandarin Value
    public static final int MANDARIN_VALUE = 5;
}
