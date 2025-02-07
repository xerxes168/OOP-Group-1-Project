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
	private EntityManager entityManager;
	
	@Override
	public void create () {
		entityManager = new EntityManager();
		batch = new SpriteBatch();
		
		//tesst
		
		
		player1 = new Character();
		entityManager.addEntities(player1);
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);	

		entityManager.draw(batch);
		entityManager.movement();

			
	}
	
	@Override
	public void dispose() {
		batch.dispose();

		
	}
}

