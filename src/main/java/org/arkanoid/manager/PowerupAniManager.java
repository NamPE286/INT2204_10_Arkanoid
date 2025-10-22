package org.arkanoid.manager;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;

import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum PowerUpType {
    SLOW,
    CATCH,
    LASER,
    EXTEND,
    DISRUPT,
    BREAK,
    PLAYER
}


public class PowerupAniManager {
    public static final int frameW = 14;
    public static final int frameH = 7;
    public static final int colNum = 8;
    public static final int rowNum = 7;
    public static final int spaceX = 2;
    public static final int startX = 1;
    public static final int startY = 0;
    public static final int spaceY = 1;
    /**
     * Using map to store the type of power up.
     */
    private Map<PowerUpType, AnimationChannel> animationCache = new HashMap<>();
    private Image powerSheet;

    public void loadAnimations() {
        powerSheet = FXGL.image("powerups.png");
        PowerUpType[] types = PowerUpType.values();
        
        for (int i = 0; i < types.length; i++) {
            // Cut each row.
            AnimationChannel anime = createAnimeChannel(i);
            // Store into map.
            animationCache.put(types[i], anime);
        }

    }


    private AnimationChannel createAnimeChannel(int rowIndex) {
        // Create a list to store all frame.
        if (rowIndex < 0 || rowIndex >= rowNum) {
           throw new IndexOutOfBoundsException("Out of bound");
        }
        List<Image> frames = new ArrayList<>();

        int y = startY + ( rowIndex *  (spaceY + frameH) );

        // Load each frame picture.
        for (int j = 0; j < colNum; j++) {

            int x = startX + j * (frameW + spaceX);

            Image cutFrame = new WritableImage(
                    powerSheet.getPixelReader(), x, y, frameW, frameH);
            // add frame into list.
            frames.add(cutFrame);
        }
        return new AnimationChannel(frames, Duration.seconds(1));
    }

    /**
     * Getter for the animation.
     */
    public AnimationChannel getAnimation(PowerUpType type) {
        return animationCache.get(type);
    }

}


