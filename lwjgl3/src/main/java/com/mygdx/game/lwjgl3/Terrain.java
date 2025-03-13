package com.mygdx.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Terrain extends Entity implements iMovable, iCollidable{
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float TERRAIN_WIDTH = CELL_WIDTH;
    private static final float TERRAIN_HEIGHT = CELL_HEIGHT;
	private float currentxPos;
	private float currentyPos;
    private static final float SPEED = 33;
    private ShapeRenderer shapeRenderer;
    
	
    public Terrain() {
		super();
		super.setTex("lily.png");
    }
	
	public Terrain(float x, float y, float speed, String imgName, float width, float height) {
		super(x, y, speed, imgName, width, height);
        currentxPos = x;
        currentyPos = y;
        shapeRenderer = new ShapeRenderer();
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
	            float posX = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (TERRAIN_WIDTH / 2f);
	            float posY = row * CELL_HEIGHT + (CELL_HEIGHT / 2f) - (TERRAIN_HEIGHT / 2f);
	            
	            terrains.add(new Terrain(posX, posY, scrollSpeed, "lily.png", TERRAIN_WIDTH, TERRAIN_HEIGHT));
	        }
	        return terrains;
	    }
	        
	    
	@Override
	public void movement() {
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    
	    // Update the y position by subtracting the movement amount.
	    currentyPos = super.getY() - PlayScene.getScrollSpeed() * deltaTime;
	    
	    // Check if the lily has moved off the bottom of the screen.
	    if (currentyPos <= -TERRAIN_HEIGHT) {
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
	        currentxPos = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (TERRAIN_WIDTH / 2f);

	        // Update the entity's position.
	        super.setX(currentxPos);
	        super.setY(currentyPos);
	    }
	
	public void draw(SpriteBatch batch) {
	    batch.begin();
	    batch.draw(this.getTex(), getX(), getY(), TERRAIN_WIDTH, TERRAIN_HEIGHT); 
	    batch.end();
	 // Drawing of collision rectangle
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(super.getX(), super.getY(), TERRAIN_WIDTH, TERRAIN_HEIGHT);
        shapeRenderer.end();
        
        setRectangle();
	
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
	    if (object instanceof Entity) {
	        int thisGridX = (int)((this.getX() + TERRAIN_WIDTH / 2)/ CELL_WIDTH);
	        int thisGridY = (int)((this.getY() + TERRAIN_HEIGHT / 2)/ CELL_HEIGHT);
	        int otherGridX = (int)((((Entity)object).getX()  + TERRAIN_WIDTH / 2) / CELL_WIDTH);
	        int otherGridY = (int)((((Entity)object).getY()  + TERRAIN_HEIGHT / 2) / CELL_HEIGHT);
	        return (thisGridX == otherGridX && thisGridY == otherGridY);
	    }
	    return false;
	}
	
	@Override
	public void onCollision(iCollidable object) {
		
	}
	
	public void dispose(){
		
		if (getTex() != null) {
            getTex().dispose();
        }
	      
	}


}
