package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Object1 extends Entity implements iMovable, iCollidable{
	
	private float currentxPos;
	private float currentyPos;
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float OBJECT_WIDTH = CELL_WIDTH;
    private static final float OBJECT_HEIGHT = CELL_HEIGHT;
	private static final float SPEED = 33;

	public Object1(){

	}
	
	public Object1(float x, float y, float speed, String imgName, float width, float height){
		super(x, y, speed, imgName, OBJECT_WIDTH, OBJECT_HEIGHT);
		this.shapeRenderer = new ShapeRenderer();

	}
	
	@Override
	public void movement(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		currentxPos = super.getX();
        currentxPos -= SPEED * deltaTime;
                
        if(currentxPos <= -OBJECT_WIDTH) {
        	currentxPos = Gdx.graphics.getWidth();;
        }
        super.setX(currentxPos);
        
        currentyPos = super.getY();
		currentyPos -= SPEED * deltaTime;
                
		if(currentyPos <= -OBJECT_HEIGHT) {
        	currentyPos = Gdx.graphics.getHeight();
        }
        super.setY(currentyPos);

	}

	
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
			batch.draw(this.getTex(),this.getX(),this.getY(), OBJECT_WIDTH, OBJECT_HEIGHT);		
		batch.end();
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        shapeRenderer.setColor(1, 0, 0, 1); // Red color
	        shapeRenderer.rect(this.getX(), this.getY(), OBJECT_WIDTH, OBJECT_HEIGHT);
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
		//System.out.println("Collided with moving object!");
	}
	
	
    public void dispose(){
       getTex().dispose();
       shapeRenderer.dispose();
      
    }

	
}
