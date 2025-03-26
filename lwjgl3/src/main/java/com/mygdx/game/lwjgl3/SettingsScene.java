package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SettingsScene extends AbstractScene implements Screen {

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

    private FitViewport viewport; 

    // Volume Stuff
    private static final float MAX_VOLUME = 1f;
    private static final float MIN_VOLUME = 0f;
    private static final float VOLUME_STEP = 0.1f;

    private float volume = 1.0f;

    private SoundManager soundManager;

    private BitmapFont font;

    // Button Positions
    private static final int BTN_BACK_X = 50;
    private static final int BTN_BACK_Y = 600;

    private final int MINUS_BTN_X = MIDDLE_BTN_X - 150;
    private final int PLUS_BTN_X = MIDDLE_BTN_X + 150;

    // Textures
    private Texture backgroundTexture;
    private Texture plusTexture;
    private Texture minusTexture;
    private Texture backButton;

    // Expand Buttons on Hover
    private boolean backHovered, minusHovered, plusHovered;

    public SettingsScene(GameMaster game, SoundManager soundManager) {
        super(game);

        this.soundManager = soundManager;

        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
                
        // Load textures
        backButton = new Texture("backButton.png");
        minusTexture = new Texture("minus.png");
        plusTexture = new Texture("plus.png");
        backgroundTexture = new Texture("Shadow.png");

        font = new BitmapFont(Gdx.files.internal("ralewayFont/myfont.fnt"), Gdx.files.internal("ralewayFont/myfont.png"), false);

    }

    @Override
    protected void draw(float delta) {

        // Draw background and buttons
        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);     

        // Current Volume Text
        String volumeText = String.format("Volume: %.0f%%", volume * 100);
        font.getData().setScale(2f); 
        font.draw(batch, volumeText, MIDDLE_BTN_X-30, TOP_BTN_Y);

        // Check for Hover
        checkHover();

        drawButtonWithHover(backButton, BTN_BACK_X, BTN_BACK_Y, backHovered);
        drawButtonWithHover(minusTexture, MINUS_BTN_X, MIDDLE_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT, minusHovered);
        drawButtonWithHover(plusTexture, PLUS_BTN_X, MIDDLE_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT, plusHovered);

        handleInput();
    }

    private void checkHover() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); // Convert screen to world coordinates

        float x = mousePos.x;
        float y = mousePos.y;

        // Check hover for Back button
        backHovered = (x >= BTN_BACK_X && x <= (BTN_BACK_X + BUTTON_WIDTH)
                && y >= BTN_BACK_Y-25 && y <= (BTN_BACK_Y-25 + BUTTON_HEIGHT));

        // Check hover for Minus button
        minusHovered = (x >= MINUS_BTN_X && x <= (MINUS_BTN_X + BUTTON_WIDTH)
                && y >= MIDDLE_BTN_Y && y <= (MIDDLE_BTN_Y + BUTTON_HEIGHT));

        // Check hover for Instructions button
        plusHovered = (x >= PLUS_BTN_X && x <= (PLUS_BTN_X + BUTTON_WIDTH)
                && y >= MIDDLE_BTN_Y && y <= (MIDDLE_BTN_Y + BUTTON_HEIGHT));
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            float x = touchPos.x;
            float y = touchPos.y;

            // Check click on Back Button
            if (x >= BTN_BACK_X && x <= (BTN_BACK_X + BUTTON_WIDTH)
                    && y >= BTN_BACK_Y - 50 && y <= (BTN_BACK_Y + BUTTON_HEIGHT)) {
                SceneManager.getInstance().setScene("Menu");
            }

            // Check click on Plus Button
            else if (x >= PLUS_BTN_X && x <= (PLUS_BTN_X + BUTTON_WIDTH)
                    && y >= MIDDLE_BTN_Y && y <= (MIDDLE_BTN_Y + BUTTON_HEIGHT)) {
                volume = Math.min(MAX_VOLUME, volume + VOLUME_STEP);
                applyVolume();
            }

            // Check click on Minus Button
            else if (x >= MINUS_BTN_X && x <= (MINUS_BTN_X + BUTTON_WIDTH)
                    && y >= MIDDLE_BTN_Y && y <= (MIDDLE_BTN_Y + BUTTON_HEIGHT)) {
                volume = Math.max(MIN_VOLUME, volume - VOLUME_STEP);
                applyVolume();
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            SceneManager.getInstance().setScene("Menu");
        }
    }

    private void applyVolume() {
        soundManager.setMasterVolume(volume);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); 
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();

        backgroundTexture.dispose();
        plusTexture.dispose();
        minusTexture.dispose();
        font.dispose();
    }
}
