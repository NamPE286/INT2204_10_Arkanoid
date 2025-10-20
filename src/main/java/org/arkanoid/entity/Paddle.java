package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import org.arkanoid.utilities.TextureUtils;


import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Paddle extends MovableObject {

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.crop(FXGL.texture("vaus.png"), 16 * 2, 0, 4 * 2, 16 * 2);

        var e = entityBuilder(spawnData)
            .type(EntityType.PADDLE)
            .viewWithBBox(texture)
            .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

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


    /**
     * Collision checking wall, power up with paddle.
     * @param e the Entity that this object has collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        float vy = this.getVelocityY();
        if (e instanceof Wall) {
            float curVx = this.getVelocityX();

            if(curVx < 0 && this.getX() < e.getX()) {
                System.out.println("Paddle collision with Left wall");
                setLinearVelocity(0, vy);
            } else if(curVx > 0 && this.getX() > e.getX()) {
                System.out.println("Paddle collision with Right wall");
                setLinearVelocity(0, vy);
            }
        }

    }

}
