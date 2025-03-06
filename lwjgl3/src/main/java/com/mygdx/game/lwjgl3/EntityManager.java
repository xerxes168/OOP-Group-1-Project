package com.mygdx.game.lwjgl3;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EntityManager {
	
	protected List<List<Entity>> allLists;
	protected List<Entity> characterList;
	protected List<Entity> object1List;
	protected List<Entity> object2List;
	protected List<Entity> terrain;
	
	
	// Default Constructor
	public EntityManager() {
		allLists = new ArrayList<>();
		characterList = new ArrayList<>();
		object1List = new ArrayList<>();
		object2List = new ArrayList<>();
		terrain = new ArrayList<>();
		addLists();
	}
	
	public List<List<Entity>> getAllLists() {
		return allLists;
	}
	
	public List<Entity> getAllEntities(){
		List<Entity> allEntities = new ArrayList<>();
		for (List<Entity> list : getAllLists()) {
            allEntities.addAll(list);
        }
		return allEntities;
	}
	
	void addLists() {
//		allLists.add(entityList);
		allLists.add(characterList);
		allLists.add(object1List);
		allLists.add(object2List);
		allLists.add(terrain);
	}
	
//	void addEntities(Entity entityName) {
//		entityList.add(entityName);
//		System.out.println("" + entityList);
//	}
	void addCharacters(Entity entityName) {
		characterList.add(entityName);
	}
	void addObject1Entities(Entity entityName) {
		object1List.add(entityName);
	}
	void addObject2Entities(Entity entityName) {
		object2List.add(entityName);
	}
	void addTerrainEntities(Entity entityName) {
		terrain.add(entityName);
	}
	
    // Draw all entities
    public void draw(SpriteBatch batch) {
    	for (List<Entity> list : getAllLists()) {
            for (Entity entity : list) {
                entity.draw(batch);
            }
        }
    }


    // Movement for all entities
    public void movement() {
    	for (List<Entity> list : getAllLists()) {
            for (Entity entity : list) {
                entity.movement();
            }
        }
    }
	
	
}

