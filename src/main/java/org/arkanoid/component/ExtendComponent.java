package org.arkanoid.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.util.Duration;
import com.almasb.fxgl.entity.component.Component;
import org.arkanoid.component.animation.ExtendAnimationComponent;
import org.arkanoid.component.animation.LaserPaddleAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.utilities.SchedulerUtils;

import java.util.concurrent.ScheduledFuture;


public class ExtendComponent extends Component {

    private final Duration duration;
    private ScheduledFuture<?> timer; 
    private final int PADDLE_NORMAL_WIDTH = 32;
    private final int PADDLE_EXTEND_WIDTH = 48;
    private final int PADDLE_HEIGHT = 8;

    public ExtendComponent(Duration duration) {
        this.duration = duration;
    }

    public void resetTimer() {
        SchedulerUtils.clear(timer);
        startNewCLock();
    }

    public void startNewCLock() {
        
        
        
        timer = SchedulerUtils.setTimeout(this::removeItself, (long) duration.toMillis());
    }

    public void removeItself() {
        FXGL.runOnce(() -> {
            if (entity != null) {
                entity.removeComponent(ExtendComponent.class);
            }
        }, Duration.ZERO);
    }

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
