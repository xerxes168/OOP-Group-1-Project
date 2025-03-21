package com.mygdx.game.lwjgl3;

public class DifficultyManager {
    private static Difficulty difficulty = Difficulty.EASY;

    public static void updateDifficultyBasedOnScore(int score, float delta) {
        // Get the correct difficulty based on the player's score
        Difficulty newDifficulty = Difficulty.getDifficultyForScore(score);

        // Update difficulty if it has changed
        if (difficulty != newDifficulty) {
            setDifficulty(newDifficulty);
        }

        // Apply scroll speed logic
        float targetScrollSpeed = difficulty.baseScrollSpeed;

        // Gradually increase speed if difficulty allows
        if (difficulty.speedIncrease > 0) {
            targetScrollSpeed += difficulty.speedIncrease * delta;
        }

        PlayScene.setScrollSpeed(targetScrollSpeed);
    }

    public static void setDifficulty(Difficulty newDifficulty) {
        if (difficulty != newDifficulty) {
            difficulty = newDifficulty;
            System.out.println("Difficulty updated to: " + difficulty);
        }
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }
}

