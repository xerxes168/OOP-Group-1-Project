package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//will be required when we decide to use a texture for the scrolling background
public class ScrollingBackground {
    private Texture background;
    private float bgY; // Background Y position
    private float scrollSpeed; // Speed of scrolling

    public ScrollingBackground(String texturePath, float speed) {
        background = new Texture(texturePath);
        this.scrollSpeed = speed;
        this.bgY = 0;
    }

    public void update(float delta) {
        bgY -= scrollSpeed * delta; // Move background downward

        // Reset background position when it fully scrolls
        if (bgY <= -background.getHeight()) {
            bgY = 0;
        }
    }

    public void render(SpriteBatch batch) {
    	
    	batch.begin();
        batch.draw(background, 0, bgY);
        batch.draw(background, 0, bgY + background.getHeight()); // Loop
        batch.end();
    }

    public void dispose() {
        background.dispose();
    }
}
