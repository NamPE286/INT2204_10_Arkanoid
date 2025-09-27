package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends GameObject {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;

    protected Entity createEntity(SpawnData spawnData) {
        return entityBuilder(spawnData)
                .type(EntityType.PADDLE)
                .viewWithBBox(FXGL.texture("ship.png"))
                .collidable()
                .build();
    }

    @Override
    public void initInput() {
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                entity.translateX(-5);
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                entity.translateX(5);
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
