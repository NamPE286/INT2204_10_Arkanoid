package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends MovableObject {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;

    protected Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .viewWithBBox(FXGL.texture("ship.png"))
                .with(new PhysicsComponent())
                .build();
        physics = e.getComponent(PhysicsComponent.class);

        physics.setBodyType(BodyType.DYNAMIC);

        return e;
    }

    @Override
    public void initInput() {
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                setVelocity(-200, 0);
            }

            @Override
            protected void onActionEnd() {
                setVelocity(0, 0); // stop moving when key released
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                setVelocity(200, 0);
            }

            @Override
            protected void onActionEnd() {
                setVelocity(0, 0); // stop moving when key released
            }
        }, KeyCode.D);
    }

    public Paddle() {
        super();
    }

    public Paddle(int x, int y) {
        super(x - WIDTH / 2, y - HEIGHT / 2);
    }
}
