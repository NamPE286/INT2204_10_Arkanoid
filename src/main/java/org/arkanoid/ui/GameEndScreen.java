package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.arkanoid.leaderboard.LeaderBoard;
import org.arkanoid.leaderboard.LeaderBoardEntry;

import java.util.List;

public class GameEndScreen {

    // Load font NES từ resource của project.
    private static final Font FONT_BIG = Font.loadFont(
            GameEndScreen.class.getResourceAsStream("/fonts/nes.otf"), 36);
    private static final Font FONT_SMALL = Font.loadFont(
            GameEndScreen.class.getResourceAsStream("/fonts/nes.otf"), 18);

    private static final Font FONT_LEADERBOARD = FONT_SMALL;

    private static StackPane overlay;       // Overlay full màn hình.
    private static LeaderBoard leaderboard; // Top scores.
    private static int finalScore;          // Điểm người chơi.
    private static int finalTime;
    private static boolean isVictory;

    // Hiển thị thời gian theo format.
    public static String formatTime(int totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    //Hiển thị màn hình kết thúc.
    public static void show(boolean win) {
        isVictory = win;
        finalScore = FXGL.geti("score");
        finalTime = FXGL.geti("time");
        leaderboard = new LeaderBoard();

        overlay = new StackPane();
        overlay.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        FXGL.getGameScene().addUINode(overlay);

        // Đặt thông báo và màu sắc dựa trên kết quả
        String titleText = isVictory ? "VICTORY!" : "GAME OVER";
        Color titleColor = isVictory ? Color.YELLOW : Color.RED;

        VBox content = new VBox(20,
                createLabel(titleText, FONT_BIG, titleColor),
                createLabel("FINAL SCORE: " + finalScore, FONT_SMALL, Color.WHITE),
                createLabel("TIME ELAPSED: " + formatTime(finalTime), FONT_SMALL, Color.WHITE)
        );
        content.setAlignment(Pos.CENTER);
        overlay.getChildren().setAll(content);

        // Chờ 3 giây rồi chuyển sang màn hình nhập tên
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> showNameInputScreen()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    // Màn hình nhập tên.
    private static void showNameInputScreen() {
        Label prompt = createLabel("ENTER YOUR NAME FROM 3 CHARACTERS:", FONT_SMALL, Color.YELLOW);
        TextField nameField = new TextField();
        nameField.setPrefWidth(100);
        nameField.setFont(FONT_SMALL);
        nameField.setAlignment(Pos.CENTER); // Căn giữa lúc nhập tên.
        nameField.setStyle(
                "-fx-text-fill:white; -fx-background-color: rgba(0,0,0,0.3); -fx-border-color: gray;");
        nameField.setPromptText("ABC");

        nameField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.equals(newText.toUpperCase())) {
                nameField.setText(newText.toUpperCase());
            }
        });

        Label hint = createLabel("PRESS ENTER TO SAVE", FONT_SMALL, Color.LIGHTGRAY);

        VBox content = new VBox(10, prompt, nameField, hint);
        content.setAlignment(Pos.CENTER);
        overlay.getChildren().setAll(content);

        FXGL.runOnce(nameField::requestFocus, Duration.millis(10));

        // Xử lý khi nhấn Enter
        nameField.setOnAction(e -> {
            String name = nameField.getText().trim().toUpperCase();
            if (name.length() == 3) {
                leaderboard.addEntry(name, finalScore, finalTime);
                showLeaderboard(); // Chuyển sang màn hình leaderboard.
            } else {
                hint.setText("NAME MUST BE 3 CHARACTERS!");
                hint.setTextFill(Color.RED);
            }
        });
    }

    /**
     * Màn hình Top 5 leaderboard đã được cải tiến căn chỉnh bằng String.format.
     */
    private static void showLeaderboard() {
        VBox entriesBox = new VBox(5);
        entriesBox.setAlignment(Pos.CENTER);

        // Thiết lập chiều rộng cố định cho VBox để các Label có thể căn giữa StackPane.
        entriesBox.setPrefWidth(FXGL.getAppWidth() * 0.6);

        List<LeaderBoardEntry> entries = leaderboard.getEntries();

        // Tạo Header với căn chỉnh cố định.
        String headerString = String.format("%-3s %-5s %6s    %8s",
                "NO.", "NAME", "SCORE", "TIME");
        entriesBox.getChildren().add(
                createLabel(headerString, FONT_LEADERBOARD, Color.LIGHTGRAY));

        // Đường phân cách.
        entriesBox.getChildren().add(createLabel("----------------------------------", FONT_LEADERBOARD, Color.DARKGRAY));

        // Thêm các mục điểm số.
        for (int i = 0; i < Math.min(5, entries.size()); i++) {
            LeaderBoardEntry entry = entries.get(i);

            // Lấy chuỗi thời gian đã được định dạng sạch.
            String timeString = formatTime(entry.getTime());

            // Định dạng chuỗi cố định, đảm bảo các cột thẳng hàng.
            String entryString = String.format("%d. %-5s %6d    %8s",
                    i + 1,
                    entry.getName(),
                    entry.getScore(),
                    timeString);

            Label entryLabel = createLabel(entryString, FONT_LEADERBOARD, Color.WHITE);
            entryLabel.setMinWidth(entriesBox.getPrefWidth());
            entryLabel.setAlignment(Pos.CENTER);

            entriesBox.getChildren().add(entryLabel);
        }

        Label title = createLabel("TOP 5 HALL OF FAME", FONT_BIG, Color.CYAN);
        Label hint = createLabel("PRESS ENTER TO RETURN TO MAIN MENU", FONT_SMALL, Color.GRAY);

        VBox content = new VBox(15, title, entriesBox, hint);
        content.setAlignment(Pos.CENTER);
        overlay.getChildren().setAll(content);

        // Nhấn Enter quay về Main Menu.
        overlay.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                FXGL.getGameScene().removeUINode(overlay);
                FXGL.getGameController().gotoMainMenu();
            }
        });
        overlay.requestFocus();
    }

    // Tạo label tiện lợi.
    private static Label createLabel(String text, Font font, Color color) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(color);
        return label;
    }
}