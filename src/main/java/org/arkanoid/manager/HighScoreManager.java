package org.arkanoid.manager;


import java.io.*;

public class HighScoreManager {

    private static final String FILE_NAME = "highscore.txt";

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

    public static void saveHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
