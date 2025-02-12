package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Character player1;
	private EntityManager entityManager;
	private EntityManager test;
	private ShapeRenderer shapeRenderer;
	
	//temporary grid for development
	private void drawGrid() {
	    float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();
	    float cellWidth = screenWidth / 8f;
	    float cellHeight = screenHeight / 8f;

	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.setColor(1, 1, 1, 1); // White grid lines

	    // Draw vertical lines
	    for (int i = 0; i <= 8; i++) {
	        float x = i * cellWidth;
	        shapeRenderer.line(x, 0, x, screenHeight);
	    }

	    // Draw horizontal lines
	    for (int i = 0; i <= 8; i++) {
	        float y = i * cellHeight;
	        shapeRenderer.line(0, y, screenWidth, y);
	    }

	    shapeRenderer.end();
	}

	@Override
	public void create () {
		entityManager = new EntityManager();
		batch = new SpriteBatch();
	    shapeRenderer = new ShapeRenderer(); // Initialize the shape renderer
		
	
		player1 = new Character();
		entityManager.addEntities(player1);
	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		drawGrid(); // Draw the grid

		entityManager.draw(batch);
		entityManager.movement();

			
	}
	
	@Override
	public void dispose() {
		batch.dispose();

		
	}
}

