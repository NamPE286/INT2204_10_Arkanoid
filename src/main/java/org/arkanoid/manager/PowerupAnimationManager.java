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

public class PowerupAnimationManager {
    public static final int FRAME_W = 14;
    public static final int FRAME_H = 7;
    public static final int COL_NUM = 8;
    public static final int ROW_NUM = 7;
    public static final int SPACE_X = 2;
    public static final int START_X = 1;
    public static final int START_Y = 0;
    public static final int SPACE_Y = 1;
    /**
     * Using map to store the type of power up.
     */
    private static Map<PowerupType, AnimationChannel> animationCache = new HashMap<>();
    private static Image powerSheet;

    public static void load(String path) {
        powerSheet = FXGL.image(path);
        PowerupType[] types = PowerupType.values();
        
        for (int i = 0; i < types.length; i++) {
            // Cut each row.
            AnimationChannel anime = createAnimationChannel(i);
            // Store into map.
            animationCache.put(types[i], anime);
            System.out.println("Loaded powerup animations: " + animationCache.keySet());
        }

    }

    private static AnimationChannel createAnimationChannel(int rowIndex) {
        // Create a list to store all frame.
        if (rowIndex < 0 || rowIndex >= ROW_NUM) {
           throw new IndexOutOfBoundsException("Out of bound");
        }
        List<Image> frames = new ArrayList<>();

        int y = START_Y + ( rowIndex *  (SPACE_Y + FRAME_H) );

        // Load each frame picture.
        for (int j = 0; j < COL_NUM; j++) {

            int x = START_X + j * (FRAME_W + SPACE_X);

            Image cutFrame = new WritableImage(
                    powerSheet.getPixelReader(), x, y, FRAME_W, FRAME_H);
            // add frame into list.
            frames.add(cutFrame);
        }
        return new AnimationChannel(frames, Duration.seconds(1));
    }

    /**
     * Getter for the animation.
     */
    public static AnimationChannel get(PowerupType type) {
        return animationCache.get(type);
    }

}


