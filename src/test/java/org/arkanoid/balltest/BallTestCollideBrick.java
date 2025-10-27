package org.arkanoid.balltest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BallTestCollideBrick {
    public final double BALL_WIDTH = 10;
    public final double BALL_HEIGHT = 10;
    public final double BRICK_WIDTH = 16;
    public final double BRICK_HEIGHT = 8;
    @Test
    void testCollisionFromTop() {
        
        double[] result = BallPhysics.handleCollision(
                50, 40, BALL_WIDTH, BALL_HEIGHT,
                0, 10,            
                40, 50, BRICK_WIDTH, BRICK_HEIGHT
        );

        assertTrue(result[1] < 0, "Ball phải bật ngược lên trên");
    }

    @Test
    void testCollisionFromBottom() {
        
        double[] result = BallPhysics.handleCollision(
                50, 60, BALL_WIDTH, BALL_HEIGHT,
                0, -10,           
                40, 50, BRICK_WIDTH, BRICK_HEIGHT
        );

        assertTrue(result[1] > 0, "Ball phải bật xuống dưới");
    }

    @Test
    void testCollisionFromLeft() {
        
        double[] result = BallPhysics.handleCollision(
                30, 50, BALL_WIDTH, BALL_HEIGHT,
                10, 0,            
                40, 50, BRICK_WIDTH, BRICK_HEIGHT
        );

        assertTrue(result[0] < 0, "Ball phải bật ngược lại sang trái");
    }

    @Test
    void testCollisionFromRight() {
        
        double[] result = BallPhysics.handleCollision(
                90, 50, BALL_WIDTH, BALL_HEIGHT,
                -10, 0,           
                40, 50, BRICK_WIDTH, BRICK_HEIGHT
        );

        assertTrue(result[0] > 0, "Ball phải bật ngược lại sang phải");
    }
}
