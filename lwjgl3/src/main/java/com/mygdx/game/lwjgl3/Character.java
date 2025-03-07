package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Character extends Entity implements iMovable, iCollidable {
	
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private SoundManager soundManager;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float CHARACTER_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CHARACTER_HEIGHT = Gdx.graphics.getWidth() / 12f;
    private static final float SPEED = 0;
    private static float maxHealth = 100;
	private float currentHealth = 100;
	protected float points;
	private float highScore;
    
 	// Default constructor
    public Character() {
        super(); // Calls default Entity constructor
        this.shapeRenderer = new ShapeRenderer();
    }
	
	
    public Character(float x, float y, float speed, String imgName, float width, float height, SoundManager soundManager) {
		super(x, y, speed, imgName, width, height);
		this.soundManager = soundManager;
		this.shapeRenderer = new ShapeRenderer();
	}
	
	public void draw(SpriteBatch batch) {

		    // Adjust X position slightly to center the duck
		    float offsetX = (CELL_WIDTH - CHARACTER_WIDTH) / 2f;
		    float offsetY = (CELL_HEIGHT - CHARACTER_HEIGHT) / 2f;
		    float barWidth = CHARACTER_WIDTH;
		    float barHeight = 8;
		    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		    
		    // Drawing of character
		    batch.begin();
		    batch.draw(this.getTex(), super.getX() + offsetX + 2, super.getY() + offsetY, CHARACTER_WIDTH, CHARACTER_HEIGHT); 
		    batch.end();
		    
		    // Drawing of collision rectangle
		    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(super.getX() + offsetX + 2, super.getY() + offsetY, CHARACTER_WIDTH, CHARACTER_HEIGHT);
	        shapeRenderer.end();
	        
	        // Drawing of health bar
	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	        shapeRenderer.setColor(Color.GRAY);
	        shapeRenderer.rect(getX(), getY() + 50, barWidth, barHeight);
	        shapeRenderer.setColor(Color.RED.lerp(Color.GREEN, currentHealth / maxHealth));
	        shapeRenderer.rect(getX(), getY() + 50, (currentHealth/maxHealth) * barWidth, barHeight);
	        shapeRenderer.end();
	        
	        points = points + 0.1f;
	        setRectangle();
	        
	       
	}

	
	@Override
	public void movement() {

	    // Calculate current grid position
	    int gridX = Math.round(super.getX() / CELL_WIDTH);
	    int gridY = Math.round(super.getY() / CELL_HEIGHT);

	    boolean moved = false;

	    // Handle grid-based input movement
	    if (Gdx.input.isKeyJustPressed(Keys.UP)) {
	        gridY++;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        gridY--;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
	        gridX--;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
	        gridX++;
	        moved = true;
	    }

	    if (moved) {
	        soundManager.playSound("move");
	    }

	    // Keep the character within the grid boundaries
	    gridX = Math.max(0, Math.min(gridX, 11));
	    gridY = Math.max(0, Math.min(gridY, 11));

	    float maxWidth = Gdx.graphics.getWidth() - CHARACTER_WIDTH;
	    float maxHeight = Gdx.graphics.getHeight() - CHARACTER_HEIGHT;

	    // Calculate the target grid position based on input
	    float targetX = Math.min(Math.round(gridX * CELL_WIDTH), maxWidth);
	    float targetY = Math.min(Math.round(gridY * CELL_HEIGHT), maxHeight);

	    // Apply continuous downward movement to the current position
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    float downwardSpeed = 33f;
	    float newY = super.getY() - (downwardSpeed * deltaTime);

	    // Use the grid-based Y position when input is detected, otherwise use current position with downward movement
	    if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        newY = targetY;
	    }

	    // Set final positions
	    super.setX(targetX);
	    super.setY(newY);
	    
	 // Check if character has fallen below screen by more than one cell height
	    if (super.getY() < - CELL_HEIGHT) {
	    	System.out.println("You fell below the screen!");
			soundManager.playSound("collision");
	        SceneManager.getInstance().setScene("GameOver");
	    }
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
			currentHealth = currentHealth - 1;
			if(currentHealth <= 0) {
				SceneManager.getInstance().setScene("GameOver");
			}
			//((Object1) object).dispose();

		}
		else if(object instanceof Object2) {
			System.out.println("Collided with object 2");
			soundManager.playSound("collision");
			points = points + 1;
			if(points > highScore) {
				// update highscore
			}
			//((Object2) object).dispose();

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
