package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class PauseMenu extends FXGLMenu {
    /**
     * Constructs a new {@code PauseMenu} for the specified {@link MenuType}.
     *
     * <p>It initializes the semi-transparent background, centers the labels,
     * and sets up the menu layout within the FXGL UI system.</p>
     *
     * @param type the type of menu; should be {@link MenuType#GAME_MENU} for pause screens.
     */
    public PauseMenu(MenuType type) {
        super(type);

        
        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0, 0, 0, 0.5));

        
        Font nesFontLarge = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 64);
        Font nesFontSmall = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 20);

        
        Label pauseLabel = new Label("PAUSE");
        pauseLabel.setFont(nesFontLarge);
        pauseLabel.setTextFill(Color.WHITE);
        pauseLabel.setAlignment(Pos.CENTER);

        
        Label hintLabel = new Label("PRESS ESC TO CONTINUE");
        hintLabel.setFont(nesFontSmall);
        hintLabel.setTextFill(Color.WHITE);
        hintLabel.setAlignment(Pos.CENTER);

        
        VBox box = new VBox(20, pauseLabel, hintLabel);
        box.setAlignment(Pos.CENTER);

        
        StackPane root = new StackPane(bg, box);
        root.setPrefSize(getAppWidth(), getAppHeight());
        root.setAlignment(Pos.CENTER);

        
        getContentRoot().getChildren().add(root);
    }
}

