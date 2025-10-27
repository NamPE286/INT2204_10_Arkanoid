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
import org.arkanoid.component.ExtendComponent;

public class Level implements MonoBehaviour {

    private final int DELAY = 2500;
    private final int WALL_THICKNESS = Main.WIDTH / 28;
    private Runnable onCompletedCallback;
    private Runnable onDeathCallback;
    private int id;
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks = new ArrayList<>();
    private boolean ballOnPaddle = true;     // Bóng đang dính paddle
    private final double BALL_OFFSET_X = 3;  // Lệch phải nhẹ cho đẹp
    private static final int BRICK_OFFSET_X = 100;
    private static final int BRICK_OFFSET_Y = 150;

    Wall leftwall = new Wall(0, 0, Main.HEIGHT, WALL_THICKNESS);
    Wall topwall = new Wall(0, 48, WALL_THICKNESS, Main.WIDTH);
    Wall rightwall = new Wall(Main.WIDTH - WALL_THICKNESS, 0, Main.HEIGHT, WALL_THICKNESS);


    private void loadBrickConfig(int[][] brickConfig) {
        for (int i = 0; i < brickConfig.length; i++) {
            for (int j = 0; j < brickConfig[i].length - 2; j += 3) {
                if (brickConfig[i][j] == 0) {
                    continue;
                }

                if (brickConfig[i][j] == 1) {
                    bricks.add(new NormalBrick(
                        BRICK_OFFSET_X + 46 * (j / 3),
                        BRICK_OFFSET_Y + 22 * i,
                        brickConfig[i][j + 1],
                        brickConfig[i][j + 2]
                    ).setPaddle(paddle));
                } else if (brickConfig[i][j] == 2) {
                    bricks.add(new StrongBrick(
                        BRICK_OFFSET_X + 46 * (j / 3),
                        BRICK_OFFSET_Y + 22 * i,
                        brickConfig[i][j + 1],
                        brickConfig[i][j + 2]
                    ).setPaddle(paddle));
                } else if (brickConfig[i][j] == 3) {
                    bricks.add(new HardBrick(
                        BRICK_OFFSET_X + 46 * (j / 3),
                        BRICK_OFFSET_Y + 22 * i,
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

        if (ball.isOutOfBound() && onDeathCallback != null) {
            onDeathCallback.run();
        }
    }

    public Level(int id) {
        this.id = id;

        var brickConfig = Objects.requireNonNull(
            LevelLoader.loadFromCSV(String.format("/levels/%d.csv", id)));

        paddle = (Paddle) new Paddle(Main.WIDTH / 2 - 16, Main.HEIGHT - 50)
            .playInitAnimation()
            .delayInput(DELAY)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(rightwall);

        ball = (Ball) new Ball(Main.WIDTH / 2 - 4, Main.HEIGHT - 61)
            .listenToCollisionWith(paddle)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(topwall)
            .listenToCollisionWith(rightwall);

        reset();
        loadBrickConfig(brickConfig.getBrickMap());
        setBackground(brickConfig.getBackgroundId());

        for (var brick : bricks) {
            ball.listenToCollisionWith(brick);
        }
    }

    public void reset() {
        // Xu li truong hop paddle dang to thi chet
        if (paddle.getEntity().hasComponent(ExtendComponent.class)) {
            paddle.getEntity().removeComponent(ExtendComponent.class);
        }

        paddle.setPosition(Main.WIDTH / 2 - 16, Main.HEIGHT - 50);
        ball.setPosition(Main.WIDTH / 2 - 4, Main.HEIGHT - 61);

        paddle.delayInput(DELAY);
        paddle.playInitAnimation();
        ball.setLinearVelocity(0, 0);

        SchedulerUtils.setTimeout(() -> {
            ball.setLinearVelocity(300, -300);
        }, DELAY);

        SoundManager.play("round_start.mp3");
    }
}
