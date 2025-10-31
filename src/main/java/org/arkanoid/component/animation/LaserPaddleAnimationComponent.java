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

    private List<Image> getLaserPaddleFrames() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 144, i * 8, 32, 8));
        }

        return frames;
    }

    public LaserPaddleAnimationComponent() {
        animLoop = new AnimationChannel(getLaserPaddleFrames(), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.playAnimationChannel(animLoop);
    }

    @Override
    public void onRemoved() {
        texture.stop();
        entity.getViewComponent().removeChild(texture);
    }
}
