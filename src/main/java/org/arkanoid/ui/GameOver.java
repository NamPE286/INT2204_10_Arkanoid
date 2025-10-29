package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.arkanoid.leaderboard.LeaderBoard;
import org.arkanoid.leaderboard.LeaderBoardEntry;

import java.util.List;

public class GameOver {
    // Load font NES từ resource của project.
    private static final Font FONT_BIG = Font.loadFont(GameOver.class.getResourceAsStream("/fonts/nes.otf"), 36);
    private static final Font FONT_SMALL = Font.loadFont(GameOver.class.getResourceAsStream("/fonts/nes.otf"), 18);

    /**
     * Hiển thị overlay Game Over.
     * Sử dụng StageStyle.TRANSPARENT để cho phép overlay trong suốt trên game.
     */
    public static void show() {
        // Tạo Stage trong suốt để overlay lên cửa sổ game (không làm mất background).
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setWidth(672);
        stage.setHeight(768);
        // Modal — chặn tương tác với các cửa sổ khác trong khi màn hình Game Over mở.
        stage.initModality(Modality.APPLICATION_MODAL);

        // Tiêu đề lớn "GAME OVER".
        Label title = new Label("GAME OVER");
        title.setFont(FONT_BIG);
        title.setTextFill(Color.RED); // chữ đỏ nổi bật

        // Hiển thị điểm người chơi.
        int finalScore = FXGL.geti("score");
        Label scoreLabel = new Label("YOUR SCORE: " + finalScore);
        scoreLabel.setFont(FONT_SMALL);
        scoreLabel.setTextFill(Color.WHITE);

        // Hướng dẫn nhập tên 3 ký tự.
        Label namePrompt = new Label("ENTER YOUR NAME (3 LETTERS):");
        namePrompt.setFont(FONT_SMALL);
        namePrompt.setTextFill(Color.YELLOW);

        // TextField để người chơi nhập 3 ký tự.
        TextField nameField = new TextField();
        nameField.setAlignment(Pos.CENTER);
        nameField.setPrefWidth(100);
        nameField.setFont(FONT_SMALL);
        // style: chữ trắng, nền field hơi mờ, viền xám.
        nameField.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.3); -fx-border-color: gray;");
        nameField.setPromptText("ABC");

        // Hướng dẫn nhỏ.
        Label hint = new Label("Press ENTER to save");
        hint.setFont(FONT_SMALL);
        hint.setTextFill(Color.LIGHTGRAY);

        // VBox chứa các thành phần theo hàng dọc, căn giữa.
        VBox content = new VBox(15, title, scoreLabel, namePrompt, nameField, hint);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        // Nền của hộp nội dung: màu đen bán trong suốt + bo góc.
        content.setBackground(new Background(
                new BackgroundFill(Color.rgb(0, 0, 0, 0.5), new CornerRadii(15), Insets.EMPTY)
        ));
        content.setPrefSize(600, 600);

        // Root là StackPane để có thể đặt overlay mờ tổng thể phía sau content.
        StackPane root = new StackPane(content);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25);");
        root.setPrefSize(800, 600);

        // Scene với nền TRANSPARENT để Stage trong suốt thực sự trong suốt.
        Scene scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);

        // Tạo/đọc leaderboard từ file (LeaderBoard xử lý I/O).
        LeaderBoard leaderboard = new LeaderBoard();

        // Sự kiện khi người chơi nhấn Enter trong TextField.
        nameField.setOnAction(e -> {
            // Lấy tên, trim khoảng trắng, chuyển in hoa.
            String name = nameField.getText().trim().toUpperCase();

            // Kiểm tra đúng 3 ký tự; nếu không hợp lệ, hiện lỗi tại hint.
            if (name.length() == 3) {
                // Lưu vào leaderboard (method addEntry chịu trách nhiệm ghi file).
                leaderboard.addEntry(name, finalScore);
                stage.close();
                showLeaderboard(leaderboard);
            } else {
                // Nếu không đúng, cảnh báo người chơi.
                hint.setTextFill(Color.RED);
                hint.setText("Name must be 3 characters!");
            }
        });

        // Hiển thị Stage (overlay).
        stage.show();
    }

    /**
     * Hiển thị Leaderboard (Top 5).
     */
    private static void showLeaderboard(LeaderBoard leaderboard) {
        // Stage trong suốt để overlay trên game.
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("LEADERBOARD");

        // Tiêu đề.
        Label title = new Label("TOP 5 SCORES");
        title.setFont(FONT_BIG);
        title.setTextFill(Color.CYAN);

        // Hộp chứa danh sách điểm.
        VBox entriesBox = new VBox(5);
        entriesBox.setAlignment(Pos.CENTER);

        // Lấy danh sách điểm (được load từ file bởi LeaderBoard).
        List<LeaderBoardEntry> entries = leaderboard.getEntries();
        int rank = 1;

        // Hiển thị chỉ Top 5 (nếu có ít hơn 5 dòng, sẽ hiển thị đủ số dòng).
        for (LeaderBoardEntry entry : entries) {
            if (rank > 5) break;
            Label label = new Label(String.format("%d. %s  -  %d", rank++, entry.getName(), entry.getScore()));
            label.setFont(FONT_SMALL);
            label.setTextFill(Color.WHITE);
            entriesBox.getChildren().add(label);
        }

        // Hướng dẫn quay về menu.
        Label hint = new Label("Press ENTER to return to Main Menu");
        hint.setFont(FONT_SMALL);
        hint.setTextFill(Color.GRAY);

        // VBox chứa toàn bộ nội dung bảng xếp hạng.
        VBox content = new VBox(20, title, entriesBox, hint);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(25));
        // Nền bán trong suốt và bo góc để vẫn thấy game phía sau.
        content.setBackground(new Background(
                new BackgroundFill(Color.rgb(0, 0, 0, 0.55), new CornerRadii(20), Insets.EMPTY)
        ));
        content.setPrefSize(500, 450);

        // Root overlay với lớp phủ nhẹ.
        StackPane root = new StackPane(content);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        root.setPrefSize(500, 500);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);

        // Bắt phím ENTER để quay về menu chính (đóng overlay).
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    stage.close();
                    FXGL.getGameController().gotoMainMenu();
                }
            }
        });

        // Hiển thị Stage (overlay).
        stage.show();
    }
}