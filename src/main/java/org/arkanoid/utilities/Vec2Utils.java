package org.arkanoid.utilities;

import com.almasb.fxgl.core.math.Vec2;
import org.arkanoid.entity.GameObject;

public class Vec2Utils {
    public static Vec2 getVec2(float x, float y) {
        return new Vec2(x, y);
    }

    public static Vec2 flip(Vec2 velocity, GameObject o, GameObject other) {
        float vx = velocity.x;
        float vy = velocity.y;

        double oX = o.getX();
        double oY = o.getY();
        double oW = o.getEntity().getWidth();
        double oH = o.getEntity().getHeight();

        double otherX = other.getX();
        double otherY = other.getY();
        double otherW = other.getEntity().getWidth();
        double otherH = other.getEntity().getHeight();

        double overlapLeft   = (oX + oW) - otherX;
        double overlapRight  = (otherX + otherW) - oX;
        double overlapTop    = (oY + oH) - otherY;
        double overlapBottom = (otherY + otherH) - oY;

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

        return new Vec2(vx, vy);
    }

    public static double getAngle(Vec2 v) {
        double radianAngle = Math.atan2(v.y, v.x);

        return Math.toDegrees(radianAngle);
    }
}
