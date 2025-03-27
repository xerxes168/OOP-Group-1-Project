package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractScene extends ScreenAdapter {

    protected GameMaster game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected OrthographicCamera uiCamera;
    protected Viewport viewport;

    protected static final int VIRTUAL_WIDTH = 1280;
    protected static final int VIRTUAL_HEIGHT = 720;

    protected static final int BUTTON_WIDTH = 200;
    protected static final int BUTTON_HEIGHT = 80;

    protected final int MIDDLE_BTN_X = 550;
    protected final int MIDDLE_BTN_Y = 400;   

    protected final int TOP_BTN_X = MIDDLE_BTN_X;
    protected final int TOP_BTN_Y = MIDDLE_BTN_Y + 150;

    protected final int BTM_BTN_X = MIDDLE_BTN_X;
    protected final int BTM_BTN_Y = MIDDLE_BTN_Y - 150;

    // Hover effect scaling factors
    protected static final float HOVER_SCALE = 1.3f;
    protected static final float NORMAL_SCALE = 1.0f;

    public AbstractScene(GameMaster game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.uiCamera = new OrthographicCamera();
        this.viewport = new FitViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw(delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    protected abstract void draw(float delta);

    // Hover Functions
    protected void drawButtonWithHover(Texture buttonTexture, float x, float y, boolean isHovered) {
        float scale = isHovered ? HOVER_SCALE : NORMAL_SCALE;
        float scaledWidth = BUTTON_WIDTH * scale;
        float scaledHeight = BUTTON_HEIGHT * scale;

        // Adjust x and y to center the scaled button
        float adjustedX = x - (scaledWidth - BUTTON_WIDTH) / 2;
        float adjustedY = y - (scaledHeight - BUTTON_HEIGHT) / 2;

        batch.draw(buttonTexture, adjustedX, adjustedY, scaledWidth, scaledHeight);
    }

    // Overloaded method
    protected void drawButtonWithHover(Texture buttonTexture, float x, float y, int inputWidth, int inputHeight, boolean isHovered) {
        float scale = isHovered ? HOVER_SCALE : NORMAL_SCALE;
        float scaledWidth = inputWidth * scale;
        float scaledHeight = inputHeight * scale;

        // Adjust x and y to center the scaled button
        float adjustedX = x - (scaledWidth - BUTTON_WIDTH) / 2;
        float adjustedY = y - (scaledHeight - BUTTON_HEIGHT) / 2;

        batch.draw(buttonTexture, adjustedX, adjustedY, scaledWidth, scaledHeight);
    }

    // protected abstract void checkHover();


    protected void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
}

