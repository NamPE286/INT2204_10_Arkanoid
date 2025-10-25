package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import org.arkanoid.core.Level;
import org.arkanoid.entity.*;
import org.arkanoid.factory.LabelFactory;
import org.arkanoid.factory.SceneFactory;
import org.arkanoid.ui.Background;
import org.arkanoid.ui.LevelBackground;
import org.arkanoid.ui.ScoreBoard;

import java.util.Map;
import java.util.ArrayList;

public class Main extends GameApplication {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 672;
    private static final int THICK = WIDTH / 28;
    private final LabelFactory labelFactory = new LabelFactory("/fonts/nes.otf", 20);
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
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