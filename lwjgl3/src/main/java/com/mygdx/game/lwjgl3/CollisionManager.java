package com.mygdx.game.lwjgl3;

import java.util.List;

public class CollisionManager extends Entity implements iCollidable{
		
	CollisionManager(){
		
	}

	
	public void checkCollisions(List<Entity> entities) {
		for(int i = 0; i < entities.size(); i++) {
			for(int k = i + 1; k < entities.size(); k++) {
				Entity obj1 = entities.get(i);
				Entity obj2 = entities.get(k);
				if(obj1 instanceof iCollidable && obj2 instanceof iCollidable) {
					if(((iCollidable) obj1).isCollided((iCollidable) obj2)) {
		                ((iCollidable) obj1).onCollision((iCollidable) obj2);
		                ((iCollidable) obj2).onCollision((iCollidable) obj1);
		            }
			}
			
			
		}
}

			
		
	}
	
	@Override
	public boolean isCollided(iCollidable object) {
		if(object instanceof Entity) {
			return this.getRectangle().overlaps(((Entity) object).getRectangle());
		}
		else{
			return false;
		}
	}
	
	@Override
	public void onCollision(iCollidable object) {
	    //Overrided by subclasses
	}
	
	public void dispose(){
		
	      
	}
}
