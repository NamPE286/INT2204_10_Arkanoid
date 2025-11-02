package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.arkanoid.component.LaserComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.core.MovableObject;
import org.arkanoid.utilities.SchedulerUtils;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends MovableObject {
    private boolean canCatch = false;
    private TimerAction catchTimer = null;
    private final int SPEED = 400;
    private static Paddle target;

    public Paddle playInitAnimation() {
        var comp = entity.getComponent(PaddleAnimationComponent.class);
        comp.setInit(true);

        return this;
    }

    public Paddle delayInput(int ms) {
        setLinearVelocity(0, 0);
        FXGL.getInput().setProcessInput(false);

        FXGL.runOnce(() -> {
            FXGL.getInput().setProcessInput(true);
        }, Duration.millis(ms));

        return this;
    }

    @Override
    public Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .bbox(new HitBox(
                        "PADDLE",
                        new Point2D(0, 0),
                        BoundingShape.box(32, 8)))
                .with(new PaddleAnimationComponent())
                .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }

    @Override
    protected void initInput() {
        try {
            FXGL.getInput().addAction(new UserAction("LEFT") {
                @Override
                protected void onAction() {
                    target.setLinearVelocity(-SPEED, 0);
                }

                @Override
                protected void onActionEnd() {
                    target.setLinearVelocity(0, 0);
                }
            }, KeyCode.LEFT);
        } catch (IllegalArgumentException _) {
        }

        try {
            FXGL.getInput().addAction(new UserAction("RIGHT") {
                @Override
                protected void onAction() {
                    target.setLinearVelocity(SPEED, 0);
                }

                @Override
                protected void onActionEnd() {
                    target.setLinearVelocity(0, 0);
                }
            }, KeyCode.RIGHT);
        } catch (IllegalArgumentException _) {
        }

        try {
            FXGL.getInput().addAction(new UserAction("FIRE_LASER") {
                @Override
                protected void onActionBegin() {
                    if (target != null && target.getEntity() != null && target.getEntity()
                            .hasComponent(LaserComponent.class)) {
                        target.getEntity().getComponent(LaserComponent.class).fire();
                    }
                }
            }, KeyCode.SPACE);
        } catch (IllegalArgumentException _) {
        }
    }

    /**
     * Constructs a new paddle at the default position (0, 0).
     */
    public Paddle() {
        super();

        target = this;
    }

    /**
     * Constructs a new paddle at the specified coordinates.
     *
     * @param x the x-coordinate for the paddle's initial position
     * @param y the y-coordinate for the paddle's initial position
     */
    public Paddle(int x, int y) {
        super(x, y);
        spawn();
        initInput();

        target = this;
    }

    /**
     * Collision checking wall, power up with paddle.
     *
     * @param e the Entity that this object has collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        float vy = this.getVelocityY();
        if (e instanceof Wall) {
            float curVx = this.getVelocityX();

            if (curVx < 0 && this.getX() > e.getX()) {
                System.out.println("Paddle collision with Left wall");
                setLinearVelocity(0, vy);
            } else if (curVx > 0 && this.getX() < e.getX()) {
                System.out.println("Paddle collision with Right wall");
                setLinearVelocity(0, vy);
            }
        }
    }

    public void activeCatchPowerup() {
        canCatch = true;
        System.out.println("Paddle: Catch ability activated !");

        if (catchTimer != null) {
            catchTimer.expire();
        }
        // sau khi da chay 10s.
        catchTimer = FXGL.getGameTimer().runOnceAfter(() -> {
            canCatch = false;
            System.out.println("Paddle: Catch ability expired");
        }, Duration.seconds(10));
    }

    public boolean isCanCatch() {
        return canCatch;
    }

    public void useCatch() {
        canCatch = false;
        if (catchTimer != null) {
            catchTimer.expire();
            catchTimer = null;
        }
    }

    public static Paddle getTarget() {
        return target;
    }
}




