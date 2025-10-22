package org.arkanoid.manager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;

public class AssetManager{
    private static final int frameW = 17;
    private static final int frameH = 7;
    private static final int space = 1;

    private static final int rowNum = 7;
    private static final int colNum = 8;

    private static AnimationChannel[] powerupAni;

    private AssetManager() {}

    public static void load() {
        Texture titles = getAssetLoader().loadTexture("powerups.png");
        powerupAni = new AnimationChannel[rowNum];

        for (int i = 0; i < rowNum; i++) {
            int startframe = i * colNum;
            int endframe = colNum + startframe - i;


        }
    }
}