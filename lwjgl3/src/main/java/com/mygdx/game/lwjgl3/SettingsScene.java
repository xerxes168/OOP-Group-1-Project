package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class SettingsScene extends AbstractScene implements Screen {

    private Texture playerTexture;

    public SettingsScene(GameMaster game) {
        super(game);
        playerTexture = new Texture("play.png"); // Load a player sprite
    }

    @Override
    protected void draw(float delta) {
        batch.draw(playerTexture, 350, 250); // Draw a player in the center
    }

    @Override
    public void dispose() {
        super.dispose();
        playerTexture.dispose();
    }

}
