package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {
	private HashMap<String, Clip> soundMap;  // Stores sound clips for easy access
    private float masterVolume = 1.0f; // master volume for all sound range from 0.0 to 1.0
    
}

	public SoundManager() {
		SoundMap= new HashMap<>();
	}

