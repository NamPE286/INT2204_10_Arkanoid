package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard {

    private Label scoreLabel;

    public ScoreBoard() {
        initLabels();
    }

    private void initLabels() {
        Font nesFont = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 24);

        // 1UP Score
        scoreLabel = new Label();
        scoreLabel.setFont(nesFont);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.textProperty().bind(FXGL.getip("score").asString("1UP %d"));
        scoreLabel.setTranslateX(20);
        scoreLabel.setTranslateY(20);

        FXGL.addUINode(scoreLabel);
    }
}
