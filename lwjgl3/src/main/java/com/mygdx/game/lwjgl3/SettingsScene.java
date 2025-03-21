package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class SettingsScene extends AbstractScene implements Screen {

    private FitViewport viewport;
    
    //scrollSpeed Stuff
    private float scrollSpeed = 50f;  // Default scroll speed
    private static final float SCROLL_STEP = 10f; // Change rate
    private static final float MAX_SCROLL = 150f;
    private static final float MIN_SCROLL = 10f;

    private static final int SCROLL_PLUS_X = 600;
    private static final int SCROLL_PLUS_Y = 250;

    private static final int SCROLL_MINUS_X = 400;
    private static final int SCROLL_MINUS_Y = 250;

    private Texture scrollPlusTexture;
    private Texture scrollMinusTexture;

    // Volume Stuff
    private static final float MAX_VOLUME = 1f;
    private static final float MIN_VOLUME = 0f;
    private static final float VOLUME_STEP = 0.1f;

    private float volume = 1.0f;

    private SoundManager soundManager;

    // Volume Text
    private static final int TEXT_X = 520;
    private static final int TEXT_Y = 500;

    private BitmapFont font;

    // Buttons
    private static final int BTN_SIZE_DBL = 200;
    private static final int BTN_SIZE = 80;
    private static final int BTN_SIZE_HALF = 40;

    private static final int BTN_PLUS_X = 600;
    private static final int BTN_PLUS_Y = 350;

    private static final int BTN_MINUS_X = 400;
    private static final int BTN_MINUS_Y = 350;

    private static final int BTN_BACK_X = 50;
    private static final int BTN_BACK_Y = 570;

    

    // Textures
    private Texture backgroundTexture;
    private Texture plusTexture;
    private Texture minusTexture;
    private Texture backButton;


    public SettingsScene(GameMaster game, SoundManager soundManager) {
        super(game);

        this.soundManager = soundManager;
        this.scrollSpeed = PlayScene.getScrollSpeed();  // Get the current scroll speed

        camera.setToOrtho(false, 1280, 720);
        viewport = new FitViewport(1280, 720, camera);
        
        
        // Load textures
        backgroundTexture = new Texture("menu.png");
        plusTexture = new Texture("plus.png");
        minusTexture = new Texture("minus.png");
        backButton = new Texture("backButton.png");
        backgroundTexture = new Texture("Shadow.png");
        
        // Load textures for scrollspeed
        scrollPlusTexture = new Texture("plus.png");
        scrollMinusTexture = new Texture("minus.png");

        font = new BitmapFont(); 
    }

    @Override
    protected void draw(float delta) {

        //batch.setProjectionMatrix(camera.combined);
        //batch.draw(backgroundTexture, 0, 0, 1280, 720);

        // Draw background and buttons
        batch.draw(backgroundTexture, 0, 0, 1280, 720);
        batch.draw(plusTexture,  BTN_PLUS_X,  BTN_PLUS_Y,  BTN_SIZE, BTN_SIZE);
        batch.draw(minusTexture, BTN_MINUS_X, BTN_MINUS_Y, BTN_SIZE, BTN_SIZE_HALF);
        batch.draw(backButton, BTN_BACK_X,  BTN_BACK_Y,  BTN_SIZE_DBL, BTN_SIZE);
        
        // Draw existing settings
        batch.draw(scrollPlusTexture, SCROLL_PLUS_X, SCROLL_PLUS_Y, BTN_SIZE, BTN_SIZE);
        batch.draw(scrollMinusTexture, SCROLL_MINUS_X, SCROLL_MINUS_Y, BTN_SIZE, BTN_SIZE_HALF);

        // Display scroll speed value
        String scrollSpeedText = String.format("Scroll Speed: %.0f", scrollSpeed);
        font.draw(batch, scrollSpeedText, 520, 200);

        // Current Volume Text
        String volumeText = String.format("Volume: %.0f%%", volume * 100);
        font.draw(batch, volumeText, TEXT_X, TEXT_Y);

        // Increase Font Size
        font.getData().setScale(2f);  
        
        


        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            
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
            else if (x >= BTN_BACK_X && x <= (BTN_BACK_X + BTN_SIZE_DBL)
            	      && y >= BTN_BACK_Y - 50 && y <= (BTN_BACK_Y + BTN_SIZE)) 
            	{
            	    SceneManager.getInstance().setScene("Menu");
            	}

            
            //for scroll speed
            if (x >= SCROLL_PLUS_X && x <= SCROLL_PLUS_X + BTN_SIZE
                    && y >= SCROLL_PLUS_Y && y <= SCROLL_PLUS_Y + BTN_SIZE) {
                       scrollSpeed = Math.min(MAX_SCROLL, scrollSpeed + SCROLL_STEP);
                   }

                   else if (x >= SCROLL_MINUS_X && x <= SCROLL_MINUS_X + BTN_SIZE
                         && y >= SCROLL_MINUS_Y && y <= SCROLL_MINUS_Y + BTN_SIZE) {
                       scrollSpeed = Math.max(MIN_SCROLL, scrollSpeed - SCROLL_STEP);
                   }
            
            PlayScene.setScrollSpeed(scrollSpeed); // Apply the new scroll speed
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
