package org.arkanoid.entity.powerup;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.component.animation.PowerupAnimationComponent;
import org.arkanoid.entity.EntityType;
import org.arkanoid.core.MovableObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupAnimationManager;
import org.arkanoid.manager.PowerupType;

public abstract class PowerUp extends MovableObject {

    private static final int speedDown = 100;

    public PowerUp(SpawnData data) {
        super((int) data.getX(), (int) data.getY());
        spawn();
        initInput();
        setLinearVelocity(0, 100);
    }

    public abstract PowerupType getType();


    /**
     *
     * @param spawnData the spawn data used to initialize the entity
     * @return entity.
     */
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        PowerupAnimationManager.load("powerups.png");

        final int W = PowerupAnimationManager.FRAME_W;
        final int H = PowerupAnimationManager.FRAME_H;
        PowerupType currentType = getType();
        // Go through screen also disappear.
        // bat buoc phải thêm allowRotation(false) thì nó sẽ không xoay doc.

        var e = entityBuilder(spawnData)
            .type(EntityType.POWERUP)
            .bbox(new HitBox(BoundingShape.box(W, H)))
            .with(new OffscreenCleanComponent())
            .with(new PowerupAnimationComponent(currentType))
            .build();

        e.setScaleX(0.5);
        e.setScaleY(0.5);

        return e;
    }

    public abstract void applyEffect(Paddle paddle);

}
