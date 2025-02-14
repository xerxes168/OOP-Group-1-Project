package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Terrain extends Entity {
    private static final float TERRAIN_OBJECT_SIZE = Gdx.graphics.getWidth() / 12f;
	
	Terrain() {
		super();
		super.setTex("testbuck.png");
	}
	
	Terrain(String imgName,float x, float y, float speed) {
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
		    float offsetX = (cellWidth - TERRAIN_OBJECT_SIZE) / 2f;
		    float offsetY = (cellHeight - TERRAIN_OBJECT_SIZE) / 2f;

		    batch.begin();
		    batch.draw(this.getTex(), super.getX() + offsetX + 2, super.getY() + offsetY, TERRAIN_OBJECT_SIZE, TERRAIN_OBJECT_SIZE); 
		    batch.end();
	}
	
	
}
