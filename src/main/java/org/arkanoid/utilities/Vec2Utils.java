package org.arkanoid.utilities;

import com.almasb.fxgl.core.math.Vec2;
import org.arkanoid.Main;
import org.arkanoid.entity.Wall;
import org.arkanoid.entity.core.GameObject;

public class Vec2Utils {
    /**
     * Creates a new 2D vector with the given X and Y components.
     *
     * @param x the X component of the vector
     * @param y the Y component of the vector
     * @return a new {@link Vec2} instance with the specified components
     */
    public static Vec2 getVec2(float x, float y) {
        return new Vec2(x, y);
    }

    /**
     * Calculates the reflected (flipped) velocity vector of a moving object after
     * colliding with another game object.
     * <p>
     * The method determines the side of collision (left, right, top, or bottom)
     * by comparing the overlap distances between the two objects, then flips
     * the corresponding velocity component.
     * </p>
     *
     * @param velocity the current velocity vector of the moving object
     * @param o        the main game object whose velocity is being flipped
     * @param other    the other game object that {@code o} collided with
     * @return a new {@link Vec2} representing the flipped velocity after collision
     */
    public static Vec2 flip(Vec2 velocity, GameObject o, GameObject other) {
        float vx = velocity.x;
        float vy = velocity.y;

        double oX = o.getX();
        double oY = o.getY();
        double oW = o.getEntity().getWidth();
        double oH = o.getEntity().getHeight();
        double oCenterX = o.getX() + oW / 2.0;
        double oCenterY = o.getY() + oH / 2.0;

        double otherX = other.getX();
        double otherY = other.getY();
        double otherW = other.getEntity().getWidth();
        double otherH = other.getEntity().getHeight();
        double otherCenterX = other.getX() + otherW / 2.0;
        double otherCenterY = other.getY() + otherH / 2.0;

        double distanceX = oCenterX - otherCenterX;
        double distanceY = oCenterY - otherCenterY;

        double totalHalfW = (oW / 2.0) + (otherW / 2.0);
        double totalHalfH = (oH / 2.0) + (otherH / 2.0);

        double overlapRatioX = Math.abs(distanceX) / totalHalfW;
        double overlapRatioY = Math.abs(distanceY) / totalHalfH;

        if (other instanceof Wall) {
            double overlapLeft   = (oX + oW) - otherX;
            double overlapRight  = (otherX + otherW) - oX;
            double overlapTop    = (oY + oH) - otherY;
            double overlapBottom = (otherY + otherH) - oY;

            double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom));

            if (minOverlap == overlapLeft) {
                vx = -Math.abs(vx);
                o.setPosition((int)oX - 5, (int) oY);
            } else if (minOverlap  == overlapRight) {
                vx = Math.abs(vx);
                o.setPosition((int)oX + 5, (int) oY);
            } else if (minOverlap  == overlapTop) {
                vy = -Math.abs(vy);
            } else if (minOverlap  == overlapBottom) {
                vy = Math.abs(vy);
            }

            return new Vec2(vx, vy);
        }

        double constant = 0.13;
        if (Math.abs(overlapRatioX - overlapRatioY) < constant) {
            if ((vx > 0 && vy > 0 && distanceX < 0 && distanceY < 0)
            || (vx < 0 && vy > 0 && distanceX > 0 && distanceY < 0)
            || (vx > 0 && vy < 0 && distanceX < 0 && distanceY > 0)
            || (vx < 0 && vy < 0 && distanceX > 0 && distanceY > 0)) {
                vx = -vx;
                vy = -vy;
            } else if (overlapRatioX > overlapRatioY) {
                vx = -vx;
            } else if (overlapRatioX < overlapRatioY) {
                vy = -vy;
            }
        } else {
            if (overlapRatioX > overlapRatioY) {
                vx = -vx;
            } else {
                vy = -vy;
            }
        }

        return new Vec2(vx, vy);
    }

    /**
     * Converts a 2D vector into its corresponding angle in degrees.
     * <p>
     * The angle is measured counterclockwise from the positive X-axis,
     * based on the vector’s direction.
     * </p>
     *
     * @param v the input vector
     * @return the angle in degrees, in the range [-180°, 180°]
     */
    public static double getAngle(Vec2 v) {
        double radianAngle = Math.atan2(v.y, v.x);

        return Math.toDegrees(radianAngle);
    }
}
