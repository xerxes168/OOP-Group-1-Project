package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
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

    protected void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
}

