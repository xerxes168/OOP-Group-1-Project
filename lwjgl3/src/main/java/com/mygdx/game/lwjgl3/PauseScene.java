package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScene extends AbstractScene implements Screen {

    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;

    // Button bounds
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 80;

    //Restart button
    private static final int RESTART_BTN_X = 550;
    private static final int RESTART_BTN_Y = 360;
    // Play button
    private static final int PLAY_BTN_X = RESTART_BTN_X;
    private static final int PLAY_BTN_Y = RESTART_BTN_Y + 150;
    // Settings button
    private static final int MENU_BTN_X = RESTART_BTN_X;
    private static final int MENU_BTN_Y = RESTART_BTN_Y - 150;

    private Texture menuTexture;
    private Texture playButtonTexture;
    private Texture restartButtonTexture;
    private Texture menuButtonTexture;

    // private SpriteBatch batch;
    private BitmapFont font; 

    private OrthographicCamera camera;
    private Viewport viewport;

    // Constructor
    public PauseScene(GameMaster game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        // viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        // camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        camera.update();

        menuTexture = new Texture("menu.png");
        playButtonTexture = new Texture("resume.png");
        restartButtonTexture = new Texture("restart.png");
        menuButtonTexture = new Texture("menuButton.png");
    }

    // @Override
    // public void show() {
    //     super.show();
    //     if (batch == null) {
    //         batch = new SpriteBatch();
    //     }
    // }

    @Override
    protected void draw(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // batch.begin();
        // font.draw(batch, "Game Paused", viewport.getWorldWidth() / 2f - 50, 
        //           viewport.getWorldHeight() / 2f + 20);
        // font.draw(batch, "Press P to Resume", viewport.getWorldWidth() / 2f - 60, 
        //           viewport.getWorldHeight() / 2f - 0);
        // font.draw(batch, "Press M for Main Menu", viewport.getWorldWidth() / 2f - 80, 
        //           viewport.getWorldHeight() / 2f - 40);
        // batch.end();

        batch.setProjectionMatrix(camera.combined);

        // Draw menu background
        batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Draw buttons
        batch.draw(playButtonTexture, PLAY_BTN_X, PLAY_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Resume Button
        batch.draw(restartButtonTexture, RESTART_BTN_X, RESTART_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Restart Button
        batch.draw(menuButtonTexture, MENU_BTN_X, MENU_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Menu Button

        handleInput();
    }

    private void resumeGame(float x) {
        SceneManager.getInstance().setScene("Play");
        
        // Retrieve that scene
        Screen screen = SceneManager.getInstance().getScene("Play");

        // Go back to PlayScene and resume the game
        if (screen instanceof PlayScene) {
            PlayScene playScene = (PlayScene) screen;
            if (x == 1) {
                playScene.updatePause(); // Resume
            } else if (x == 2) {
                playScene.restartGame(); // Restart
            }
        }
    }
 
    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            resumeGame(1);
        } 
        else if (Gdx.input.isKeyJustPressed(Keys.M)) {
            // Go back to main menu
            SceneManager.getInstance().setScene("Menu");
        } 
        else if (Gdx.input.isKeyJustPressed(Keys.R)) {
            // Restart PlayScene
            resumeGame(2);
        }

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen to world coordinates

            float x = touchPos.x;
            float y = touchPos.y;

            // Check if user clicked on RESUME button
            if (x >= PLAY_BTN_X && x <= (PLAY_BTN_X + BUTTON_WIDTH)
                    && y >= PLAY_BTN_Y && y <= (PLAY_BTN_Y + BUTTON_HEIGHT)) {
                resumeGame(1);
            }
            // Check if user clicked on RESTART button
            else if (x >= RESTART_BTN_X && x <= (RESTART_BTN_X + BUTTON_WIDTH)
                    && y >= RESTART_BTN_Y && y <= (RESTART_BTN_Y + BUTTON_HEIGHT)) {
                resumeGame(2);
            }
            // Check if user clicked on MENU button
            else if (x >= MENU_BTN_X && x <= (MENU_BTN_X + BUTTON_WIDTH)
                    && y >= MENU_BTN_Y && y <= (MENU_BTN_Y + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Menu");
            }
        }    
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTexture.dispose();
        playButtonTexture.dispose();
        restartButtonTexture.dispose();
        menuButtonTexture.dispose();
    }
}
