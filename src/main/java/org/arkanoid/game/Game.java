package org.arkanoid.game;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.Paddle;
import org.arkanoid.level.Level;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.ui.GameOver;
import org.arkanoid.ui.LivesUI;

/**
 * Singleton quản lý level, mạng, reset bóng/paddle và Game Over.
 */
public class Game implements MonoBehaviour {

    private static Game instance;

    Level currentLevel;
    int levelIndex = 1;

    private int lives = 3;
    private LivesUI livesUI;

    private Ball ball;
    private Paddle paddle;

    private boolean ballOnPaddle = true;     // Bóng đang dính paddle
    private final double BALL_OFFSET_X = 3;  // Lệch phải nhẹ cho đẹp
    private boolean gameOver = false;        // Trạng thái game over

    // Singleton
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
     * Khởi tạo level và gán callback.
     */
    private void setLevel(int id) {
        currentLevel = new Level(id);

        // Lấy bóng + paddle từ level.
        ball = currentLevel.getBall();
        paddle = currentLevel.getPaddle();

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
        resetBallAndPaddle();
    }

    /**
     * Mất mạng.
     */
    private void loseLife() {
        lives--;
        FXGL.set("lives", lives);

        if (lives > 0) {
            resetBallAndPaddle();
        } else {
            SoundManager.play("death.wav");
            gameOver = true;
            new GameOver().show();
        }
    }

    /**
     * Reset bóng + paddle.
     * Bóng sẽ dính với paddle trong 3s rồi mới bay lên.
     */
    private void resetBallAndPaddle() {
        if (gameOver) return;

        ballOnPaddle = true;

        // Đưa paddle về giữa.
        paddle.getEntity().setX(FXGL.getAppWidth() / 2.0 - paddle.getEntity().getWidth() / 2);
        paddle.getEntity().setY(FXGL.getAppHeight() - 50);

        // Đặt bóng lên paddle.
        updateBallOnPaddle();
        ball.setLinearVelocity(0, 0);
        ball.setAttached(true); // Chặn va chạm lúc dính paddle

        SoundManager.play("round_start.mp3");

        // Sau 3 giây -> bóng bay.
        FXGL.runOnce(() -> {
            if (!gameOver) {
                ballOnPaddle = false;
                ball.setAttached(false);
                ball.setLinearVelocity(300f, -300f);
            }
        }, Duration.seconds(3));
    }

    /**
     * Giữ bóng trên paddle khi đang dính.
     */
    private void updateBallOnPaddle() {
        if (!ballOnPaddle) return;

        double paddleCenterX = paddle.getEntity().getX() + paddle.getEntity().getWidth() / 2.0;
        double ballHalfWidth = ball.getEntity().getWidth() / 2.0;

        ball.getEntity().setX(paddleCenterX - ballHalfWidth + BALL_OFFSET_X);
        ball.getEntity().setY(paddle.getEntity().getY() - ball.getEntity().getHeight() - 1);
    }

    /**
     * Update mỗi frame.
     */
    @Override
    public void onUpdate(double deltaTime) {
        if (gameOver) return;

        if (currentLevel != null)
            currentLevel.onUpdate(deltaTime);

        // Bóng bám paddle trước khi bay.
        if (ballOnPaddle) {
            updateBallOnPaddle();
            ball.setLinearVelocity(0, 0);
        }
    }
}
