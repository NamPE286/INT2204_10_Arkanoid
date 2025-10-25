package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.manager.PowerupType;
import java.util.List;
import java.util.ArrayList;

public class ExtendPowerup extends PowerUp{

    /**
     * Do notthing.
     */
    public ExtendPowerup(SpawnData data) {
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
