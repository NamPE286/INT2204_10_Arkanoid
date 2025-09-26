package org.arkanoid;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Main extends GameApplication {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("Arkanoid");
        settings.setVersion("0.0.1");
    }

    @Override
    protected void initUI() {
        Font nesFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/nes.otf"),
                20
        );
        Label label = new Label("HELLO, WORLD!");

        label.setFont(nesFont);
        FXGL.addUINode(label, 280.0, 290.0);
    }

    static void main(String[] args) {
        launch(args);
    }
}
