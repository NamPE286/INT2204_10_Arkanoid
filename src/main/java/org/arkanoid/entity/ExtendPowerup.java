package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.manager.PowerupType;

public class ExtendPowerup extends PowerUp{

    public ExtendPowerup() {

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
