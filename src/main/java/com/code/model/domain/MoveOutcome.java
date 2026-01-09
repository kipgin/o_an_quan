package com.code.model.domain;

import com.code.model.game.MoveStep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveOutcome {
    private final List<MoveStep> steps;
    private int scoreEarned;

    public MoveOutcome() {
        this.steps = new ArrayList<>();
        this.scoreEarned = 0;
    }

    public void addStep(int squareId, int stones) {
        this.steps.add(new MoveStep(squareId, stones));
    }

    public void addSteps(List<MoveStep> newSteps) {
        this.steps.addAll(newSteps);
    }

    public void addScore(int points) {
        this.scoreEarned += points;
    }

    public List<MoveStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public int getScoreEarned() {
        return scoreEarned;
    }
}
