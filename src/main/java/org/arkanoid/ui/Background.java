package org.arkanoid.ui;


import com.almasb.fxgl.texture.Texture;
import org.arkanoid.utilities.TextureUtils;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Background {

    private static final int BG_WIDTH = 224;
    private static final int BG_HEIGHT = 240;
    private static final int DISTANCE = 8;
    private static final int NUM_BACKGROUND = 5;

    // array to saving all textures cropped.
    private final Texture[] textures;

    // setting the current background by an entity.
    private Entity currentBackground;

    private void loadAndCrop() {
        Texture tiles = getAssetLoader().loadTexture("fields.png");
        int posX = 0;

        for (int i = 0; i < NUM_BACKGROUND; i++) {
            textures[i] = TextureUtils.crop(tiles, posX, 0, BG_HEIGHT, BG_WIDTH);
            posX += BG_WIDTH + DISTANCE;
        }
    }
    //constructor for loading and cropping once.
    public Background() {
        this.textures = new Texture[NUM_BACKGROUND];
        loadAndCrop();
    }

    public void displayLevel (int level) {

        if (currentBackground != null) {
            currentBackground.removeFromWorld();
        }

        // set background black.
        FXGL.getGameScene().getRoot().setStyle("-fx-background-color: black;");

        // create a index for background.
        int indexBG = (level - 1) % NUM_BACKGROUND;

        // text the texture was cropped.
        Texture newBG = textures[indexBG];

        // add size into UI
        newBG.setFitWidth(getAppWidth());
        newBG.setFitHeight(getAppHeight());

        /*
           Create an new entity for loading background.
           at (0, 0), zIndex is the order of background,
           ensure that background behind of the things,
           build and attach into game world.
         */
        currentBackground = FXGL.entityBuilder()
                .at(0, 48)
                .view(newBG)
                .zIndex(-100)
                .buildAndAttach();

    }

}

