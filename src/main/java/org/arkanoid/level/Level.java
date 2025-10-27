package org.arkanoid.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.arkanoid.Main;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.entity.brick.HardBrick;
import org.arkanoid.entity.brick.NormalBrick;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.Wall;
import org.arkanoid.entity.brick.StrongBrick;
import org.arkanoid.manager.BackgroundManager;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.utilities.SchedulerUtils;

public class Level implements MonoBehaviour {

    Runnable onCompletedCallback;
    Runnable onDeathCallback;
    int id;
    Paddle paddle;
    Ball ball;
    List<Brick> bricks = new ArrayList<>();

    private void loadBrickConfig(int[][] brickConfig) {
        for (int i = 0; i < brickConfig.length; i++) {
            for (int j = 0; j < brickConfig[i].length - 2; j += 3) {
                if (brickConfig[i][j] == 0) {
                    continue;
                }

                if (brickConfig[i][j] == 1) {
                    bricks.add(new NormalBrick(
                            300 + 48 * (j / 3),
                            300 + 24 * i,
                            brickConfig[i][j + 1],
                            brickConfig[i][j + 2]
                    ).setPaddle(paddle));
                } else if (brickConfig[i][j] == 2) {
                    bricks.add(new StrongBrick(
                            300 + 48 * (j / 3),
                            300 + 24 * i,
                            brickConfig[i][j + 1],
                            brickConfig[i][j + 2]
                    ).setPaddle(paddle));
                } else if (brickConfig[i][j] == 3) {
                    bricks.add(new HardBrick(
                            300 + 48 * (j / 3),
                            300 + 24 * i,
                            brickConfig[i][j + 1],
                            brickConfig[i][j + 2]
                    ).setPaddle(paddle));
                }
            }
        }
    }

    public void setOnCompletedCallback(Runnable callback) {
        this.onCompletedCallback = callback;
    }

    public void setOnDeathCallback(Runnable onDeathCallback) {
        this.onDeathCallback = onDeathCallback;
    }

    public void setBackground(int id) {
        BackgroundManager backGround = BackgroundManager.getInstance();
        backGround.displayLevel(id);
    }

    public void onUpdate(double deltaTime) {
        paddle.onUpdate(deltaTime);
        ball.onUpdate(deltaTime);

        boolean isCompleted = true;

        for (var brick : bricks) {
            if (brick.getEntity() == null) {
                continue;
            }

            brick.onUpdate(deltaTime);

            if (brick.getHealth() > 0) {
                isCompleted = false;
            }
        }

        if (isCompleted && onCompletedCallback != null) {
            onCompletedCallback.run();
        }

        if(ball.isOutOfBound() && onDeathCallback != null) {
            onDeathCallback.run();
        }
    }

    public Level(int id) {
        this.id = id;

        final int WALL_THICKNESS = Main.WIDTH / 28;
        final int INIT_DELAY = 2500;

        var leftwall = new Wall(0, 0, Main.HEIGHT, WALL_THICKNESS);
        var topwall = new Wall(0, 48, WALL_THICKNESS, Main.WIDTH);
        var rightwall = new Wall(Main.WIDTH - WALL_THICKNESS, 0, Main.HEIGHT, WALL_THICKNESS);

        paddle = (Paddle) new Paddle(Main.WIDTH / 2 - 16, Main.HEIGHT - 50, INIT_DELAY)
            .addInitAnimation()
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(rightwall);

        var brickConfig = Objects.requireNonNull(
            LevelLoader.loadFromCSV(String.format("/levels/%d.csv", id)));

        loadBrickConfig(brickConfig.getBrickMap());
        setBackground(brickConfig.getBackgroundId());

        ball = (Ball) new Ball(Main.WIDTH / 2 - 4, Main.HEIGHT - 61)
            .listenToCollisionWith(paddle)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(topwall)
            .listenToCollisionWith(rightwall);

        SchedulerUtils.setTimeout(() -> {
            ball.setLinearVelocity(300, -300);
        }, INIT_DELAY);

        for (var brick : bricks) {
            ball.listenToCollisionWith(brick);
        }

        SoundManager.play("round_start.mp3");
    }
    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
