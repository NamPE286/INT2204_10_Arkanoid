package org.arkanoid.utilities;

import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;

public class TextureUtils {
    public static Texture crop(Texture tex, int x, int y, int h, int w) {
        PixelReader reader = tex.getImage().getPixelReader();
        WritableImage croppedImage = new WritableImage(reader, x, y, w, h);

        return new Texture(croppedImage);
    }
}