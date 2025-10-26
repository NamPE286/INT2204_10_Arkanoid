package org.arkanoid.ui;

import com.almasb.fxgl.texture.Texture;
import org.arkanoid.utilities.TextureUtils;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class LevelBackground {

    private int background;

    /**
     * Getting the level
     */
    public int getBackground() {
        return background;
    }

    /**
     * Default size of background,
     * The distance between two backgrounds.
     */
    private static final int BG_WIDTH = 224;
    private static final int BG_HEIGHT = 240;
    private static final int DISTANCE = 8;

    /**
     * Load Background function.
     *
     * @param level
     */
    public void loadBackground(int level) {

        /**
         * Load the texture, then crop it for each level.
         */
        Texture tiles = getAssetLoader().loadTexture("fields.png");

        Texture[] Backgrounds = new Texture[5];
        int posX = 0; // First position x

        for (int i = 0; i < 5; i++) {
            Backgrounds[i] = TextureUtils.crop(tiles, posX, 0, BG_HEIGHT, BG_WIDTH);
            posX += BG_WIDTH + DISTANCE;
        }

        /**
         * Set the level
         */
        if (level <= 5) {
            this.background = level;
        } else {
            this.background = level % 5;
        }
        /**
         * Setting the background level-th
         */
        Backgrounds[this.background - 1].setFitWidth(getAppWidth());
        Backgrounds[this.background - 1].setFitHeight(getAppHeight());
        getGameScene().addUINode(Backgrounds[this.background - 1]);
    }


    public void hitboxBackground(){
        /**
         * Size of Renderer window.
         */
        double appWidth = getAppWidth();
        double appHeight = getAppHeight();
        double thickness = 5;

        /**
         * Top hitbox
         */
        Entity topWall = new Entity();
        topWall.getBoundingBoxComponent().addHitBox(new HitBox("TOP", BoundingShape.box(appWidth, thickness)));
        topWall.setPosition(new Point2D(0, 0));
        getGameWorld().addEntity(topWall);

        /**
         * Left hitbox
         */
        Entity leftWall = new Entity();
        leftWall.getBoundingBoxComponent().addHitBox(new HitBox("lEFT", BoundingShape.box(thickness, appHeight)));
        leftWall.setPosition(new Point2D(0, 0));
        getGameWorld().addEntity((leftWall));

        /**
         * Right hitbox
         */
        Entity rightWall = new Entity();
        rightWall.getBoundingBoxComponent().addHitBox(new HitBox("RIGHT", BoundingShape.box(thickness, appHeight)));
        rightWall.setPosition(new Point2D(appWidth - thickness, 0));
        getGameWorld().addEntity(rightWall);

    }



}
