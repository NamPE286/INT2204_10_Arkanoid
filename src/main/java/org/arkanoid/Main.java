package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import org.arkanoid.entity.*;
import org.arkanoid.factory.LabelFactory;
import org.arkanoid.factory.SceneFactory;
import org.arkanoid.manager.PowerupAniManager;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.ui.Background;
import org.arkanoid.ui.ScoreBoard;

import java.util.Map;
import java.util.ArrayList;

public class Main extends GameApplication {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 672;
    private static final int THICK = WIDTH / 28;
    private final LabelFactory labelFactory = new LabelFactory("/fonts/nes.otf", 20);
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    //khai bao background

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("Arkanoid");
        settings.setVersion("0.0.1");
        settings.setProfilingEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
        settings.setTicksPerSecond(60);
        settings.setSceneFactory(new SceneFactory());

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0); // player score.
        vars.put("highScore", 50000); // default high score.
    }

    @Override
    protected void initGame() {
        PowerupAniManager aniManager = new PowerupAniManager();
        aniManager.loadAnimations();
        FXGL.set("AnimationManager", aniManager);
        var leftwall = new Wall(0, 0, HEIGHT, THICK);
        var topwall = new Wall(0, 48, THICK, WIDTH);
        var rightwall = new Wall(WIDTH - THICK, 0, HEIGHT, THICK);
        var brick = new NormalBrick(300, 300, 1, 0);
        var paddle = new Paddle(WIDTH / 2, HEIGHT - 50)
                .listenToCollisionWith(leftwall)
                .listenToCollisionWith(rightwall);
        var brick2 = new NormalBrick(360, 360, 2, 0);

        var ball = new Ball(WIDTH / 2, HEIGHT - 50 - 100)
                .setLinearVelocity(300f, 300f)
                .listenToCollisionWith(paddle)
                .listenToCollisionWith(brick)
                .listenToCollisionWith(leftwall)
                .listenToCollisionWith(topwall)
                .listenToCollisionWith(rightwall)
                .listenToCollisionWith(brick)
                .listenToCollisionWith(brick2);
        gameObjects.add(paddle);
        gameObjects.add(ball);
        gameObjects.add(leftwall);
        gameObjects.add(topwall);
        gameObjects.add(rightwall);

        SoundManager.play("round_start.mp3");
    }

    @Override
    protected void initUI() {
        // Init scoreboard.
        new ScoreBoard();
        // Init background.
        Background backGround = new Background();
        backGround.displayLevel(1);
    }


    @Override
    protected void onUpdate(double deltaTime) {
        for (var i : gameObjects) {
            i.onUpdate(deltaTime);
        }
    }

    static void main(String[] args) {
        launch(args);
    }
}