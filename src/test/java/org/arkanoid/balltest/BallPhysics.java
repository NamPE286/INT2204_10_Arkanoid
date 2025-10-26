package org.arkanoid.balltest;

public class BallPhysics {

    /**
     * Xử lý va chạm giữa ball và paddle, trả về vận tốc mới (vx, vy).
     */
    public static double[] handleCollision(
            double ballX, double ballY, double ballW, double ballH, double vx, double vy,
            double eX, double eY, double eW, double eH) {

        double overlapLeft   = (ballX + ballW) - eX;
        double overlapRight  = (eX + eW) - ballX;
        double overlapTop    = (ballY + ballH) - eY;
        double overlapBottom = (eY + eH) - ballY;

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft) {
            vx = -Math.abs(vx);
        } else if (minOverlap  == overlapRight) {
            vx = Math.abs(vx);
        } else if (minOverlap  == overlapTop) {
            vy = -Math.abs(vy);
        } else if (minOverlap  == overlapBottom) {
            vy = Math.abs(vy);
        }

        return new double[]{vx, vy};
    }
}
