package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.input.KeyCode;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends MovableObject {
    protected Entity createEntity(SpawnData spawnData) {
        var e = entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .viewWithBBox(TextureUtils.scale(
                        TextureUtils.crop(FXGL.texture("vaus.png"), 32, 0, 8, 32),
                        2.0
                ))
                .with(new PhysicsComponent())
                .build();
        physics = e.getComponent(PhysicsComponent.class);

        physics.setBodyType(BodyType.DYNAMIC);

        return e;
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                setLinearVelocity(-200, 0);
            }

            @Override
            protected void onActionEnd() {
                setLinearVelocity(0, 0);
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                setLinearVelocity(200, 0);
            }

            @Override
            protected void onActionEnd() {
                setLinearVelocity(0, 0);
            }
        }, KeyCode.D);
    }

    /**
     * Constructs a new paddle at the default position (0, 0).
     */
    public Paddle() {
        super();
    }

    /**
     * Constructs a new paddle at the specified coordinates.
     *
     * @param x the x-coordinate for the paddle's initial position
     * @param y the y-coordinate for the paddle's initial position
     */
    public Paddle(int x, int y) {
        super(x, y);
    }
}
