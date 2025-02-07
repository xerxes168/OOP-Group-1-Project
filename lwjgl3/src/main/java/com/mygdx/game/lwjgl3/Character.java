package com.mygdx.game.lwjgl3;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character extends Entity implements iMovable, iCollidable {
	private float currentxPos;
	private float currentyPos;
	
    private static final float CHARACTER_SIZE = 50;
    private static final float SPEED = 200;
    private static final float SCREEN_WIDTH = 640; // NEED TO CHANGE
    private static final float SCREEN_HEIGHT = 480; // NEED TO CHANGE
	
	Character() {
		super();
		super.setTex("character.png");
	}
	
	Character(String imgName,float x, float y, float speed) {
		super.setX(x);
		super.setY(y);
		super.setSpeed(speed);
		super.setTex(imgName);
	}
	
	public void draw(SpriteBatch batch) {
		batch.begin();

		batch.draw(this.getTex(),this.getX(),this.getY(), CHARACTER_SIZE, CHARACTER_SIZE);
		
		batch.end();
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
		
	}
	
	@Override
	public void collision() {
		// To be written
		
		// Collision with other objects
	}

	
	
}
