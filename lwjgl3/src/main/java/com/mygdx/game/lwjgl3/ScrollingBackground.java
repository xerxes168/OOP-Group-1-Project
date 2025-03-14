package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
    private Texture background;
    private float y1, y2; // Two Y positions for looping effect
    private float speed;
    private float screenHeight;

    public ScrollingBackground(String backgroundImage, float speed) {
        this.background = new Texture(Gdx.files.internal(backgroundImage));
        this.speed = speed;
        this.screenHeight = Gdx.graphics.getHeight();

        // Start with one background at 0 and the other right above it
        this.y1 = 0;
        this.y2 = y1 + screenHeight;
    }

    public void update(float deltaTime) {
        // Move both backgrounds down
    	float scrollSpeed = PlayScene.getScrollSpeed();

        y1 -= scrollSpeed * deltaTime;
        y2 -= scrollSpeed * deltaTime;

        // If y1 moves off screen, reset it above y2
        if (y1 + screenHeight <= 0) {
            y1 = y2 + screenHeight;
        }

        // If y2 moves off screen, reset it above y1
        if (y2 + screenHeight <= 0) {
            y2 = y1 + screenHeight;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, y1, Gdx.graphics.getWidth(), screenHeight);
        batch.draw(background, 0, y2, Gdx.graphics.getWidth(), screenHeight);
        batch.end();
    }

    public void dispose() {
        background.dispose();
    }
}
