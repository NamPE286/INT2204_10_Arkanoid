package org.arkanoid.managers;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import java.util.HashMap;

public class SoundManager {
    private final static HashMap<String, Sound> soundHashMap = new HashMap<>();

    public void loadSound(String path) {
        if (soundHashMap.containsKey(path)) {
            return;
        }

        Sound sound = FXGL.getAssetLoader().loadSound(path);
        soundHashMap.put(path, sound);
    }

    public void play(String path) {
        Sound sound = soundHashMap.get(path);

        if (sound == null) {
            loadSound(path);
            sound = soundHashMap.get(path);
        }

        if (sound != null) {
            FXGL.getAudioPlayer().playSound(sound);
        } else {
            System.out.println("Cannot play sound: " + path);
        }
    }
}
