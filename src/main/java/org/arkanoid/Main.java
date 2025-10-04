package org.arkanoid;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.control.Label;
import org.arkanoid.entity.*;
import org.arkanoid.factory.LabelFactory;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;

public class Main extends GameApplication {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private final LabelFactory labelFactory = new LabelFactory("/fonts/nes.otf", 20);
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("Arkanoid");
        settings.setVersion("0.0.1");
    }

    @Override
    protected void initGame() {
        gameObjects.add(new Paddle(WIDTH/2, HEIGHT - 50));
        gameObjects.add(new Ball(WIDTH / 2, HEIGHT - 50 - 100));
        gameObjects.add(new Brick(300, HEIGHT/2, 0, 0));
    }

    @Override
    protected void initUI() {
        Label label = labelFactory.createLabel("HELLO, WORLD!");
        FXGL.addUINode(label, 280.0, 20.0);
    }

    @Override
    protected void onUpdate(double deltaTime) {
        for(var i : gameObjects) {
            i.update(deltaTime);
        }
    }

    static void main(String[] args) {
        launch(args);
    }
}
