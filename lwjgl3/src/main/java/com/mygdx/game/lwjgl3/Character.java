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
    //private static final float SCREEN_WIDTH = 640; // NEED TO CHANGE
    //private static final float SCREEN_HEIGHT = 480; // NEED TO CHANGE
    
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
		// Get screen dimensions dynamically
		float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();

	    float cellWidth = screenWidth / 12f;
	    float cellHeight = screenHeight / 12f;

	    int gridX = Math.round(super.getX() / cellWidth);
	    int gridY = Math.round(super.getY() / cellHeight);

	    if (Gdx.input.isKeyJustPressed(Keys.UP)) {gridY++;
	    	soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {gridY--;
	    	soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {gridX--;
	    	soundManager.playSound("move");
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {gridX++;
	    	soundManager.playSound("move");
	    }

	    gridX = Math.max(0, Math.min(gridX, 11));
	    gridY = Math.max(0, Math.min(gridY, 11));

	    float maxWidth = screenWidth - CHARACTER_SIZE;
	    float maxHeight = screenHeight - CHARACTER_SIZE;
	    
	    

	    super.setX(Math.min(Math.round(gridX * cellWidth), maxWidth));
	    super.setY(Math.min(Math.round(gridY * cellHeight), maxHeight));
		
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
		// To be written
		
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
		
		super.setX(x);  // Use Entity's setX()
	    super.setY(y);  // Use Entity's setY()
		
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		soundManager.dispose();
	}

	
	
}
