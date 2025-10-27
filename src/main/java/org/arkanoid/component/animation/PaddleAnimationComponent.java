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
    private final AnimationChannel animLoop;

    private List<Image> getIdleFrames() {
        Image image = FXGL.image("vaus.png");
        List<Image> frames = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            frames.add(new WritableImage(image.getPixelReader(), 32, 8 * i, 32, 8));
        }

        return frames;
    }

    public PaddleAnimationComponent() {
        animLoop = new AnimationChannel(getIdleFrames(), Duration.seconds(1));
        texture = new AnimatedTexture(animLoop);

    }

    @Override
    public void onAdded() {
        
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(animLoop); 
    }

    /**
     *  Xu li sau khi paddle to ra
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

