package org.arkanoid.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.util.Duration;
import com.almasb.fxgl.entity.component.Component;
import org.arkanoid.component.animation.ExtendAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.utilities.SchedulerUtils;

import java.util.concurrent.ScheduledFuture;


public class ExtendComponent extends Component {

    private final Duration duration;
    private ScheduledFuture<?> timer; 
    private final int PADDLE_NORMAL_WIDTH = 32;
    private final int PADDLE_EXTEND_WIDTH = 48;
    private final int PADDLE_HEIGHT = 8;

    /**
     * Constructs an {@code ExtendComponent} with a specified duration.
     *
     * @param duration the amount of time the paddle remains extended
     */
    public ExtendComponent(Duration duration) {
        this.duration = duration;
    }

    /**
     * Resets the extension timer, restarting the duration countdown.
     * Useful if the player collects another extend power-up while already extended.
     */
    public void resetTimer() {
        SchedulerUtils.clear(timer);
        startNewCLock();
    }

    /**
     * Starts a new timer that will automatically remove this component
     * (thus ending the effect) after the specified duration.
     */
    public void startNewCLock() {
        timer = SchedulerUtils.setTimeout(this::removeItself, (long) duration.toMillis());
    }

    /**
     * Removes this component from the entity safely via FXGL’s scheduler.
     * Called automatically when the duration timer expires.
     */
    public void removeItself() {
        FXGL.runOnce(() -> {
            if (entity != null) {
                entity.removeComponent(ExtendComponent.class);
            }
        }, Duration.ZERO);
    }

    /**
     * Called automatically when this component is added to an entity.
     * <p>Performs the following actions:
     * <ul>
     *     <li>Removes any conflicting animation or laser components.</li>
     *     <li>Adds the {@link ExtendAnimationComponent} for visual effect.</li>
     *     <li>Expands the paddle’s hitbox to match the extended width.</li>
     *     <li>Shifts the paddle’s position to remain centered.</li>
     *     <li>Starts the duration timer.</li>
     * </ul>
     */
    @Override
    public void onAdded() {
        if (entity.hasComponent(PaddleAnimationComponent.class)) {
            entity.removeComponent(PaddleAnimationComponent.class);
        }

        if (entity.hasComponent(LaserComponent.class)) {
            entity.removeComponent(LaserComponent.class);
        }

        if (entity.hasComponent(PaddleAnimationComponent.class)) {
            entity.removeComponent(PaddleAnimationComponent.class);
        }

        entity.addComponent(new ExtendAnimationComponent());

        entity.getBoundingBoxComponent().clearHitBoxes();
        entity.getBoundingBoxComponent().addHitBox(
            new HitBox(BoundingShape.box(PADDLE_EXTEND_WIDTH, PADDLE_HEIGHT))
        );

        entity.setX(entity.getX() - 16);

        startNewCLock();
    }

    /**
     * Called automatically when this component is removed from an entity.
     * <p>Restores the paddle to its normal size and animation state:
     * <ul>
     *     <li>Cancels the timer.</li>
     *     <li>Removes the {@link ExtendAnimationComponent}.</li>
     *     <li>Restores the normal paddle hitbox and animation.</li>
     *     <li>Re-centers the paddle position.</li>
     * </ul>
     */
    @Override
    public void onRemoved() {
        
        SchedulerUtils.clear(timer);

        if (entity == null || !entity.isActive()) {
            return;
        }

        if (entity.hasComponent(ExtendAnimationComponent.class)) {
            entity.removeComponent(ExtendAnimationComponent.class);
        }

        entity.getBoundingBoxComponent().clearHitBoxes();
        entity.getBoundingBoxComponent().addHitBox(
            new HitBox(BoundingShape.box(PADDLE_NORMAL_WIDTH, PADDLE_HEIGHT))
        );

        if (!entity.hasComponent(LaserComponent.class)) {
            entity.addComponent(new PaddleAnimationComponent());
        }

        entity.setX(entity.getX() + 8);
    }
}
