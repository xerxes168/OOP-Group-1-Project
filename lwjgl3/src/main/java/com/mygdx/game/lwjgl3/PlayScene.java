package com.mygdx.game.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScene extends AbstractScene implements Screen {
    
    // Entities
    private SpriteBatch batch;
	private Character player1;

	private Object1 object1;
	private Object2 object2;

	private Terrain terrainObj;

	private EntityManager entityManager;
	private EntityManager test;
	private ShapeRenderer shapeRenderer;
	private CollisionManager collisionManager;
	
	private SoundManager soundManager;

    // Scrolling Items
	private ScrollingBackground scrollingBackground;
    private float scrollOffset = 0; // Offset for scrolling effect
    private float scrollSpeed = 50; // Pixels per second
    private List<Entity> toRemove;


    public PlayScene(GameMaster game) {
        super(game);
        
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(640, 360, 0);
        camera.update();
        toRemove = new ArrayList<>();
        
    }


    @Override   // Similar to Create() in GameMaster
    public void show() {
        super.show();

        // ---------------------------------------------------------

        // If you want to further adjust the parent's camera:
        // camera.position.set(1280, 720, 0); 
        // camera.update();

        // If the parent’s constructor made an 800x600 FitViewport, that’s typically fine. 
        // But if you want a different resolution, you’d recreate it here.
        // viewport = new FitViewport(1280, 720, camera);

        // ---------------------------------------------------------

        entityManager = new EntityManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        soundManager = new SoundManager();
        collisionManager = new CollisionManager();
        
        // ScrollingBackground class
        // scrollingBackground = new ScrollingBackground("background.png", 100);

        // Create entities
        player1 = new Character(soundManager);
        object1 = new Object1();
        object2 = new Object2();
        terrainObj = new Terrain();

        // Add them to your entity manager
        //entityManager.addEntities(terrainObj); removing lili pad for now
        
		entityManager.addEntities(player1);
		entityManager.addEntities(object1);
		entityManager.addEntities(object2);
    }


    @Override // Similar to Render() in GameMaster
    protected void draw(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        // Grid scrolling
        updateGridScroll(delta);
        drawGrid(); 

        // Draw Scrolling background
        // scrollingBackground.draw(batch, delta);

        // Draw and update entities
        entityManager.draw(batch);
        entityManager.movement();

        // Check collisions
        collisionManager.checkCollisions(entityManager.entityList);
    }

    // Grid scrolling functions
    // Reposition the grid’s scrolling offset based on scrollSpeed and delta time
    private void updateGridScroll(float delta) {
    	
    	//float screenHeight = Gdx.graphics.getHeight();
        //float cellHeight = screenHeight / 8f;  // Adjusted dynamically
        float cellHeight = viewport.getWorldHeight() / 12f; // Adjusted dynamically

        scrollOffset -= scrollSpeed * delta;

        // For a typical grid of 8 cells
        //float cellHeight = Gdx.graphics.getHeight() / 8f;
        if (scrollOffset <= -cellHeight) {
            scrollOffset = 0;
        }
        
        
    }

    // Draw an 8x8 grid that scrolls upward
    private void drawGrid() {
        //float screenWidth  = Gdx.graphics.getWidth();
        //float screenHeight = Gdx.graphics.getHeight();
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

        // Draw horizontal lines (scrolling)
        for (int i = -1; i <= 12; i++) {
            float y = i * cellHeight + scrollOffset;
            shapeRenderer.line(0, y, screenWidth, y);
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        //Recalculate grid cell size based on new dimensions
        /*
        float screenWidth = width;
        float screenHeight = height;
        float cellWidth = screenWidth / 8f;
        float cellHeight = screenHeight / 8f;
        */
        
        viewport.update(width, height, true); // Updates viewport and centers camera
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
        // if (entityManager != null) {
        //     entityManager.dispose();
        // }
        if (scrollingBackground != null) {
            scrollingBackground.dispose();
        }


    }
}
