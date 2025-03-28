package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScene extends AbstractScene implements Screen {

    // DEFINED IN ABSTRACT SCENE
    // protected static final int VIRTUAL_WIDTH = 1280;
    // protected static final int VIRTUAL_HEIGHT = 720;
    // protected static final int BUTTON_WIDTH = 200;
    // protected static final int BUTTON_HEIGHT = 80;
    // protected final int MIDDLE_BTN_X = 550;
    // protected final int MIDDLE_BTN_Y = 400;   
    // protected final int TOP_BTN_X = MIDDLE_BTN_X;
    // protected final int TOP_BTN_Y = MIDDLE_BTN_Y + 150;
    // protected final int BTM_BTN_X = MIDDLE_BTN_X;
    // protected final int BTM_BTN_Y = MIDDLE_BTN_Y - 150;
    // protected static final float HOVER_SCALE = 1.2f;
    // protected static final float NORMAL_SCALE = 1.0f;

    private Texture menuTexture;
    private Texture playButtonTexture;
    private Texture settingButtonTexture;
    private Texture instructButtonTexture;
    private Texture logoTexture;
    private GameMaster game;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Expand Buttons on Hover
    private boolean playHovered, settingsHovered, instructionsHovered;

    // Constructor
    public MenuScene(GameMaster game) {
        super(game);

        // Initialize camera and viewport
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        // viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        menuTexture = new Texture("default.png");
        playButtonTexture = new Texture("play1.png");
        settingButtonTexture = new Texture("settings1.png");
        instructButtonTexture = new Texture("instructions1.png");
        logoTexture = new Texture("logo1.png");
    }

    @Override
    protected void draw(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined); // Apply camera projection

        // Draw menu background
        batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        
        batch.draw(logoTexture, TOP_BTN_X - (24+TOP_BTN_X/4), TOP_BTN_Y - 140);

        // Check for Hover
        checkHover();
    
        drawButtonWithHover(playButtonTexture, TOP_BTN_X, TOP_BTN_Y - 190, playHovered);
        drawButtonWithHover(settingButtonTexture, MIDDLE_BTN_X, MIDDLE_BTN_Y - 190, settingsHovered);
        drawButtonWithHover(instructButtonTexture, BTM_BTN_X, BTM_BTN_Y - 190, instructionsHovered);

        handleInput();

    }

    private void checkHover() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); // Convert screen to world coordinates

        float x = mousePos.x;
        float y = mousePos.y;

        // Check hover for Play button
        playHovered = (x >= TOP_BTN_X && x <= (TOP_BTN_X + BUTTON_WIDTH)
                && y >= TOP_BTN_Y-190 && y <= (TOP_BTN_Y - 190 + BUTTON_HEIGHT));

        // Check hover for Settings button
        settingsHovered = (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
                && y >= MIDDLE_BTN_Y-190 && y <= (MIDDLE_BTN_Y - 190 + BUTTON_HEIGHT));

        // Check hover for Instructions button
        instructionsHovered = (x >= BTM_BTN_X && x <= (BTM_BTN_X + BUTTON_WIDTH)
                && y >= BTM_BTN_Y-190 && y <= (BTM_BTN_Y - 190 + BUTTON_HEIGHT));
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen to world coordinates

            float x = touchPos.x;
            float y = touchPos.y;

            // Check if user clicked on PLAY button
            if (x >= TOP_BTN_X && x <= (TOP_BTN_X + BUTTON_WIDTH)
                    && y >= TOP_BTN_Y-190 && y <= (TOP_BTN_Y - 190 + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Play");
                
                // Retrieve that scene
                Screen screen = SceneManager.getInstance().getScene("Play");
                
                // // Go back to PlayScene and resume the game
                if (screen instanceof PlayScene) {
                    PlayScene playScene = (PlayScene) screen;
                    playScene.restartGame();
                }
            }

            // Check if user clicked on SETTINGS button
            else if (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
                    && y >= MIDDLE_BTN_Y-190 && y <= (MIDDLE_BTN_Y - 190 + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Setting");
            }

            // Check if user clicked on INSTRUCTIONS button
            else if (x >= BTM_BTN_X && x <= (BTM_BTN_X + BUTTON_WIDTH)
                    && y >= BTM_BTN_Y-190 && y <= (BTM_BTN_Y - 190 + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Instructions");
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
        instructButtonTexture.dispose();
    }
}
