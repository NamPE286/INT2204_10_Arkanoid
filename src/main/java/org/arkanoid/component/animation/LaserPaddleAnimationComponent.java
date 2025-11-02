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
public class LaserPaddleAnimationComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel animLoop;

    /**
     * Loads and extracts the animation frames for the laser paddle.
     *
     * @return a list of {@link Image} frames from the {@code vaus.png} sprite sheet
     */
    private List<Image> getLaserPaddleFrames() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 144, i * 8, 32, 8));
        }

        return frames;
    }

    /**
     * Constructs a {@code LaserPaddleAnimationComponent} and initializes
     * its looping animation using frames from the sprite sheet.
     */
    public LaserPaddleAnimationComponent() {
        animLoop = new AnimationChannel(getLaserPaddleFrames(), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);
    }

    /**
     * Called automatically when this component is added to an entity.
     * Adds the animated texture to the entity's view and starts looping the animation.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animLoop);
    }

    /**
     * Called automatically when this component is removed from an entity.
     * Stops the animation and removes the texture from the entity's view.
     */
    @Override
    public void onRemoved() {
        texture.stop();
        entity.getViewComponent().removeChild(texture);
    }
}
