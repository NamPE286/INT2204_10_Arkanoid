package org.arkanoid.paddletest;

import org.arkanoid.balltest.BallPhysics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaddleTestCollideWall {
    public double PADDLE_WIDTH = 32;
    public double PADDLE_HEIGHT = 8;
    public double WALL_THICK = 24;
    public double WALL_HEIGHT = 600;

    @Test
    public void PaddleTestCollideRightWall1() {
        // Paddle đi từ trái sang phải
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            // vx dương = đi sang phải
                50 + PADDLE_WIDTH, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] == 0, "Paddle phải va chạm với right wall và dừng lại");
    }

    @Test
    public void PaddleTestCollideRightWall2() {
        // Paddle đi từ trái sang phải
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            // vx dương = đi sang phải
                100, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với right wall và vẫn di chuyển");
    }

    @Test
    public void PaddleTestCollideRightWall3() {
        // Paddle đi từ phải sang trái
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            // vx âm = đi sang trái
                50 + PADDLE_WIDTH, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với right wall và vẫn di chuyển");
    }

    @Test
    public void PaddleTestCollideLeftWall1() {
        // Ball đi từ trái sang phải
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            // vx âm = đi sang trái
                50, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] == 0, "Paddle phải va chạm với left wall và dừng lại");
    }

    @Test
    public void PaddleTestCollideLeftWall2() {
        // Ball đi từ trái sang phải
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            // vx âm = đi sang trái
                30, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với left wall và di chuyển tiếp");
    }

    @Test
    public void PaddleTestCollideLeftWall3() {
        // Ball đi từ trái sang phải
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            // vx dương = đi sang phải
                50, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với left wall và di chuyển tiếp");
    }
}
