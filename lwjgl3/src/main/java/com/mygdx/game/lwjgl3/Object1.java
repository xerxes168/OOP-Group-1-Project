package com.mygdx.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Object1 extends Entity implements iMovable, iCollidable{

	private float currentxPos;
	private float currentyPos;
    private static final int GRID_COLS = 12;
    private static final int OBJECT_WIDTH = CELL_WIDTH;
    private static final int OBJECT_HEIGHT = CELL_HEIGHT;
	private static int lastCol = -1;

	public Object1(){

	}

	public Object1(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, OBJECT_WIDTH, OBJECT_HEIGHT);
	}

	public static ArrayList<Object1> spawnObject1(int numberOfFries, float scrollSpeed) {
	    ArrayList<Object1> object1List = new ArrayList<>();

	    for (int i = 0; i < numberOfFries; i++) {
	        int col;
	        do {
	            col = MathUtils.random(0, GRID_COLS - 1);

	        // Avoid spawning Object in the same column
	        } while (col == lastCol);
	        lastCol = col;

	        int posX = col * CELL_WIDTH;
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
		if(getRemovalBoolean()) {
			return;
		}
		batch.begin();
			batch.draw(this.getTex(),this.getX(),this.getY(), OBJECT_WIDTH, OBJECT_HEIGHT);
		batch.end();

        setRectangle();

	}

	@Override
	public boolean isCollided(iCollidable object) {
	    if (object instanceof Entity) {
	        // Use your bounding boxes (LibGDX Rectangle objects)
	        return this.getRectangle().overlaps(((Entity) object).getRectangle());
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
    }


}
