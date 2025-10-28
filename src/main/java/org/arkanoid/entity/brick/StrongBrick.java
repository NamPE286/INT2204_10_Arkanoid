package org.arkanoid.entity.brick;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.component.animation.BrickAnimationComponent;
import org.arkanoid.entity.EntityType;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class StrongBrick extends Brick {
    public static final double SETSCALE = 3.0;

    /**
     * Creates a new {@code StrongBrick} instance at a specific position and tile coordinates.
     * <p>
     * This brick can be destroyed and starts with {@code health = 2}.
     * The corresponding texture is cropped from {@code bricks.png}, and
     * the FXGL entity is spawned in the game world.
     * </p>
     *
     * @param x      the x-coordinate of the brick in the game world.
     * @param y      the y-coordinate of the brick in the game world.
     * @param tileX  the X index in the brick texture atlas (sprite sheet).
     * @param tileY  the Y index in the brick texture atlas (sprite sheet).
     */
    public StrongBrick(int x, int y, int tileX, int tileY) {
        super(x, y, tileX, tileY);
        this.canDestroy = true; 
        this.health = 2;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("tileX", tileX);
        spawnData.put("tileY", tileY);

        this.entity = createEntity(spawnData);
        spawn();
        initInput();
    }

    /**
     * Creates the FXGL {@link Entity} representing this strong brick.
     * <p>
     * The brick texture is cropped from {@code bricks.png} using
     * the tile coordinates and scaled by a factor of 2 for better visibility.
     * </p>
     *
     * @param spawnData data containing the brick's position and texture tile info.
     * @return the FXGL {@link Entity} representing this strong brick.
     */

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.BRICK)
                .bbox(new HitBox(
                        "BRICK",
                        BoundingShape.box(16, 8)))
                .with(new BrickAnimationComponent(this.tileX, this.tileY))
                .build();

        e.setScaleX(SETSCALE);
        e.setScaleY(SETSCALE);
        return e;
    }
}
