package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Brick extends GameObject {
    private int tileX;
    private int tileY;

    /**
     * Constructs a new brick at the default position (0, 0).
     */
    public Brick() {
        super();
    }

    /**
     * Constructs a new brick at the specified coordinates.
     *
     * @param x the x-coordinate for the paddle's initial position
     * @param y the y-coordinate for the paddle's initial position
     * @param tileX to position color
     * @param tileY to position color
     */
    public Brick(int x, int y, int tileX, int tileY) {
        super(x, y);
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        int brickWidth = 16;
        int brickHeight = 8;
        double factor = 3.0;

        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("bricks.png"),
                        tileX * brickWidth, tileY * brickHeight, brickHeight, brickWidth),
                factor
        );

        return entityBuilder(spawnData)
                .type(EntityType.BRICK)
                .view(texture)
                .bbox(new HitBox(BoundingShape.box(
                        texture.getWidth() * factor,
                        texture.getHeight() * factor)))
                .build();
    }

    public void destroy() {
        entity.removeFromWorld();
    }
}
