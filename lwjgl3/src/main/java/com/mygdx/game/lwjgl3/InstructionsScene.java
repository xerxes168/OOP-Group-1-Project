package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InstructionsScene extends AbstractScene implements Screen {
	
//	private int windowWidth;
//	private int windowHeight;
	
	private final int VIRTUAL_WIDTH = 1280;
	private final int VIRTUAL_HEIGHT = 720;

    // Buttons
    private static final int BTN_SIZE_DBL = 200;
    private static final int BTN_SIZE = 80;

    private static final int BTN_BACK_X = 50;
    private static final int BTN_BACK_Y = 600;

    // Camera and viewport
    private OrthographicCamera camera;
    private Viewport viewport;

    // Rendering
    private SpriteBatch batch;
    private BitmapFont font;

    // Textures
    private Texture friesTexture;
    private Texture appleTexture;
    private Texture backgroundTexture;
    private Texture backButton;

    public InstructionsScene(GameMaster game) {
        super(game);
        
        // Get current window size
//        windowWidth = Gdx.graphics.getWidth();
//        windowHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {
        super.show();
        // Initialize rendering objects
        batch = new SpriteBatch();
        font = new BitmapFont(); 
        font.getData().setScale(1.3f);

        // Load images
        friesTexture = new Texture("fries.png");
        appleTexture = new Texture("apple.png");
        backgroundTexture = new Texture("menuBlack.png");
        backButton = new Texture("backButton.png");
    }

    @Override
    protected void draw(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Use our camera’s projection
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        
        batch.draw(backgroundTexture, 0, 0, 1280, 720);

        batch.draw(backButton, BTN_BACK_X,  BTN_BACK_Y,  BTN_SIZE_DBL, BTN_SIZE);
        
        // Draw your instructions text
        font.draw(batch, "Instructions", BTN_BACK_X + 50, BTN_BACK_Y - 30);
        font.draw(batch, "In the vibrant and fast-paced world of Apple Dash, players embark on a thrilling journey as a human \nnavigating through a bustling environment. \n\nThe objective is simple yet challenging: Dodge the tempting but unhealthy fries and \ncollect as many nutritious apples as possible to rack up points and achieve the highest score!", BTN_BACK_X + 50, BTN_BACK_Y - 80);

        font.draw(batch, "Avoid the fries:", BTN_BACK_X + 50, BTN_BACK_Y - 250);
        batch.draw(friesTexture, BTN_BACK_X + 200, BTN_BACK_Y - 280, 64, 64);

        font.draw(batch, "Collect the apple to gain points:", BTN_BACK_X + 50, BTN_BACK_Y - 350);
        batch.draw(appleTexture, BTN_BACK_X + 330, BTN_BACK_Y - 380, 64, 64);

        font.draw(batch, "Press ESC Key / Back Button to return to Menu", BTN_BACK_X + 100, 120);
        batch.end();

        handleInput();
    }

    // Check for key to go back to menu
    private void handleInput() {
        
        if (Gdx.input.justTouched()) {
            
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            float x = touchPos.x;
            float y = touchPos.y;

            // Check click on Back Button
            if (x >= BTN_BACK_X && x <= (BTN_BACK_X + BTN_SIZE_DBL)
                  && y >= BTN_BACK_Y && y <= (BTN_BACK_Y + BTN_SIZE))
            {
                SceneManager.getInstance().setScene("Menu");
            }
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            SceneManager.getInstance().setScene("Menu");
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
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (friesTexture != null) friesTexture.dispose();
        if (appleTexture != null) appleTexture.dispose();
    }
}
