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

public class LaserAnimationComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel animLoop;

    private List<Image> getLaserFrames(String type) {
        Image image = FXGL.image("laser.png");
        List<Image> frames = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (type == null) {
                frames.add(new WritableImage(image.getPixelReader(), 8, i * 12, 5, 11));
                continue;
            }
            if (type.equals("LEFT")) {
                frames.add(new WritableImage(image.getPixelReader(), 8, 24 - i * 12, 5, 11));
            } else if (type.equals("RIGHT")) {
                frames.add(new WritableImage(image.getPixelReader(), 42, 24 - i * 12, 5, 11));
            }
        }

        return frames;
    }

    public LaserAnimationComponent(String type) {
        animLoop = new AnimationChannel(getLaserFrames(type), Duration.seconds(0.5));
        texture = new AnimatedTexture(animLoop);

    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.playAnimationChannel(animLoop);
    }
}
