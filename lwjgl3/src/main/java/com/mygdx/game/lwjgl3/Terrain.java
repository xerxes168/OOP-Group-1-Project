package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Terrain extends Entity implements iMovable{
    private static final float TERRAIN_OBJECT_SIZE = Gdx.graphics.getWidth() / 12f;
    private float currentyPos;
    private static final float SPEED = 33;
	
	Terrain() {
		super();
		super.setTex("lily.png");
	}
	
	Terrain(float x, float y, float speed, String imgName, float width, float height) {
		super(x, y, speed, imgName, width, height);
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
	
	public void draw(SpriteBatch batch) {
		 float screenWidth = Gdx.graphics.getWidth();
		    float screenHeight = Gdx.graphics.getHeight();
		    float cellWidth = screenWidth / 8f;
		    float cellHeight = screenHeight / 8f;

		    // Adjust X position slightly to center the object
		    float offsetX = (cellWidth - TERRAIN_OBJECT_SIZE) / 2f;
		    float offsetY = (cellHeight - TERRAIN_OBJECT_SIZE) / 2f;
		    
//			float randomX = MathUtils.random(0,Gdx.graphics.getWidth() - 64); //64pxiels being the width of the drops
//			float randomY = MathUtils.random(Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight());

		    batch.begin();
		    batch.draw(this.getTex(), getX() + offsetX + 2, getY() + offsetY, TERRAIN_OBJECT_SIZE, TERRAIN_OBJECT_SIZE); 
		    batch.end();
	}
	
	
}
