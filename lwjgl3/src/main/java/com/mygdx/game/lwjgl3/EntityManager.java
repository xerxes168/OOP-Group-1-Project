package com.mygdx.game.lwjgl3;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EntityManager {
	
	private List<Entity> entityList;
	
	// Default Constructor
	public EntityManager() {
		entityList = new ArrayList<>();
	}
	
	void addEntities(Entity entityName) {
		entityList.add(entityName);
		System.out.println("" + entityList);
	}
	
    // Draw all entities
    public void draw(SpriteBatch batch) {
        for (Entity entity : entityList) {
            entity.draw(batch);
        }
    }


    // Movement for all entities
    public void movement() {
        for (Entity entity : entityList) {
            entity.movement(); 
        }
    }
	
	
	
}

