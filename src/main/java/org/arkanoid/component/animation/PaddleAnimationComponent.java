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

public class PaddleAnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel animInit;
    private final AnimationChannel animLoop;
    private boolean isInit = false;

    /**
     * Loads and extracts frames for the paddle's initialization animation.
     *
     * @return a list of {@link Image} frames from the {@code vaus.png} sprite sheet
     */
    private List<Image> getInitFrames() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 0, 8 * i, 32, 8));
        }

        return frames;
    }

    /**
     * Loads and extracts frames for the paddle’s idle (looping) animation.
     *
     * @return a list of {@link Image} frames from the {@code vaus.png} sprite sheet
     */
    private List<Image> getIdleFrames() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 32, 8 * i, 32, 8));
        }

        return frames;
    }

    /**
     * Constructs a {@code PaddleAnimationComponent}, initializing
     * both the intro and idle animation channels.
     */
    public PaddleAnimationComponent() {
        animInit = new AnimationChannel(getInitFrames(), Duration.seconds(0.5));
        animLoop = new AnimationChannel(getIdleFrames(), Duration.seconds(1));

        texture = new AnimatedTexture(animInit);
    }

    /**
     * Called automatically when this component is added to an entity.
     * Adds the texture to the entity’s view and starts the idle looping animation.
     */
    @Override
    public void onAdded() {

        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animLoop);
    }

    /**
     * Updates the component each frame.
     * If {@code isInit} is set to {@code true}, the initialization animation is played once,
     * and the idle animation will start after it finishes.
     *
     * @param tpf time per frame (in seconds)
     */
    @Override
    public void onUpdate(double tpf) {
        if (isInit) {
            texture.setOnCycleFinished(() -> texture.loopAnimationChannel(animLoop));
            texture.playAnimationChannel(animInit);
            isInit = false;
        }
    }

    /**
     * Sets whether the initialization animation should play.
     *
     * @param value {@code true} to trigger the initialization animation, {@code false} otherwise
     */
    public void setInit(boolean value) {
        isInit = value;
    }

    /**
     * Xu li sau khi paddle to ra
     */
    @Override
    public void onRemoved() {
        if (texture != null) {
            texture.stop();
        }

        if (entity != null && entity.getViewComponent() != null && texture != null) {
            entity.getViewComponent().removeChild(texture);
        }
    }
}

