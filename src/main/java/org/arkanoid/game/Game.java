package org.arkanoid.game;

import com.almasb.fxgl.dsl.FXGL;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.ui.GameOver;
import org.arkanoid.ui.LivesUI;

/**
 * Singleton quản lý level, mạng, reset bóng/paddle và Game Over.
 */
public class Game implements MonoBehaviour {
    private boolean gameOver = false;        // Trạng thái game over
    private static Game instance;

    Level currentLevel;
    int levelIndex = 1;

    private int lives = 3;
    private LivesUI livesUI;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Game() {
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
     * Khởi tạo level và gán callback.
     */
    private void setLevel(int id) {
        currentLevel = new Level(id);

        // Khi chết -> xử lý mất mạng.
        currentLevel.setOnDeathCallback(this::loseLife);

        // Khi phá xong level.
        currentLevel.setOnCompletedCallback(() -> {
            System.out.println("Level completed!");
        });
    }

    /**
     * Khởi tạo UI mạng và reset bóng ở lần đầu.
     */
    public void initUI() {
        livesUI = new LivesUI();
        livesUI.render();
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
