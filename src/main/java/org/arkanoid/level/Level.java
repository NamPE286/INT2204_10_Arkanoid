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

/**
 * Represents a single playable level in the Arkanoid game.
 *
 * <p>The {@code Level} class manages all entities and interactions that occur
 * during a specific level, including the {@link Paddle}, {@link Ball},
 * {@link Brick}s, {@link Laser}s, and {@link Wall}s. It also handles
 * level initialization, progression, completion detection, and player death events.</p>
 *
 * <p>This class implements the {@link MonoBehaviour} interface to support
 * per-frame updates of in-game objects.</p>
 *
 * <h2>Main Responsibilities:</h2>
 * <ul>
 *     <li>Load brick configurations from a CSV file.</li>
 *     <li>Spawn and manage paddle, ball, and active lasers.</li>
 *     <li>Handle collision registration between objects.</li>
 *     <li>Detect when the level is completed or failed.</li>
 *     <li>Display UI messages like “ROUND” and “READY”.</li>
 * </ul>
 *
 * @see Paddle
 * @see Ball
 * @see Brick
 * @see Laser
 * @see BackgroundManager
 * @see SoundManager
 */
public class Level implements MonoBehaviour {

    private static final int DELAY_DURATION = 1500;
    private static final int HIDE_DURATION = 1500;
    private static final int WALL_THICKNESS = Main.WIDTH / 28;
    private boolean onCompletedCallbackCalled = false;
    private Runnable onCompletedCallback;
    private Runnable onDeathCallback;
    private final int id;
    private final Paddle paddle;
    private final Ball mainBall;
    private final List<Brick> bricks = new ArrayList<>();
    private final List<Laser> activeLasers = new ArrayList<>();
    private boolean ballOnPaddle = true;
    private final double BALL_OFFSET_X = 3;
    private static final int BRICK_OFFSET_X = 100;
    private static final int BRICK_OFFSET_Y = 150;
    private List<Ball> ballTwinslist = new ArrayList<>();


    final Wall leftwall = new Wall(0, 0, Main.HEIGHT, WALL_THICKNESS);
    final Wall topwall = new Wall(0, 48, WALL_THICKNESS, Main.WIDTH);
    final Wall rightwall = new Wall(Main.WIDTH - WALL_THICKNESS, 0, Main.HEIGHT, WALL_THICKNESS);

    /**
     * Destroys all game objects in the current level.
     * <p>This includes bricks, lasers, paddle, and all active balls.</p>
     */
    public void destroy() {
        for (var i : bricks) {
            i.destroy();
        }

        for (var laser : activeLasers) {
            laser.destroy();
        }
        activeLasers.clear();

        paddle.destroy();
        for (var b : ballTwinslist) {
            b.destroy();
        }
        ballTwinslist.clear();
        mainBall.destroy();

    }

    /**
     * Adds a new {@link Laser} to the list of active lasers in this level.
     *
     * @param laser the laser entity to add
     */
    public void addLaser(Laser laser) {
        activeLasers.add(laser);
    }

    /**
     * Adds a duplicate ball (for “Disrupt” power-up) to the level.
     *
     * @param twinBall the cloned {@link Ball} instance to add
     */
    public void addBall(Ball twinBall) {
        twinBall.listenToCollisionWith(paddle)
                .listenToCollisionWith(leftwall)
                .listenToCollisionWith(topwall)
                .listenToCollisionWith(rightwall);

        for (var brick : bricks) {
            if (brick.getEntity() != null && brick.getEntity().isActive()) {
                twinBall.listenToCollisionWith(brick);
            }
        }
        ballTwinslist.add(twinBall);
    }

    /**
     * Loads and initializes brick objects from a 2D configuration array.
     *
     * @param brickConfig a 2D array representing brick types and their parameters
     */
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

    public List<Ball> getBallTwinslist() {
        return ballTwinslist;
    }

