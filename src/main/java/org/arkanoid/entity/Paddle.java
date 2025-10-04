package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.input.KeyCode;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends MovableObject {

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        double factor = 3.0;
        var texture = TextureUtils.scale(
                TextureUtils.crop(FXGL.texture("vaus.png"), 32, 0, 8, 32),
                factor
        );

        return entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .view(texture)
                .bbox(new HitBox(BoundingShape.box(
                        texture.getWidth() * factor,
                        texture.getHeight() * factor)))
                .build();
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
