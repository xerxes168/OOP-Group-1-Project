package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Character player1;
	private Object1 object1;
	private EntityManager entityManager;
	
	@Override
	public void create () {
		entityManager = new EntityManager();
		batch = new SpriteBatch();
		
		//test
		
		
		player1 = new Character();
		object1 = new Object1();
		//object2 = new Object2();
		entityManager.addEntities(player1);
		entityManager.addEntities(object1);
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);	

		entityManager.draw(batch);
		entityManager.movement();
		if(player1.isCollided(object1)) {
			player1.onCollision(object1);
		}

			
	}
	
	@Override
	public void dispose() {
		batch.dispose();

		
	}
}

