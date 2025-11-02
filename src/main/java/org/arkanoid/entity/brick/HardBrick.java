package org.arkanoid.entity.brick;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.arkanoid.component.animation.BrickAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.entity.EntityType;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class HardBrick extends Brick {
    public static final double SETSCALE = 3.0;
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
