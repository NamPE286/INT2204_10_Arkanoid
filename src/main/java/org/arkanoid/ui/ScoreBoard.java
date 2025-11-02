package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard {

    private Label scoreLabel;
    private Label highScoreLabel;

    /**
     * Constructs a new {@code ScoreBoard} and initializes
     * the score and high-score labels.
     *
     * <p>The labels are automatically added to the FXGL UI scene graph.</p>
     */
    public ScoreBoard() {
        initLabels();
    }


    /**
     * Initializes the score and high-score labels with NES-style font
     * and binds them to FXGL integer properties:
     * <ul>
     *     <li>{@code score} → displays as {@code "1UP <value>"}</li>
     *     <li>{@code highScore} → displays as {@code "HIGH SCORE <value>"}</li>
     * </ul>
     *
     * <p>This method also sets label color, font size, and position,
     * then attaches them to the active UI scene via {@link FXGL#addUINode}.</p>
     */
    private void initLabels() {
        Font nesFont = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 24);

        
        scoreLabel = new Label();
        scoreLabel.setFont(nesFont);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.textProperty().bind(FXGL.getip("score").asString("1UP %d"));
        scoreLabel.setTranslateX(20);
        scoreLabel.setTranslateY(20);

        
        highScoreLabel = new Label();
        highScoreLabel.setFont(nesFont);
        highScoreLabel.setTextFill(Color.WHITE);
        highScoreLabel.textProperty().bind(FXGL.getip("highScore").asString("HIGH SCORE %d"));
        highScoreLabel.setTranslateX(FXGL.getAppWidth() / 2.0 - 100);
        highScoreLabel.setTranslateY(20);

        FXGL.addUINode(scoreLabel);
        FXGL.addUINode(highScoreLabel);
    }
}
