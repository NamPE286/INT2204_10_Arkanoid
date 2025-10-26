package org.arkanoid.bricktest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrickTestSpawnPowerUp {
    @Test
    void testCanNotSpawn1() {
        boolean canSpawn = false;
        int health = 0;
        int check = BrickMechanism.destroy(canSpawn, health);

        assertTrue(check == 0, "Brick không thể spawn");
    }

    @Test
    void testCanNotSpawn2() {
        boolean canSpawn = false;
        int health = 3;
        int check = BrickMechanism.destroy(canSpawn, health);

        assertTrue(check == 0, "Brick không thể spawn");
    }

    @Test
    void testCanSpawn1() {
        boolean canSpawn = true;
        int health = 1;
        int check = BrickMechanism.destroy(canSpawn, health);

        assertTrue(check == 0, "Brick không spawn");
    }

    @Test
    void testCanSpawn2() {
        boolean canSpawn = true;
        int health = 0;
        int check = BrickMechanism.destroy(canSpawn, health);

        assertTrue(check == 1, "Brick có spawn");
    }
}
