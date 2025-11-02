package org.arkanoid.component.animation;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LaserTransformAnimationComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel animLoop;

    /**
     * Loads and extracts the animation frames for the paddle transformation.
     *
     * @param type specifies the transformation direction:
     *             <ul>
     *                 <li>{@code "InIt"} – transform into laser mode</li>
     *                 <li>{@code "OutIt"} – transform back to normal mode</li>
     *                 <li>{@code null} – defaults to "InIt" behavior</li>
     *             </ul>
     * @return a list of {@link Image} frames from the {@code vaus.png} sprite sheet
     */
    private List<Image> getTransformFrames(String type) {
        javafx.scene.image.Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (type == null || type.equals("InIt")) {
                frames.add(new WritableImage(image.getPixelReader(), 112, i * 8, 32, 8));
            } else if (type.equals("OutIt")) {
                frames.add(new WritableImage(image.getPixelReader(), 112, 64 - i * 8, 32, 8));
            }
        }
        return frames;
    }

    /**
     * Constructs a {@code LaserTransformAnimationComponent} with the specified transformation type.
     *
     * @param type the type of transformation ("InIt" or "OutIt")
     */
    public LaserTransformAnimationComponent(String type) {
        animLoop = new AnimationChannel(getTransformFrames(type), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);
    }

    /**
     * Called automatically when this component is added to an entity.
     * Adds the animated texture to the entity’s view and starts playing the animation.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.playAnimationChannel(animLoop);
    }

    /**
     * Called automatically when this component is removed from an entity.
     * Stops the animation and removes the texture from the entity’s view.
     */
    @Override
    public void onRemoved() {
        texture.stop();
        entity.getViewComponent().removeChild(texture);
    }
}
