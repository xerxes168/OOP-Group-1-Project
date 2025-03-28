package com.mygdx.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Terrain extends Entity implements iMovable, iCollidable{
    private static final int GRID_COLS = 12;
    private static final int GRID_ROWS = 12;
    private static final float TERRAIN_WIDTH = CELL_WIDTH;
    private static final float TERRAIN_HEIGHT = CELL_HEIGHT;
	private float currentxPos;
	private float currentyPos;

    public Terrain() {
		super();
		super.setTex("tree.png");
    }

	public Terrain(float x, float y, float speed, String imgName, float width, float height) {
		super(x, y, speed, imgName, width, height);
        currentxPos = x;
        currentyPos = y;
	}


    // Spawning terrain without overlap
	public static ArrayList<Terrain> spawnTerrains(int numberOfTerrain, float scrollSpeed) {
		ArrayList<Terrain> terrains = new ArrayList<>();
		boolean[][] usedCells = new boolean[GRID_COLS][GRID_ROWS];

		for (int j = 0; j < numberOfTerrain; j++) {
			int col, row;
			do {
				col = MathUtils.random(0, GRID_COLS - 1);
				row = MathUtils.random(0, GRID_ROWS - 1);
			} while (usedCells[col][row]);

			usedCells[col][row] = true;

			// Snap terrain to the exact grid position
			float posX = col * CELL_WIDTH;
			float posY = row * CELL_HEIGHT;

			System.out.println("Terrain added at (" + posX + "," + posY + ")");

			terrains.add(new Terrain(posX, posY, scrollSpeed, "tree.png", TERRAIN_WIDTH, TERRAIN_HEIGHT));
		}
		return terrains;
	}

	@Override
	public void movement() {
	    float deltaTime = Gdx.graphics.getDeltaTime();

	    // Update the y position by subtracting the movement amount.
	    currentyPos = super.getY() - getSpeed() * deltaTime;

	    // Check if the lily has moved off the bottom of the screen.
	    if (currentyPos <= -TERRAIN_HEIGHT) {
	        // Reset y to the top of the screen.
	    	reset();
	    } else {
            // Otherwise, just update the y position.
            super.setY(currentyPos);
        }
    }

	private void reset() {
		// Reset y to the top.
		currentyPos = Gdx.graphics.getHeight();

		// Choose a random column.
		int col = MathUtils.random(0, GRID_COLS - 1);

		// Calculate the centered x position for that column.
		currentxPos = Math.round((col * CELL_WIDTH) / CELL_WIDTH) * CELL_WIDTH;

		// Update the entity's position.
		super.setX(currentxPos);
		super.setY(currentyPos);
	}

	public void draw(SpriteBatch batch) {
	    batch.begin();
	    batch.draw(this.getTex(), getX(), getY(), TERRAIN_WIDTH, TERRAIN_HEIGHT);
	    batch.end();
        setRectangle();

	}

	@Override
	public boolean isCollided(iCollidable object) {
	    if (object instanceof Entity) {
	        Entity other = (Entity) object;

	        // Skip objects that are too far away (optimization)
	        float dx = Math.abs(this.getX() - other.getX());
	        float dy = Math.abs(this.getY() - other.getY());
	        if (dx > CELL_WIDTH || dy > CELL_HEIGHT) {
	            return false;
	        }

	        // Get the bounds of both objects
	        float thisLeft = this.getX();
	        float thisRight = this.getX() + TERRAIN_WIDTH;
	        float thisTop = this.getY() + TERRAIN_HEIGHT;
	        float thisBottom = this.getY();

	        float otherLeft = other.getX();
	        float otherRight = other.getX() + other.getWidth();
	        float otherTop = other.getY() + other.getHeight();
	        float otherBottom = other.getY();

	        // Check for overlap in both X and Y axes
			boolean overlapX = (thisLeft + 2 < otherRight) && (thisRight - 2 > otherLeft);
			boolean overlapY = (thisBottom + 2 < otherTop) && (thisTop - 2 > otherBottom);

			// Calculate overlap amount for debugging
	        if (overlapX && overlapY) {
	            float overlapXAmount = Math.min(thisRight - otherLeft, otherRight - thisLeft);
	            float overlapYAmount = Math.min(thisTop - otherBottom, otherTop - thisBottom);

	            // Only count as collision if overlap is significant (prevents invisible collisions)
	            if (overlapXAmount > 5 && overlapYAmount > 5) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	@Override
	public void onCollision(iCollidable object) {

	}

	public void dispose(){

		if (getTex() != null) {
            getTex().dispose();
        }

	}


}