    public Ball getMainBall() {
        return mainBall;
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

    /**
     * Handles the logic when all bricks are destroyed and the level is completed.
     */
    public void onCompleted() {
        paddle.getEntity().setVisible(false);
        mainBall.getEntity().setVisible(false);
        mainBall.setLinearVelocity(0, 0);

        FXGL.runOnce(() -> onCompletedCallback.run(), Duration.millis(500));
    }

    /**
     * Performs per-frame updates for all active game objects.
     *
     * @param deltaTime the time (in seconds) since the last frame
     */
    public void onUpdate(double deltaTime) {
        paddle.onUpdate(deltaTime);
        mainBall.onUpdate(deltaTime);
        Iterator<Ball> ballIterator = ballTwinslist.iterator();
        while (ballIterator.hasNext()) {
            Ball curBall = ballIterator.next();
            if (curBall.getEntity() != null && curBall.getEntity().isActive()) {
                curBall.onUpdate(deltaTime);
                if (curBall.isOutOfBound()) {
                    curBall.destroy();
                    ballIterator.remove();
                }
            } else {
                ballIterator.remove();
            }
        }
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

        if ((mainBall.isOutOfBound() && ballTwinslist.isEmpty()) && onDeathCallback != null) {
            onDeathCallback.run();
        }

    }

    /**
     * Displays the “ROUND” and “READY” text messages at the start of the level.
     */
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

    /**
     * Creates a new level with the given identifier.
     *
     * <p>This constructor loads the level’s configuration from a CSV file,
     * spawns the paddle and main ball, displays the “ROUND” and “READY” UI,
     * and prepares the level environment (walls, background, bricks, etc.).</p>
     *
     * @param id the identifier of the level (used to locate the corresponding CSV file)
     */
    public Level(int id) {
        this.id = id;

        var brickConfig = Objects.requireNonNull(
                LevelLoader.loadFromCSV(String.format("/levels/%d.csv", id)));

        paddle = (Paddle) new Paddle(Main.WIDTH / 2 - 16, Main.HEIGHT - 50)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(rightwall);

        mainBall = (Ball) new Ball(Main.WIDTH / 2 - 4, Main.HEIGHT - 61)
                .listenToCollisionWith(paddle)
                .listenToCollisionWith(leftwall)
                .listenToCollisionWith(topwall)
                .listenToCollisionWith(rightwall);

        showRoundInfo();

        paddle.hideFor(HIDE_DURATION);
        mainBall.hideFor(HIDE_DURATION);

        SchedulerUtils.setTimeout(paddle::playInitAnimation, HIDE_DURATION);

        reset(false);
        loadBrickConfig(brickConfig.getBrickMap());
        setBackground(brickConfig.getBackgroundId());

        for (var brick : bricks) {
            mainBall.listenToCollisionWith(brick);
        }
    }

    /**
     * Resets the level to its initial playable state.
     *
     * <p>Removes temporary components (like {@link ExtendComponent} or {@link LaserComponent}),
     * repositions the paddle and ball, and restarts the movement after a short delay.</p>
     *
     * @param playInit whether to play the paddle initialization animation
     */
    public void reset(boolean playInit) {
        if (paddle.getEntity().hasComponent(ExtendComponent.class)) {
            paddle.getEntity().removeComponent(ExtendComponent.class);
        }

        if (paddle.getEntity().hasComponent(LaserComponent.class)) {
            paddle.getEntity().removeComponent(LaserComponent.class);
        }

        paddle.setPosition(Main.WIDTH / 2 - 16, Main.HEIGHT - 50);
        mainBall.setPosition(Main.WIDTH / 2 - 4, Main.HEIGHT - 61);
        paddle.delayInput(HIDE_DURATION + DELAY_DURATION);

        if (playInit) {
            paddle.playInitAnimation();
        }

        mainBall.setLinearVelocity(0, 0);

        SchedulerUtils.setTimeout(() -> {
            mainBall.setLinearVelocity(250, -250);
        }, playInit ? DELAY_DURATION : HIDE_DURATION + DELAY_DURATION);

        SoundManager.play("round_start.mp3");
    }
}