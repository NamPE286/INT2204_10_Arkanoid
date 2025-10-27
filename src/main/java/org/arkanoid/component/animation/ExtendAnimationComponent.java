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

/**
 * Extend animation for Bigger paddle.
 */
public class ExtendAnimationComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel animLoop;

    private List<Image> getExtendFrame() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 64, 8 * i, 48, 8));
        }

        return frames;
    }

    /**
     * Loop 6 fromes per second
     * Then texture is drawing
     */
    public ExtendAnimationComponent() {
        animLoop = new AnimationChannel(getExtendFrame(), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);
    }

    /**
     * Pin the texture into Entity, then getViewComponent, addChild(texture)
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animLoop);
    }

    @Override
    public void onRemoved() {
        texture.stop();
        entity.getViewComponent().removeChild(texture);
    }

}
