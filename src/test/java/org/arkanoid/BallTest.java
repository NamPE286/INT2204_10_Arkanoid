package org.arkanoid;

import org.arkanoid.BallPhysics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    @Test
    void testCollisionFromTop() {
        // Ball rơi từ trên xuống paddle
        double[] result = BallPhysics.handleCollision(
                50, 40, 10, 10,   // ballX, ballY, ballW, ballH
                0, 10,            // vx, vy (đang đi xuống)
                40, 50, 40, 10    // paddleX, paddleY, paddleW, paddleH
        );

        assertTrue(result[1] < 0, "Ball phải bật ngược lên trên");
    }

    @Test
    void testCollisionFromBottom() {
        // Ball đi từ dưới lên paddle
        double[] result = BallPhysics.handleCollision(
                50, 60, 10, 10,
                0, -10,           // vy âm = đi lên
                40, 50, 40, 10
        );

        assertTrue(result[1] > 0, "Ball phải bật xuống dưới");
    }

    @Test
    void testCollisionFromLeft() {
        // Ball đi từ trái sang phải
        double[] result = BallPhysics.handleCollision(
                30, 50, 10, 10,
                10, 0,            // vx dương = đi sang phải
                40, 50, 40, 10
        );

        assertTrue(result[0] < 0, "Ball phải bật ngược lại sang trái");
    }

    @Test
    void testCollisionFromRight() {
        // Ball đi từ phải sang trái
        double[] result = BallPhysics.handleCollision(
                90, 50, 10, 10,
                -10, 0,           // vx âm = đi sang trái
                40, 50, 40, 10
        );

        assertTrue(result[0] > 0, "Ball phải bật ngược lại sang phải");
    }
}
