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

    
    private List<Label> menuItems = new ArrayList<>();

    
    private List<ImageView> arrowSlots = new ArrayList<>();

    
    private int currentIndex = 0;

    
    private ImageView arrowImage;

    
    private Timeline blinkTimeline;


    public MainMenu(MenuType type) {
        super(MenuType.MAIN_MENU);

        
        nesFont = Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 24);

        
        Texture fullTexture = FXGL.texture("ship.png");

        
        Rectangle2D region = new Rectangle2D(0, 0, 200, 45);
        Texture cropped = fullTexture.subTexture(region);

        
        ImageView logoView = new ImageView(cropped.getImage());
        logoView.setFitWidth(400);
        logoView.setPreserveRatio(true);

        
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER);

        
        Texture arrowTexture = FXGL.texture("arrow.png");
        arrowTexture.setFitWidth(24);
        arrowTexture.setPreserveRatio(true);
        arrowImage = new ImageView(arrowTexture.getImage());
        arrowImage.setSmooth(false);

        
        String[] options = {"START GAME", "EXIT  GAME"};

        
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

        
        Label copyright = new Label("HANOI36PP");
        copyright.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/nes.otf"), 16));
        copyright.setTextFill(Color.GRAY);

        
        VBox menuBox = new VBox(30, logoBox, menuVBox, copyright);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());

        
        getContentRoot().setStyle("-fx-background-color: black;");

        
        getContentRoot().getChildren().add(menuBox);
        getContentRoot().setFocusTraversable(true);
        getContentRoot().requestFocus();
        getContentRoot().setOnMouseClicked(e -> e.consume()); 

        
        addKeyControls();
        highlightCurrent();
    }

    
    private void addKeyControls() {
        getContentRoot().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                currentIndex = Math.max(0, currentIndex - 1);
                highlightCurrent();
            }
            if (e.getCode() == KeyCode.DOWN) {
                int lastIndex = menuItems.size() - 1;
                currentIndex = Math.min(lastIndex, currentIndex + 1);
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

    
    private void highlightCurrent() {
        for (int i = 0; i < menuItems.size(); i++) {
            arrowSlots.get(i).setImage(i == currentIndex ? arrowImage.getImage() : null);
            menuItems.get(i).setTextFill(i == currentIndex ? Color.GREEN : Color.WHITE);
        }
        blinkingEffect();
    }

    
    private void blinkingEffect() {
        
        if (blinkTimeline != null) {
            blinkTimeline.stop();
        }

        blinkTimeline = new Timeline(
                
                new KeyFrame(Duration.seconds(0.0), e -> {
                    arrowSlots.get(currentIndex).setOpacity(1);
                    menuItems.get(currentIndex).setTextFill(Color.GREEN);
                }),

                
                new KeyFrame(Duration.seconds(0.5), e -> {
                    arrowSlots.get(currentIndex).setOpacity(0);
                    menuItems.get(currentIndex).setTextFill(Color.WHITE);
                }),

                
                new KeyFrame(Duration.seconds(1.0), e -> {
                    arrowSlots.get(currentIndex).setOpacity(1);
                    menuItems.get(currentIndex).setTextFill(Color.GREEN);
                })
        );

        
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);

        
        blinkTimeline.play();
    }
}
