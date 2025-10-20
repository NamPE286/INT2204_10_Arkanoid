package org.arkanoid.entity;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.utilities.TextureUtils;
import org.arkanoid.managers.SoundManager;
import org.arkanoid.utilities.Vec2Utils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {

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

        double minAngle = 5;
        double maxAngle = 55; // Hằng số để đảm bảo 5 độ <= góc <= 55 độ và giữ nguyên tốc độ bóng
        double ANGLE =
                Math.sin(Math.toRadians(maxAngle)) - Math.sin(Math.toRadians(minAngle));

        double nonLinearDistanceRatio = Math.pow(Math.abs(distanceRatio),
                0.5); // Hằng số để độ lệch của bóng không bị tuyến tính
        if (nonLinearDistanceRatio > 1) {
            nonLinearDistanceRatio = 1;
        }

        float tempVx = (float) ballSpeed * (
                (float) Math.abs(nonLinearDistanceRatio) * (float) ANGLE + (float) Math.sin(
                        Math.toRadians(minAngle))
        );
        float tempVy = (float) Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(tempVx, 2));

        if (distanceRatio > 0) {
            tempVy = -Math.abs(tempVy);
        } else if (distanceRatio < 0) {
            tempVx = -tempVx;
            tempVy = -Math.abs(tempVy);
        } else {
            tempVx = 0;
            tempVy = -Math.abs(tempVy);
        }

        System.out.println(String.format("(%.3f, %.3f)", tempVx, tempVy));

        setLinearVelocity(tempVx, tempVy);
        System.out.println("Collide with Paddle");
        SoundManager.play("ball_hit_1.wav");
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
