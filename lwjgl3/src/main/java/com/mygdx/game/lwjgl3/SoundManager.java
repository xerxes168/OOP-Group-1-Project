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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Sound moveSound;

    public SoundManager() {
        preloadMoveSound("soundmanager/jump-retro-game-jam.wav");
    }

    // Preload the movement sound
    private void preloadMoveSound(String filePath) {
        moveSound = Gdx.audio.newSound(Gdx.files.internal(filePath));
        System.out.println("Movement sound preloaded.");
    }

    // Play the movement sound
    public void playMoveSound() {
        if (moveSound != null) {
            moveSound.play(1.0f);  // 1.0f = full volume
        } else {
            System.err.println("Movement sound not found!");
        }
    }

    // Dispose the sound to avoid memory leaks
    public void dispose() {
        if (moveSound != null) {
            moveSound.dispose();
            System.out.println("Movement sound disposed.");
        }
    }
}