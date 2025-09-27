package org.arkanoid.entity;

import com.almasb.fxgl.physics.PhysicsComponent;

abstract public class MovableObject extends GameObject {
    protected PhysicsComponent physics;

    public void setVelocity(int x, int y) {
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

    public MovableObject() {
        super();
    }

    public MovableObject(int x, int y) {
        super(x, y);
    }
}
