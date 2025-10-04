package org.arkanoid.entity;

import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * Abstract base class for all movable game objects.
 * <p>
 * Extends {@link GameObject} and adds physics-based movement capabilities.
 */
abstract public class MovableObject extends GameObject {
    /** The physics component used to control velocity and collisions. */
    protected PhysicsComponent physics;

    public void setLinearVelocity(double x, double y) {
        physics.setLinearVelocity(x, y);
    }

    public double getVelocityX() {
        return physics.getVelocityX();
    }

    public double getVelocityY() {
        return physics.getVelocityY();
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
        physics = entity.getComponent(PhysicsComponent.class);
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
        physics = entity.getComponent(PhysicsComponent.class);
    }
}
