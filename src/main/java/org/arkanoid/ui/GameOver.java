package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.arkanoid.factory.LabelFactory;

public class GameOver {

    public static void show() {
        // Load custom font
        Font font = Font.loadFont(GameOver.class.getResourceAsStream("/fonts/nes.otf"), 32);

        // Create and style label
        Label label = new Label("GAME OVER");
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        label.setTranslateX(FXGL.getAppWidth() / 2.0 - 120); // slightly left of center
        label.setTranslateY(FXGL.getAppHeight() - 100.0);     // near bottom

        FXGL.addUINode(label); // add to UI

        // Return to Main Menu after 3 seconds
        FXGL.runOnce(() -> FXGL.getGameController().gotoMainMenu(),
            javafx.util.Duration.seconds(3));
    }
}

