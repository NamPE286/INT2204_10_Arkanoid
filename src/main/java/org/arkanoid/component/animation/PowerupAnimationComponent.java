package org.arkanoid.component.animation;


import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import org.arkanoid.entity.brick.NormalBrick;
import org.arkanoid.manager.PowerupAnimationManager;
import org.arkanoid.manager.PowerupType;


public class PowerupAnimationComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel animeLoop;

    /**
     * Constructor tao ra PowerUp base to PowerUp type.
     */
    public PowerupAnimationComponent(PowerupType type) {
        this.animeLoop = PowerupAnimationManager.get(type);
        if (this.animeLoop == null) {
            throw new NullPointerException("Khong tim thay");
        }
        // Khoi tao texture, chinh lai theo dung ti le.
        this.texture = new AnimatedTexture(this.animeLoop);

        double scale = NormalBrick.SETSCALE * 8 / 7;
        System.out.println("width = " + this.texture.getImage().getWidth());
        this.texture.setScaleX(scale);
        this.texture.setScaleY(scale);


    }

    /**
     * After adding an entity, Output a texture.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.setOnCycleFinished(() -> texture.loopAnimationChannel(animeLoop));
        texture.loopAnimationChannel(animeLoop);

    }

    /**
     * remove texture when entity is gone.
     */
    @Override
    public void onRemoved() {
        texture.stop();
    }
}
