package org.arkanoid.core;

import org.arkanoid.behaviour.MonoBehaviour;

public class Game implements MonoBehaviour {
    Level currentLevel;

    public Game() {
        currentLevel = new Level();
    }

    @Override
    public void onUpdate(double deltaTime) {
        if(currentLevel != null) {
            currentLevel.onUpdate(deltaTime);
        }
    }
}
