package org.arkanoid.game;

import org.arkanoid.behaviour.MonoBehaviour;
import org.arkanoid.level.Level;

public class Game implements MonoBehaviour {

    Level currentLevel;

    public Game() {
        currentLevel = new Level(1);
        currentLevel.setOnCompletedCallback(() -> {
            System.out.println("Level completed!");
        });

        currentLevel.setOnDeathCallback(() -> {
            System.out.println("Died");
        });
    }

    public void onUpdate(double deltaTime) {
        if (currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }

    public void initUI() {

    }
}
