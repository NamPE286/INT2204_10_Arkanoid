package org.arkanoid.entity.powerup;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupType;

import java.util.ArrayList;
import java.util.List;

public class CatchPowerup extends PowerUp {

    public CatchPowerup(SpawnData spawnData) {
        super(spawnData);
    }

    @Override
    public PowerupType getType() {
        return PowerupType.CATCH;
    }

    /**
     * Apply effect of catch.
     * @param entityPaddle current entity of paddle.
     */
    @Override
    public void applyEffect(Entity entityPaddle) {
        Paddle curPaddle = Paddle.getTarget();

        if (curPaddle != null) {
            System.out.println("Apply Catch Effect");
            curPaddle.activeCatchPowerup();
        }
    }

    /**
     * Execute power up when collision happen.
     * @param e the Entity that this object has collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        System.out.println("Catch power up collied with paddle");
        if (!(e instanceof Paddle)) {
            return;
        }

        System.out.println("Catch Power up collied with paddle");

        this.applyEffect(e.getEntity());

        this.getEntity().removeFromWorld();

    }
}
