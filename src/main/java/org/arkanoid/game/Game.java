package org.arkanoid.game;

import com.almasb.fxgl.dsl.FXGL;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.factory.LabelFactory;
import org.arkanoid.level.Level;
import org.arkanoid.manager.BackgroundManager;
import org.arkanoid.manager.HighScoreManager;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.ui.*;

/**
 * Singleton quản lý level, mạng, reset bóng/paddle và Game Over.
 */
public class Game implements MonoBehaviour {

    private static final int MAX_LEVEL = 3;
    private boolean gameOver = false;
    private static Game instance;
    private Level currentLevel;
    private int levelIndex = 1;
    private int lives = 3;
    private LivesUI livesUI;
    private ScoreBoard scoreBoard;
    private double elapsedTime = 0.0;
    public static final int SCORE_NORMAL_BRICK = 10;
    public static final int SCORE_STRONG_BRICK = 20;

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
        elapsedTime = 0.0;
        LabelFactory.setGlobalFont("/fonts/nes.otf", 24);
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
            currentLevel.reset(true);

        } else {
            SoundManager.play("death.wav");
            gameOver = true;
            GameEndScreen.show(false);
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
        currentLevel.setOnCompletedCallback(() -> {
            if (id >= MAX_LEVEL) {
                GameEndScreen.show(true);
            } else {
                setLevel(id + 1);
            }
        });
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

        elapsedTime += deltaTime;
        FXGL.set("time", (int)elapsedTime);

        if (currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }
}
