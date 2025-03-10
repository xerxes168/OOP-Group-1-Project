package com.mygdx.game.lwjgl3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EntityManager {
	
	protected List<List<Entity>> allLists;
	protected List<Entity> characterList;
	protected List<Entity> object1List;
	protected List<Entity> object2List;
	protected List<Entity> terrainList;
	
	
	// Default Constructor
	public EntityManager() {
		allLists = new ArrayList<>();
		characterList = new ArrayList<>();
		object1List = new ArrayList<>();
		object2List = new ArrayList<>();
		terrainList = new ArrayList<>();
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
	
	public void addLists() {
		allLists.add(characterList);
		allLists.add(object1List);
		allLists.add(object2List);
		allLists.add(terrainList);
	}
	public void addCharacters(Entity entityName) {
		characterList.add(entityName);
	}
	public void addObject1Entities(Entity entityName) {
		object1List.add(entityName);
	}
	public void addObject2Entities(Entity entityName) {
		object2List.add(entityName);
	}
	
	public void addTerrainEntities(Entity entityName) {
		terrainList.add(entityName);
	}
	
	public void spawnObject1Entities(int count, float scrollSpeed) {
	    List<Object1> object1 = Object1.spawnObject1(count, scrollSpeed);
	    for (Object1 obj : object1) {
	        addObject1Entities(obj);
	    }
	}
	
	public void removeObject1(Entity entity) {
	    object1List.remove(entity);
	}
	
	public void removeObject2(Entity entity) {
	    object2List.remove(entity);
	}
	
	public void removeTerrain(Entity entity) {
		terrainList.remove(entity);
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
//    public void cleanupEntities() {
//        Iterator<Entity> iterator = getAllEntities().iterator();
//        while (iterator.hasNext()) {
//            Entity entity = iterator.next();
//            if (entity.getRemovalBoolean()) { // ✅ If marked for removal
//                //entity.dispose();  // ✅ Free memory
//                iterator.remove(); // ✅ Remove safely using Iterator
//                System.out.println("Object removed!");
//            }
//        }
//    }


}

