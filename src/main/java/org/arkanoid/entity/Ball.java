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
        double factor = 3.0;
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5),
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

        double paddleX = e.getX();
        double paddleY = e.getY();
        double paddleW = e.getEntity().getWidth();
        double paddleH = e.getEntity().getHeight();

        double ballX = entity.getX();
        double ballY = entity.getY();
        double ballW = entity.getWidth();
        double ballH = entity.getHeight();

        double overlapLeft   = (ballX + ballW) - paddleX;
        double overlapRight  = (paddleX + paddleW) - ballX;
        double overlapTop    = (ballY + ballH) - paddleY;
        double overlapBottom = (paddleY + paddleH) - ballY;

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft) {
            vx = -(Math.abs(vx) + 5);
        } else if (minOverlap  == overlapRight) {
            vx = Math.abs(vx) + 5;
        } else if (minOverlap  == overlapTop) {
            vy = -Math.abs(vy);
        } else if (minOverlap  == overlapBottom) {
            vy = Math.abs(vy);
        }

        setLinearVelocity(vx, vy);
        System.out.println("Collide");
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
