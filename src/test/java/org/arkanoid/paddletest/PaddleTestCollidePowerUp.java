package org.arkanoid.paddletest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaddleTestCollidePowerUp {
    public double PADDLE_WIDTH = 32;
    public double PADDLE_HEIGHT = 8;
    public double POWER_WIDTH = 8;
    public double POWER_HEIGHT = 4;

    @Test
    public void PaddleTestCollidePower1() {
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                0, 10,            
                50, 50 - POWER_HEIGHT, POWER_WIDTH, POWER_HEIGHT, 0, PADDLE_HEIGHT
        );

        assertTrue(result[1] == 0, "Power phải va chạm với paddle và dừng lại");
    }

    @Test
    public void PaddleTestCollidePower2() {
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                0, 10,            
                43, 50 - POWER_HEIGHT, POWER_WIDTH, POWER_HEIGHT, 0, PADDLE_HEIGHT
        );

        assertTrue(result[1] == 0, "Power phải va chạm với paddle và dừng lại");
    }

    @Test
    public void PaddleTestCollidePower3() {
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                0, 10,            
                49 + PADDLE_WIDTH, 50 - POWER_HEIGHT, POWER_WIDTH, POWER_HEIGHT, 0, PADDLE_HEIGHT
        );

        assertTrue(result[1] == 0, "Power phải va chạm với paddle và dừng lại");
    }

    @Test
    public void PaddleTestCollidePower4() {
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                0, 10,            
                40, 50 - POWER_HEIGHT, POWER_WIDTH, POWER_HEIGHT, 0, PADDLE_HEIGHT
        );

        assertTrue(result[1] != 0, "Power k va chạm với paddle và k dừng lại");
    }

    @Test
    public void PaddleTestCollidePower5() {
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                0, 10,            
                90, 50 - POWER_HEIGHT, POWER_WIDTH, POWER_HEIGHT, 0, PADDLE_HEIGHT
        );

        assertTrue(result[1] != 0, "Power k va chạm với paddle và k dừng lại");
    }
}
