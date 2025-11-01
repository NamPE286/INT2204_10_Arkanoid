package org.arkanoid.game;

import com.almasb.fxgl.dsl.FXGL;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;
import org.arkanoid.manager.BackgroundManager;
import org.arkanoid.manager.HighScoreManager;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.ui.GameOver;
import org.arkanoid.ui.LivesUI;
import org.arkanoid.ui.ScoreBoard;

/**
 * Singleton quản lý level, mạng, reset bóng/paddle và Game Over.
 */
public class Game implements MonoBehaviour {

    private boolean gameOver = false;
    private static Game instance;
    private Level currentLevel;
    private int levelIndex = 1;
    private int lives = 3;
    private LivesUI livesUI;
    private ScoreBoard scoreBoard;

    public void destroy() {
        currentLevel.destroy();
    }


    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public static Game reInit() {
        if (instance != null) {
            instance.destroy();

            BackgroundManager.reset();

            instance = null;
        }

        return getInstance();
    }

    public Game() {

        int savedHighScore = HighScoreManager.loadHighScore();
        FXGL.set("highScore", savedHighScore);

        setLevel(levelIndex);
    }

    /**
     * Mất mạng.
     */
    private void loseLife() {
        lives--;
        FXGL.set("lives", lives);

        if (lives > 0) {
            currentLevel.reset();

        } else {
            SoundManager.play("death.wav");
            gameOver = true;
            GameOver.show();
        }
    }

    /**
     * Cộng điểm vào score.
     */
    public void addScore(int points) {

        int currentScore = FXGL.geti("score");
        int newScore = currentScore + points;

        FXGL.set("score", newScore);

        if (newScore >= FXGL.geti("highScore")) {
            FXGL.set("highScore", newScore);
            HighScoreManager.saveHighScore(newScore);
        }
    }

    /**
     * Khởi tạo level và gán callback.
     */
    private void setLevel(int id) {
        if (currentLevel != null) {
            currentLevel.destroy();
        }

        currentLevel = new Level(id);
        currentLevel.setOnDeathCallback(this::loseLife);
        currentLevel.setOnCompletedCallback(() -> setLevel(id + 1));
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Khởi tạo UI mạng và reset bóng ở lần đầu.
     */
    public void initUI() {
        livesUI = new LivesUI();
        livesUI.render();
        scoreBoard = new ScoreBoard();
        FXGL.set("lives", lives);
    }

    /**
     * Update mỗi frame.
     */
    @Override
    public void onUpdate(double deltaTime) {
        if (gameOver) {
            return;
        }

        if (currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }
}
