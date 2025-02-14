package com.mygdx.game.lwjgl3;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character extends Entity implements iMovable, iCollidable {
	private float currentxPos;
	private float currentyPos;
	
    private static final float CHARACTER_SIZE = Gdx.graphics.getWidth() / 12f;
    private static final float SPEED = 200;
    //private static final float SCREEN_WIDTH = 640; // NEED TO CHANGE
    //private static final float SCREEN_HEIGHT = 480; // NEED TO CHANGE
	
	Character() {
		super(280, 0, 1, "character.png", CHARACTER_SIZE, CHARACTER_SIZE);
		super.setTex("character.png");
	}
	
	Character(String imgName,float x, float y, float speed) {
		super.setX(x);
		super.setY(y);
		super.setSpeed(speed);
		super.setTex(imgName);
	}
	
	public void draw(SpriteBatch batch) {
		 float screenWidth = Gdx.graphics.getWidth();
		    float screenHeight = Gdx.graphics.getHeight();
		    float cellWidth = screenWidth / 8f;
		    float cellHeight = screenHeight / 8f;

		    // Adjust X position slightly to center the duck
		    float offsetX = (cellWidth - CHARACTER_SIZE) / 2f;
		    float offsetY = (cellHeight - CHARACTER_SIZE) / 2f;

		    batch.begin();
		    batch.draw(this.getTex(), super.getX() + offsetX + 2, super.getY() + offsetY, CHARACTER_SIZE, CHARACTER_SIZE); 
		    batch.end();
		    setRectangle();

	}
	
	@Override
	public void movement() {
		// Get screen dimensions dynamically
		float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();

	    float cellWidth = screenWidth / 8f;
	    float cellHeight = screenHeight / 8f;

	    int gridX = Math.round(super.getX() / cellWidth);
	    int gridY = Math.round(super.getY() / cellHeight);

	    if (Gdx.input.isKeyJustPressed(Keys.UP)) gridY++;
	    if (Gdx.input.isKeyJustPressed(Keys.DOWN)) gridY--;
	    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) gridX--;
	    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) gridX++;

	    gridX = Math.max(0, Math.min(gridX, 7));
	    gridY = Math.max(0, Math.min(gridY, 7));

	    float maxWidth = screenWidth - CHARACTER_SIZE;
	    float maxHeight = screenHeight - CHARACTER_SIZE;

	    super.setX(Math.min(Math.round(gridX * cellWidth), maxWidth));
	    super.setY(Math.min(Math.round(gridY * cellHeight), maxHeight));
		
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
		if(object instanceof Entity) {
			return this.getRectangle().overlaps(((Entity) object).getRectangle());
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void onCollision(iCollidable object) {
		// To be written
		
		// Collision with other objects
		if(object instanceof Object1) {
			System.out.println("Collided with object 1");
		}
		else if(object instanceof Object2) {
			System.out.println("Collided with object 2");
		}
	}

	
	
}
