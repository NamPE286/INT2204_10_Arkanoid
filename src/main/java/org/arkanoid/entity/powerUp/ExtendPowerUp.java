package org.arkanoid.entity.powerUp;

import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupType;

public class ExtendPowerUp extends PowerUp {

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
    public void applyEffect(Paddle paddle) {
        System.out.println("Paddle get more Bigger");
    }



}
