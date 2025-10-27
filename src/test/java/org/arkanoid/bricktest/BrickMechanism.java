package org.arkanoid.bricktest;

public class BrickMechanism {
    public static int destroy(boolean canDestroy, int health) {
        if (!canDestroy || health > 0) {
            return 0;
        }

        return 1;
    }

    public static int spawnPowerUp(boolean canSpawn, int health) {
        if (!canSpawn || health > 0) {
            return 0;
        }

        return 1;
    }
}
