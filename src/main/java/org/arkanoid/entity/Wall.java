package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

public class Wall extends GameObject{
    private double wallHeight;
    private double wallThick;

    // Initilize wall.
    public Wall(int x, int y, double wallHeight, double wallThick) {
        this.wallHeight = wallHeight;
        this.wallThick = wallThick;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("height",  wallHeight);
        spawnData.put("thick",   wallThick);

        this.entity = createEntity(spawnData);
        spawn();
        initInput();
    }


    /// Setting the wall with entity builder, collidable, va build.
    /// The background has all wall, so unecessary to drowing a wall.
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(wallThick, wallHeight)))
                .collidable()
                .build();
    }

}
