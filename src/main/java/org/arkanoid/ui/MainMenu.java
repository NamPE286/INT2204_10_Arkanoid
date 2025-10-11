package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;



public class MainMenu extends FXGLMenu {
    public MainMenu(MenuType type) {
        super(MenuType.MAIN_MENU);

        // Lấy ảnh.
        Texture fullTexture = FXGL.texture("ship.png");

        // Cắt ảnh.
        Rectangle2D region = new Rectangle2D(0, 0, 200, 45);
        Texture cropped = fullTexture.subTexture(region);

        // hiển thị ảnh.
        ImageView LogoView = new ImageView(cropped.getImage());
        LogoView.setFitWidth(400);
        LogoView.setPreserveRatio(true);
        LogoView.setSmooth(false);

        // Tạo HBox chứa logo và căn giữa.
        HBox LogoBox = new HBox(LogoView);
        LogoBox.setAlignment(Pos.CENTER);

        // Tạo 2 nút "Start Game" và "Exit Game".
        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit Gmae");

        // Bắt đầu game và thoát game khi nhấn nút.
        startButton.setOnAction(e -> fireNewGame());
        exitButton.setOnAction(e -> fireExit());

        // Gom 2 nút trên thành VBox.
        VBox menuOptions = new VBox(20, startButton, exitButton);
        menuOptions.setAlignment(Pos.CENTER);

        // Gom toàn bộ phần logo và menu lại thành VBox.
        VBox menuBox = new VBox(30, LogoBox, menuOptions);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        // Nền đen.
        getContentRoot().setStyle("-fx-background-color: black;");

        getContentRoot().getChildren().add(menuBox);
    }
}

