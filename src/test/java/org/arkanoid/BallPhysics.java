package org.arkanoid;

public class BallPhysics {

    /**
     * Xử lý va chạm giữa ball và paddle, trả về vận tốc mới (vx, vy).
     */
    public static double[] handleCollision(
            double ballX, double ballY, double ballW, double ballH, double vx, double vy,
            double paddleX, double paddleY, double paddleW, double paddleH) {

        double overlapLeft   = (ballX + ballW) - paddleX;
        double overlapRight  = (paddleX + paddleW) - ballX;
        double overlapTop    = (ballY + ballH) - paddleY;
        double overlapBottom = (paddleY + paddleH) - ballY;

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft) {
            vx = -(Math.abs(vx) + 5);
        } else if (minOverlap  == overlapRight) {
            vx = Math.abs(vx) + 5;
        } else if (minOverlap  == overlapTop) {
            vy = -Math.abs(vy);
        } else if (minOverlap  == overlapBottom) {
            vy = Math.abs(vy);
        }

        return new double[]{vx, vy};
    }
}
