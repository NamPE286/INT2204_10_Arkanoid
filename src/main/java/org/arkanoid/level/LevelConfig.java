package org.arkanoid.level;

public class LevelConfig {
    private final int backgroundId;
    private final int[][] brickMap;

    public LevelConfig(int backgroundId, int[][] brickMap) {
        this.backgroundId = backgroundId;
        this.brickMap = brickMap;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public int[][] getBrickMap() {
        return brickMap;
    }
}
