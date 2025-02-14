package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class GameMaster extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Character player1;

	private Object1 object1;
	private Object2 object2;

	private Terrain terrainObj;

	private EntityManager entityManager;
	private EntityManager test;
	private ShapeRenderer shapeRenderer;
	private ScrollingBackground scrollingBackground;
	
	private float scrollOffset = 0; // Offset for scrolling effect
    private float scrollSpeed = 100; // Pixels per second
	
	//temporary grid for development
	private void drawGrid() {
	    float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();
	    float cellWidth = screenWidth / 8f;
	    float cellHeight = screenHeight / 8f;

	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.setColor(1, 1, 1, 1); // White grid lines

	    // Draw vertical lines
	    for (int i = 0; i <= 8; i++) {
	        float x = i * cellWidth;
	        shapeRenderer.line(x, 0, x, screenHeight);
	    }

	    // Draw horizontal lines
	    for (int i = -1; i <= 8; i++) { // Start from -1 to ensure smooth wrapping
            float y = i * cellHeight + scrollOffset;
            shapeRenderer.line(0, y, screenWidth, y);
        }

	    shapeRenderer.end();
	}

	@Override
	public void create () {
		entityManager = new EntityManager();
		batch = new SpriteBatch();
	    shapeRenderer = new ShapeRenderer(); // Initialize the shape renderer
		
	    //if required to add a background image for scrolling instead scrollingBackground = new ScrollingBackground("background.png", 100); // Adjust speed
	
		player1 = new Character();
		object1 = new Object1();
		object2 = new Object2();
		entityManager.addEntities(player1);

		entityManager.addEntities(object1);
		entityManager.addEntities(object2);
		terrainObj = new Terrain();
		entityManager.addEntities(terrainObj);

	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		float delta = Gdx.graphics.getDeltaTime();

        // Update grid scroll position
        scrollOffset -= scrollSpeed * delta;

        // Reset offset when it scrolls past one cell
        float cellHeight = Gdx.graphics.getHeight() / 8f;
        if (scrollOffset <= -cellHeight) {
            scrollOffset = 0;
        }

        drawGrid(); // Draw scrolling grid

        entityManager.draw(batch); // Draw entities
        entityManager.movement(); // Handle movement
        
        if(player1.isCollided(object1)) {
			player1.onCollision(object1);
		}
		else if(player1.isCollided(object2)) {
			player1.onCollision(object2);
		}

		
	
			
	}
	
	@Override
	public void dispose() {
		
		batch.dispose();
		shapeRenderer.dispose();
		scrollingBackground.dispose();

		
	}
}

