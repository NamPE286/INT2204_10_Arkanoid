package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

abstract public class GameObject {
    protected Entity entity = null;

    public Entity getEntity() {
        return entity;
    }

    public void spawn() {
        getGameWorld().addEntity(entity);
    }

    protected abstract Entity createEntity(SpawnData spawnData);

    public void initInput() {
    }

    public void update() {
    }

    public GameObject() {
        entity = createEntity(new SpawnData());
        spawn();
        initInput();
    }

    public GameObject(int x, int y) {
        entity = createEntity(new SpawnData(x, y));
        spawn();
        initInput();
    }
}
