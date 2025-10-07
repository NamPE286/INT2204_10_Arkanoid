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

    @Override
    public void onCollisionWith(GameObject e) {
        float vx = this.getVelocityX();
        float vy = this.getVelocityY();

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
                double angularConstant = Math.sqrt(3) - 1; // Hằng số để đảm bảo góc tối đa của bóng với paddle là 60 độ
                float tempVx = vy * (float)distanceRatio * (float)angularConstant + vy;
                if (vx < 0) {
                    vx = -tempVx;
                } else {
                    vx = tempVx;
                }

                vy = -Math.abs(vy);
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
        // Xử lí khi va chạm tường
        if (entity.getX() < 0 || entity.getX() + entity.getWidth()/2 > 800) {
            setLinearVelocity(this.getVelocityX() * -1, this.getVelocityY());
        }

        if (entity.getY() < 0 || entity.getY() + entity.getHeight()/2 > 600) {
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
