package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

public class Wall extends GameObject{
    private int wallHeight;
    private int wallWidth;
    private final int THICK_WALL = 8;

    // Initilize wall.
    public Wall(int x, int y, int wallHeight, int wallWidth) {
        super(x, y);
        this.wallHeight = wallHeight;
        this.wallWidth = wallWidth;
    }


    /**
     * Setting the wall with entity builder, collidable, va build.
     * The background has all wall, so Unecessary to drowing a wall.
     */
     @Override
     protected Entity createEntity(SpawnData spawnData) {
         return FXGL.entityBuilder(spawnData)
                 .type(EntityType.WALL)
                 .collidable()
                 .build();
     }

}
