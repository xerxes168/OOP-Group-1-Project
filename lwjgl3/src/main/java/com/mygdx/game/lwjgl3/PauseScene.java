package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseScene extends AbstractScene implements Screen {

    private SpriteBatch batch;
    private BitmapFont font; 

    public PauseScene(GameMaster game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        font = new BitmapFont(); // Use a custom font if desired
    }

    @Override
    protected void draw(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        font.draw(batch, "Game Paused", viewport.getWorldWidth() / 2f - 50, 
                  viewport.getWorldHeight() / 2f + 20);
        font.draw(batch, "Press P to Resume", viewport.getWorldWidth() / 2f - 60, 
                  viewport.getWorldHeight() / 2f - 0);
        font.draw(batch, "Press M for Main Menu", viewport.getWorldWidth() / 2f - 80, 
                  viewport.getWorldHeight() / 2f - 40);
        batch.end();

        // Key Inputs to change Scenes
        if (Gdx.input.isKeyJustPressed(Keys.P)) {

            // Return to the play scene
            SceneManager.getInstance().setScene("Play");
            
            Screen screen = SceneManager.getInstance().getScene("Play");
            
            // Go back to PlayScene and resume the game
            if (screen instanceof PlayScene) {
                PlayScene playScene = (PlayScene) screen;
                playScene.updatePause();
            }         

            
        } else if (Gdx.input.isKeyJustPressed(Keys.M)) {
            // Go back to main menu
            SceneManager.getInstance().setScene("Menu");
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
        camera.position.set(width / 2f, height / 2f, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (batch != null) {
            batch.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }
}
