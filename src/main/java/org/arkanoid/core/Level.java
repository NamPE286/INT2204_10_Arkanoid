package org.arkanoid.core;

import java.util.ArrayList;
import java.util.List;
import org.arkanoid.Main;
import org.arkanoid.entity.Ball;
import org.arkanoid.entity.Brick;
import org.arkanoid.entity.NormalBrick;
import org.arkanoid.entity.Paddle;
import org.arkanoid.entity.Wall;
import org.arkanoid.manager.SoundManager;

public class Level {
    private static final int WALL_THICKNESS = Main.WIDTH / 28;

    Paddle paddle;
    Ball ball;
    List<Brick> bricks = new ArrayList<>();

    private void loadBrickConfiguration() {
        bricks.add(new NormalBrick(300, 300, 1, 0)
            .setPaddle(paddle));
        bricks.add(new NormalBrick(360, 360, 2, 0)
            .setPaddle(paddle));
    }

    public void onUpdate(double deltaTime) {
        paddle.onUpdate(deltaTime);
        ball.onUpdate(deltaTime);

        for (var brick : bricks) {
            brick.onUpdate(deltaTime);
        }
    }

    public Level() {
        var leftwall = new Wall(0, 0, Main.HEIGHT, WALL_THICKNESS);
        var topwall = new Wall(0, 48, WALL_THICKNESS, Main.WIDTH);
        var rightwall = new Wall(Main.WIDTH - WALL_THICKNESS, 0, Main.HEIGHT, WALL_THICKNESS);

        paddle = (Paddle) new Paddle(Main.WIDTH / 2, Main.HEIGHT - 50)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(rightwall);

        loadBrickConfiguration();

        ball = (Ball) new Ball(Main.WIDTH / 2, Main.HEIGHT - 50 - 100)
            .setLinearVelocity(300f, 300f)
            .listenToCollisionWith(paddle)
            .listenToCollisionWith(leftwall)
            .listenToCollisionWith(topwall)
            .listenToCollisionWith(rightwall);

        for (var brick : bricks) {
            ball.listenToCollisionWith(brick);
        }

        SoundManager.play("round_start.mp3");
    }
}
