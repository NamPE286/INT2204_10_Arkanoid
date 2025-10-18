package org.arkanoid.ui;


import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.texture.Texture;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class BackGround {

    private static final int BG_WIDTH = 224;
    private static final int BG_HEIGHT = 240;
    private static final int DISTANCE = 8;
    private static final int NUM_BACKGROUND = 5;

    // array to saving all textures cropped.
    private final Texture[] textures;

    // setting the current background.
    private Texture currentBackground = null;

    //constructor lam nhiem vu tai va cat anh 1 lan
    public BackGround() {
        this.textures = new Texture[NUM_BACKGROUND];
        loadAndCrop();
    }

    private void loadAndCrop() {
        Texture tiles = getAssetLoader().loadTexture("fields.png");
        int posX = 0;

        for (int i = 0; i < NUM_BACKGROUND; i++) {
            textures[i] = TextureUtils.crop(tiles, posX, 0, BG_HEIGHT, BG_WIDTH);
            posX += BG_WIDTH + DISTANCE;
        }
    }

    public void displayBackgroundeachLevel(int level) {
        GameScene gameScene = getGameScene();

        if (currentBackground != null) {
            gameScene.removeUINode(currentBackground);
        }

        // create a index for background.
        int indexBackground = (level - 1) % NUM_BACKGROUND;

        // text the texture was cropped.
        Texture newbackground = textures[indexBackground];

        // add size into UI
        newbackground.setFitWidth(getAppWidth());
        newbackground.setFitHeight(getAppHeight());
        gameScene.addUINode(newbackground);

        // saving for the next background.
        this.currentBackground = newbackground;
    }
}

