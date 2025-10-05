package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var title = FXGL.getUIFactoryService().newText("My Game", 48);
        var startButton = new Button("Start Game");
        var exitButton = new Button("Exit");

        startButton.setOnAction(e -> fireNewGame());
        exitButton.setOnAction(e -> fireExit());

        var menuBox = new VBox(15, title, startButton, exitButton);
        menuBox.setTranslateX((double) FXGL.getAppWidth() / 2 - 100);
        menuBox.setTranslateY((double) FXGL.getAppHeight() / 2 - 100);

        getContentRoot().getChildren().add(menuBox);
    }
}