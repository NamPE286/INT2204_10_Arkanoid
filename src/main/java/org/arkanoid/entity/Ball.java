package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        double factor = 1.0;
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus(1).png"), 0, 40*2, 4*2, 5*2),
                factor
        );

        return entityBuilder(spawnData)
                .type(EntityType.BALL)
                .view(texture)
                .bbox(new HitBox(BoundingShape.box(
                        texture.getWidth() * factor,
                        texture.getHeight() * factor)))
                .build();
    }

    /**
     * Handles collision logic between the ball and another game object.
     * <p>
     * When colliding with a {@link Paddle}, the bounce angle depends on how far
     * the ball hits from the paddle's center, producing a dynamic reflection.
     * The bounce angle is constrained between 35° and 55°, and the total
     * velocity (speed) of the ball remains constant.
     * </p>
     *
     * <p>
     * When colliding with a {@link Brick}, the ball simply reverses its velocity
     * along the axis of collision and triggers the brick’s destruction.
     * </p>
     *
     * @param e the {@link GameObject} that the ball collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        float vx = this.getVelocityX();
        float vy = this.getVelocityY();
        double ballSpeed = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));

        double ballX = entity.getX();
        double ballY = entity.getY();
        double ballW = entity.getWidth();
        double ballH = entity.getHeight();

        double eX = e.getX();
        double eY = e.getY();
        double eW = e.getEntity().getWidth();
        double eH = e.getEntity().getHeight();

        double overlapLeft   = (ballX + ballW) - eX;
        double overlapRight  = (eX + eW) - ballX;
        double overlapTop    = (ballY + ballH) - eY;
        double overlapBottom = (eY + eH) - ballY;

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (e instanceof Paddle) {
            double ballCenter = ballX + ballW / 2;
            double paddleCenter = eX + eW / 2;
            double haftPaddleWidth = eW / 2;


            double distanceBallToPaddleCenter = Math.abs(paddleCenter - ballCenter);
            double distanceRatio = distanceBallToPaddleCenter / haftPaddleWidth;

            if (minOverlap == overlapLeft) {
                vx = -(Math.abs(vx) + 5);
            } else if (minOverlap  == overlapRight) {
                vx = Math.abs(vx) + 5;
            } else if (minOverlap  == overlapTop) {
                double ANGLE = Math.sin(Math.toRadians(55)) - Math.sin(Math.toRadians(0)); // Hằng số để đảm bảo 35 độ <= góc <= 55 độ và giữ nguyên tốc độ bóng
                float tempVx = (float)ballSpeed * (
                        (float)distanceRatio * (float)ANGLE + (float)Math.sin(Math.toRadians(0))
                );
                float tempVy = (float)Math.sqrt(Math.pow(ballSpeed, 2) - Math.pow(tempVx, 2));

                if (vx < 0) {
                    vx = -tempVx;
                    vy = -Math.abs(tempVy);
                } else if (vx > 0) {
                    vx = tempVx;
                    vy = -Math.abs(tempVy);
                } else {
                    vx = 0;
                    vy = -Math.abs(vy);
                }

                System.out.println(String.format("(%.3f, %.3f)", vx, vy));
            } else if (minOverlap  == overlapBottom) {
                vy = Math.abs(vy);
            }

            setLinearVelocity(vx, vy);
            System.out.println("Collide with Paddle");
        } else if (e instanceof Brick) {
            if (minOverlap == overlapLeft) {
                vx = -Math.abs(vx);
            } else if (minOverlap  == overlapRight) {
                vx = Math.abs(vx);
            } else if (minOverlap  == overlapTop) {
                vy = -Math.abs(vy);
            } else if (minOverlap  == overlapBottom) {
                vy = Math.abs(vy);
            }

            setLinearVelocity(vx, vy);
            System.out.println("Collide with brick");
            ((Brick) e).destroy();
        }
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
        if (entity.getX() + entity.getWidth()/2 >= 800 ||
            entity.getX() <= 0) {
            setLinearVelocity(this.getVelocityX() * -1, this.getVelocityY());
        }
        if (entity.getY() + entity.getHeight()/2 >= 600 ||
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
