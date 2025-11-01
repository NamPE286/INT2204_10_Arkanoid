package org.arkanoid.level;

import com.almasb.fxgl.dsl.FXGL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.arkanoid.Main;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.component.LaserComponent;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.Laser;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.entity.brick.HardBrick;
import org.arkanoid.entity.brick.NormalBrick;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.Wall;
import org.arkanoid.entity.brick.StrongBrick;
import org.arkanoid.factory.LabelFactory;
import org.arkanoid.manager.BackgroundManager;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.utilities.SchedulerUtils;
import org.arkanoid.component.ExtendComponent;

public class Level implements MonoBehaviour {

    private static final int DELAY_DURATION = 1500;
    private static final int HIDE_DURATION = 1500;
    private static final int WALL_THICKNESS = Main.WIDTH / 28;
    private boolean onCompletedCallbackCalled = false;
    private Runnable onCompletedCallback;
    private Runnable onDeathCallback;
    private final int id;
    private final Paddle paddle;
    private final Ball ball;
    private final List<Brick> bricks = new ArrayList<>();
    private final List<Laser> activeLasers = new ArrayList<>();
    private boolean ballOnPaddle = true;
    private final double BALL_OFFSET_X = 3;
    private static final int BRICK_OFFSET_X = 100;
    private static final int BRICK_OFFSET_Y = 150;

    final Wall leftwall = new Wall(0, 0, Main.HEIGHT, WALL_THICKNESS);
    final Wall topwall = new Wall(0, 48, WALL_THICKNESS, Main.WIDTH);
    final Wall rightwall = new Wall(Main.WIDTH - WALL_THICKNESS, 0, Main.HEIGHT, WALL_THICKNESS);

    public void destroy() {
        for (var i : bricks) {
            i.destroy();
        }

        for (var laser : activeLasers) {
            laser.destroy();
        }
        activeLasers.clear();

        paddle.destroy();
        ball.destroy();
    }

    public void addLaser(Laser laser) {
        activeLasers.add(laser);
    }

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

    public List<Brick> getBricks() {
        return bricks;
    }

    public Wall getLeftwall() {
        return leftwall;
    }

    public Wall getTopwall() {
        return topwall;
    }

    public Wall getRightwall() {
        return rightwall;
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

    public void onCompleted() {
        paddle.getEntity().setVisible(false);
        ball.getEntity().setVisible(false);
        ball.setLinearVelocity(0, 0);

        FXGL.runOnce(() -> onCompletedCallback.run(), Duration.millis(500));
    }

    public void onUpdate(double deltaTime) {
        paddle.onUpdate(deltaTime);
        ball.onUpdate(deltaTime);

        // Dùng Iterator để có thể xóa phần tử (nếu laser bị hủy) trong khi lặp
        Iterator<Laser> laserIterator = activeLasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            if (laser.getEntity() != null && laser.getEntity().isActive()) {
                laser.onUpdate(deltaTime);
            } else {
                laserIterator.remove();
            }
        }

        boolean isCompleted = true;

        for (var brick : bricks) {
            if (brick.getEntity() == null) {
                continue;
            }

            brick.onUpdate(deltaTime);

            if (brick.getHealth() > 0 && brick.isCanDestroy()) {
                isCompleted = false;
            }
        }

        if (isCompleted && onCompletedCallback != null && !onCompletedCallbackCalled) {
            onCompleted();
            onCompletedCallbackCalled = true;
        }

        if (ball.isOutOfBound() && onDeathCallback != null) {
            onDeathCallback.run();
        }
    }

    private void showRoundInfo() {
        var roundLabel = LabelFactory.createLabel(String.format("ROUND %d", id));
        roundLabel.setTextFill(Color.WHITE);
        roundLabel.setTranslateX((Main.WIDTH - 160) / 2);
        roundLabel.setTranslateY(500);

        var readyLabel = LabelFactory.createLabel("READY");
        readyLabel.setTextFill(Color.WHITE);
        readyLabel.setTranslateX((Main.WIDTH - 120) / 2);
        readyLabel.setTranslateY(550);

        FXGL.getGameScene().addUINode(roundLabel);

        FXGL.runOnce(() -> {
            FXGL.getGameScene().addUINode(readyLabel);
        }, Duration.seconds(0.5));

        FXGL.runOnce(() -> {
            FXGL.getGameScene().removeUINode(roundLabel);
            FXGL.getGameScene().removeUINode(readyLabel);
        }, Duration.millis(HIDE_DURATION));
    }

    public Level(int id) {
        this.id = id;

        var brickConfig = Objects.requireNonNull(
            LevelLoader.loadFromCSV(String.format("/levels/%d.csv", id)));

        paddle = (Paddle) new Paddle(Main.WIDTH / 2 - 16, Main.HEIGHT - 50)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(rightwall);

        ball = (Ball) new Ball(Main.WIDTH / 2 - 4, Main.HEIGHT - 61)
            .listenToCollisionWith(paddle)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(topwall)
            .listenToCollisionWith(rightwall);

        showRoundInfo();

        paddle.hideFor(HIDE_DURATION);
        ball.hideFor(HIDE_DURATION);

        SchedulerUtils.setTimeout(paddle::playInitAnimation, HIDE_DURATION);

        reset(false);
        loadBrickConfig(brickConfig.getBrickMap());
        setBackground(brickConfig.getBackgroundId());

        for (var brick : bricks) {
            ball.listenToCollisionWith(brick);
        }
    }

    public void reset(boolean playInit) {
        if (paddle.getEntity().hasComponent(ExtendComponent.class)) {
            paddle.getEntity().removeComponent(ExtendComponent.class);
        }

        if (paddle.getEntity().hasComponent(LaserComponent.class)) {
            paddle.getEntity().removeComponent(LaserComponent.class);
        }

        paddle.setPosition(Main.WIDTH / 2 - 16, Main.HEIGHT - 50);
        ball.setPosition(Main.WIDTH / 2 - 4, Main.HEIGHT - 61);
        paddle.delayInput(HIDE_DURATION + DELAY_DURATION);

        if (playInit) {
            paddle.playInitAnimation();
        }

        ball.setLinearVelocity(0, 0);

        SchedulerUtils.setTimeout(() -> {
            ball.setLinearVelocity(250, -250);
        }, playInit ? DELAY_DURATION : HIDE_DURATION + DELAY_DURATION);

        SoundManager.play("round_start.mp3");
    }
}
