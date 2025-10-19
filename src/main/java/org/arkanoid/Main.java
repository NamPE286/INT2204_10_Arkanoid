package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import org.arkanoid.entity.*;
import org.arkanoid.factory.LabelFactory;
import org.arkanoid.factory.SceneFactory;
import org.arkanoid.ui.BackGround;

import java.util.ArrayList;

public class Main extends GameApplication {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 672;
    private static final int WALLTHICK = 24;
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
        settings.setTicksPerSecond(60);
        settings.setSceneFactory(new SceneFactory());

    }

    @Override
    protected void initGame() {
        // Init background.
        BackGround backGround = new BackGround();
        backGround.displayBackgroundeachLevel(1);

        // Init wall.
        var leftwall = new Wall(0, 0, HEIGHT, WALLTHICK);
        var topwall = new Wall(0, 0 , WALLTHICK, WIDTH);
        var rightwall = new Wall(WIDTH - WALLTHICK, 0, HEIGHT, WALLTHICK);

        var paddle = new Paddle(WIDTH / 2, HEIGHT - 50);
        var brick1 = new Brick(300, 100, 0, 0);
        var ball = new Ball(WIDTH / 2, HEIGHT - 50 - 100)
                .setLinearVelocity(300f, 300f)
                .listenToCollisionWith(paddle)
                .listenToCollisionWith(brick1);

        gameObjects.add(paddle);
        gameObjects.add(ball);
        gameObjects.add(brick1);
    }

    @Override
    protected void initUI() {
        Label label = labelFactory.createLabel("HELLO, WORLD!");
        FXGL.addUINode(label, 280.0, 20.0);
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
