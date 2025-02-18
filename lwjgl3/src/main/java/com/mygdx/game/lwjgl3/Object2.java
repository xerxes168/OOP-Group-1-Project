package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;
	
public class Object2 extends Entity implements iMovable, iCollidable{
	
	private float currentyPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private static final float OBJECT_SIZE = 50;
	private static final float SPEED = 33;
	private static final Random random = new Random();
	
	public Object2() 
	{
		super();
	}
	
	
	public Object2(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, width, height);
		this.shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void movement(){
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentyPos = super.getY();
		float dynamicSpeed = PlayScene.getScrollSpeed();
		currentyPos -= dynamicSpeed * deltaTime;
                
        if(currentyPos <= -OBJECT_SIZE) {
        	currentyPos = Gdx.graphics.getHeight();
        	respawn();
        }
       
        super.setY(currentyPos);

	}
	
	 private void respawn() {
	        float screenWidth = Gdx.graphics.getWidth();
	        float randomX = random.nextFloat() * (screenWidth - OBJECT_SIZE); // Ensure it stays within bounds
	        super.setX(randomX);
	        super.setY(Gdx.graphics.getHeight()); // Reset Y to top of the screen
	    }
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
			batch.draw(this.getTex(),this.getX(),this.getY(), OBJECT_SIZE, OBJECT_SIZE);
		batch.end();
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), OBJECT_SIZE, OBJECT_SIZE);
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
		System.out.println("Collided with static object!");
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
