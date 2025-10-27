package org.arkanoid.entity.powerup;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.core.GameObject;
import org.arkanoid.entity.ExtendPaddle;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupType;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
public class ExtendPowerUp extends PowerUp {
    private Entity currentExtended;
    private boolean extendActive = false;
    /**
     * Do notthing.
     */
    public ExtendPowerUp(SpawnData data) {
        super(data);

    }

    @Override
    public PowerupType getType() {
        return PowerupType.EXTEND;
    }



    @Override
    public void onCollisionWith(GameObject e) {
        System.out.println("Power up collided with paddle");

        if (!(e instanceof Paddle )) {
            return;
        }

        if (!(entity != null && entity.isActive())) {
            return;
        }

        if (entity.getBoundingBoxComponent() != null) {
            entity.getBoundingBoxComponent().clearHitBoxes();
        }

        entity.removeFromWorld();
    }
}
