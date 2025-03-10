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
    private static float scrollSpeed = 5; // Pixels per second 
    private BitmapFont font;
   
        
    public static void setScrollSpeed(float speed) {
        scrollSpeed = speed;
    }

    public static float getScrollSpeed() {
        return scrollSpeed;
    }
    

    public PlayScene(GameMaster game, SoundManager soundManager) {
        super(game);
        this.soundManager = soundManager;
        camera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        camera.update();
        font = new BitmapFont();
    }

    // All show logic in here
    public void startGameState() {

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
        List<Object2> object2 = Object2.spawnObject2(10, scrollSpeed);
        for (Object2 obj : object2) {
            entityManager.addObject2Entities(obj);
        }
            
        // Create the lily
        List<Terrain> terrains = Terrain.spawnTerrains(10, scrollSpeed);
        for (Terrain terrain : terrains) {
            entityManager.addTerrainEntities(terrain);
        }

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
    	
        if (!isPaused) {
            // All Game Logic in here
        
	    	scrollingBackground.update(delta);
	                
	        camera.position.set(
		        viewport.getWorldWidth()/2,
		        player1.getY() + player1.getHeight() * 0.5f,
		        0
	        );
	        
	        // Camera Y never goes below the bottom of the background
	        float minY = viewport.getWorldHeight() * 0.5f;
	        if (camera.position.y < minY) {
	            camera.position.y = minY;
	        }
	
	        camera.update();
	        
	        ScreenUtils.clear(0, 0, 0.2f, 1);
	        
	        shapeRenderer.setProjectionMatrix(camera.combined);
	        batch.setProjectionMatrix(camera.combined);
	        scrollingBackground.draw(batch);
	        
	        // Grid scrolling
	        //updateGridScroll(delta);
	        //drawGrid(); 
	        
	        entityManager.movement();
	        
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
        
        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        font.draw(batch, "Points: " + Math.round(player1.points), Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);
        batch.end();
        
        
    }
    
    // Remove Game Pause after coming back from Menu
    public void updatePause() {
    	this.isPaused = false;
    }

    // Restart Game
    public void restartGame() {
        initialized = false;
        startGameState();
    }

    // Grid scrolling functions
    // Adjust grid scrolling based on scrollSpeed and delta time
    private void updateGridScroll(float delta) {
    	
        float cellHeight = viewport.getWorldHeight() / 12f; // Adjusted dynamically

        scrollOffset = (scrollOffset - scrollSpeed * delta) % cellHeight;

        // For a typical grid of 8 cells
        if (scrollOffset <= -cellHeight) {
            scrollOffset = 0;
        }  
    }

    // Draw grid that scrolls upward
    private void drawGrid() {
        float screenWidth  = viewport.getWorldWidth();  // Get from viewport
        float screenHeight = viewport.getWorldHeight(); // Get from viewport
        float cellWidth    = screenWidth  / 12f;
        float cellHeight   = screenHeight / 12f;        

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1); // White grid lines

        // Draw vertical lines
        for (int i = 0; i <= 12; i++) {
            float x = i * cellWidth;
            shapeRenderer.line(x, 0, x, screenHeight);
        }

        // Draw horizontal lines that scrolls
        for (int i = -1; i <= 12; i++) {
            float y = i * cellHeight + scrollOffset;
            shapeRenderer.line(0, y, screenWidth, y);
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
                
        viewport.update(width, height, true); // Updates viewport and centers camera
        camera.position.set(width / 2f, height / 2f, 0);
        camera.update();       
        
       
        float cellHeight = viewport.getWorldHeight()/12f;
        // Ensure the scrolling offset remains proportional to new cell size
        scrollOffset = scrollOffset % cellHeight;
        
      
        
        if (player1 != null) {
            float newY = Math.max(player1.getY(), viewport.getWorldHeight() / 6f);
            player1.setPosition(player1.getX(), newY);
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
