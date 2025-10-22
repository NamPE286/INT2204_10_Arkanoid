package org.arkanoid.component.animation;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import org.arkanoid.manager.PowerupAniManager;
import org.arkanoid.manager.PowerupType;



public class PowerupAnimationComponent extends Component{
    private final AnimatedTexture texture;
    private final AnimationChannel animeLoop;

    /**
     * Constructor tao ra PowerUp base to PowerUp type.
     */
    public PowerupAnimationComponent(PowerupType type) {
        PowerupAniManager aniManager = FXGL.geto("AnimationManager");

        this.animeLoop = aniManager.getAnimation(type);
        // Khoi tao texture.
        this.texture = new AnimatedTexture(this.animeLoop);
    }

    /**
     * After adding an entity, Output a texture.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
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
