package org.arkanoid.entity;

abstract class GameObject {
    public abstract void update();
    public void fixed_update() {}
    public abstract void render();
}
