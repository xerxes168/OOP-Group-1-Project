package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	private float xPosition;
	private float yPosition;
	private float speed;
	private Texture tex;
	
	Entity() {
		xPosition = 280;
		yPosition = 0; 
		speed = 1;
		tex = new Texture(Gdx.files.internal("testbuck.png"));
	}
	
	Entity(float x, float y, float speed, String imgName) {
		xPosition = x;
		yPosition = y; 
		this.speed = speed;
		tex = new Texture(Gdx.files.internal(imgName));
	}
	
	public float getX() {
		return xPosition;
	}
	
	public void setX(float x) {
		xPosition = x;
	}

	public float getY()	{
		return yPosition;
	}

	public void setY(float y) {
		yPosition = y;
    }
	    

	public float getSpeed()	{
		return speed;
	}

	public void setSpeed(float newSpeed) {
		speed = newSpeed;
	}
	
	public Texture getTex() {
		return tex;
	}
	
	public void setTex(String imgName) {
		tex = new Texture(Gdx.files.internal(imgName));
	}
	
    public void draw(SpriteBatch batch) {
    	// Child Class will override
    }

    public void movement() {	
    	// Child Class will override
    }
	
}
