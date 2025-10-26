package org.arkanoid.game;

import java.io.IOException;
import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;

public class Game implements MonoBehaviour {

    Level currentLevel;

    public Game() {

        try {
            currentLevel = new Level(1);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load level");
        }
    }

    public void onUpdate(double deltaTime) {
        if (currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }

    public void initUI() {
        currentLevel.setBackground();
    }
}
