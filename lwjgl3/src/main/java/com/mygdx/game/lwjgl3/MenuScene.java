package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScene extends AbstractScene implements Screen {

    private static final int VIRTUAL_WIDTH = 800;
    private static final int VIRTUAL_HEIGHT = 600;

    // Example button bounds
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;

    // "Play" button top-left corner
    private static final int PLAY_BTN_X = 300;
    private static final int PLAY_BTN_Y = 300;
    // "Settings" button top-left corner
    private static final int SETTING_BTN_X = 300;
    private static final int SETTING_BTN_Y = 200;

    private Texture menuTexture;
    private Texture playButtonTexture;
    private Texture settingButtonTexture;
    private GameMaster game;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Constructor
    public MenuScene(GameMaster game) {
        super(game);

        // Initialize camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        menuTexture = new Texture("menu.png");
        playButtonTexture = new Texture("play.png");
        settingButtonTexture = new Texture("settings.png");
    }

    @Override
    protected void draw(float delta) {

        batch.setProjectionMatrix(camera.combined); // Apply camera projection


        // Draw menu background
        batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Draw buttons
        batch.draw(playButtonTexture, PLAY_BTN_X, PLAY_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Play Button
        batch.draw(settingButtonTexture, SETTING_BTN_X, SETTING_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Settings Button

        handleInput();

    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen to world coordinates

            float x = touchPos.x;
            float y = touchPos.y;

            // Check if user clicked on "Play" button
            if (x >= PLAY_BTN_X && x <= (PLAY_BTN_X + BUTTON_WIDTH)
                    && y >= PLAY_BTN_Y && y <= (PLAY_BTN_Y + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Play");
            }

            // Check if user clicked on "Settings" button
            else if (x >= SETTING_BTN_X && x <= (SETTING_BTN_X + BUTTON_WIDTH)
                    && y >= SETTING_BTN_Y && y <= (SETTING_BTN_Y + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Setting");
            }
        }

        
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTexture.dispose();
        playButtonTexture.dispose();
        settingButtonTexture.dispose();
    }
}
