package org.arkanoid.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.arkanoid.component.animation.LaserPaddleAnimationComponent;
import org.arkanoid.component.animation.PaddleAnimationComponent;
import org.arkanoid.entity.Laser;
import org.arkanoid.entity.Wall;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.game.Game;
import org.arkanoid.level.Level;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.utilities.SchedulerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class LaserComponent extends Component {
    private int ammo = 10;
    private ScheduledFuture<?> timer;
    private final int PADDLE_WIDTH = 32;
    private final int PADDLE_HEIGHT = 8;
    private static final Duration TRANSFORM_DURATION = Duration.seconds(0.5);

    public LaserComponent() {

    }

    private List<Image> getTransformFrames(String type) {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (type == null || type.equals("InIt")) {
                frames.add(new WritableImage(image.getPixelReader(), 112, i * 8, 32, 8));
            } else if (type.equals("OutIt")) {
                frames.add(new WritableImage(image.getPixelReader(), 112, 64 - i * 8, 32, 8));
            }
        }
        return frames;
    }

    public void addAmmo(int amount) {
        this.ammo += amount;
    }

    public void fire() {
        if (ammo > 0) {
            double paddleCenterX = entity.getX() + entity.getWidth() / 2;
            double paddleY = entity.getY();
            final int LASER_HEIGHT = 11;
            double x1 = paddleCenterX - 14;
            double x2 = paddleCenterX + 14;
            double y = paddleY - LASER_HEIGHT;

            Laser laser1 = new Laser((int)x1, (int)y, "LEFT");
            Laser laser2 = new Laser((int)x2, (int)y, "RIGHT");

            if (Game.getInstance().getCurrentLevel() != null) {
                Game.getInstance().getCurrentLevel().addLaser(laser1);
                Game.getInstance().getCurrentLevel().addLaser(laser2);

                List<Brick> bricks = Game.getInstance().getCurrentLevel().getBricks();
                Wall leftWall = Game.getInstance().getCurrentLevel().getLeftwall();
                Wall rightWall = Game.getInstance().getCurrentLevel().getRightwall();
                Wall topWall = Game.getInstance().getCurrentLevel().getTopwall();
                for (Brick brick : bricks) {
                    if (brick.getEntity() != null && brick.getEntity().isActive()) {
                        laser1.listenToCollisionWith(brick)
                                .listenToCollisionWith(leftWall)
                                .listenToCollisionWith(rightWall)
                                .listenToCollisionWith(topWall);
                        laser2.listenToCollisionWith(brick)
                                .listenToCollisionWith(leftWall)
                                .listenToCollisionWith(rightWall)
                                .listenToCollisionWith(topWall);
                    }
                }
            }

            SoundManager.play("paddle_fire.wav");
            ammo -= 2;

            if (ammo <= 0) {
                if (entity != null) {
                    entity.removeComponent(LaserComponent.class);
                }
            }
        } else {
            if (entity != null) {
                entity.removeComponent(LaserComponent.class);
            }
        }
    }

    @Override
    public void onAdded() {
        if (entity.hasComponent(PaddleAnimationComponent.class)) {
            entity.removeComponent(PaddleAnimationComponent.class);
        }

        AnimationChannel animTransformLoop = new AnimationChannel(getTransformFrames("InIt"), TRANSFORM_DURATION);
        AnimatedTexture transformTexture = new AnimatedTexture(animTransformLoop);

        entity.getViewComponent().addChild(transformTexture);
        transformTexture.play();

        FXGL.runOnce(() -> {
            entity.getViewComponent().removeChild(transformTexture);

            if (entity != null && entity.isActive()) {
                entity.addComponent(new LaserPaddleAnimationComponent());
            }

        }, TRANSFORM_DURATION);

        entity.getBoundingBoxComponent().clearHitBoxes();
        entity.getBoundingBoxComponent().addHitBox(
                new HitBox(BoundingShape.box(PADDLE_WIDTH, PADDLE_HEIGHT))
        );

        entity.setX(entity.getX());
    }

    @Override
    public void onRemoved() {

        if (entity == null || !entity.isActive()) {
            return;
        }
        final Entity localEntity = entity;


        if (localEntity.hasComponent(LaserPaddleAnimationComponent.class)) {
            localEntity.removeComponent(LaserPaddleAnimationComponent.class);
        }

        AnimationChannel animTransformLoop = new AnimationChannel(getTransformFrames("OutIt"), TRANSFORM_DURATION);
        AnimatedTexture transformTexture = new AnimatedTexture(animTransformLoop);

        localEntity.getViewComponent().addChild(transformTexture);
        transformTexture.play();

        FXGL.runOnce(() -> {
            if (localEntity != null && localEntity.isActive()) {
                localEntity.getViewComponent().removeChild(transformTexture);
                localEntity.addComponent(new PaddleAnimationComponent());
            }

        }, TRANSFORM_DURATION);

        localEntity.getBoundingBoxComponent().clearHitBoxes();
        localEntity.getBoundingBoxComponent().addHitBox(
                new HitBox(BoundingShape.box(PADDLE_WIDTH, PADDLE_HEIGHT))
        );

        localEntity.setX(localEntity.getX());
    }
}
