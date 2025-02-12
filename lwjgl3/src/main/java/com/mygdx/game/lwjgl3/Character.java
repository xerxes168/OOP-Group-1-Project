package com.mygdx.game.lwjgl3;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Character extends Entity implements iMovable, iCollidable {
	private float currentxPos;
	private float currentyPos;
	private ShapeRenderer shapeRenderer;
	
    private static final float CHARACTER_SIZE = 50;
    private static final float SPEED = 200;
    private static final float SCREEN_WIDTH = 640; // NEED TO CHANGE
    private static final float SCREEN_HEIGHT = 480; // NEED TO CHANGE
	
	Character() {
		super();
		super.setTex("character.png");
		this.shapeRenderer = new ShapeRenderer();
	}
	
	Character(float x, float y, float speed, String imgName) {
		super.setX(x);
		super.setY(y);
		super.setSpeed(speed);
		super.setTex(imgName);
		this.shapeRenderer = new ShapeRenderer();
	}
	
	public void draw(SpriteBatch batch) {
		batch.begin();
			batch.draw(this.getTex(),this.getX(),this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
		batch.end();
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
        shapeRenderer.end();
	}
	
	@Override
	public void movement() {
		float deltaTime = Gdx.graphics.getDeltaTime();
        
        currentyPos = super.getY();
        if (Gdx.input.isKeyPressed(Keys.UP)) currentyPos += SPEED * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) currentyPos -= SPEED * deltaTime;
        
        currentxPos = super.getX();
        if (Gdx.input.isKeyPressed(Keys.LEFT)) currentxPos -= SPEED * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) currentxPos += SPEED * deltaTime;
	
        // Prevent character from leaving the screen bounds
        currentxPos = Math.max(0, Math.min(currentxPos, SCREEN_WIDTH - CHARACTER_SIZE));
        currentyPos = Math.max(0, Math.min(currentyPos, SCREEN_HEIGHT - CHARACTER_SIZE));
        
        super.setX(currentxPos);
        super.setY(currentyPos);	
        
        setRectangle();
		
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
		if(object instanceof Object1) {
			return this.getRectangle().overlaps(((Object1) object).getRectangle());
		}
//		else if (object instanceof Object2) {
//			
//		}
		
		else {
			return false;
		}
		
	}
	
	@Override
	public void onCollision(iCollidable object) {
		// To be written
		
		// Collision with other objects
		if(isCollided(object)) {
			System.out.println("Collided with object 1");
		}
	}
	


	
	
}
