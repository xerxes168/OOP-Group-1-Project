package com.mygdx.game.lwjgl3;

public class DifficultyManager {
	private static Difficulty difficulty = Difficulty.MEDIUM; // Default difficulty
    private static float enemySpawnRateMultiplier = 1.0f;
    private static float playerHealthMultiplier = 1.0f;
    private static float scrollSpeedMultiplier = 1.0f;
    private static int scoreThreshold = 500; // Score required to increase difficulty

    public static void setDifficulty(Difficulty newDifficulty) {
    	if (difficulty != newDifficulty) { // Prevent unnecessary updates
            difficulty = newDifficulty;
            adjustDifficultySettings();
            System.out.println("⚡ Difficulty updated to: " + difficulty);
        }
    }
    
    public static Difficulty getDifficulty() {
        return difficulty;
    }

    private static void adjustDifficultySettings() {
        switch (difficulty) {
            case EASY:
                enemySpawnRateMultiplier = 1.2f;
                playerHealthMultiplier = 1.0f;
                scrollSpeedMultiplier = 1.0f;
                scoreThreshold = 1000;
                System.out.println("Difficulty set to Easy");
                break;
            case MEDIUM:
                enemySpawnRateMultiplier = 4.0f;
                playerHealthMultiplier = 0.8f;
                scrollSpeedMultiplier = 2.0f;
                scoreThreshold = 500;
                System.out.println("Difficulty set to Medium");
                break;
            case HARD:
                enemySpawnRateMultiplier = 10.0f;
                playerHealthMultiplier = 0.6f;
                scrollSpeedMultiplier = 3.0f;
                scoreThreshold = 250;
                System.out.println("Difficulty set to Hard");
                break;
        }
    }

    public static float getEnemySpawnRateMultiplier() {
        return enemySpawnRateMultiplier;
    }

    public static float getPlayerHealthMultiplier() {
        return playerHealthMultiplier;
    }

    public static float getScrollSpeedMultiplier() {
        return scrollSpeedMultiplier;
    }

    public static void updateDifficultyBasedOnScore(int score) {
    	System.out.println("Checking difficulty update. Current Score: " + score + ", Threshold: " + scoreThreshold);
        if (score >= scoreThreshold) {
            increaseDifficulty();
        }
    }

    private static void increaseDifficulty() {
        if (difficulty == Difficulty.EASY) {
            setDifficulty(Difficulty.MEDIUM);
        } else if (difficulty == Difficulty.MEDIUM) {
            setDifficulty(Difficulty.HARD);
        }
    }
}
