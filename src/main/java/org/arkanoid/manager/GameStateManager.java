package org.arkanoid.manager;

import java.io.*;

/**
 * Manager for saving and loading game state to allow continuing from previous session.
 * Saves: current level, lives, score, elapsed time
 * Does NOT save: brick configuration (bricks are loaded fresh from level files)
 */
public class GameStateManager {

    private static final String FILE_NAME = "gamestate.txt";

    public static class GameState {
        private int levelIndex;
        private int lives;
        private int score;
        private int elapsedTime;

        public GameState(int levelIndex, int lives, int score, int elapsedTime) {
            this.levelIndex = levelIndex;
            this.lives = lives;
            this.score = score;
            this.elapsedTime = elapsedTime;
        }

        public int getLevelIndex() {
            return levelIndex;
        }

        public int getLives() {
            return lives;
        }

        public int getScore() {
            return score;
        }

        public int getElapsedTime() {
            return elapsedTime;
        }
    }

    /**
     * Save current game state to file
     */
    public static void saveGameState(int levelIndex, int lives, int score, int elapsedTime) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(levelIndex + "\n");
            bw.write(lives + "\n");
            bw.write(score + "\n");
            bw.write(elapsedTime + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load saved game state from file
     * @return GameState object if file exists and is valid, null otherwise
     */
    public static GameState loadGameState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int levelIndex = Integer.parseInt(br.readLine().trim());
            int lives = Integer.parseInt(br.readLine().trim());
            int score = Integer.parseInt(br.readLine().trim());
            int elapsedTime = Integer.parseInt(br.readLine().trim());
            
            return new GameState(levelIndex, lives, score, elapsedTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a saved game state exists
     */
    public static boolean hasSavedGame() {
        File file = new File(FILE_NAME);
        return file.exists();
    }

    /**
     * Delete saved game state (e.g., when game is completed or game over)
     */
    public static void clearGameState() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}
