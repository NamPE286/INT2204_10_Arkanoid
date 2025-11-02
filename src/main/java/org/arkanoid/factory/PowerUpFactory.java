package org.arkanoid.factory;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.powerup.*;
import org.arkanoid.manager.PowerupType;

import java.util.List;

public class PowerUpFactory {
    private static final List<PowerupType> SPAWNABLE_POWERUPS = List.of(
            PowerupType.EXTEND,
            PowerupType.LASER,
            PowerupType.DISRUPT,
            PowerupType.CATCH
    );

    public static PowerUp spawnRandomPowerUp(SpawnData spawnData, Paddle paddle) {
        if (SPAWNABLE_POWERUPS.isEmpty()) {
            return null;
        }

        PowerupType randomType = FXGLMath.random(SPAWNABLE_POWERUPS).get();

        PowerUp powerUp = null;

        switch (randomType) {
            case LASER:
                powerUp = new LaserPowerUp(spawnData);
                break;
            case EXTEND:
                powerUp = new ExtendPowerUp(spawnData);
                break;
            case DISRUPT:
                powerUp = new DisruptPowerup(spawnData);
                break;
            case CATCH:
                powerUp = new CatchPowerup(spawnData);
                break;
            default:
                System.out.println("Not found: " + randomType);
                break;
        }

        if (powerUp != null) {
            powerUp.listenToCollisionWith(paddle);
        }

        return powerUp;
    }
}
