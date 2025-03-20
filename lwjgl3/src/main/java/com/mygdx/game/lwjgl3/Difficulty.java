package com.mygdx.game.lwjgl3;

public enum Difficulty {
    EASY(0, 1000, 40, 3),      // 0 - 999 points → Scroll speed 40, speed increase +3
    MEDIUM(1000, 2000, 90, 3), // 1000 - 1999 points → Scroll speed 90, speed increase +3
    HARD(2000, Integer.MAX_VALUE, 120, 0); // 2000+ points → Speed locked at 120

    public final int minScore;
    public final int maxScore;
    public final float baseScrollSpeed;
    public final float speedIncrease;

    Difficulty(int minScore, int maxScore, float baseScrollSpeed, float speedIncrease) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.baseScrollSpeed = baseScrollSpeed;
        this.speedIncrease = speedIncrease;
    }

    public static Difficulty getDifficultyForScore(int score) {
        for (Difficulty d : values()) {
            if (score >= d.minScore && score < d.maxScore) {
                return d;
            }
        }
        return HARD; // Default to HARD if above max threshold
    }
}
