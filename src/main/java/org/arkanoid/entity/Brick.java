package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public abstract class Brick extends GameObject {

    protected int tileX;
    protected int tileY;
    protected final int width = 16;
    protected final int height = 8;
    protected boolean canDestroy;
    protected int health;

    /**
     * Constructs a new brick at the default position (0, 0).
     */
    public Brick() {
        super();
    }

    /**
     * Constructs a new brick at the specified coordinates.
     *
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDestroyed() {
        return canDestroy;
    }

    /**
     * Destroys the brick and removes it from the game world.
     * <p>
     * This method ensures that the brick’s hitbox is cleared before removal to prevent potential
     * null-pointer errors when accessing the bounding box component. If the brick is already
     * destroyed, this method has no effect.
     */
    public void destroy() {
        this.health--;

        if (!canDestroy || this.health > 0) {
            return; // Nếu gạch là loại không thể bị phá hoặc có máu > 0 thì không destroy
        }
        canDestroy = false;

        if (entity != null && entity.isActive()) {
            // Xóa hitBox (tránh bị lỗi crash: BoundingBoxComponent.getEntity() is null)
            if (entity.getBoundingBoxComponent() != null) {
                entity.getBoundingBoxComponent().clearHitBoxes();
            }

            // Xóa khỏi world
            entity.removeFromWorld();
        }
    }
}
