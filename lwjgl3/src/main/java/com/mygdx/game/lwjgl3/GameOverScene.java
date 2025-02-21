package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScene extends AbstractScene implements Screen{
				
	 	private static final int VIRTUAL_WIDTH = 1280;
	    private static final int VIRTUAL_HEIGHT = 720;

	    // Example button bounds
	    private static final int BUTTON_WIDTH = 200;
	    private static final int BUTTON_HEIGHT = 80;

	    // Play button
	    private static final int PLAY_BTN_X = 550;
	    private static final int PLAY_BTN_Y = 360;
	    // Settings button
	    private static final int QUIT_BTN_X = PLAY_BTN_X;
	    private static final int QUIT_BTN_Y = PLAY_BTN_Y - 150;

	    private Texture menuTexture;
	    private Texture playButtonTexture;
	    private Texture quitButtonTexture;

	    private OrthographicCamera camera;
	    private Viewport viewport;

	    // Constructor
	    public GameOverScene(GameMaster game) {
	        super(game);
	        
	        // Initialize camera and viewport
	        camera = new OrthographicCamera();
	        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

	        // viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
	        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
	        camera.update();

	        menuTexture = new Texture("menu.png");
	        playButtonTexture = new Texture("restart.png");
	        quitButtonTexture = new Texture("quit.png");
	        
	    }

	    @Override
	    protected void draw(float delta) {

	        batch.setProjectionMatrix(camera.combined); // Apply camera projection


	        // Draw menu background
	        batch.draw(menuTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

	        // Draw buttons
	        batch.draw(playButtonTexture, PLAY_BTN_X, PLAY_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Play Button
	        batch.draw(quitButtonTexture, QUIT_BTN_X, QUIT_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT); // Settings Button

	        handleInput();

	    }

	    private void handleInput() {
	        if (Gdx.input.justTouched()) {
	            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
	            camera.unproject(touchPos); // Convert screen to world coordinates

	            float x = touchPos.x;
	            float y = touchPos.y;

	            // Check click on Restart Button
	            if (x >= PLAY_BTN_X && x <= (PLAY_BTN_X + BUTTON_WIDTH)
	                    && y >= PLAY_BTN_Y && y <= (PLAY_BTN_Y + BUTTON_HEIGHT)) {
	            	SceneManager.getInstance().setScene("Play");
	             }

	            // Check click on Settings Button
	            else if (x >= QUIT_BTN_X && x <= (QUIT_BTN_X + BUTTON_WIDTH)
	                    && y >= QUIT_BTN_Y && y <= (QUIT_BTN_Y + BUTTON_HEIGHT)) {
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
	    }
	


}
