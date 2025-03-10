package com.mygdx.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Object1 extends Entity implements iMovable, iCollidable{
	
	private float currentxPos;
	private float currentyPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
	private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float OBJECT_WIDTH = CELL_WIDTH;
    private static final float OBJECT_HEIGHT = CELL_HEIGHT;
	private static final float SPEED = 33;

	public Object1(){

	}
	
	public Object1(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, OBJECT_WIDTH, OBJECT_HEIGHT);
		this.shapeRenderer = new ShapeRenderer();

	}
	
	   public static ArrayList<Object1> spawnObject1(int numberOfFries, float scrollSpeed) {
	        ArrayList<Object1> object1 = new ArrayList<>();
	        // Track which grid cells have been used so each cell is unique.
	        boolean[][] usedCells = new boolean[GRID_COLS][GRID_ROWS];

	        for (int j = 0; j < numberOfFries; j++) {
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
	            
	            object1.add(new Object1(posX, posY, scrollSpeed, "fries.png", OBJECT_WIDTH, OBJECT_HEIGHT));
	        }
	        return object1;
	    }
	
	   
	@Override
	public void movement(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentxPos = super.getX();
        //currentxPos -= SPEED * deltaTime;
                
        currentyPos -= PlayScene.getScrollSpeed() * deltaTime; // Move down with background

        if (currentyPos <= -OBJECT_HEIGHT) {
            currentyPos = Gdx.graphics.getHeight(); // Reset to top
        }
        super.setY(currentyPos);

	}

	
	
	@Override
	public void draw(SpriteBatch batch) {
		if(getRemovalBoolean() == true) {
			return;
		}
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
		if (object instanceof Entity && this.getRectangle().width == 0 && this.getRectangle().height == 0) {
            return this.getRectangle().overlaps(((Entity) object).getRectangle());
        }
		else {
			return false;
		}
	}
	
	@Override 
	public void onCollision(iCollidable object) {
		// for any class specific collision
		//System.out.println("Collided with moving object!");
		getRectangle().setSize(0, 0);
		getRectangle().setPosition(-1000, -1000);
		setRemovalBoolean();
	}
	
	
    public void dispose(){
    	if (getTex() != null) {
            getTex().dispose();
        }
//        if (shapeRenderer != null) {
//            shapeRenderer.dispose();
//        }
    }

	
}
