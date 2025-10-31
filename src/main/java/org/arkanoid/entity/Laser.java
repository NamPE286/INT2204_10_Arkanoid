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

    @Override
    public void onCollisionWith(GameObject e) {
        if (e instanceof Brick) {
            this.onCollisionWith((Brick) e);
        } else if (e instanceof Wall) {
            this.onCollisionWith((Wall) e);
        }
    }

    public void onCollisionWith(Wall wall) {
        removeLaser();
    }

    public void onCollisionWith(Brick brick) {
        if (brick instanceof HardBrick || brick instanceof StrongBrick) {
            brick.getEntity().getComponentOptional(BrickAnimationComponent.class)
                    .ifPresent(BrickAnimationComponent::playHitAnimation);
        }

        System.out.println("Laser shot to brick");
        brick.breakBrick();
        removeLaser();
    }

    public void removeLaser() {
        if (entity != null && entity.isActive()) {

            if (entity.getBoundingBoxComponent() != null) {
                entity.getBoundingBoxComponent().clearHitBoxes();
            }


            entity.removeFromWorld();
        }
    }
}
