package org.arkanoid.manager;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import java.util.HashMap;

/**
 * Manages loading and playing sound effects in the game.
 * <p>
 * This class uses a cache (HashMap) to store loaded sounds and avoid reloading the same sound file
 * multiple times. Sounds are played using FXGL's built-in audio system.
 * </p>
 */
public class SoundManager {
    private static final HashMap<String, Sound> soundHashMap = new HashMap<>();

    /**
     * Define a non-public constructor to prevent the class from being instantiated.
     */
    private SoundManager() {

    }

    /**
     * Loads a sound file into the cache. If the sound already exists in the cache, the method does
     * nothing.
     *
     * @param path The path to the sound file to be loaded.
     */
    public static void loadSound(String path) {
        if (soundHashMap.containsKey(path)) {
            return;
        }

        Sound sound = FXGL.getAssetLoader().loadSound(path);
        soundHashMap.put(path, sound);
    }

    /**
     * Plays a sound from the specified file path.
     * <p>
     * If the sound has not been loaded yet, it will be loaded first and then played. If loading
     * fails, a warning message will be printed to the console.
     * </p>
     *
     * @param path The path to the sound file to be played.
     */
    public static void play(String path) {
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