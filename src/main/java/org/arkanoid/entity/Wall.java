package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.entity.core.GameObject;

public class Wall extends GameObject {
    private final double wallHeight;
    private final double wallThick;

    /**
     * Creates a new {@code Wall} entity at the specified position.
     *
     * @param x the x-coordinate where the wall will be placed
     * @param y the y-coordinate where the wall will be placed
     * @param wallHeight the height of the wall in pixels
     * @param wallThick the thickness (width) of the wall in pixels
     */
    public Wall(int x, int y, double wallHeight, double wallThick) {
        this.wallHeight = wallHeight;
        this.wallThick = wallThick;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("height", wallHeight);
        spawnData.put("thick", wallThick);

        this.entity = createEntity(spawnData);
        spawn();
        initInput();
    }


    @Override
    protected Entity createEntity(SpawnData spawnData) {
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(wallThick, wallHeight)))
                .collidable()
                .build();
    }
}
