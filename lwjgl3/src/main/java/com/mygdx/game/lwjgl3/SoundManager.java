package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {
	private HashMap<String, Clip> soundMap;  // Stores sound clips for easy access
    private float masterVolume = 1.0f; // master volume for all sound range from 0.0 to 1.0
    
	
	public SoundManager() {
		soundMap= new HashMap<>();
	}

	public void MoveSound(String name) {
		try {
			
			FileHandle fileHandle = Gdx.files.internal("soundmanager/jump-retro-game-jam.wav");
			
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileHandle.file());
			
			
			Clip clip = AudioSystem.getClip();

            // Open the clip with the audio data
            clip.open(audioInputStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            gainControl.setValue(4.0f);
            
            // Start playing the sound
            clip.start();
            
		  } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	
}
	
	
	