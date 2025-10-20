package org.arkanoid.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import org.arkanoid.utilities.TextureUtils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class NormalBrick extends Brick {
    public NormalBrick() {
        super();
    }

    public NormalBrick(int x, int y, int tileX, int tileY) {
        super(tileX, tileY);
        this.canDestroy = true; // Viên gạch có thể bị phá hủy
        this.health = 2;

        SpawnData spawnData = new SpawnData(x, y);
        spawnData.put("tileX", tileX);
        spawnData.put("tileY", tileY);

        this.entity = createEntity(spawnData);
        spawn();
        initInput();
    }

    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.crop(FXGL.texture("bricks.png"),
                tileX * width, tileY * height, height, width);

        var e = entityBuilder(spawnData)
                .type(EntityType.BRICK)
                .viewWithBBox(texture)
                .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }
}
