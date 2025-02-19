package com.mygdx.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Terrain extends Entity implements iMovable, iCollidable{
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float TERRAIN_SIZE = Gdx.graphics.getWidth() / 12f;
	private float currentxPos;
	private float currentyPos;
    private static final float SPEED = 33;
	
    public Terrain() {
		super();
		super.setTex("lily.png");
    }
	
	public Terrain(float x, float y, float speed, String imgName, float width, float height) {
		super(x, y, speed, imgName, width, height);
        currentxPos = x;
        currentyPos = y;		
	}
	
	public boolean isOverlapping(Terrain other) {
	    return this.getX() < other.getX() + other.getWidth() &&
	           this.getX() + this.getWidth() > other.getX() &&
	           this.getY() < other.getY() + other.getHeight() &&
	           this.getY() + this.getHeight() > other.getY();
	}
	

    // Lily spawning without overlap
	   public static ArrayList<Terrain> spawnTerrains(int numberOfLily, float scrollSpeed) {
	        ArrayList<Terrain> terrains = new ArrayList<>();
	        // Track which grid cells have been used so each cell is unique.
	        boolean[][] usedCells = new boolean[GRID_COLS][GRID_ROWS];

	        for (int j = 0; j < numberOfLily; j++) {
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
	            float posX = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (TERRAIN_SIZE / 2f);
	            float posY = row * CELL_HEIGHT + (CELL_HEIGHT / 2f) - (TERRAIN_SIZE / 2f);
	            
	            terrains.add(new Terrain(posX, posY, scrollSpeed, "lily.png", TERRAIN_SIZE, TERRAIN_SIZE));
	        }
	        return terrains;
	    }
	        
	    
	@Override
	public void movement() {
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    
	    // Update the y position by subtracting the movement amount.
	    currentyPos = super.getY() - SPEED * deltaTime;
	    
	    // Check if the lily has moved off the bottom of the screen.
	    if (currentyPos <= -TERRAIN_SIZE) {
	        // Reset y to the top of the screen.
	        currentyPos = Gdx.graphics.getHeight();
	        
	        // Select a random grid column.
	        int col = MathUtils.random(0, GRID_COLS - 1);
	        
	        // Calculate the centered x position for that column.
	        currentxPos = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (TERRAIN_SIZE / 2f);
	        
	        // Update the x position.
	        super.setX(currentxPos);
	    }
	    
	    // Update the y position.
	    super.setY(currentyPos);
	}
	
	public void draw(SpriteBatch batch) {
	    batch.begin();
	    batch.draw(this.getTex(), getX(), getY(), TERRAIN_SIZE, TERRAIN_SIZE); 
	    batch.end();
	
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
		return false;
	}
	
	@Override
	public void onCollision(iCollidable object) {
		
	}


}
