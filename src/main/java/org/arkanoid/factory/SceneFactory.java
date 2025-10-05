package org.arkanoid.factory;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import org.arkanoid.ui.MainMenu;
import org.arkanoid.ui.PauseMenu;
import org.jetbrains.annotations.NotNull;

public class SceneFactory extends com.almasb.fxgl.app.scene.SceneFactory {
    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenu(MenuType.MAIN_MENU);
    }

    @NotNull
    @Override
    public FXGLMenu newGameMenu() {
        return new PauseMenu(MenuType.GAME_MENU);
    }
}
