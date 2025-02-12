package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Object1 extends Entity implements iMovable, iCollidable{
	
	private float currentxPos;
	private ShapeRenderer shapeRenderer;
	private static final float CHARACTER_SIZE = 100;
	private static final float SPEED = 10;
	private static final float SCREEN_WIDTH = 640; // NEED TO CHANGE
		
	Object1(){
		super();
		super.setTex("car1.png");
		this.shapeRenderer = new ShapeRenderer();

	}
	
	Object1(float x, float y, float speed, String imgName){
		super.setX(x);
		super.setY(y);
		super.setSpeed(speed);
		super.setTex(imgName);
		this.shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void movement(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentxPos = super.getX();
        currentxPos -= SPEED * deltaTime;
        
        if(currentxPos == SCREEN_WIDTH) {
        	System.out.println("reach end of screen");
        	//GENERATE ANOTHER CAR IN A RANDOM AMOUNT OF TIME AGAIN
        }
        super.setX(currentxPos);

	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();

			batch.draw(this.getTex(),this.getX(),this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
			//batch.draw();
		
		batch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
        shapeRenderer.end();
        setRectangle();

	}

	
	@Override
	public boolean isCollided(iCollidable object) {
		return true;
	}
	
	@Override 
	public void onCollision(iCollidable object) {
		
	}
	
}
