package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends FXGLMenu {

    private Font nesFont;

    // Danh sách các lựa chọn menu (START GAME, EXIT GAME).
    private List<Label> menuItems = new ArrayList<>();

    // Danh sách các mũi tên hiển thị bên cạnh lựa chọn.
    private List<ImageView> arrowSlots = new ArrayList<>();

    // Chỉ số của lựa chọn hiện tại.
    private int currentIndex = 0;

    // Hình ảnh mũi tên.
    private ImageView arrowImage;

    // Hiệu ứng nhấp nháy.
    private Timeline blinkTimeline;


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

        // Loát ảnh mũi tên.
        Texture arrowTexture = FXGL.texture("arrow.png");
        arrowTexture.setFitWidth(24);
        arrowTexture.setPreserveRatio(true);
        arrowImage = new ImageView(arrowTexture.getImage());
        arrowImage.setSmooth(false);

        // Các lựa chọn trong mainmenu.
        String[] options = {"START GAME", "EXIT  GAME"};

        // VBox chứa các dòng lựa chọn.
        VBox menuVBox = new VBox(20);
        menuVBox.setAlignment(Pos.CENTER);

        // Tạo từng lựa chọn gồm label + chỗ hiển thị mũi tên.
        for (String option : options) {
            // Tạo label.
            Label label = new Label(option);
            label.setFont(nesFont);
            label.setTextFill(Color.WHITE);

            // Chỗ gắn mũi tên.
            ImageView arrowSlot = new ImageView();
            arrowSlot.setFitWidth(24);
            arrowSlot.setPreserveRatio(true);

            // HBox label + mũi tên.
            HBox row = new HBox(15, arrowSlot, label);
            row.setAlignment(Pos.CENTER);

            // lưu vào.
            menuItems.add(label);
            arrowSlots.add(arrowSlot);
            menuVBox.getChildren().add(row);
        }

        // Thêm bản quyền nhỏ ở cuối.
        Label copyright = new Label("HANOI36PP");
        copyright.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 16));
        copyright.setTextFill(Color.GRAY);

        // Gom toàn bộ phần logo, menu và bản quyền vào VBox.
        VBox menuBox = new VBox(30, logoBox, menuVBox, copyright);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        // Nền đen.
        getContentRoot().setStyle("-fx-background-color: black;");

        // Thêm menuBox vào giao diện.
        getContentRoot().getChildren().add(menuBox);
        getContentRoot().setFocusTraversable(true);
        getContentRoot().requestFocus();
        getContentRoot().setOnMouseClicked(e -> e.consume()); // Không cho chuột click.

        // Thêm điều khiển bàn phím và highlight.
        addKeyControls();
        highlightCurrent();
    }

    // Xử lý phím.
    private void addKeyControls() {
        getContentRoot().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                currentIndex = (currentIndex + 1) % menuItems.size();
                highlightCurrent();
            }
            if (e.getCode() == KeyCode.ENTER) {
                if (currentIndex == 0) {
                    fireNewGame();
                } else {
                    FXGL.getGameController().exit();
                }
            }
        });
    }

    // Đổi màu và gắn mũi tên vào lựa chọn.
    private void highlightCurrent() {
        for (int i = 0; i < menuItems.size(); i++) {
            arrowSlots.get(i).setImage(i == currentIndex ? arrowImage.getImage() : null);
            menuItems.get(i).setTextFill(i == currentIndex ? Color.GREEN : Color.WHITE);
        }
        blinkingEffect();
    }

    // Hiệu ứng nhấp nháy mũi tên lựa chọn.
    private void blinkingEffect() {
        // Tránh nhân đôi hiệu ứng.
        if (blinkTimeline != null) {
            blinkTimeline.stop();
        }

        blinkTimeline = new Timeline(
                // Tại 0s mũi tên hiện, chữ xanh.
                new KeyFrame(Duration.seconds(0.0), e -> {
                    arrowSlots.get(currentIndex).setOpacity(1);
                    menuItems.get(currentIndex).setTextFill(Color.GREEN);
                }),

                // Tại 0.5s mũi tên ẩn, chữ trắng.
                new KeyFrame(Duration.seconds(0.5), e -> {
                    arrowSlots.get(currentIndex).setOpacity(0);
                    menuItems.get(currentIndex).setTextFill(Color.WHITE);
                }),

                // Tại 1.0s mũi tên hiện, chữ xanh.
                new KeyFrame(Duration.seconds(1.0), e -> {
                    arrowSlots.get(currentIndex).setOpacity(1);
                    menuItems.get(currentIndex).setTextFill(Color.GREEN);
                })
        );

        // Lặp vô hạn.
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);

        // Bắt đầu chạy hiệu ứng.
        blinkTimeline.play();
    }
}
