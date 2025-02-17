package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Object1 extends Entity implements iMovable, iCollidable{
	
	private float currentxPos;
	private float currentyPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private static final float OBJECT_SIZE = 100;
	private static final float SPEED = 33;
		
	Object1(){
		super(400, 0, 50, "car1.png", OBJECT_SIZE, OBJECT_SIZE);
		super.setTex("car1.png");
		this.shapeRenderer = new ShapeRenderer();
	}
	
	Object1(float x, float y, float speed, String imgName, float width, float height){
		super.setX(x);
		super.setY(y);
		super.setSpeed(speed);
		super.setTex(imgName);
	}
	
	@Override
	public void movement(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentxPos = super.getX();
        currentxPos -= SPEED * deltaTime;
                
        if(currentxPos <= 0) {
        	currentxPos += 400;
        }
        super.setX(currentxPos);
        
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
		System.out.println("Collided with moving object!");
	}
	
	
    public void dispose(){
       getTex().dispose();
       shapeRenderer.dispose();
      
    }

	
}
