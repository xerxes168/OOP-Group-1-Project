package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
    private Texture background;
    private float y1, y2;
    private float speed;

    public ScrollingBackground(String backgroundImage, float speed) {
        this.background = new Texture(Gdx.files.internal(backgroundImage));
        this.speed = speed;
        this.y1 = 0;
        this.y2 = y1 + Gdx.graphics.getHeight();
    }

    public void update(float deltaTime) {
        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        if (y1 + Gdx.graphics.getHeight() <= 0) {
            y1 = y2 + Gdx.graphics.getHeight();
        }
        if (y2 + Gdx.graphics.getHeight() <= 0) {
            y2 = y1 + Gdx.graphics.getHeight();
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, y1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(background, 0, y2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void dispose() {
        background.dispose();
    }
}
