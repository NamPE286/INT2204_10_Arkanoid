package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.component.animation.BrickAnimationComponent;
import org.arkanoid.component.animation.LaserShotAnimationComponent;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.entity.brick.HardBrick;
import org.arkanoid.entity.brick.StrongBrick;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.core.MovableObject;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Laser extends MovableObject {
    private final int LASER_WIDTH = 3;
    private final int LASER_HEIGHT = 11;
    private String type;
    private final float speed = -200;

    /**
     * Constructs a new {@code Laser} instance at the specified position.
     *
     * @param x    the initial X coordinate.
     * @param y    the initial Y coordinate.
     * @param type the type identifier of the laser (used for animation or logic).
     */
    public Laser(int x, int y, String type) {
        super(x, y);
        this.type = type;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("type", type);

        this.entity = createEntity(spawnData);
        setLinearVelocity(0, speed);
        spawn();
        initInput();
    }

    /**
     * Updates the laser's position and removes it if it goes out of screen bounds.
     *
     * @param deltaTime the time elapsed since the last frame, used for frame-based updates.
     */
    @Override
    public void onUpdate(double deltaTime) {
        super.onUpdate(deltaTime);
        if (isOutOfBound()) {
            removeLaser();
        }
    }

    @Override
    public Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.LASER)
                .bbox(new HitBox(BoundingShape.box(LASER_WIDTH, LASER_HEIGHT)))
                .with(new LaserShotAnimationComponent(this.type))
                .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }

    /**
     * Handles collision logic between the laser and another game object.
     *
     * @param e the {@link GameObject} that the laser has collided with.
     */
    @Override
    public void onCollisionWith(GameObject e) {
        if (e instanceof Brick) {
            this.onCollisionWith((Brick) e);
        } else if (e instanceof Wall) {
            this.onCollisionWith((Wall) e);
        }
    }

    /**
     * Called when the laser collides with a wall.
     *
     * <p>The laser is immediately removed since walls are indestructible.</p>
     *
     * @param wall the wall that the laser has hit.
     */
    public void onCollisionWith(Wall wall) {
        removeLaser();
    }

    /**
     * Called when the laser collides with a brick.
     *
     * <p>Plays a hit animation for {@link HardBrick} or {@link StrongBrick} types
     * before breaking the brick. The laser is then removed from the world.</p>
     *
     * @param brick the {@link Brick} that the laser has hit.
     */
    public void onCollisionWith(Brick brick) {
        if (brick instanceof HardBrick || brick instanceof StrongBrick) {
            brick.getEntity().getComponentOptional(BrickAnimationComponent.class)
                    .ifPresent(BrickAnimationComponent::playHitAnimation);
        }

        System.out.println("Laser shot to brick");
        brick.breakBrick();
        removeLaser();
    }

    /**
     * Removes the laser entity from the game world.
     *
     * <p>Before removal, the hitboxes are cleared to prevent lingering collision
     * detection frames. This ensures safe and consistent cleanup of the laser object.</p>
     */
    public void removeLaser() {
        if (entity != null && entity.isActive()) {

            if (entity.getBoundingBoxComponent() != null) {
                entity.getBoundingBoxComponent().clearHitBoxes();
            }


            entity.removeFromWorld();
        }
    }
}
