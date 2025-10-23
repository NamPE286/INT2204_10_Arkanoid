package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.arkanoid.component.animation.PowerupAnimationComponent;
import org.arkanoid.manager.PowerupAniManager;
import org.arkanoid.manager.PowerupType;

public abstract class PowerUp extends MovableObject {
    private final int speedDown = 80;

    public abstract PowerupType getType();


    /**
     *
     * @param spawnData the spawn data used to initialize the entity
     * @return entity.
     */
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        final int W = PowerupAniManager.FRAME_W;
        final int H = PowerupAniManager.FRAME_H;

        PowerupType currentType = getType();
        // Go through screen also disappear.
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.POWERUP)
                .bbox(new HitBox(BoundingShape.box(W, H)))
                .with(new ProjectileComponent(new Point2D(0, 1 ), speedDown))
                .collidable()
                .with(new OffscreenCleanComponent())
                .with(new PowerupAnimationComponent(currentType))
                .build();
    }

    @Override
    public void onCollisionWith(GameObject e) {

        if (e instanceof Paddle) {
            this.entity.removeFromWorld();
        }
    }

    public abstract void applyEffect(Paddle paddle);
}
