package org.arkanoid.entity;

import com.almasb.fxgl.physics.PhysicsComponent;

abstract public class MovableObject extends GameObject {
    private PhysicsComponent physics;

    private void initPhysics() {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        entity.addComponent(physicsComponent);
        physics = physicsComponent;
    }

    public void setVelocity(int x, int y) {
        physics.setLinearVelocity(x, y);
    }

    public double getVelocityX() {
        return physics.getVelocityX();
    }

    public double getVelocityY() {
        return physics.getVelocityY();
    }

    public void move(int dx, int dy) {
        entity.translateX(dx);
        entity.translateY(dy);
    }

    public MovableObject() {
        super();
        initPhysics();
    }

    public MovableObject(int x, int y) {
        super(x, y);
        initPhysics();
    }
}
