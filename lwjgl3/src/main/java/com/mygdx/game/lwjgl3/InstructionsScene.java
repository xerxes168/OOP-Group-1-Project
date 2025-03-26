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

    private static final int BTN_BACK_X = 50;
    private static final int BTN_BACK_Y = 600;

    private static final int TXT_X = 100; 
    private static final int ICON_SIZE = 64;
    private final int FRIES_X = BTN_BACK_X + 160;
    private final int FRIES_Y = BTN_BACK_Y - 290;
    private final int APPLE_X = BTN_BACK_X + 320;
    private final int APPLE_Y = BTN_BACK_Y - 390;

    // Expand Buttons on Hover
    private boolean backHovered, appleHovered, friesHovered;

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
        font = new BitmapFont(Gdx.files.internal("ralewayFont/myfont.fnt"), Gdx.files.internal("ralewayFont/myfont.png"), false);

        // Load images
        friesTexture = new Texture("fries.png");
        appleTexture = new Texture("apple.png");
        backgroundTexture = new Texture("shadow.png");
        backButton = new Texture("backButton.png");
    }

    @Override
    protected void draw(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Use our camera’s projection
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        
        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Draw your instructions text
        font.draw(batch, "Instructions", TXT_X, BTN_BACK_Y - 30);
        font.draw(batch, "In the vibrant and fast-paced world of Fried Up Baby, players embark on a thrilling journey as a baby \nnavigating through a bustling environment. \n\nThe objective is simple yet challenging: Dodge the tempting but unhealthy fries and \ncollect as many nutritious apples as possible to rack up points and achieve the highest score!", BTN_BACK_X + 50, BTN_BACK_Y - 80);

        font.draw(batch, "Avoid the fries:", TXT_X, BTN_BACK_Y - 250);
        // batch.draw(friesTexture, BTN_BACK_X + 200, BTN_BACK_Y - 280, 64, 64);

        font.draw(batch, "Collect the apple to gain points:", TXT_X, BTN_BACK_Y - 350);
        // batch.draw(appleTexture, BTN_BACK_X + 330, BTN_BACK_Y - 380, 64, 64);

        font.draw(batch, "Press P Key to Pause the Game\n\nPress ESC Key / Back Button to return to Menu", BTN_BACK_X + 50, 180);

        batch.end();

        // Check for Hover
        checkHover();

        drawButtonWithHover(backButton, BTN_BACK_X, BTN_BACK_Y, backHovered);
        drawButtonWithHover(friesTexture, FRIES_X, FRIES_Y, ICON_SIZE, ICON_SIZE, friesHovered);
        drawButtonWithHover(appleTexture, APPLE_X, APPLE_Y, ICON_SIZE, ICON_SIZE, appleHovered);
        
        handleInput();
    }

    private void checkHover() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); // Convert screen to world coordinates

        float x = mousePos.x;
        float y = mousePos.y;

        // Check hover for Back button
        backHovered = (x >= BTN_BACK_X && x <= (BTN_BACK_X + BUTTON_WIDTH)
                && y >= BTN_BACK_Y && y <= (BTN_BACK_Y + BUTTON_HEIGHT));

        // Check hover for Fries
        friesHovered = (x >= FRIES_X && x <= (FRIES_X + BUTTON_WIDTH)
                && y >= FRIES_Y && y <= (FRIES_Y + BUTTON_HEIGHT));

        // Check hover for Apple
        appleHovered = (x >= APPLE_X && x <= (APPLE_X + BUTTON_WIDTH)
                && y >= APPLE_Y && y <= (APPLE_Y + BUTTON_HEIGHT));
    }

    // Check for key to go back to menu
    private void handleInput() {
        
        if (Gdx.input.justTouched()) {
            
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            float x = touchPos.x;
            float y = touchPos.y;

            // Check click on Back Button
            if (x >= BTN_BACK_X && x <= (BTN_BACK_X + BUTTON_WIDTH)
                  && y >= BTN_BACK_Y && y <= (BTN_BACK_Y + BUTTON_HEIGHT))
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
        if (friesTexture != null) backgroundTexture.dispose();
        if (appleTexture != null) backButton.dispose();
    }
}
