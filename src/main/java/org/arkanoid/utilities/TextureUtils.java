package org.arkanoid.utilities;

import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;

/**
 * Utility class for common texture operations such as cropping and scaling.
 */
public class TextureUtils {

    /**
     * Crops a rectangular region from the given texture.
     *
     * @param tex the original {@link Texture} to crop from
     * @param x the x-coordinate of the top-left corner of the crop rectangle
     * @param y the y-coordinate of the top-left corner of the crop rectangle
     * @param h the height of the crop rectangle
     * @param w the width of the crop rectangle
     * @return a new {@link Texture} containing only the cropped area
     */
    public static Texture crop(Texture tex, int x, int y, int h, int w) {
        PixelReader reader = tex.getImage().getPixelReader();
        WritableImage croppedImage = new WritableImage(reader, x, y, w, h);

        return new Texture(croppedImage);
    }

    /**
     * Scales a texture by a given factor.
     *
     * @param tex the original {@link Texture} to scale
     * @param factor the scaling factor (e.g., 2.0 doubles the size)
     * @return a new {@link Texture} scaled by the specified factor
     */
    public static Texture scale(Texture tex, double factor) {
        Texture newTex = tex.copy();

        newTex.setScaleX(factor);
        newTex.setScaleY(factor);

        return newTex;
    }
}
