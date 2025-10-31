package org.arkanoid.entity.core;

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

    /**
     * Updates the entity's position based on its linear velocity and elapsed time,
     * performing collision detection at small incremental steps to prevent tunneling.
     * <p>
     * The method divides the total movement distance into smaller steps (based on
     * the entity's smallest dimension) and moves the entity step-by-step.
     * After each step, it checks for collisions with registered {@code collisionListeners}.
     * </p>
     *
     * @param deltaTime the time elapsed (in seconds) since the last frame
     */
    @Override
    public void onUpdate(double deltaTime) {
        double minSide = Math.min(getEntity().getWidth(), getEntity().getHeight());
        final double minStepDistance = (minSide > 1.0) ? minSide / 2.0 : 2.0;

        Vec2 fullMoveVector = getLinearVelocity().mul((float) deltaTime);
        double distance = fullMoveVector.length();

        int numSteps = (int) Math.ceil(distance / minStepDistance);
        if (numSteps <= 0) {
            numSteps = 1;
        }

        Vec2 stepVector = (numSteps > 1) ? fullMoveVector.mul(1.0f / numSteps) : fullMoveVector;

        for (int i = 0; i < numSteps; i++) {
            setX(getX() + stepVector.x);
            setY(getY() + stepVector.y);

            boolean hasCollided = false;
            for (var other : collisionListeners) {
                if (getEntity().getBoundingBoxComponent()
                        .isCollidingWith(other.getEntity().getBoundingBoxComponent())) {
                    onCollisionWith(other);

                    setX(getX() - stepVector.x);
                    setY(getY() - stepVector.y);

                    hasCollided = true;
                    break;
                }
            }
            if (hasCollided) {
                break;
            }
        }
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
