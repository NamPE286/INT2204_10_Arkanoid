package org.arkanoid.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;
import org.arkanoid.component.animation.LaserPaddleAnimationComponent;
import org.arkanoid.component.animation.LaserTransformAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.entity.Laser;
import org.arkanoid.entity.Wall;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.game.Game;
import org.arkanoid.manager.SoundManager;

import java.util.List;

public class LaserComponent extends Component {
    private int ammo = 10;
    private final int PADDLE_WIDTH = 32;
    private final int PADDLE_HEIGHT = 8;
    private static final Duration TRANSFORM_DURATION = Duration.seconds(0.5);

    private TimerAction onAddedTimer;
    private TimerAction onRemovedTimer;

    /** Creates a new {@code LaserComponent} with default ammo. */
    public LaserComponent() {

    }

    /**
     * Adds extra laser ammo to the paddle.
     *
     * @param amount number of shots to add
     */
    public void addAmmo(int amount) {
        this.ammo += amount;
    }

    /**
     * Fires two laser beams upward from the paddle, one from each side.
     *
     * <p>If there is no ammo remaining, the component removes itself
     * from the entity, reverting the paddle to its normal state.
     */
    public void fire() {
        if (ammo > 0) {
            double paddleCenterX = entity.getX() + entity.getWidth() / 2;
            double paddleY = entity.getY();
            final int LASER_HEIGHT = 11;
            double x1 = paddleCenterX - 14;
            double x2 = paddleCenterX + 14;
            double y = paddleY - LASER_HEIGHT;

            Laser laser1 = new Laser((int)x1, (int)y, "LEFT");
            Laser laser2 = new Laser((int)x2, (int)y, "RIGHT");

            if (Game.getInstance().getCurrentLevel() != null) {
                Game.getInstance().getCurrentLevel().addLaser(laser1);
                Game.getInstance().getCurrentLevel().addLaser(laser2);

                List<Brick> bricks = Game.getInstance().getCurrentLevel().getBricks();
                Wall leftWall = Game.getInstance().getCurrentLevel().getLeftwall();
                Wall rightWall = Game.getInstance().getCurrentLevel().getRightwall();
                Wall topWall = Game.getInstance().getCurrentLevel().getTopwall();
                for (Brick brick : bricks) {
                    if (brick.getEntity() != null && brick.getEntity().isActive()) {
                        laser1.listenToCollisionWith(brick)
                                .listenToCollisionWith(leftWall)
                                .listenToCollisionWith(rightWall)
                                .listenToCollisionWith(topWall);
                        laser2.listenToCollisionWith(brick)
                                .listenToCollisionWith(leftWall)
                                .listenToCollisionWith(rightWall)
                                .listenToCollisionWith(topWall);
                    }
                }
            }

            SoundManager.play("paddle_fire.wav");
            ammo -= 2;

            if (ammo <= 0) {
                if (entity != null) {
                    entity.removeComponent(LaserComponent.class);
                }
            }
        } else {
            if (entity != null) {
                entity.removeComponent(LaserComponent.class);
            }
        }
    }

    /**
     * Called when this component is added to an entity.
     *
     * <p>This method:
     * <ul>
     *     <li>Removes conflicting components (e.g. {@link ExtendComponent}).</li>
     *     <li>Adds a {@link LaserTransformAnimationComponent} to play the transition.</li>
     *     <li>After the animation, replaces it with a {@link LaserPaddleAnimationComponent}.</li>
     *     <li>Resets the hitbox to the paddle’s base dimensions.</li>
     * </ul>
     */
    @Override
    public void onAdded() {
        if (entity.hasComponent(PaddleAnimationComponent.class)) {
            entity.removeComponent(PaddleAnimationComponent.class);
        }

        if (entity.hasComponent(ExtendComponent.class)) {
            entity.removeComponent(ExtendComponent.class);
        }

        if (entity.hasComponent(PaddleAnimationComponent.class)) {
            entity.removeComponent(PaddleAnimationComponent.class);
        }

        entity.addComponent(new LaserTransformAnimationComponent("InIt"));

        onAddedTimer = FXGL.runOnce(() -> {
            if (entity != null && entity.isActive()) {
                entity.removeComponent(LaserTransformAnimationComponent.class);
                entity.addComponent(new LaserPaddleAnimationComponent());
            }

        }, TRANSFORM_DURATION);

        entity.getBoundingBoxComponent().clearHitBoxes();
        entity.getBoundingBoxComponent().addHitBox(
                new HitBox(BoundingShape.box(PADDLE_WIDTH, PADDLE_HEIGHT))
        );

        entity.setX(entity.getX());
    }

    /**
     * Called when this component is removed from an entity.
     *
     * <p>This method:
     * <ul>
     *     <li>Cancels active timers.</li>
     *     <li>Removes the {@link LaserPaddleAnimationComponent}.</li>
     *     <li>Restores the normal paddle animation (unless extended).</li>
     *     <li>Plays the “out” transition animation via {@link LaserTransformAnimationComponent}.</li>
     *     <li>Restores the hitbox and paddle position.</li>
     * </ul>
     */
    @Override
    public void onRemoved() {
        if (onAddedTimer != null && !onAddedTimer.isExpired()) {
            onAddedTimer.expire();
        }

        if (onRemovedTimer != null && !onRemovedTimer.isExpired()) {
            onRemovedTimer.expire();
        }

        if (entity == null || !entity.isActive()) {
            return;
        }
        final Entity localEntity = entity;


        if (localEntity.hasComponent(LaserPaddleAnimationComponent.class)) {
            localEntity.removeComponent(LaserPaddleAnimationComponent.class);
        }

        if (!localEntity.hasComponent(ExtendComponent.class)) {
            localEntity.addComponent(new PaddleAnimationComponent());
        }

        entity.addComponent(new LaserTransformAnimationComponent("OutIt"));

        onRemovedTimer = FXGL.runOnce(() -> {
            if (localEntity != null && localEntity.isActive()) {
                localEntity.removeComponent(LaserTransformAnimationComponent.class);
            }

        }, TRANSFORM_DURATION);

        localEntity.getBoundingBoxComponent().clearHitBoxes();
        localEntity.getBoundingBoxComponent().addHitBox(
                new HitBox(BoundingShape.box(PADDLE_WIDTH, PADDLE_HEIGHT))
        );

        localEntity.setX(localEntity.getX());
    }
}
