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

    private List<Image> getInitFrames() {
        Image image = FXGL.image("vaus.png");

        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 0, 8 * i, 32, 8));
        }

        return frames;
    }

    private List<Image> getIdleFrames() {
        Image image = FXGL.image("vaus.png");

        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 32, 8 * i, 32, 8));
        }

        return frames;
    }

    public PaddleAnimationComponent() {
        animInit = new AnimationChannel(getInitFrames(), Duration.seconds(1));
        animLoop = new AnimationChannel(getIdleFrames(), Duration.seconds(1));

        texture = new AnimatedTexture(animInit);

    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.setOnCycleFinished(() -> texture.loopAnimationChannel(animLoop));
        texture.playAnimationChannel(animInit);

    }
}

