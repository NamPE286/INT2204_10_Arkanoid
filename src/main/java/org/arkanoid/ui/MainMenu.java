package org.arkanoid.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.arkanoid.Main;
import org.arkanoid.leaderboard.LeaderBoard;
import org.arkanoid.leaderboard.LeaderBoardEntry;
import org.arkanoid.manager.GameStateManager;

import java.util.ArrayList;
import java.util.List;

import static org.arkanoid.ui.GameEndScreen.formatTime;

public class MainMenu extends FXGLMenu {

    private final Font nesFont;
    private final List<Label> menuItems = new ArrayList<>();
    private final List<ImageView> arrowSlots = new ArrayList<>();
    private int currentIndex = 0;
    private final ImageView arrowImage;
    private Timeline blinkTimeline;

    private final LeaderBoard leaderBoard = new LeaderBoard();
    private VBox leaderBoardBox;

    public MainMenu(MenuType type) {
        super(MenuType.MAIN_MENU);

        nesFont = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 24); // Load font.

        // Logo.
        Texture fullTexture = FXGL.texture("ship.png");
        Rectangle2D region = new Rectangle2D(0, 0, 200, 45);
        Texture cropped = fullTexture.subTexture(region);
        ImageView logoView = new ImageView(cropped.getImage());
        logoView.setFitWidth(400);
        logoView.setPreserveRatio(true);
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER);

        // Arrow.
        Texture arrowTexture = FXGL.texture("arrow.png");
        arrowTexture.setFitWidth(24);
        arrowTexture.setPreserveRatio(true);
        arrowImage = new ImageView(arrowTexture.getImage());
        arrowImage.setSmooth(false);

        // Menu.
        boolean hasSavedGame = GameStateManager.hasSavedGame();
        List<String> optionsList = new ArrayList<>();
        
        if (hasSavedGame) {
            optionsList.add("CONTINUE");
        }
        optionsList.add("NEW GAME");
        optionsList.add("LEADERBOARD");
        optionsList.add("EXIT GAME");
        
        String[] options = optionsList.toArray(new String[0]);
        VBox menuVBox = new VBox(20);
        menuVBox.setAlignment(Pos.CENTER);

        for (String option : options) {
            Label label = new Label(option);
            label.setFont(nesFont);
            label.setTextFill(Color.WHITE);

            ImageView arrowSlot = new ImageView();
            arrowSlot.setFitWidth(24);
            arrowSlot.setPreserveRatio(true);

            HBox row = new HBox(15, arrowSlot, label);
            row.setAlignment(Pos.CENTER);

            menuItems.add(label);
            arrowSlots.add(arrowSlot);
            menuVBox.getChildren().add(row);
        }

        // Copyright.
        Label copyright = new Label("HANOI36PP");
        copyright.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 16));
        copyright.setTextFill(Color.GRAY);

        // Gộp menu chính.
        VBox menuBox = new VBox(30, logoBox, menuVBox, copyright);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        // LeaderBoard overlay.
        leaderBoardBox = createLeaderBoardBox();
        leaderBoardBox.setVisible(false);
        leaderBoardBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        leaderBoardBox.setLayoutX(0);
        leaderBoardBox.setLayoutY(0);

        getContentRoot().setStyle("-fx-background-color: black;");
        getContentRoot().getChildren().add(menuBox);      // Menu dưới.
        getContentRoot().getChildren().add(leaderBoardBox); // Overlay.

        getContentRoot().setFocusTraversable(true);
        getContentRoot().requestFocus();
        getContentRoot().setOnMouseClicked(e -> e.consume()); // Chặn click.

        addKeyControls();
        highlightCurrent();
    }

    private void addKeyControls() {
        getContentRoot().setOnKeyPressed(e -> {
            if (leaderBoardBox.isVisible()) { // ESC thoát LeaderBoard.
                if (e.getCode() == KeyCode.ESCAPE) {
                    leaderBoardBox.setVisible(false);
                    if (blinkTimeline != null) blinkTimeline.play(); // Tiếp tục nhấp nháy.
                    getContentRoot().requestFocus();
                }
                return;
            }

            if (e.getCode() == KeyCode.UP) currentIndex = Math.max(0, currentIndex - 1); // Lên.
            if (e.getCode() == KeyCode.DOWN) currentIndex = Math.min(menuItems.size() - 1, currentIndex + 1); // Xuống.
            highlightCurrent();

            if (e.getCode() == KeyCode.ENTER) {
                handleMenuSelection();
            }
        });
    }

    private void handleMenuSelection() {
        String selectedOption = menuItems.get(currentIndex).getText();
        
        switch (selectedOption) {
            case "CONTINUE" -> fireContinueGame();
            case "START GAME" -> fireNewGame();
            case "LEADERBOARD" -> {
                leaderBoardBox.setVisible(true);
                if (blinkTimeline != null) blinkTimeline.pause();
                leaderBoardBox.requestFocus();
            }
            case "EXIT GAME" -> FXGL.getGameController().exit();
        }
    }

    private void fireContinueGame() {
        Main.shouldContinue = true;
        fireNewGame();
    }

    private void highlightCurrent() {
        for (int i = 0; i < menuItems.size(); i++) {
            arrowSlots.get(i).setImage(i == currentIndex ? arrowImage.getImage() : null);
            arrowSlots.get(i).setOpacity(1); // Arrow luôn hiện.
            menuItems.get(i).setTextFill(i == currentIndex ? Color.GREEN : Color.WHITE);
        }
        startBlinking();
    }

    private void startBlinking() {
        if (blinkTimeline != null) blinkTimeline.stop();
        blinkTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> toggleBlink()));
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.play();
    }

    private void toggleBlink() {
        Label label = menuItems.get(currentIndex);
        boolean isGreen = label.getTextFill().equals(Color.GREEN);
        label.setTextFill(isGreen ? Color.WHITE : Color.GREEN); // Đổi màu nhấp nháy.
    }

    private VBox createLeaderBoardBox() {
        VBox lbBox = new VBox(10);
        lbBox.setAlignment(Pos.CENTER);
        lbBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        lbBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.95);"); // Nền mờ.
        lbBox.setFocusTraversable(true);

        Label title = new Label("TOP 5 HIGHSCORES");
        title.setFont(nesFont);
        title.setTextFill(Color.YELLOW);
        lbBox.getChildren().add(title);

        List<LeaderBoardEntry> topEntries = leaderBoard.getEntries();
        int limit = Math.min(5, topEntries.size());

        if (limit == 0) {
            Label emptyLabel = new Label("NO SCORES YET");
            emptyLabel.setFont(nesFont);
            emptyLabel.setTextFill(Color.WHITE);
            lbBox.getChildren().add(emptyLabel);
        } else {
            for (int i = 0; i < limit; i++) {
                LeaderBoardEntry entry = topEntries.get(i);
                Label lbLabel = new Label(String.format("%d. %s - %d (%s)",
                        i + 1, entry.getName(), entry.getScore(), formatTime(entry.getTime())));
                lbLabel.setFont(nesFont);
                lbLabel.setTextFill(Color.WHITE);
                lbBox.getChildren().add(lbLabel);
            }
        }

        Label back = new Label("PRESS ESC TO GO BACK");
        back.setFont(nesFont);
        back.setTextFill(Color.GRAY);
        lbBox.getChildren().add(back);

        return lbBox;
    }
}
