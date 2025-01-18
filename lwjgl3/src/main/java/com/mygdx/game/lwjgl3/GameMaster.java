package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameMaster extends ApplicationAdapter {
	
	private Texture bucketImage;
	private SpriteBatch batch;
	//hello test test
	
	@Override
	public void create ()
	{
		bucketImage = new Texture(Gdx.files.internal("testbuck.png"));
		batch = new SpriteBatch();	
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		batch.begin();

			batch.draw(bucketImage, 150, 150, 50, 50);
			
		batch.end();

			
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		bucketImage.dispose();
		
	}
}

