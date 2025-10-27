package org.arkanoid.entity.brick;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.EntityType;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class HardBrick extends Brick {
    public static double SETSCALE = 3.0;
    /**
     * Default constructor for serialization or framework usage.
     * Initializes a HardBrick with no position or texture information.
     */




    /**
     * Creates a new {@code HardBrick} instance at a specific position and tile coordinates.
     * <p>
     * This brick cannot be destroyed and has {@code health = 0}.
     * It loads the corresponding texture from {@code bricks.png} and spawns
     * an FXGL entity in the game world.
     * </p>
     *
     * @param x      the x-coordinate position of the brick.
     * @param y      the y-coordinate position of the brick.
     * @param tileX  the X index in the brick sprite sheet.
     * @param tileY  the Y index in the brick sprite sheet.
     */
    public HardBrick(int x, int y, int tileX, int tileY) {
        super(x, y, tileX, tileY);
        this.canDestroy = false; 
        this.health = 0;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("tileX", tileX);
        spawnData.put("tileY", tileY);

        this.entity = createEntity(spawnData);
        spawn();
        initInput();
    }

    /**
     * Creates the visual and physical representation (FXGL entity) of the hard brick.
     * <p>
     * It uses a cropped texture from {@code bricks.png} based on the given
     * tile coordinates and scales it by a factor of 2 for better visibility.
     * </p>
     *
     * @param spawnData data containing position and texture tile info.
     * @return the FXGL {@link Entity} representing this hard brick.
     */
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.crop(FXGL.texture("bricks.png"),
                tileX * width, tileY * height, height, width);

        var e = entityBuilder(spawnData)
                .type(EntityType.BRICK)
                .viewWithBBox(texture)
                .build();

        e.setScaleX(SETSCALE);
        e.setScaleY(SETSCALE);

        return e;
    }
}
