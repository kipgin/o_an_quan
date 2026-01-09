package com.code.model.domain;

public class CaptureResult {
    private final boolean success;
    private final int points;

    private CaptureResult(boolean success, int points) {
        this.success = success;
        this.points = points;
    }

    public static CaptureResult empty() {
        return new CaptureResult(false, 0);
    }

    public static CaptureResult success(int points) {
        return new CaptureResult(true, points);
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPoints() {
        return points;
    }
}
