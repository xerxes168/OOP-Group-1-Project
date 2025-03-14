package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Character extends Entity implements iMovable, iCollidable {
	
	private ShapeRenderer shapeRenderer; // Only for debugging purposes
	private SoundManager soundManager;
	private float currentxPos;
	private float currentyPos;
    private static final float CELL_WIDTH = Gdx.graphics.getWidth() / 12f;
    private static final float CELL_HEIGHT = Gdx.graphics.getHeight() / 12f;
    private static final float CHARACTER_WIDTH = CELL_WIDTH;
    private static final float CHARACTER_HEIGHT = CELL_HEIGHT;
    private static final float SPEED = 0;
    private static float maxHealth = 100;
	private float currentHealth = 100;
	protected float points;
	protected float highScore;
	private EntityManager entityManager;
    private float previousX;
    private float previousY;
    
 	// Default constructor
    public Character() {
        super(); // Calls default Entity constructor
        this.shapeRenderer = new ShapeRenderer();
        // Initialize previous position with the starting position
        this.previousX = super.getX();
        this.previousY = super.getY();
    }
	
	
    public Character(float x, float y, float speed, String imgName, float width, float height, SoundManager soundManager, EntityManager entityManager) {
		super(x, y, speed, imgName, width, height);
		this.soundManager = soundManager;
		this.shapeRenderer = new ShapeRenderer();
		this.entityManager = entityManager;
        this.previousX = x;
        this.previousY = y;
	}
	
	public void draw(SpriteBatch batch) {

	    float barWidth = CHARACTER_WIDTH;
	    float barHeight = 8;
	    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	    
	    // Drawing of character
	    batch.begin();
	    batch.draw(this.getTex(), super.getX(), super.getY(), CHARACTER_WIDTH, CHARACTER_HEIGHT); 
	    batch.end();
	    
	    // Drawing of collision rectangle
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(super.getX(), super.getY() , CHARACTER_WIDTH, CHARACTER_HEIGHT);
        shapeRenderer.end();
        
        // Drawing of health bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(getX(), getY() + 50, barWidth, barHeight);
        shapeRenderer.setColor(Color.RED.lerp(Color.GREEN, currentHealth / maxHealth));
        shapeRenderer.rect(getX(), getY() + 50, (currentHealth/maxHealth) * barWidth, barHeight);
        shapeRenderer.end();
        
        points = points + 0.1f;
        setRectangle();    
	}

	
	@Override
	public void movement() {
        previousX = super.getX();
        previousY = super.getY();
        
	    // Calculate current grid position
	    int currentxPos = (int)((super.getX() + CHARACTER_WIDTH / 2)/ CELL_WIDTH);
	    int currentyPos = (int)((super.getY() + CHARACTER_WIDTH / 2)/ CELL_HEIGHT);

	    int candidateX = currentxPos;
	    int candidateY = currentyPos;
	    boolean moved = false;

	    // Handle grid-based input movement
	    if (Gdx.input.isKeyJustPressed(Keys.UP)) {
	        candidateY = currentyPos + 1;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        candidateY = currentyPos - 1;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
	        candidateX = currentxPos - 1;
	        moved = true;
	    }
	    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
	        candidateX = currentxPos + 1;
	        moved = true;
	    }

	    if (moved) {
	        boolean blocked = false;
	        // Iterate through all entities and check for Terrain instances
	        for (Entity entity : entityManager.getAllEntities()) {
	            if (entity instanceof Terrain) {
	                // Compute the terrain's cell based on its center
	                int terrainCellX = (int)((entity.getX() + CELL_WIDTH / 2) / CELL_WIDTH);
	                int terrainCellY = (int)((entity.getY() + CELL_HEIGHT / 2) / CELL_HEIGHT);
	                if (terrainCellX == candidateX && terrainCellY == candidateY) {
	                    blocked = true;
	                    break;
	                }
	            }
	        }
	        if (!blocked) {
	            // Allow movement: update current cell to candidate cell and play sound.
	            currentxPos = candidateX;
	            currentyPos = candidateY;
	            soundManager.playSound("move");
	        } else {
	            System.out.println("Movement blocked by terrain!");
	        }
	    }

	    // Keep the character within the grid boundaries
	    currentxPos = Math.max(0, Math.min(currentxPos, 11));
//	    gridY = Math.max(0, Math.min(gridY, 11));

	    float maxWidth = Gdx.graphics.getWidth() - CHARACTER_WIDTH;
//	    float maxHeight = Gdx.graphics.getHeight() - CHARACTER_HEIGHT;

	    // Calculate the target grid position based on input
	    float targetX = Math.min(Math.round(currentxPos * CELL_WIDTH), maxWidth);
//	    float targetY = Math.min(Math.round(gridY * CELL_HEIGHT), maxHeight);
	    float targetY = Math.round(currentyPos * CELL_HEIGHT);

	    // Apply continuous downward movement to the current position
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    float newY = super.getY() - PlayScene.getScrollSpeed() * deltaTime;
	    
	    // Use the grid-based Y position when input is detected, otherwise use current position with downward movement
	    if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN)) {
	        newY = targetY;
	    }

	    // Set final positions
	    super.setX(targetX);
	    super.setY(newY);
	    
	 // Check if character has fallen below screen by more than one cell height
	    if (super.getY() < - CELL_HEIGHT) {
	    	System.out.println("You fell below the screen!");
			soundManager.playSound("collision");
			if(points > ScoreManager.highScore) {
				// update highscore
				ScoreManager.highScore = Math.round(points);
				
			}
	        SceneManager.getInstance().setScene("GameOver");
	    }
	}
	

	
	@Override
	public boolean isCollided(iCollidable object) {
		if(object instanceof Entity) {
			return this.getRectangle().overlaps(((Entity) object).getRectangle());
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void onCollision(iCollidable object) {
		// To be improved
		
		// Collision with other objects
		if(object instanceof Object1) {
			if(getRemovalBoolean() == true) {
				return;
			}
			System.out.println("Collided with object 1");
			soundManager.playSound("collision");
			currentHealth = currentHealth - 20;
	        entityManager.removeObject1((Entity) object);
	        entityManager.spawnObject1Entities(1, PlayScene.getScrollSpeed());
			if(currentHealth <= 0) {
				if(points > ScoreManager.highScore) {
					ScoreManager.highScore = Math.round(points);
				}
				SceneManager.getInstance().setScene("GameOver");
			}

		}
		else if(object instanceof Object2) {
			if(getRemovalBoolean() == true) {
				return;
			}
			System.out.println("Collided with object 2");
			soundManager.playSound("collision");
	        entityManager.removeObject2((Entity) object);
	        entityManager.spawnObject2Entities(1, PlayScene.getScrollSpeed());
			points = points + 100;
		}
		
		else if (object instanceof Terrain) {
			System.out.println("Collided with terrain");
		   }
	}

	public void setPosition(float x, float y) {
		
		super.setX(x);  // Use Entity setX()
	    super.setY(y);  // Use Entity setY()
		
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		soundManager.dispose();
	}

	
	
}
