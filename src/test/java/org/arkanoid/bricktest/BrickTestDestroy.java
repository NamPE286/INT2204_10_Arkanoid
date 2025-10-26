package org.arkanoid.bricktest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrickTestDestroy {
    @Test
    void testCanNotDestroy1() {
        boolean canDestroy = false;
        int health = 0;
        int check = BrickMechanism.destroy(canDestroy, health);

        assertTrue(check == 0, "Brick không thể bị xóa");
    }

    @Test
    void testCanNotDestroy2() {
        boolean canDestroy = false;
        int health = 3;
        int check = BrickMechanism.destroy(canDestroy, health);

        assertTrue(check == 0, "Brick không thể bị xóa");
    }

    @Test
    void testCanDestroy1() {
        boolean canDestroy = true;
        int health = 1;
        int check = BrickMechanism.destroy(canDestroy, health);

        assertTrue(check == 0, "Brick không bị xóa");
    }

    @Test
    void testCanDestroy2() {
        boolean canDestroy = true;
        int health = 0;
        int check = BrickMechanism.destroy(canDestroy, health);

        assertTrue(check == 1, "Brick phải bị xóa");
    }
}
