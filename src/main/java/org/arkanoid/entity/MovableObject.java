package org.arkanoid.entity;

import com.almasb.fxgl.core.math.Vec2;

/**
 * Abstract base class for all movable game objects.
 * <p>
 * Extends {@link GameObject} and adds physics-based movement capabilities.
 */
abstract public class MovableObject extends GameObject {
    /** The physics component used to control velocity and collisions. */
    Vec2 velocity = new Vec2(0, 0);

    public void setLinearVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public double getX() {
        return entity.getX();
    }

    public double getY() {
        return entity.getY();
    }

    /**
     * Constructs a new movable object at the default location (0, 0).
     * <p>
     * Initializes the entity and physics component and spawns the object.
     */
    public MovableObject() {
        super();
    }

    /**
     * Constructs a new movable object at the specified coordinates.
     * <p>
     * Initializes the entity and physics component and spawns the object.
     *
     * @param x the x-coordinate for the entity's initial position
     * @param y the y-coordinate for the entity's initial position
     */
    public MovableObject(int x, int y) {
        super(x, y);
    }
}
