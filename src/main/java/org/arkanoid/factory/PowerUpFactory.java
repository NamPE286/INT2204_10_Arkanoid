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

    /**
     * Spawns a random power-up entity at the given spawn location.
     *
     * <p>This method selects a random {@link PowerupType} from the list of
     * available types, creates the corresponding power-up entity, and sets
     * up collision detection with the given {@link Paddle}.</p>
     *
     * @param spawnData the data containing the spawn position and any additional parameters
     * @param paddle the player's paddle that the power-up should interact with
     * @return a newly created {@link PowerUp} instance, or {@code null} if no power-up could be spawned
     */
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
