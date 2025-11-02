package org.arkanoid.component.animation;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class BrickAnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel animLoop;

    /**
     * Creates a list of animation frames for the brick's idle animation.
     *
     * @param tileX the horizontal tile index
     * @param tileY the vertical tile index in the sprite sheet
     * @return a list of {@link Image} frames extracted from the sprite sheet
     */
    private List<Image> getIdleFrames(int tileX, int tileY) {
        Image image = FXGL.image("specialBricks.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 16 * i, 8 * tileY, 16, 8));
        }

        return frames;
    }

    /**
     * Constructs a {@code BrickAnimationComponent} using the given tile coordinates.
     *
     * @param tileX the horizontal tile index (currently unused)
     * @param tileY the vertical tile index used to select the animation row
     */
    public BrickAnimationComponent(int tileX, int tileY) {
        animLoop = new AnimationChannel(getIdleFrames(tileX, tileY), Duration.seconds(0.5));
        texture = new AnimatedTexture(animLoop);

    }

    /**
     * Called automatically when this component is added to an entity.
     * Adds the animated texture to the entity's view and starts the looping animation.
     */
    @Override
    public void onAdded() {

        entity.getViewComponent().addChild(texture);
        texture.playAnimationChannel(animLoop);
    }

    /**
     * Plays the brick's hit animation. In this implementation,
     * it simply replays the looping idle animation.
     */
    public void playHitAnimation() {
        texture.playAnimationChannel(animLoop);
    }
}

