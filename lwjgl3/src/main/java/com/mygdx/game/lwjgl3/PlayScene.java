package com.mygdx.game.lwjgl3;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScene extends AbstractScene implements Screen {
    
    // Entities
    private SpriteBatch batch;
	private Character player1;
	
	private EntityManager entityManager;
	private ShapeRenderer shapeRenderer;
	private CollisionManager collisionManager;
	
	private SoundManager soundManager;
	
	// Pause Variables
	private boolean isPaused = false;
	private boolean initialized = false;
	
    // Scrolling Items
	private ScrollingBackground scrollingBackground;
    private float scrollOffset = 0; // Offset for scrolling effect
    private static float scrollSpeed = 40; // Pixels per second 
    private BitmapFont font;
    private static PlayScene currentInstance;
        
    public static void setScrollSpeed(float speed) {
        scrollSpeed = speed;
    }

    public static float getScrollSpeed() {
        return scrollSpeed;
    }
    
    public static float getTopBoundary() {
        return currentInstance.camera.position.y + currentInstance.viewport.getWorldHeight() / 2;
    }

    public PlayScene(GameMaster game, SoundManager soundManager) {
        super(game);
        this.soundManager = soundManager;
        
        float worldWidth = 800; // Use a fixed world width
        float worldHeight = 600; // Use a fixed world height
        
        camera = new OrthographicCamera();
//        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        // -------------------------
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0);
        camera.update();
        // UI camera setup
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // ---------------------------
        
        font = new BitmapFont();
    }

    // All show logic in here
    public void startGameState() {
    	currentInstance = this;
        scrollingBackground = new ScrollingBackground("background.png", scrollSpeed);

        entityManager = new EntityManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        collisionManager = new CollisionManager();
        soundManager.playBackgroundMusic();
        
        
        // Create entities
        player1 = new Character(500, 50, 1, "character.png", Gdx.graphics.getWidth() / 12f, Gdx.graphics.getWidth() / 12f, soundManager, entityManager);
        
        //create Object 1
        entityManager.spawnObject1Entities(3, scrollSpeed);        
        //create Object 2
        entityManager.spawnObject2Entities(5, scrollSpeed);            
        //create the terrain
        entityManager.spawnTerrainEntities(8, scrollSpeed);

        // Add them to entity manager
        entityManager.addCharacters(player1);
        
        isPaused = false;
        initialized = true;
        
    }


    @Override   // Similar to Create() in GameMaster
    // Add code into startGameState() not here
    public void show() {
        super.show();
        if (!initialized) {
            startGameState();
        }
    }


    @Override // Similar to Render() in GameMaster
    protected void draw(float delta) {
    	
    	ScreenUtils.clear(0, 0, 0.2f, 1);
    	
        if (!isPaused) {
            // All Game Logic in here
	    	
        	DifficultyManager.updateDifficultyBasedOnScore((int) player1.points, delta);
	    	scrollingBackground.update(delta);
	        
	        // Camera Y never goes below the bottom of the background
	        float minY = viewport.getWorldHeight() * 0.5f;
	        if (camera.position.y < minY) {
	            camera.position.y = minY;
	        }
	
	        // Update camera position if needed based on player
	        if (player1 != null) {
	            // Example: camera follows player vertically
	            camera.position.y = Math.max(
	                viewport.getWorldHeight() / 2f,
	                player1.getY()
	            );
	            camera.update();
	        }
	        
	        
	        
	        
	        batch.setProjectionMatrix(camera.combined);
	        scrollingBackground.draw(batch);
	        shapeRenderer.setProjectionMatrix(camera.combined);
	        batch.setProjectionMatrix(camera.combined);
	        
	        // Grid scrolling
	        //updateGridScroll(delta);
	        //drawGrid(); 

	        entityManager.movement();
	        
	        entityManager.updateEntitySpeeds(getScrollSpeed());
	        
	        // Draw and update entities
	        entityManager.draw(batch);
	
	
	        // Check collisions
	        collisionManager.checkCollisions(entityManager.getAllEntities());
	        
        }
        
        // Always draw the scene
        entityManager.draw(batch);
        
        // Check for pause
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            isPaused = !isPaused;
            SceneManager.getInstance().setScene("Pause");
        }
                
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            SceneManager.getInstance().setScene("Menu");
        }
        
//        uiCamera.update();
//        batch.setProjectionMatrix(uiCamera.combined);
//        batch.begin();
//        font.draw(batch, "Points: " + Math.round(player1.points), Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
//        batch.end();
        
        // UI elements with UI camera
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        font.draw(batch, "Points: " + Math.round(player1.points), 
                  Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
        batch.end();
        
        
    }
    
    // Remove Game Pause after coming back from Menu
    public void updatePause() {
    	this.isPaused = false;
    }

    // Restart Game
    public void restartGame() {
        initialized = false;
        setScrollSpeed(40);
        startGameState();
        
    }

    // Grid scrolling functions
    // Adjust grid scrolling based on scrollSpeed and delta time
   

    // Draw grid that scrolls upward
    

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        // Update viewport with new dimensions
        viewport.update(width, height, false); // Don't center camera automatically
        
        // If you need to adjust camera position
        if (player1 != null) {
            // Keep camera centered on player (with bounds)
            float cameraY = Math.max(
                viewport.getWorldHeight() / 2f,
                Math.min(player1.getY(), Float.MAX_VALUE) // Add upper bound if needed
            );
            camera.position.set(camera.position.x, cameraY, 0);
        } else {
            // Default position if player doesn't exist yet
            camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        }
        camera.update();
        
        // Update UI camera
        uiCamera.setToOrtho(false, width, height);
        uiCamera.update();
        
        // Recalculate scroll offset if needed
        float cellHeight = viewport.getWorldHeight() / 12f;
        scrollOffset = scrollOffset % cellHeight;
        
        // Ensure player is in valid position after resize
        if (player1 != null) {
            // Keep player within visible bounds
            float minX = player1.getWidth() / 2;
            float maxX = viewport.getWorldWidth() - player1.getWidth() / 2;
            float minY = player1.getHeight() / 2;
            float maxY = viewport.getWorldHeight() - player1.getHeight() / 2;
            
            float newX = Math.max(minX, Math.min(player1.getX(), maxX));
            float newY = Math.max(minY, Math.min(player1.getY(), maxY));
            
            player1.setPosition(newX, newY);
        }
    }
    

    @Override
    public void dispose() {
        super.dispose();

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (scrollingBackground != null) {
            scrollingBackground.dispose();
        }
    }
}
