package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Object2 extends Entity implements iMovable, iCollidable{
	
	private float currentyPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private static final float OBJECT_SIZE = 100;
	private static final float SPEED = 33;
	
	Object2(){
		super(400, 200, 50, "car.png", OBJECT_SIZE, OBJECT_SIZE);
		super.setTex("car.png");
		this.shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void movement(){
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentyPos = super.getY();
		currentyPos -= SPEED * deltaTime;
                
        if(currentyPos <= 0) {
        	currentyPos += 300;
        }
        super.setY(currentyPos);

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
	       getTex().dispose();
	       shapeRenderer.dispose();
	      
	}

	

}
