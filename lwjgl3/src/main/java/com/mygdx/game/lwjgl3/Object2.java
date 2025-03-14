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
	private static int lastCol = -1;
    
	
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
	
    public static ArrayList<Object2> spawnObject2(int numberOfObjects, float scrollSpeed) {
    	ArrayList<Object2> object2 = new ArrayList<Object2>();
        boolean[][] usedCells = new boolean[GRID_COLS][GRID_ROWS];

        for (int j = 0; j < numberOfObjects; j++) {
            int col, row;
            // Try to find an unused cell (up to 100 attempts)
            do {
                col = MathUtils.random(0, GRID_COLS - 1);
                row = MathUtils.random(0, GRID_ROWS - 1);
            } while (usedCells[col][row]);            
            usedCells[col][row] = true; // Mark this cell as used
            
            // Calculate the center position for the cell, then adjust so the Terrain is centered.
            float posX = col * CELL_WIDTH + (CELL_WIDTH / 2f) - (OBJECT_WIDTH / 2f);
            float posY = row * CELL_HEIGHT + (CELL_HEIGHT / 2f) - (OBJECT_HEIGHT / 2f);
            
            object2.add(new Object2(posX, posY, scrollSpeed, "apple.png", OBJECT_WIDTH, OBJECT_HEIGHT));
        }
        return object2;
    }
    

	
	@Override
	public void movement(){
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    
	    // Normal movement: move downward with background scroll speed.
	    currentyPos = super.getY() - PlayScene.getScrollSpeed() * deltaTime;
	    
	    // If the apple has moved off the bottom of the screen, reset its position.
	    if (currentyPos <= -OBJECT_HEIGHT) {
//	        reset();
            currentyPos = Gdx.graphics.getHeight() + MathUtils.random(0, 200);
            int newCol;
            do {
                newCol = MathUtils.random(0, GRID_COLS - 1);
            } while (newCol == lastCol);
            lastCol = newCol;

            currentxPos = newCol * CELL_WIDTH;

        }
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
//	    if (!isActive) return false;
	    if (object instanceof Entity) {
	        int thisGridX = (int)((this.getX() + OBJECT_WIDTH / 2)/ CELL_WIDTH);
	        int thisGridY = (int)((this.getY() + OBJECT_HEIGHT / 2)/ CELL_HEIGHT);
	        int otherGridX = (int)((((Entity)object).getX()  + OBJECT_WIDTH / 2) / CELL_WIDTH);
	        int otherGridY = (int)((((Entity)object).getY()  + OBJECT_HEIGHT / 2) / CELL_HEIGHT);
	        return (thisGridX == otherGridX && thisGridY == otherGridY);
	    }
	    return false;
	}
	
	@Override 
	public void onCollision(iCollidable object) {
		// for any class specific collision
		//System.out.println("Collided with static object!");

		getRectangle().setPosition(-1000, -1000);
       if(object instanceof Character) {
   		getRectangle().setSize(0, 0);
   		getRectangle().setPosition(-1000, -1000);
        setRemovalBoolean();


        }
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
