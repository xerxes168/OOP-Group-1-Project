package com.mygdx.game.lwjgl3;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Character extends Entity implements iMovable, iCollidable {
	
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private SoundManager soundManager;
	private Game game;
	
    private static final float CHARACTER_SIZE = Gdx.graphics.getWidth() / 12f;
    private static final float SPEED = 200;
    
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
		 float screenWidth = Gdx.graphics.getWidth();
		    float screenHeight = Gdx.graphics.getHeight();
		    float cellWidth = screenWidth / 12f;
		    float cellHeight = screenHeight / 12f;

		    // Adjust X position slightly to center the duck
		    float offsetX = (cellWidth - CHARACTER_SIZE) / 2f;
		    float offsetY = (cellHeight - CHARACTER_SIZE) / 2f;

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
	    float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();
	    float cellWidth = screenWidth / 12f;
	    float cellHeight = screenHeight / 12f;

	    // Calculate current grid position
	    int gridX = Math.round(super.getX() / cellWidth);
	    int gridY = Math.round(super.getY() / cellHeight);

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

	    float maxWidth = screenWidth - CHARACTER_SIZE;
	    float maxHeight = screenHeight - CHARACTER_SIZE;

	    // Calculate the target grid position based on input
	    float targetX = Math.min(Math.round(gridX * cellWidth), maxWidth);
	    float targetY = Math.min(Math.round(gridY * cellHeight), maxHeight);

	    // Apply continuous downward movement to the current position
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    float downwardSpeed = 33f;
	    float newY = super.getY() - (downwardSpeed * deltaTime);

	    // Use the grid-based Y position when input is detected, otherwise use current position with downward movement
	    if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        newY = targetY;
	    }

	    // Ensure character doesn't go below bottom of screen
	    newY = Math.max(0, newY);

	    // Set final positions
	    super.setX(targetX);
	    super.setY(newY);
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
