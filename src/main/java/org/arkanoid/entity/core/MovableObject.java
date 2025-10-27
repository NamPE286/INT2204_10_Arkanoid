package org.arkanoid.core;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;

/**
 * Abstract base class for all movable game objects.
 * <p>
 * Extends {@link GameObject} and adds physics-based movement capabilities.
 */
public abstract class MovableObject extends GameObject {

    /**
     * The physics component used to control velocity and collisions.
     */
    private final Vec2 velocity = new Vec2(0, 0);

    public void setLinearVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vec2 getLinearVelocity() {
        return velocity;
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    /**
     * Constructs a new movable object at the default location (0, 0).
     * <p>
     * Initializes the entity and physics component and spawns the object.
     */
    protected MovableObject() {
        super();
    }

    @Override
    public void onUpdate(double deltaTime) {
        super.onUpdate(deltaTime);

        setX(getX() + getVelocityX() * deltaTime);
        setY(getY() + getVelocityY() * deltaTime);
    }

    public boolean isOutOfBound() {
        return getX() < 0 || getX() > FXGL.getAppWidth() ||
            getY() < 0 || getY() > FXGL.getAppHeight();
    }

    /**
     * Constructs a new movable object at the specified coordinates.
     * <p>
     * Initializes the entity and physics component and spawns the object.
     *
     * @param x the x-coordinate for the entity's initial position
     * @param y the y-coordinate for the entity's initial position
     */
    protected MovableObject(int x, int y) {
        super(x, y);
    }
}
