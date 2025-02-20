package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Character extends Entity implements iMovable, iCollidable {
	
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private SoundManager soundManager;
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float SPEED = PlayScene.getScrollSpeed();
    private static final float CHARACTER_SIZE = Gdx.graphics.getWidth() / 12f;
    private int baseGridX = 5; // Initialize to a starting grid cell (for example, 5)
    private int baseGridY = 5; // Adjust as needed for your starting position.
    private float dropOffset = 0;
        
 // Default constructor with no predefined values
    public Character() {
        super(); // Calls the default Entity constructor
        this.shapeRenderer = new ShapeRenderer();
    }
	
	
    public Character(float x, float y, float speed, String imgName, float width, float height, SoundManager soundManager) {
		super(x, y, speed, imgName, width, height);
		this.soundManager = soundManager;
		this.shapeRenderer = new ShapeRenderer();
	}
	
	public void draw(SpriteBatch batch) {
		    // Adjust X position slightly to center the duck
		    float offsetX = (CELL_WIDTH - CHARACTER_SIZE) / 2f;
		    float offsetY = (CELL_HEIGHT - CHARACTER_SIZE) / 2f;

		    batch.begin();
		    batch.draw(this.getTex(), super.getX() + offsetX + 2, super.getY() + offsetY, CHARACTER_SIZE, CHARACTER_SIZE); 
		    batch.end();
		    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
	        shapeRenderer.end();

		    setRectangle();
	}
	
	@Override
	public void movement() {
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    boolean moved = false;
	    
	    // Process key input: update the base grid coordinates and reset drop offset.
	    if (Gdx.input.isKeyJustPressed(Keys.UP)) {
	        baseGridY = Math.min(baseGridY + 1, GRID_ROWS - 1);
	        dropOffset = 0;
	        moved = true;
	        soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        baseGridY = Math.max(baseGridY - 1, 0);
	        dropOffset = 0;
	        moved = true;
	        soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
	        baseGridX = Math.max(baseGridX - 1, 0);
	        dropOffset = 0;
	        moved = true;
	        soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
	        baseGridX = Math.min(baseGridX + 1, GRID_COLS - 1);
	        dropOffset = 0;
	        moved = true;
	        soundManager.playSound("move");
	    }
	    
	    // Compute the base (centered) position for the grid cell from the stored base grid coordinates.
	    float baseX = baseGridX * CELL_WIDTH + (CELL_WIDTH / 2f) - (CHARACTER_SIZE / 2f);
	    float baseY = baseGridY * CELL_HEIGHT + (CELL_HEIGHT / 2f) - (CHARACTER_SIZE / 2f);
	    
	    // If no key input occurred, accumulate the drop offset.
	    if (!moved) {
	        dropOffset += SPEED * deltaTime;
	    }
	    
	    // Compute the final position by applying the drop offset to the base Y position.
	    float finalX = baseX;
	    float finalY = baseY - dropOffset;
	    
	    // Update the character's position.
	    super.setX(finalX);
	    super.setY(finalY);
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
		if(object instanceof Entity) {
			return this.getRectangle().overlaps(((Entity) object).getRectangle());
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void onCollision(iCollidable object) {
		// To be improved
		
		// Collision with other objects
		if(object instanceof Object1) {
			System.out.println("Collided with object 1");
			soundManager.playSound("collision");
			SceneManager.getInstance().setScene("GameOver");
		}
		else if(object instanceof Object2) {
			System.out.println("Collided with object 2");
			soundManager.playSound("collision");
			SceneManager.getInstance().setScene("GameOver");
		}
	}

	public void setPosition(float x, float y) {
		
		super.setX(x);  // Use Entity setX()
	    super.setY(y);  // Use Entity setY()
		
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		soundManager.dispose();
	}

	
	
}
