package org.arkanoid.paddletest;

public class PaddlePhysics {
    public static double[] handleCollision(
            double paddleX, double paddleY, double paddleW, double paddleH, double vx, double vy,
            double eX, double eY, double eW, double eH, double eVelX, double eVelY) {

        double overlapLeft   = (paddleX + paddleW) - eX;
        double overlapRight  = (eX + eW) - paddleX;
        double overlapTop    = (paddleY + paddleH) - eY;
        double overlapBottom = (eY + eH) - paddleY;

        if (0 == overlapLeft && vx >= 0) {
            vx = 0;
        } else if (0 == overlapRight && vx <= 0) {
            vx = 0;
        } else if (0 == overlapTop && eVelY <= 0 && overlapLeft >= 0 && overlapRight >= 0) {
            return new double[]{eVelX, 0};
        } else if (0 == overlapBottom && eVelY >= 0 && overlapLeft >= 0 && overlapRight >= 0) {
            return new double[]{eVelX, 0};
        }

        return new double[]{vx, vy};
    }
}
