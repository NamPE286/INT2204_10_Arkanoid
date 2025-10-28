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

    private List<Image> getIdleFrames(int tileX, int tileY) {
        Image image = FXGL.image("specialBricks.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 16 * i, 8 * tileY, 16, 8));
        }

        return frames;
    }

    public BrickAnimationComponent(int tileX, int tileY) {
        animLoop = new AnimationChannel(getIdleFrames(tileX, tileY), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);

    }

    @Override
    public void onAdded() {

        entity.getViewComponent().addChild(texture);
        texture.playAnimationChannel(animLoop);
    }

    public void playHitAnimation() {
        texture.playAnimationChannel(animLoop);
    }
}

