package org.arkanoid.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import static org.arkanoid.Main.HEIGHT;

public class LivesUI {

    
    private static final int PADDLE_ICON_WIDTH = 48;
    private static final int PADDLE_ICON_HEIGHT = 12;

    
    private static final int PADDING = 8;
    private static final int START_X = 30;
    private static final int START_Y = HEIGHT - 30;

    
    private static final int PADDLE_SRC_X = 32;
    private static final int PADDLE_SRC_Y = 0;
    private static final int PADDLE_SRC_W = 32;
    private static final int PADDLE_SRC_H = 8;

    /**
     * Hiển thị số mạng ra UI.
     * Khi giảm mạng -> icon tự biến mất (listener phía dưới xử lý).
     */
    public void render() {

        
        var paddleTexture = FXGL.texture("vaus.png").subTexture(
                new Rectangle2D(PADDLE_SRC_X, PADDLE_SRC_Y, PADDLE_SRC_W, PADDLE_SRC_H)
        );

        
        int lives = FXGL.geti("lives");

        
        for (int i = 0; i < lives; i++) {
            ImageView lifeIcon = new ImageView(paddleTexture.getImage());
            lifeIcon.setFitWidth(PADDLE_ICON_WIDTH);
            lifeIcon.setFitHeight(PADDLE_ICON_HEIGHT);
            lifeIcon.setTranslateX(START_X + i * (PADDLE_ICON_WIDTH + PADDING));
            lifeIcon.setTranslateY(START_Y);
            lifeIcon.setId("lifeIcon" + i);

            FXGL.getGameScene().addUINode(lifeIcon);
        }

        
        FXGL.getip("lives").addListener((obs, oldValue, newValue) -> {
            int oldLives = oldValue.intValue();
            int newLives = newValue.intValue();

            
            if (newLives < oldLives) {
                for (int i = oldLives - 1; i >= newLives; i--) {
                    String symbol = "lifeIcon" + i;
                    FXGL.getGameScene().getUINodes().stream()
                            .filter(n -> symbol.equals(n.getId()))
                            .findFirst()
                            .ifPresent(n -> FXGL.getGameScene().removeUINode(n));
                }
            }
        });
    }
}
