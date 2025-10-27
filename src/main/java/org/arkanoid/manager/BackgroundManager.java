package org.arkanoid.manager;


import com.almasb.fxgl.texture.Texture;
import org.arkanoid.utilities.TextureUtils;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class BackgroundManager {

    private static final int BG_WIDTH = 224;
    private static final int BG_HEIGHT = 240;
    private static final int DISTANCE = 8;
    private static final int NUM_BACKGROUND = 5;

    
    private final Texture[] textures;

    
    private Entity currentBackground;
    private static BackgroundManager instance;

    private void loadAndCrop() {
        Texture tiles = getAssetLoader().loadTexture("fields.png");
        int posX = 0;

        for (int i = 0; i < NUM_BACKGROUND; i++) {
            textures[i] = TextureUtils.crop(tiles, posX, 0, BG_HEIGHT, BG_WIDTH);
            posX += BG_WIDTH + DISTANCE;
        }
    }

    
    public BackgroundManager() {
        this.textures = new Texture[NUM_BACKGROUND];
        loadAndCrop();
    }

    public void displayLevel(int level) {

        if (currentBackground != null) {
            currentBackground.removeFromWorld();
        }

        
        FXGL.getGameScene().getRoot().setStyle("-fx-background-color: black;");

        
        int indexBG = (level - 1) % NUM_BACKGROUND;

        
        Texture newBG = textures[indexBG];

        
        newBG.setFitWidth(getAppWidth());
        newBG.setFitHeight(getAppHeight());

        





        currentBackground = FXGL.entityBuilder()
            .at(0, 48)
            .view(newBG)
            .zIndex(-100)
            .buildAndAttach();

    }

    public static BackgroundManager getInstance() {
        if (instance == null) {
            instance = new BackgroundManager();
        }
        return instance;
    }

    /**
     * Reset the BackgroundManager singleton and remove any current background entity from the world.
     * Call this when restarting the game so the background will be recreated fresh.
     */
    public static void reset() {
        if (instance != null) {
            if (instance.currentBackground != null) {
                try {
                    instance.currentBackground.removeFromWorld();
                } catch (Exception ignored) {
                    
                }
                instance.currentBackground = null;
            }

            instance = null;
        }
    }
}

