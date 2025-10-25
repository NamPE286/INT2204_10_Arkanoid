package org.arkanoid.game;

import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;

public class Game implements MonoBehaviour {
    Level currentLevel;

    public Game() {
        currentLevel = new Level();
    }

    public void onUpdate(double deltaTime) {
        if(currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }

    public void initUI() {
        currentLevel.setBackground();
    }
}
