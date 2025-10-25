package org.arkanoid.entity.brick;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.powerup.ExtendPowerUp;
import org.arkanoid.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.powerup.PowerUp;
import javafx.geometry.Point2D;


import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public abstract class Brick extends GameObject {

    Paddle paddle;
    PowerUp powerUp;
    protected int tileX;
    protected int tileY;
    protected final int width = 16;
    protected final int height = 8;
    protected boolean canDestroy;
    protected int health;

    /**
     * Constructs a new brick at the default position (0, 0).
     */
//    public Brick() {
//        super();
//    }

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

    public Brick setPaddle(Paddle paddle) {
        this.paddle = paddle;
        return this;
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

        // 36% pop out Power up
        if (FXGLMath.randomBoolean(1.0)) {
            System.out.println("--- DA VAO HAM SPAWN POWERUP ---");
            Point2D brickPosition = entity.getPosition();
            System.out.println(brickPosition.getX());
            System.out.println(brickPosition.getY());
            SpawnData spawnData = new SpawnData(brickPosition);

            if (paddle != null) {
                powerUp = new ExtendPowerUp(spawnData);
                powerUp.listenToCollisionWith(paddle);
            }

        }

        if (entity != null && entity.isActive()) {
            // Xóa hitBox (tránh bị lỗi crash: BoundingBoxComponent.getEntity() is null)
            if (entity.getBoundingBoxComponent() != null) {
                entity.getBoundingBoxComponent().clearHitBoxes();
            }

            // Xóa khỏi world
            entity.removeFromWorld();
        }
    }

    @Override
    public void onUpdate(double deltaTime) {
        if (powerUp != null) {
            powerUp.onUpdate(deltaTime);
        }
    }
}
