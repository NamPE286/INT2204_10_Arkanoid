package org.arkanoid.entity.powerup;

import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;
import org.arkanoid.component.ExtendComponent;
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

    @Override
    public void applyEffect(Paddle paddle) {
        System.out.println("LaserPowerUp");
        var paddleEntity = paddle.getEntity();

        if (paddleEntity.hasComponent(ExtendComponent.class)) {
            paddleEntity.getComponent(ExtendComponent.class).resetTimer();
        } else {

            paddleEntity.addComponent(new ExtendComponent(Duration.seconds(5)));

        }
    }

    @Override
    public void onCollisionWith(GameObject e) {
        SoundManager.play("paddle_fire.wav");
        System.out.println("Power up collided with paddle");

        if (!(e instanceof Paddle curPaddle)) {
            return;
        }

        applyEffect(curPaddle);
        if (!(entity != null && entity.isActive())) {
            return;
        }

        if (entity.getBoundingBoxComponent() != null) {
            entity.getBoundingBoxComponent().clearHitBoxes();
        }

        entity.removeFromWorld();
    }
}
