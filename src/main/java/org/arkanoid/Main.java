package org.arkanoid;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import org.arkanoid.entity.Paddle;
import org.arkanoid.factory.LabelFactory;

public class Main extends GameApplication {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private final LabelFactory labelFactory = new LabelFactory("/fonts/nes.otf", 20);
    private final Paddle paddle = new Paddle(WIDTH / 2, HEIGHT - 50);

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("Arkanoid");
        settings.setVersion("0.0.1");
    }

    @Override
    protected void initInput() {
        paddle.initInput();
    }

    @Override
    protected void initUI() {
        Label label = labelFactory.createLabel("HELLO, WORLD!");
        FXGL.addUINode(label, 280.0, 20.0);
    }

    @Override
    protected void initGame() {
        paddle.spawn();
    }

    static void main(String[] args) {
        launch(args);
    }
}
