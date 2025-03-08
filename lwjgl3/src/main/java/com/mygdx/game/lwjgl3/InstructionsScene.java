package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InstructionsScene extends AbstractScene implements Screen {

    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;

    // Camera and viewport
    private OrthographicCamera camera;
    private Viewport viewport;

    // Rendering
    private SpriteBatch batch;
    private BitmapFont font;

    // Textures for instructions
    private Texture friesTexture;
    private Texture appleTexture;

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
        font = new BitmapFont(); // or use a custom .fnt if desired

        // Load images
        friesTexture = new Texture("fries.png");
        appleTexture = new Texture("apple.png");
    }

    @Override
    protected void draw(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Use our camera’s projection
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Draw your instructions text
        font.draw(batch, "Instructions", 50, 600);

        font.draw(batch, "Avoid the fries:", 50, 530);
        batch.draw(friesTexture, 220, 500, 64, 64);

        font.draw(batch, "Collect the apple to gain health:", 50, 430);
        batch.draw(appleTexture, 380, 400, 64, 64);

        font.draw(batch, "Press ESC to return to Menu", 50, 300);
        batch.end();

        // Check for key to go back to menu
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
