package com.code.model.domain;

import com.code.model.game.MoveStep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CaptureChain {
    private final List<MoveStep> steps;
    private int totalPoints;

    public CaptureChain() {
        this.steps = new ArrayList<>();
        this.totalPoints = 0;
    }

    public void addCapture(SquareId squareId, int points) {
        this.totalPoints += points;
        this.steps.add(new MoveStep(squareId.getValue(), 0));
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public List<MoveStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}
