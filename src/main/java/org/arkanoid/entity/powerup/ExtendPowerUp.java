package org.arkanoid.entity.powerup;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;
import org.arkanoid.component.ExtendComponent;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupType;
import org.arkanoid.manager.SoundManager;


public class ExtendPowerUp extends PowerUp {

    public ExtendPowerUp(SpawnData data) {
        super(data);
    }

    @Override
    public PowerupType getType() {
        return PowerupType.EXTEND;
    }

    /**
     * Applies the "extend paddle" effect to the given paddle entity.
     *
     * <p>If the paddle already has an {@link ExtendComponent},
     * its internal timer is reset (extending the effect duration).
     * Otherwise, a new {@link ExtendComponent} is added for 5 seconds.
     *
     * @param paddleEntity the paddle entity to apply the effect to
     */
    @Override
    public void applyEffect(Entity paddleEntity) {
        System.out.println("Paddle get more Bigger");


        if (paddleEntity.hasComponent(ExtendComponent.class)) {
            paddleEntity.getComponent(ExtendComponent.class).resetTimer(); 
        } else {
            paddleEntity.addComponent(new ExtendComponent(Duration.seconds(5)));
        }


    }

    /**
     * Called when this power-up collides with another game object.
     *
     * <p>If the object is a {@link Paddle}, this method plays a sound,
     * applies the extension effect, and removes the power-up from the world.
     *
     * @param e the {@link GameObject} that collided with this power-up
     */
    @Override
    public void onCollisionWith(GameObject e) {
        SoundManager.play("paddle_extend.wav");
        System.out.println("Extend power up collided with paddle");

        if (!(e instanceof Paddle curPaddle)) {
            return;
        }

        applyEffect(curPaddle.getEntity());
        if (!(entity != null && entity.isActive())) {
            return;
        }

        if (entity.getBoundingBoxComponent() != null) {
            entity.getBoundingBoxComponent().clearHitBoxes();
        }

        entity.removeFromWorld();
    }
}
