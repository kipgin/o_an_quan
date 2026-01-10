package com.code.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveResult {
    private final List<MoveStep> allSteps;
    private final int totalScore;

    public MoveResult() {
        this.allSteps = new ArrayList<>();
        this.totalScore = 0;
    }

    public MoveResult(List<MoveStep> allSteps, int totalScore) {
        this.allSteps = new ArrayList<>(allSteps);
        this.totalScore = totalScore;
    }

    public List<MoveStep> getAllSteps() {
        return Collections.unmodifiableList(allSteps);
    }

    public int getTotalScore() {
        return totalScore;
    }
}
