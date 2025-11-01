package org.arkanoid.entity.powerup;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.EntityType;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.game.Game;
import org.arkanoid.level.Level;
import org.arkanoid.manager.PowerupType;


import javafx.geometry.Point2D;

import java.util.ArrayList;
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
    public void applyEffect(Entity entity) {
        System.out.println("Need overload function");
    }

    public void applyEffect(Ball curBall) {
        curBall.createTwins();
    }

    @Override
    public void onCollisionWith(GameObject e) {
        System.out.println("Disrupt power up collied with paddle");
        if (!(e instanceof Paddle curpaddle)) {
            return;
        }

        Level curlevel = Game.getInstance().getCurrentLevel();
        List<Ball> Balllist = new ArrayList<>(curlevel.getBallTwinslist());
        Balllist.add(curlevel.getMainBall());
        for (var b : Balllist) {
            applyEffect(b);
        }

        this.getEntity().removeFromWorld();
    }
}
