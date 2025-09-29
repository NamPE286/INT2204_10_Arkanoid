package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5),
                2.0
        );

        var e = entityBuilder(spawnData)
                .type(EntityType.BALL)
                .view(texture)
                .bbox(new HitBox("Ball", BoundingShape.circle(texture.getHeight() / 2.0)))
                .with(new PhysicsComponent())
                .build();
        physics = e.getComponent(PhysicsComponent.class);

        physics.setBodyType(BodyType.DYNAMIC);

        return e;
    }
}
