package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.arkanoid.component.animation.ExtendAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class ExtendPaddle extends Paddle{

    @Override
    public Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .bbox(new HitBox(
                        "PADDLE",
                        new Point2D(0, 0),
                        BoundingShape.box(48, 8)))
                .with(new ExtendAnimationComponent())
                .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }

}
