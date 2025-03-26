package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScene extends AbstractScene implements Screen {

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

    //Restart button
    private static final int RESTART_BTN_Y = 300;
    // Play button
    private final int PLAY_BTN_X = MIDDLE_BTN_X - 250;
    private static final int PLAY_BTN_Y = RESTART_BTN_Y;
    // Settings button
    private final int MENU_BTN_X = MIDDLE_BTN_X + 250;
    private static final int MENU_BTN_Y = RESTART_BTN_Y;

    private Texture menuTexture;
    private Texture playButtonTexture;
    private Texture restartButtonTexture;
    private Texture menuButtonTexture;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Expand Buttons on Hover
    private boolean playHovered, restartHovered, menuHovered;

    // Constructor
    public PauseScene(GameMaster game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        menuTexture = new Texture("Shadow.png");
        playButtonTexture = new Texture("resume1.png");
        restartButtonTexture = new Texture("restart1.png");
        menuButtonTexture = new Texture("menu1.png");
    }

    @Override
    protected void draw(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.setProjectionMatrix(camera.combined);

        // Draw menu background
        batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Check for Hover
        checkHover();

        drawButtonWithHover(playButtonTexture, PLAY_BTN_X, PLAY_BTN_Y, BUTTON_WIDTH, BUTTON_WIDTH, playHovered);
        drawButtonWithHover(restartButtonTexture, MIDDLE_BTN_X, RESTART_BTN_Y, BUTTON_WIDTH, BUTTON_WIDTH, restartHovered);
        drawButtonWithHover(menuButtonTexture, MENU_BTN_X, MENU_BTN_Y, BUTTON_WIDTH, BUTTON_WIDTH, menuHovered);

        handleInput();
    }

    private void checkHover() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); // Convert screen to world coordinates

        float x = mousePos.x;
        float y = mousePos.y;

        // Check hover for Play button
        playHovered = (x >= PLAY_BTN_X && x <= (PLAY_BTN_X + BUTTON_WIDTH)
                && y >= PLAY_BTN_Y && y <= (PLAY_BTN_Y + BUTTON_HEIGHT));

        // Check hover for Settings button
        restartHovered = (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
                && y >= RESTART_BTN_Y && y <= (RESTART_BTN_Y + BUTTON_HEIGHT));

        // Check hover for Instructions button
        menuHovered = (x >= MENU_BTN_X && x <= (MENU_BTN_X + BUTTON_WIDTH)
                && y >= MENU_BTN_Y && y <= (MENU_BTN_Y + BUTTON_HEIGHT));
    }

    private void resumeGame(float x) {
        SceneManager.getInstance().setScene("Play");
        
        // Retrieve that scene
        Screen screen = SceneManager.getInstance().getScene("Play");

        // Go back to PlayScene and resume/restart the game
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
                    && y >= PLAY_BTN_Y && y <= (PLAY_BTN_Y + BUTTON_WIDTH)) {
                resumeGame(1);
            }
            // Check if user clicked on RESTART button
            else if (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
                    && y >= RESTART_BTN_Y && y <= (RESTART_BTN_Y + BUTTON_WIDTH)) {
                resumeGame(2);
            }
            // Check if user clicked on MENU button
            else if (x >= MENU_BTN_X && x <= (MENU_BTN_X + BUTTON_WIDTH)
                    && y >= MENU_BTN_Y && y <= (MENU_BTN_Y + BUTTON_WIDTH)) {
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
