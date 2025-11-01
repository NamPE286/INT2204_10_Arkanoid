package org.arkanoid.entity.powerup;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.box2d.dynamics.joints.LimitState;
import javafx.util.Duration;
import org.arkanoid.component.ExtendComponent;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.EntityType;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.manager.PowerupType;
import org.arkanoid.manager.SoundManager;

import javafx.geometry.Point2D;
import java.util.Optional;
import java.util.List;

public class DisruptPowerup extends PowerUp {

    public DisruptPowerup(SpawnData data) {
        super(data);
    }

    @Override
    public PowerupType getType() {
        return PowerupType.DISRUPT;
    }

    @Override
    public void applyEffect(Entity Ballentity) {
        System.out.println("Create 2 new balls");

        Ball ball1 = new Ball((int) Ballentity.getX(), (int) Ballentity.getY());
        Ball ball2 = new Ball((int) Ballentity.getX(), (int) Ballentity.getY());

        ball1.setLinearVelocity(-200, -300);
        ball2.setLinearVelocity(200, -300);
    }

    @Override
    public void onCollisionWith(GameObject e) {
        System.out.println("Disrupt power up collied with paddle");
        if (!(e instanceof Paddle curpaddle)) {
            return;
        }

        List<Entity> ballList = FXGL.getGameWorld().getEntitiesByType(EntityType.BALL);
        Entity mainBall = null;
        Point2D spawnPos;
        if (!ballList.isEmpty()) {
            mainBall = ballList.get(0);
            spawnPos = mainBall.getCenter();
            applyEffect(mainBall);
        } else {
            System.out.println("Cannot find the main Ball");
        }

        this.getEntity().removeFromWorld();
    }
}
