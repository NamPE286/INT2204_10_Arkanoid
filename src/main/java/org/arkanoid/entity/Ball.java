package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5),
                2.0
        );

        var e = entityBuilder(spawnData)
                .type(EntityType.BALL)
                .view(texture)
                .bbox(new HitBox("Ball", BoundingShape.circle(texture.getHeight() / 2.0)))
                .with(new PhysicsComponent())
                .build();
        e.setProperty("gameObject", this);

        physics = e.getComponent(PhysicsComponent.class);
        physics.setBodyType(BodyType.DYNAMIC);

        return e;
    }

    /**
     * Constructs a new ball at the default position (0, 0).
     */
    public Ball() {
        super();
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

    public void setVelocity(double vx, double vy) {
        physics.setLinearVelocity(vx, vy);
    }

    public void CollisionWith(Paddle paddle) {
        double vx = physics.getVelocityX();
        double vy = physics.getVelocityY();

        double paddleX = paddle.entity.getX();
        double paddleY = paddle.entity.getY();
        double paddleW = paddle.entity.getWidth();
        double paddleH = paddle.entity.getHeight();

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

        this.physics.setLinearVelocity(vx, vy);
    }
}
