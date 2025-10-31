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

    /**
     * Do notthing.
     */
    public ExtendPowerUp(SpawnData data) {
        super(data);

    }

    @Override
    public PowerupType getType() {
        return PowerupType.EXTEND;
    }

    @Override
    public void applyEffect(Entity paddleEntity) {
        System.out.println("Paddle get more Bigger");


        if (paddleEntity.hasComponent(ExtendComponent.class)) {
            paddleEntity.getComponent(ExtendComponent.class).resetTimer(); 
        } else {
            
            paddleEntity.addComponent(new ExtendComponent(Duration.seconds(5)));

        }


    }

    @Override
    public void onCollisionWith(GameObject e) {
        SoundManager.play("paddle_extend.wav");
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
