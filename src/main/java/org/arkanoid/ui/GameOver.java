package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOver {

    public static void show() {
        
        Font font = Font.loadFont(GameOver.class.getResourceAsStream("/fonts/nes.otf"), 32);

        
        Label label = new Label("GAME OVER");
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        label.setTranslateX(FXGL.getAppWidth() / 2.0 - 120); 
        label.setTranslateY(FXGL.getAppHeight() - 100.0);     

        FXGL.addUINode(label); 

        
        FXGL.runOnce(() -> FXGL.getGameController().gotoMainMenu(),
            javafx.util.Duration.seconds(3));
    }
}

