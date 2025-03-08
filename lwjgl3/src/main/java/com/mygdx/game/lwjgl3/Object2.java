package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;
	
public class Object2 extends Entity implements iMovable, iCollidable{
	private float currentyPos;
	private float currentxPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float OBJECT_WIDTH = CELL_WIDTH;
    private static final float OBJECT_HEIGHT = CELL_HEIGHT;
    private static final float SPEED = 33;
	
	public Object2() 
	{
		super();
	}
	
	
	public Object2(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, width, height);
		this.shapeRenderer = new ShapeRenderer();
        currentxPos = x;
        currentyPos = y;	
	}
	
    public static ArrayList<Object2> spawnObjects(int numberOfObjects, float scrollSpeed) {
    	ArrayList<Object2> objects = new ArrayList<Object2>();
        boolean[][] usedCells = new boolean[GRID_COLS][GRID_ROWS];

        for (int j = 0; j < numberOfObjects; j++) {
            int attempts = 0;
            int col, row;
            // Try to find an unused cell (up to 100 attempts)
            do {
                col = MathUtils.random(0, GRID_COLS - 1);
                row = MathUtils.random(0, GRID_ROWS - 1);
                attempts++;
            } while (usedCells[col][row] && attempts < 100);
            
            // If we exceed attempts, break out of the loop.
            if (attempts >= 100) {
                break;
            }
            
            usedCells[col][row] = true; // Mark this cell as used
            
            // Calculate the center position for the cell, then adjust so the Terrain is centered.
            float posX = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (OBJECT_WIDTH / 2f);
            float posY = row * CELL_HEIGHT + (CELL_HEIGHT / 2f) - (OBJECT_HEIGHT / 2f);
            
            objects.add(new Object2(posX, posY, scrollSpeed, "apple.png", OBJECT_WIDTH, OBJECT_HEIGHT));
        }
        return objects;
    }
	
	@Override
	public void movement(){
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    
	    currentyPos -= PlayScene.getScrollSpeed() * deltaTime; // Move down with background
//	    // Update the y position by subtracting the movement amount.
//	    currentyPos = super.getY();
//		float dynamicSpeed = PlayScene.getScrollSpeed();
//		currentyPos -= dynamicSpeed * deltaTime;
	    // Check if the object has moved off the bottom of the screens.
	    if (currentyPos <= -OBJECT_HEIGHT) {
	        // Reset y to the top of the screen.
	    	reset();
	    } else {
            // Otherwise, just update the y position.
            super.setY(currentyPos);
        }
    }

    private void reset() {
        // Reset y to the top.
        currentyPos = Gdx.graphics.getHeight();

        // Choose a random column.
        int col = MathUtils.random(0, GRID_COLS - 1);

        // Calculate the centered x position for that column.
        currentxPos = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (OBJECT_WIDTH / 2f);

        // Update the entity's position.
        super.setX(currentxPos);
        super.setY(currentyPos);
    }

	
	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
			batch.draw(this.getTex(),this.getX(),this.getY(), OBJECT_WIDTH, OBJECT_HEIGHT);
		batch.end();
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), OBJECT_WIDTH, OBJECT_HEIGHT);
        shapeRenderer.end();
        setRectangle();

	}

	
	@Override
	public boolean isCollided(iCollidable object) {
		
		if (object instanceof Entity) {
            return this.getRectangle().overlaps(((Entity) object).getRectangle());
        }
		else {
			return false;
		}
	}
	
	@Override 
	public void onCollision(iCollidable object) {
		// for any class specific collision
		//System.out.println("Collided with static object!");
		
	}
	
	public void dispose(){
		
		if (getTex() != null) {
            getTex().dispose();
        }
	    if (shapeRenderer != null) {
	    	shapeRenderer.dispose();
	    }
	      
	}

	

}
