package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private float masterVolume = 1.0f;
    private HashMap<String, Sound> soundMap;  // For short sound effects
    private Music backgroundMusic;            // For background music
    private long lastCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 1500;  // 1.5 second cool down for collision sound

    public SoundManager() {
        soundMap = new HashMap<>();
        preloadSoundFromFolder("soundmanager/");
        preloadBackgroundMusic("soundmanager/background-music.mp3");  
    }

    // Preload a short sound effect and store it in the HashMap
    private void preloadSoundFromFolder(String folderPath) {
    	FileHandle dirHandle = Gdx.files.internal(folderPath);
    	
    	if (dirHandle.isDirectory()) {
            for (FileHandle file : dirHandle.list()) {
                if (file.extension().equals("wav") || file.extension().equals("mp3")) {
                    String soundName = file.nameWithoutExtension();  // Use filename as key
                    Sound sound = Gdx.audio.newSound(file);
                    soundMap.put(soundName, sound);
                    System.out.println("Preloaded sound: " + soundName);
                }
            }
        } else {
            System.err.println("SoundManager: Folder not found - " + folderPath);
        }
    }
    	 
    //Preload background music
    private void preloadBackgroundMusic(String filePath) {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        backgroundMusic.setLooping(true);  // Make the background music loop
        backgroundMusic.setVolume(0.5f);   // Set volume to 50%
        System.out.println("Preloaded background music.");
    }

    // Play the background music
    public void playBackgroundMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
            System.out.println("Background music started.");
        }
    }

    // Stop the background music
    public void stopBackgroundMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
            System.out.println("Background music stopped.");
        }
    } 

    // Play a sound by its name (with cool down for collision sound)
    public void playSound(String name) {
        if ("collision".equals(name)) {
            playCollisionSoundWithCooldown();
        } else {
            Sound sound = soundMap.get(name);
            if (sound != null) {
                sound.play(masterVolume);
            } else {
                System.err.println("Sound '" + name + "' not found!");
            }
        }
    } 

    // Play collision sound with 1.5 second cool down
    private void playCollisionSoundWithCooldown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime >= COLLISION_COOLDOWN) {
            Sound collisionSound = soundMap.get("collision");
            if (collisionSound != null) {
                collisionSound.play(masterVolume);
                lastCollisionTime = currentTime;
            }
        }
    } 

    // Volume change
    public void setMasterVolume(float volume) {
        masterVolume = volume;

       if (backgroundMusic != null) {
           backgroundMusic.setVolume(volume); 
       }
   } 

    // Dispose all sounds and music 
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        System.out.println("All sounds and music disposed.");
    }
}