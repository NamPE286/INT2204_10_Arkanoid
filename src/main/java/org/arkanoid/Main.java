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
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
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
        BackGround backGroundmanager = new BackGround();
        backGroundmanager.displayBackgroundeachLevel(4);
        var paddle = new Paddle(WIDTH / 2, HEIGHT - 50);
        var brick = new Brick(WIDTH / 2, HEIGHT / 2, 0, 0);
        var ball = new Ball(WIDTH / 2, HEIGHT - 50 - 100)
                .setLinearVelocity(30f, 50f)
                .listenToCollisionWith(paddle)
                .listenToCollisionWith(brick);

        gameObjects.add(paddle);
        gameObjects.add(ball);
        gameObjects.add(brick);

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
