package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Terrain extends Entity implements iMovable, iCollidable{
    private static final float TERRAIN_SIZE = Gdx.graphics.getWidth() / 12f;
	private float currentxPos;
	private float currentyPos;
    private static final float SPEED = 33;
	
    public Terrain() {
		super();
		super.setTex("lily.png");
//		
//        // Generate a random X coordinate from 0 to (screen width - terrain size)
//        float randomX = MathUtils.random(0, Gdx.graphics.getWidth() - TERRAIN_SIZE);
//        // Generate a random Y coordinate in the top half of the screen
//        float randomY = MathUtils.random(Gdx.graphics.getHeight() / 2f, Gdx.graphics.getHeight());
//        
//        // Set the initial position for the terrain object
//        super.setX(randomX);
//        super.setY(randomY);
//        
//        // Update current positions as well
//        currentxPos = randomX;
//        currentyPos = randomY;
    }
	
	public Terrain(float x, float y, float speed, String imgName, float width, float height) {
		super(x, y, speed, imgName, width, height);
	}
	
	public boolean isOverlapping(Terrain other) {
	    return this.getX() < other.getX() + other.getWidth() &&
	           this.getX() + this.getWidth() > other.getX() &&
	           this.getY() < other.getY() + other.getHeight() &&
	           this.getY() + this.getHeight() > other.getY();
	}
	
	@Override
	public void movement(){
		
		float deltaTime = Gdx.graphics.getDeltaTime();
        super.setX(currentxPos);
		currentyPos = super.getY();
		currentyPos -= SPEED * deltaTime;
                
		if(currentyPos <= -TERRAIN_SIZE) {
        	currentyPos = Gdx.graphics.getHeight();
            // Randomize the x-coordinate on reset.
            currentxPos = MathUtils.random(0, Gdx.graphics.getWidth() - TERRAIN_SIZE);
            super.setX(currentxPos);
        }
        super.setY(currentyPos);

	}
	
	public void draw(SpriteBatch batch) {
		 float screenWidth = Gdx.graphics.getWidth();
		    float screenHeight = Gdx.graphics.getHeight();
		    float cellWidth = screenWidth / 12f;
		    float cellHeight = screenHeight / 12f;

		    // Adjust X position slightly to center the object
		    float offsetX = (cellWidth - TERRAIN_SIZE) / 2f;
		    float offsetY = (cellHeight - TERRAIN_SIZE) / 2f;
		    
//			float randomX = MathUtils.random(0,Gdx.graphics.getWidth() - 64); //64pxiels being the width of the drops
//			float randomY = MathUtils.random(Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight());

		    batch.begin();
		    batch.draw(this.getTex(), getX() + offsetX, getY() + offsetY, TERRAIN_SIZE, TERRAIN_SIZE); 
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
