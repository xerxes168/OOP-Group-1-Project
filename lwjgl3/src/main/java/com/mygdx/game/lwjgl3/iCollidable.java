package com.mygdx.game.lwjgl3;

public interface iCollidable {
	// Anything that can be collided calls this Interface for collision and writes it own collision
	public boolean isCollided(iCollidable object);
	public void onCollision(iCollidable object);
}

