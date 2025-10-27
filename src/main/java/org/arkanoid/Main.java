package org.arkanoid;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import org.arkanoid.game.Game;
import org.arkanoid.factory.SceneFactory;
import org.arkanoid.ui.ScoreBoard;

import java.util.Map;

public class Main extends GameApplication {

    public static final int HEIGHT = 768;
    public static final int WIDTH = 672;
    Game game;
    ScoreBoard scoreBoard;

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
        settings.setTicksPerSecond(144);
        settings.setSceneFactory(new SceneFactory());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0); // player score.
        vars.put("highScore", 50000); // default high score.
        vars.put("lives", 3);
    }

    @Override
    protected void initGame() {
        game = Game.getInstance();
    }

    @Override
    protected void initUI() {
        scoreBoard = new ScoreBoard();
        Game.getInstance().initUI();
    }


    @Override
    protected void onUpdate(double deltaTime) {
        game.onUpdate(deltaTime);
    }

    /**
     * Cộng điểm vào score và tự động cập nhật UI (vì score đã được bind).
     * @param points Số điểm cần cộng.
     */
    public void addScore(int points) {
        // 1. Lấy giá trị score hiện tại.
        int currentScore = FXGL.geti("score");

        // 2. Cập nhật giá trị score mới, ScoreBoard sẽ tự động thay đổi hiển thị.
        FXGL.set("score", currentScore + points);

        // (Optional: Nếu muốn cập nhật High Score thì thêm logic ở đây)
    }

    static void main(String[] args) {
        launch(args);
    }
}