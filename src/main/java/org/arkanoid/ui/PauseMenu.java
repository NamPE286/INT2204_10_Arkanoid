package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameController;

public class PauseMenu extends FXGLMenu {
    public PauseMenu(MenuType type) {
        super(type);

        var bg = new Rectangle(getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.5));
        getContentRoot().getChildren().add(bg);


        Button btnResume = new Button("Resume");
        btnResume.setOnAction(e -> fireResume());

        Button btnToMain = new Button("Main Menu");
        btnToMain.setOnAction(e -> getGameController().gotoMainMenu());

        VBox box = new VBox(20, btnResume, btnToMain);

        box.setAlignment(Pos.CENTER);
        box.setTranslateX((getAppWidth() - box.getWidth()) / 2);
        box.setTranslateY((getAppHeight() - box.getHeight()) / 2);

        getContentRoot().getChildren().add(box);
    }
}
