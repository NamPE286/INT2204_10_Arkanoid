package org.arkanoid.manager;


import java.io.*;

public class HighScoreManager {

    private static final String FILE_NAME = "highscore.txt";

    /**
     * Loads the current high score from the save file.
     * <p>
     * If the file does not exist, is empty, or contains invalid data,
     * this method returns {@code 0}.
     * </p>
     *
     * @return the saved high score, or {@code 0} if not available
     */
    public static int loadHighScore() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Saves the given score as the new high score.
     * <p>
     * This method overwrites the existing file (if any) and replaces
     * its contents with the provided score value.
     * </p>
     *
     * @param score the score value to save as the new high score
     */
    public static void saveHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
