package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.Stack;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameController;
import static com.almasb.fxgl.dsl.FXGLForKtKt.gets;

public class PauseMenu extends FXGLMenu {
    public PauseMenu(MenuType type) {
        super(type);

        // Nền mờ phủ toàn màn hình.
        Rectangle bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0, 0, 0, 0.5));

        // Font chữ nes.
        Font nesFontLarge = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 64);
        Font nesFontSmall = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 20);

        // Dòng chữ "PAUSE".
        Label pauseLabel = new Label("PAUSE");
        pauseLabel.setFont(nesFontLarge);
        pauseLabel.setTextFill(Color.WHITE);
        pauseLabel.setAlignment(Pos.CENTER);

        // Dòng chữ "PRESS ESC TO CONTINUE".
        Label hintLabel = new Label("PRESS ESC TO CONTINUE");
        hintLabel.setFont(nesFontSmall);
        hintLabel.setTextFill(Color.WHITE);
        hintLabel.setAlignment(Pos.CENTER);

        // Gom 2 dòng chữ vào VBox.
        VBox box = new VBox(20, pauseLabel, hintLabel);
        box.setAlignment(Pos.CENTER);

        // Căn giữa và phủ nền bằng StackPane.
        StackPane root = new StackPane(bg, box);
        root.setPrefSize(getAppWidth(), getAppHeight());
        root.setAlignment(Pos.CENTER);

        // Thêm tất cả vào menu.
        getContentRoot().getChildren().add(root);
    }
}

