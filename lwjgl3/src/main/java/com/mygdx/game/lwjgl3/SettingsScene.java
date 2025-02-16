package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class SettingsScene extends AbstractScene implements Screen {

    private static final float MAX_VOLUME = 1f;
    private static final float MIN_VOLUME = 0f;
    private static final float VOLUME_STEP = 0.1f;

    // Current Volume Level
    private float volume = 1.0f;

    // Buttons
    private static final int BTN_SIZE     = 80;
    private static final int BTN_PLUS_X   = 600;
    private static final int BTN_PLUS_Y   = 350;
    private static final int BTN_MINUS_X  = 400;
    private static final int BTN_MINUS_Y  = 350;
    private static final int BTN_BACK_X  = 50;
    private static final int BTN_BACK_Y  = 350;

    // Volume Text
    private static final int TEXT_X       = 520;
    private static final int TEXT_Y       = 500;

    // Textures
    private Texture backgroundTexture;
    private Texture plusTexture;
    private Texture minusTexture;
    private Texture backButton;

    // For drawing text
    private BitmapFont font;

    public SettingsScene(GameMaster game) {
        super(game);

        // Load textures
        backgroundTexture = new Texture("menu.png");
        plusTexture = new Texture("plus.png");
        minusTexture = new Texture("minus.png");
        backButton = new Texture("backButton.png");

        // For drawing text
        font = new BitmapFont(); 
    }

    @Override
    protected void draw(float delta) {
        // Clear screen color is handled in AbstractScene.clearScreen().
        // Make sure to set the batch's projection matrix if using a custom camera/viewport:
        batch.setProjectionMatrix(camera.combined);

        // Draw the background (assuming you have a 1280x720 viewport, for example)
        batch.draw(backgroundTexture, 0, 0, 1280, 720);

        // Draw buttons
        batch.draw(plusTexture,  BTN_PLUS_X,  BTN_PLUS_Y,  BTN_SIZE, BTN_SIZE);
        batch.draw(minusTexture, BTN_MINUS_X, BTN_MINUS_Y, BTN_SIZE, BTN_SIZE/2);
        batch.draw(backButton, BTN_BACK_X,  BTN_BACK_Y,  BTN_SIZE, BTN_SIZE);

        // Show the current volume as text
        String volumeText = String.format("Volume: %.0f%%", volume * 100);
        font.draw(batch, volumeText, TEXT_X, TEXT_Y);

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            // Convert screen coords to world coords
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            float x = touchPos.x;
            float y = touchPos.y;

            // Check click on Plus Button
            if (x >= BTN_PLUS_X && x <= (BTN_PLUS_X + BTN_SIZE)
             && y >= BTN_PLUS_Y && y <= (BTN_PLUS_Y + BTN_SIZE)) 
            {
                volume = Math.min(MAX_VOLUME, volume + VOLUME_STEP);
                applyVolume();
            }

            // Check click on Minus Button
            else if (x >= BTN_MINUS_X && x <= (BTN_MINUS_X + BTN_SIZE)
                  && y >= BTN_MINUS_Y && y <= (BTN_MINUS_Y + BTN_SIZE))
            {
                volume = Math.max(MIN_VOLUME, volume - VOLUME_STEP);
                applyVolume();
            }

            // Check click on Back Button
            else if (x >= BTN_BACK_X && x <= (BTN_BACK_X + BTN_SIZE)
                  && y >= BTN_BACK_Y && y <= (BTN_BACK_Y + BTN_SIZE))
            {
                SceneManager.getInstance().setScene("Menu");
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            SceneManager.getInstance().setScene("Menu");
        }
    }

    /**
     * This method applies the volume change to your audio engine.
     * If you’re using LibGDX’s Music or Sound instances, you’d call setVolume(...) on each.
     * Or if you have a global audio manager, you’d pass the updated volume to it here.
     */
    private void applyVolume() {
        // Example: if you store references to your background music or SFX, do:
        // backgroundMusic.setVolume(volume);
        // clickSound.setVolume(volume);
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
