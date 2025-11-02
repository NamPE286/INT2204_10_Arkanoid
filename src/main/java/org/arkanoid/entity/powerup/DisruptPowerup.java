package org.arkanoid.entity.powerup;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.Paddle;
import org.arkanoid.game.Game;
import org.arkanoid.level.Level;
import org.arkanoid.manager.PowerupType;
import java.util.ArrayList;
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

    /**
     * Limit the number of twins ball.
     * @param e the Entity that this object has collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {
        System.out.println("Disrupt power up collied with paddle");
        if (!(e instanceof Paddle)) {
            return;
        }

        // Maximum number of balls is 24.
        final int maxBallwhenspawn = 9;
        Level curlevel = Game.getInstance().getCurrentLevel();

        List<Ball> Balllist = new ArrayList<>(curlevel.getBallTwinslist());
        Balllist.add(curlevel.getMainBall());

        int currentNumbal = Balllist.size();
        int freeslot = maxBallwhenspawn - currentNumbal;

        if (freeslot <= 0) {
            this.getEntity().removeFromWorld();
            return;
        }

        for (var b : Balllist) {
            if (!b.isHeldByPaddle()) {
                applyEffect(b);
            }
        }

        this.getEntity().removeFromWorld();
    }
}
