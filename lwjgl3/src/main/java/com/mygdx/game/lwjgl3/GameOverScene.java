package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScene extends AbstractScene implements Screen{
				
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
	private Texture quitButtonTexture;
	private Texture menuReturnButtonTexture;

	private OrthographicCamera camera;
	private Viewport viewport;
	private Character character;
	private BitmapFont font;

	// Expand Buttons on Hover
	private boolean topHovered, middleHovered, bottomHovered;

	// Constructor
	public GameOverScene(GameMaster game) {
		super(game);
		
		// Initialize camera and viewport
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

		camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
		camera.update();

		menuTexture = new Texture("Shadow.png");
		playButtonTexture = new Texture("restart.png");
		quitButtonTexture = new Texture("quit.png");	        
		menuReturnButtonTexture = new Texture("menuButton.png");
		
		character = new Character();
		font = new BitmapFont(Gdx.files.internal("ralewayFont/myfont.fnt"), Gdx.files.internal("ralewayFont/myfont.png"), false);
		font.getData().scale(1.5f);
	}

	@Override
	protected void draw(float delta) {
		batch.setProjectionMatrix(camera.combined); // Apply camera projection

		// Draw menu background
		batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		// Draw high score text
		font.draw(batch, "High Score: " + Math.round(ScoreManager.highScore), TOP_BTN_X-80, TOP_BTN_Y+50);

		// Check for Hover
		checkHover();

		drawButtonWithHover(playButtonTexture, TOP_BTN_X, TOP_BTN_Y - 150, topHovered);
		drawButtonWithHover(menuReturnButtonTexture, MIDDLE_BTN_X, MIDDLE_BTN_Y - 150, middleHovered);
		drawButtonWithHover(quitButtonTexture, BTM_BTN_X, BTM_BTN_Y - 150, bottomHovered);

		handleInput();
	}

	private void checkHover() {
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos); // Convert screen to world coordinates

		float x = mousePos.x;
		float y = mousePos.y;

		// Check hover for Play button
		topHovered = (x >= TOP_BTN_X && x <= (TOP_BTN_X + BUTTON_WIDTH)
				&& y >= TOP_BTN_Y-150 && y <= (TOP_BTN_Y - 150 + BUTTON_HEIGHT));

		// Check hover for Settings button
		middleHovered = (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
				&& y >= MIDDLE_BTN_Y-150 && y <= (MIDDLE_BTN_Y - 150 + BUTTON_HEIGHT));

		// Check hover for Instructions button
		bottomHovered = (x >= BTM_BTN_X && x <= (BTM_BTN_X + BUTTON_WIDTH)
				&& y >= BTM_BTN_Y-150 && y <= (BTM_BTN_Y - 150 + BUTTON_HEIGHT));
	}

	private void handleInput() {
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // Convert screen to world coordinates

			float x = touchPos.x;
			float y = touchPos.y;

			// Check click on Restart Button
			if (x >= TOP_BTN_X && x <= (TOP_BTN_X + BUTTON_WIDTH)
					&& y >= TOP_BTN_Y-150 && y <= (TOP_BTN_Y-150 + BUTTON_HEIGHT)) {
				SceneManager.getInstance().setScene("Play");

				// Retrieve that scene
				Screen screen = SceneManager.getInstance().getScene("Play");

				// Go back to PlayScene and restart the game
				if (screen instanceof PlayScene) {
					PlayScene playScene = (PlayScene) screen;
					playScene.restartGame(); // Restart
				}
				}

			// Return-to-Menu Button
			else if (x >= MIDDLE_BTN_X && x <= (MIDDLE_BTN_X + BUTTON_WIDTH)
					&& y >= MIDDLE_BTN_Y-150 && y <= (MIDDLE_BTN_Y-150 + BUTTON_HEIGHT)) {

					// Simply switch to "Menu"
					SceneManager.getInstance().setScene("Menu");
			}

			// Check click on Quit Button
			else if (x >= BTM_BTN_X && x <= (BTM_BTN_X + BUTTON_WIDTH)
					&& y >= BTM_BTN_Y-150 && y <= (BTM_BTN_Y-150 + BUTTON_HEIGHT)) {
				System.exit(0);
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
		quitButtonTexture.dispose();
		menuReturnButtonTexture.dispose();
	}


}
