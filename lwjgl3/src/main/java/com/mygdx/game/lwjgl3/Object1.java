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
	private static int lastCol = -1;

	public Object1(){

	}
	
	public Object1(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, OBJECT_WIDTH, OBJECT_HEIGHT);
		this.shapeRenderer = new ShapeRenderer();

	}
	
	public static ArrayList<Object1> spawnObject1(int numberOfFries, float scrollSpeed) {
	    ArrayList<Object1> object1List = new ArrayList<Object1>();

	    for (int i = 0; i < numberOfFries; i++) {
	        int col;
	        do {
	            col = MathUtils.random(0, GRID_COLS - 1);
	            
	        // Avoid spawning Object in the same column
	        } while (col == lastCol);
	        lastCol = col;

	        float posX = col * CELL_WIDTH;
	        float posY = Gdx.graphics.getHeight() + MathUtils.random(0, 200);

	        object1List.add(new Object1(posX, posY, scrollSpeed, "fries.png", OBJECT_WIDTH, OBJECT_HEIGHT));
	    }

	    return object1List;
	}
	
	   
	@Override
	public void movement(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentxPos = super.getX();
		currentyPos = super.getY();
        currentyPos -=  2.5f * getSpeed() * deltaTime; // Move down with background

        if (currentyPos <= -OBJECT_HEIGHT) {
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
	    if (object instanceof Entity) {
	        // Calculate grid position for this Object1
	        int thisGridX = (int)((this.getX() + OBJECT_WIDTH / 2)/ CELL_WIDTH);
	        int thisGridY = (int)((this.getY() + OBJECT_HEIGHT / 2)/ CELL_HEIGHT);

	        // Calculate grid position for the other object
	        int otherGridX = (int)((((Entity)object).getX()  + OBJECT_WIDTH / 2) / CELL_WIDTH);
	        int otherGridY = (int)((((Entity)object).getY()  + OBJECT_HEIGHT / 2) / CELL_HEIGHT);
	        // Return true only if both objects are in the same grid cell
	        return (thisGridX == otherGridX && thisGridY == otherGridY);
	    }
	    return false;
	}
	
	@Override 
	public void onCollision(iCollidable object) {

	    // Only remove Object1 (fries) if the collision is with the Character.
	    if (object instanceof Character) {
	        getRectangle().setSize(0, 0);
	        getRectangle().setPosition(-1000, -1000);
	        setRemovalBoolean();
	    }
	    // Otherwise, do nothing (ignore collisions with terrain, etc.)
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
