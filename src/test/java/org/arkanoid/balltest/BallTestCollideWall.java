package org.arkanoid.balltest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BallTestCollideWall {
    public double BALL_WIDTH = 10;
    public double BALL_HEIGHT = 10;
    public double WALL_THICK = 24;
    public double WALL_HEIGHT = 600;

    @Test
    void testCollisionFromTop() {
        
        double[] result = BallPhysics.handleCollision(
                50, 40, BALL_WIDTH, BALL_HEIGHT,
                0, 10,            
                40, 50, WALL_HEIGHT, WALL_THICK
        );

        assertTrue(result[1] < 0, "Ball phải bật ngược lên trên");
    }

    @Test
    void testCollisionFromBottom() {
        
        double[] result = BallPhysics.handleCollision(
                70, 60, BALL_WIDTH, BALL_HEIGHT,
                0, -10,           
                40, 50, WALL_HEIGHT, WALL_THICK
        );

        assertTrue(result[1] > 0, "Ball phải bật xuống dưới");
    }

    @Test
    void testCollisionFromLeft() {
        
        double[] result = BallPhysics.handleCollision(
                30, 50, BALL_WIDTH, BALL_HEIGHT,
                10, 0,            
                40, 50, WALL_THICK, WALL_HEIGHT
        );

        assertTrue(result[0] < 0, "Ball phải bật ngược lại sang trái");
    }

    @Test
    void testCollisionFromRight() {
        
        double[] result = BallPhysics.handleCollision(
                90, 50, BALL_WIDTH, BALL_HEIGHT,
                -10, 0,           
                40, 50, WALL_THICK, WALL_HEIGHT
        );

        assertTrue(result[0] > 0, "Ball phải bật ngược lại sang phải");
    }
}
