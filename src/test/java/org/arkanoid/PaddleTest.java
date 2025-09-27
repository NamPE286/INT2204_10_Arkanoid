package org.arkanoid;

import org.junit.jupiter.api.*;
import org.arkanoid.entity.Paddle;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {
    private final Paddle paddle = new Paddle(0, 0);

    @Test
    @DisplayName("Paddle created")
    public void paddleCreated() {
        assertEquals((double) -Paddle.WIDTH / 2, paddle.getEntity().getX());
        assertEquals((double) -Paddle.HEIGHT / 2, paddle.getEntity().getY());
    }
}
