package com.mygdx.game.lwjgl3;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character extends Entity implements iMovable, iCollidable {
	private float currentxPos;
	private float currentyPos;
	
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

		batch.draw(this.getTex(),this.getX(),this.getY(), 50, 50);
		
		batch.end();
	}
	
	@Override
	public void movement() {
		currentyPos = super.getY();
		if (Gdx.input.isKeyPressed(Keys.UP)) currentyPos += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.DOWN)) currentyPos -= 200 * Gdx.graphics.getDeltaTime();
		super.setY(currentyPos);
		
		currentxPos = super.getX();
		if (Gdx.input.isKeyPressed(Keys.LEFT)) currentxPos -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) currentxPos += 200 * Gdx.graphics.getDeltaTime();
		super.setX(currentxPos);	
	}
	
	@Override
	public void collision() {
		// To be written
		
		// Collision with Boundaries of screen
	}

	
	
}
