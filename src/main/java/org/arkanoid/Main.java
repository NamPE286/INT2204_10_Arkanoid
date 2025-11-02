package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import org.arkanoid.game.Game;
import org.arkanoid.factory.SceneFactory;

import java.util.Map;

public class Main extends GameApplication {

    public static final int HEIGHT = 768;
    public static final int WIDTH = 672;
    Game game;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("Arkanoid");
        settings.setVersion("0.0.1");
        settings.setProfilingEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
        settings.setTicksPerSecond(60);
        settings.setSceneFactory(new SceneFactory());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("highScore", 0);
        vars.put("lives", 3);
        vars.put("time", 0);
    }

    @Override
    protected void initGame() {
        game = Game.reInit();
    }

    @Override
    protected void initUI() {
        Game.getInstance().initUI();
    }

    @Override
    protected void onUpdate(double deltaTime) {
        game.onUpdate(deltaTime);
    }

    static void main(String[] args) {
        launch(args);
    }
}