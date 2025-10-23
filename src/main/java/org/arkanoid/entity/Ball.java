package org.arkanoid.entity;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.utilities.TextureUtils;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.utilities.Vec2Utils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {

    private int paddleCollisionSound = 0;

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5);

        var e = entityBuilder(spawnData)
            .type(EntityType.PADDLE)
            .viewWithBBox(texture)
            .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }

    /**
     * Handles collision logic between the ball and another game object.
     *
     * @param e the {@link GameObject} that the ball collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        if (e instanceof Paddle) {
            this.onCollisionWith((Paddle) e);
        } else if (e instanceof Brick) {
            this.onCollisionWith((Brick) e);
        } else if (e instanceof Wall) {
            this.onCollisionWith((Wall) e);
        }
    }

    /**
     * Handles collision logic between the ball and paddle.
     *
     * <p>
     * When colliding with a {@link Paddle}, the bounce angle depends on how far the ball hits from
     * the paddle's center, producing a dynamic reflection. The bounce angle is constrained between
     * 35° and 55°, and the total velocity (speed) of the ball remains constant.
     * </p>
     *
     * @param paddle the {@link Paddle} that the ball collided with
     */
    public void onCollisionWith(Paddle paddle) {
        double ballSpeed = this.getLinearVelocity().length();

        double ballCenter = entity.getX() + entity.getWidth() / 2;
        double paddleCenter = paddle.getX() + paddle.getEntity().getWidth() / 2;
        double haftPaddleWidth = paddle.getEntity().getWidth() / 2;

        double distanceBallToPaddleCenter = ballCenter - paddleCenter;
        double distanceRatio = distanceBallToPaddleCenter / haftPaddleWidth;
        distanceRatio = Math.clamp(distanceRatio, -1.0, 1.0);
        double nonLinearDistanceRatio = Math.pow(Math.abs(distanceRatio),
            0.5); // Hằng số để độ lệch của bóng không bị tuyến tính

        if (distanceRatio < 0) {
            nonLinearDistanceRatio *= -1;
        }

        double ANGLE = 90 - (55
            * nonLinearDistanceRatio); // Hằng số để đảm bảo góc xiên <= 55 độ và giữ nguyên tốc độ bóng

        double vx = ballSpeed * Math.cos(Math.toRadians(ANGLE));
        double vy = ballSpeed * Math.sin(Math.toRadians(ANGLE));
        setLinearVelocity((float) vx, (float) -vy);

        System.out.println(String.format("(%.3f, %.3f)", vx, vy));
        System.out.println("Collide with Paddle");

        if (paddleCollisionSound == 0) {
            SoundManager.play("ball_hit_1.wav");
        } else {
            SoundManager.play("ball_hit_2.wav");
        }

        paddleCollisionSound = 1 - paddleCollisionSound;
    }

    /**
     * Handles collision logic between the ball and brick.
     *
     * <p>
     * When colliding with a {@link Brick}, the ball simply reverses its velocity along the axis of
     * collision and triggers the brick’s destruction.
     * </p>
     *
     * @param brick the {@link Brick} that the ball collided with
     */
    public void onCollisionWith(Brick brick) {
        Vec2 newVelocity = Vec2Utils.flip(this.getLinearVelocity(), this, brick);
        setLinearVelocity(newVelocity.x, newVelocity.y);

        System.out.println("Collide with brick");
        brick.destroy();
    }

    /**
     * Handles collision logic between the ball and wall.
     *
     * <p>
     * When colliding with a {@link Wall}, the ball simply reverses its velocity along the axis of
     * collision and triggers the brick’s destruction.
     * </p>
     *
     * @param wall the {@link Wall} that the ball collided with
     */
    public void onCollisionWith(Wall wall) {
        Vec2 newVelocity = Vec2Utils.flip(this.getLinearVelocity(), this, wall);
        setLinearVelocity(newVelocity.x, newVelocity.y);

        System.out.println("Collide with wall");
    }

    /**
     * Constructs a new ball at the default position (0, 0).
     */
    public Ball() {
        super();
    }

    @Override
    public void onUpdate(double deltaTime) {
        super.onUpdate(deltaTime);

        // Va chạm tường
        if (entity.getX() + entity.getWidth() / 2 >= 672 ||
            entity.getX() <= 0) {
            setLinearVelocity(this.getVelocityX() * -1, this.getVelocityY());
        }
        if (entity.getY() + entity.getHeight() / 2 >= 768 ||
            entity.getY() <= 0) {
            setLinearVelocity(this.getVelocityX(), this.getVelocityY() * -1);
        }
    }

    /**
     * Constructs a new ball at the specified coordinates.
     *
     * @param x the x-coordinate for the ball's initial position
     * @param y the y-coordinate for the ball's initial position
     */
    public Ball(int x, int y) {
        super(x, y);
    }
}
