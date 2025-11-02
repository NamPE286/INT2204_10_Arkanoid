package org.arkanoid.entity.brick;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.powerup.ExtendPowerUp;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.powerup.LaserPowerUp;
import org.arkanoid.entity.powerup.PowerUp;
import javafx.geometry.Point2D;
import org.arkanoid.factory.PowerUpFactory;
import org.arkanoid.game.Game;

public abstract class Brick extends GameObject {

    Paddle paddle;
    PowerUp powerUp;
    protected final int tileX;
    protected final int tileY;
    protected final int width = 16;
    protected final int height = 8;
    protected boolean canDestroy;
    protected int health;

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

    public int getHealth() {
        return health;
    }

    public Brick setPaddle(Paddle paddle) {
        this.paddle = paddle;
        return this;
    }

    public boolean isCanDestroy() {
        return canDestroy;
    }

    @Override
    public void destroy() {
        super.destroy();

        if (powerUp != null) {
            powerUp.destroy();
        }
    }

    protected abstract int getScoreValue();

    /**
     * Destroys the brick and removes it from the game world.
     */
    public void breakBrick() {
        this.health--;

        
        if (!canDestroy || this.health > 0) {
            return;
        }

        Game.getInstance().addScore(getScoreValue());

        canDestroy = false;

        
        if (FXGLMath.randomBoolean(0.36)) {
            Point2D brickPosition = entity.getPosition();
            System.out.println(brickPosition.getX());
            System.out.println(brickPosition.getY());
            SpawnData spawnData = new SpawnData(brickPosition);

            if (paddle != null) {
                powerUp = PowerUpFactory.spawnRandomPowerUp(spawnData, paddle);
            }

        }

        if (entity != null && entity.isActive()) {
            
            if (entity.getBoundingBoxComponent() != null) {
                entity.getBoundingBoxComponent().clearHitBoxes();
            }

            
            entity.removeFromWorld();
        }
    }

    @Override
    public void onUpdate(double deltaTime) {
        if (powerUp != null) {
            powerUp.onUpdate(deltaTime);

            if (powerUp.isOutOfBound()) {
                powerUp.destroy();
                powerUp = null;
            }
        }
    }
}