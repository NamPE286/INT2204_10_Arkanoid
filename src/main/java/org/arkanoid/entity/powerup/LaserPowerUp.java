package org.arkanoid.entity.powerup;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.component.LaserComponent;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.manager.PowerupType;
import org.arkanoid.manager.SoundManager;

public class LaserPowerUp extends PowerUp {
    public LaserPowerUp(SpawnData data) {
        super(data);
    }

    @Override
    public PowerupType getType() {
        return PowerupType.LASER;
    }

    /**
     * Applies the laser effect to the given paddle entity.
     *
     * <p>If the paddle already has a {@link LaserComponent},
     * this method increases its ammo count.
     * Otherwise, it adds a new {@link LaserComponent} to enable laser firing.
     *
     * @param paddleEntity the paddle entity to which the effect is applied
     */
    @Override
    public void applyEffect(Entity paddleEntity) {
        System.out.println("LaserPowerUp");

        if (paddleEntity.hasComponent(LaserComponent.class)) {
            paddleEntity.getComponent(LaserComponent.class).addAmmo(4);
        } else {
            paddleEntity.addComponent(new LaserComponent());
        }
    }


    /**
     * Handles collision between this power-up and another game object.
     *
     * <p>If the object is a {@link Paddle}, this method plays a sound,
     * applies the laser effect, and then removes the power-up from the world.
     *
     * @param e the {@link GameObject} that collided with this power-up
     */
    @Override
    public void onCollisionWith(GameObject e) {
        SoundManager.play("paddle_fire.wav");
        System.out.println("Power up collided with paddle");

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
