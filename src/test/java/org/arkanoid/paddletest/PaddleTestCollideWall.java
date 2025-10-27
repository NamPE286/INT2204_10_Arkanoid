package org.arkanoid.paddletest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaddleTestCollideWall {
    public final double PADDLE_WIDTH = 32;
    public final double PADDLE_HEIGHT = 8;
    public final double WALL_THICK = 24;
    public final double WALL_HEIGHT = 600;

    @Test
    public void PaddleTestCollideRightWall1() {
        
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            
                50 + PADDLE_WIDTH, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] == 0, "Paddle phải va chạm với right wall và dừng lại");
    }

    @Test
    public void PaddleTestCollideRightWall2() {
        
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            
                100, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với right wall và vẫn di chuyển");
    }

    @Test
    public void PaddleTestCollideRightWall3() {
        
        double[] result = PaddlePhysics.handleCollision(
                50, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            
                50 + PADDLE_WIDTH, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với right wall và vẫn di chuyển");
    }

    @Test
    public void PaddleTestCollideLeftWall1() {
        
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            
                50, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] == 0, "Paddle phải va chạm với left wall và dừng lại");
    }

    @Test
    public void PaddleTestCollideLeftWall2() {
        
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                -10, 0,            
                30, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với left wall và di chuyển tiếp");
    }

    @Test
    public void PaddleTestCollideLeftWall3() {
        
        double[] result = PaddlePhysics.handleCollision(
                50 + WALL_THICK, 50, PADDLE_WIDTH, PADDLE_HEIGHT,
                10, 0,            
                50, 50, WALL_THICK, WALL_HEIGHT, 0, 0
        );

        assertTrue(result[0] != 0, "Paddle k va chạm với left wall và di chuyển tiếp");
    }
}
