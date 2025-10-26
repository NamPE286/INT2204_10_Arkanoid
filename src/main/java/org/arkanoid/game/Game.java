package org.arkanoid.game;

import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;

public class Game implements MonoBehaviour {

    Level currentLevel;
    int levelIndex = 1;

    private void setLevel(int id) {
        currentLevel = new Level(id);
        currentLevel.setOnCompletedCallback(() -> {
            System.out.println("Level completed!");
        });

        currentLevel.setOnDeathCallback(() -> {
            System.out.println("Died");
        });
    }

    public Game() {
        setLevel(levelIndex);
    }

    public void onUpdate(double deltaTime) {
        if (currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }

    public void initUI() {

    }
}
