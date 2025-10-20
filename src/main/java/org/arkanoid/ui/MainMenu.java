package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class MainMenu extends FXGLMenu {

    private Font nesFont;

    public MainMenu(MenuType type) {
        super(MenuType.MAIN_MENU);

        // font chữ NES.
        nesFont = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 24);

        // Lấy ảnh từ ship.png.
        Texture fullTexture = FXGL.texture("ship.png");

        // Cắt ảnh.
        Rectangle2D region = new Rectangle2D(0, 0, 200, 45);
        Texture cropped = fullTexture.subTexture(region);

        // Hiển thị ảnh.
        ImageView logoView = new ImageView(cropped.getImage());
        logoView.setFitWidth(400);
        logoView.setPreserveRatio(true);

        // Tạo HBox chứa logo và căn giữa.
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER);

        // Tạo nhãn kiểu NES (Start, Exit)
        Label startLabel = createMenuLabel("START GAME", this::fireNewGame);
        Label exitLabel = createMenuLabel("EXIT GAME", () -> FXGL.getGameController().exit());

        // Gom nhãn lại thành VBox
        VBox menuOptions = new VBox(20, startLabel, exitLabel);
        menuOptions.setAlignment(Pos.CENTER);

        // Thêm bản quyền nhỏ ở cuối
        Label copyright = new Label("HANOI36PP");
        copyright.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 16));
        copyright.setTextFill(Color.GRAY);
        copyright.setAlignment(Pos.CENTER);

        // Gom toàn bộ phần logo, menu và bản quyền vào VBox
        VBox menuBox = new VBox(30, logoBox, menuOptions, copyright);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        // Nền đen
        getContentRoot().setStyle("-fx-background-color: black;");

        // Thêm menuBox vào giao diện
        getContentRoot().getChildren().add(menuBox);
    }

    /**
     * Tạo một Label có font NES, hiệu ứng hover xanh lá, và hành động khi click.
     */
    private Label createMenuLabel(String text, Runnable action) {
        Label label = new Label(text);
        label.setFont(nesFont);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-cursor: hand;");

        // Khi di chuột vào → đổi sang màu xanh lá
        label.setOnMouseEntered(e -> label.setTextFill(Color.LIMEGREEN));
        // Khi rời chuột ra → về trắng
        label.setOnMouseExited(e -> label.setTextFill(Color.WHITE));
        // Khi nhấn chuột → chạy hành động
        label.setOnMouseClicked(e -> action.run());

        return label;
    }
}
