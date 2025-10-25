package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import org.arkanoid.core.Level;
import org.arkanoid.factory.SceneFactory;
import org.arkanoid.ui.Background;
import org.arkanoid.ui.ScoreBoard;

import java.util.Map;

public class Main extends GameApplication {

    public static final int HEIGHT = 768;
    public static final int WIDTH = 672;
    Level level;
    //khai bao background

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
        vars.put("score", 0); // player score.
        vars.put("highScore", 50000); // default high score.
    }

    @Override
    protected void initGame() {
        level = new Level();
    }

    @Override
    protected void initUI() {
        // Init scoreboard.
        new ScoreBoard();
        // Init background.
        Background backGround = new Background();
        backGround.displayLevel(1);
    }


    @Override
    protected void onUpdate(double deltaTime) {
        level.onUpdate(deltaTime);
    }

    static void main(String[] args) {
        launch(args);
    }
}