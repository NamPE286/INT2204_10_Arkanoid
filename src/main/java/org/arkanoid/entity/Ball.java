package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5),
                2.0
        );

        return entityBuilder(spawnData)
                .type(EntityType.BALL)
                .view(texture)
                .bbox(new HitBox(BoundingShape.box(texture.getWidth() * 2, texture.getHeight() * 2)))
                .build();
    }

    @Override
    public void onCollisionWith(Entity e) {
        System.out.println("Collide");
        // TODO
    }

    /**
     * Constructs a new ball at the default position (0, 0).
     */
    public Ball() {
        super();
    }

    @Override
    public void onUpdate(double deltaTime) {
        super.onUpdate(deltaTime);
    }

    /**
     * Constructs a new ball at the specified coordinates.
     *
     * @param x the x-coordinate for the ball's initial position
     * @param y the y-coordinate for the ball's initial position
     */
    public Ball(int x, int y) {
        super(x, y);
    }
}
