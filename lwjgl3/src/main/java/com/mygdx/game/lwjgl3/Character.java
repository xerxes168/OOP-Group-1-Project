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
    private static final int CHARACTER_WIDTH = CELL_WIDTH;
    private static final int CHARACTER_HEIGHT = CELL_HEIGHT;
    private static float maxHealth = 100;
    private float currentHealth = 100;
    protected float points;
    protected float highScore;
    private EntityManager entityManager;

    // Default constructor
    public Character() {
        super(); // Calls default Entity constructor
        this.shapeRenderer = new ShapeRenderer();
    }


    public Character(float x, float y, float speed, String imgName, float width, float height, SoundManager soundManager, EntityManager entityManager) {
        super(x, y, speed, imgName, width, height);
        this.soundManager = soundManager;
        this.shapeRenderer = new ShapeRenderer();
        this.entityManager = entityManager;
    }

    public void draw(SpriteBatch batch) {

        float barWidth = CHARACTER_WIDTH;
        float barHeight = 8;
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Drawing of character
        batch.begin();
        batch.draw(this.getTex(), super.getX(), super.getY(), CHARACTER_WIDTH, CHARACTER_HEIGHT);
        batch.end();

        // Drawing of health bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(getX(), getY() + 50, barWidth, barHeight);
        shapeRenderer.setColor(Color.RED.lerp(Color.GREEN, currentHealth / maxHealth));
        shapeRenderer.rect(getX(), getY() + 50, (currentHealth / maxHealth) * barWidth, barHeight);
        shapeRenderer.end();

        points = points + 0.1f;
        setRectangle();
    }

    @Override
    public void movement() {

        // Calculate current grid position
        int currentxPos = (int) ((super.getX() + CHARACTER_WIDTH / 2) / CELL_WIDTH);
        int currentyPos = (int) ((super.getY() + CHARACTER_HEIGHT / 2) / CELL_HEIGHT);

        int candidateX = currentxPos;
        int candidateY = currentyPos;
        boolean moved = false;

        // Handle grid-based input movement
        if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)) {
            candidateY = currentyPos + 1;
            moved = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.DOWN) || Gdx.input.isKeyJustPressed(Keys.S)) {
            candidateY = currentyPos - 1;
            moved = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.LEFT) || Gdx.input.isKeyJustPressed(Keys.A)) {
            candidateX = currentxPos - 1;
            moved = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyJustPressed(Keys.D)) {
            candidateX = currentxPos + 1;
            moved = true;
        }

        // Keep the character within the grid boundaries
        candidateX = Math.max(0, Math.min(candidateX, 11));
        candidateY = Math.max(0, Math.min(candidateY, 11));

        // Calculate the target grid position based on input
        float targetX = candidateX * CELL_WIDTH;
        float targetY = candidateY * CELL_HEIGHT;

        // Calculate the position after user input (if any)
        float newX = moved ? targetX : super.getX();
        float newY = moved ? targetY: super.getY();

//        if (moved && (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN) || Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.S) )) {
//            newY = targetY;
//        }

        // Check for collisions if moved
        if (moved) {
            boolean blocked = false;
            for (Entity entity : entityManager.getAllEntities()) {
                if (entity instanceof Terrain) {
                    // Skip terrain objects that are too far away for optimization
                    if (Math.abs(entity.getX() - newX) > CELL_WIDTH * 2 ||
                            Math.abs(entity.getY() - newY) > CELL_HEIGHT * 2) {
                        continue;
                    }
                    

                    // Create rectangle for the character at new position
                    float charLeft = newX;
                    float charRight = newX + CHARACTER_WIDTH;
                    float charTop = newY + CHARACTER_HEIGHT;
                    float charBottom = newY;

                    // Create rectangle for the terrain
                    float terrainLeft = entity.getX();
                    float terrainRight = entity.getX() + CELL_WIDTH;
                    float terrainTop = entity.getY() + CELL_HEIGHT;
                    float terrainBottom = entity.getY();

                    // Test for overlap
                    boolean overlapX = (charLeft < terrainRight) && (charRight > terrainLeft);
                    boolean overlapY = (charBottom < terrainTop) && (charTop > terrainBottom);

                    if (overlapX && overlapY) {
                        blocked = true;
                        System.out.println("Movement blocked by terrain at " +
                                entity.getX() + "," + entity.getY());
                        break;
                    }
                }
            }

            if (!blocked) {
                super.setX(newX);
                if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN) || Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.S)) {
                    super.setY(newY);
                }
                soundManager.playSound("move");
            }
        }

        // Apply continuous downward movement (scrolling)
        float deltaTime = Gdx.graphics.getDeltaTime();
        float scrollAmount = PlayScene.getScrollSpeed() * deltaTime;
        newY = super.getY() - scrollAmount;

        // Test if scrolling would cause collision
        Entity collidedTerrain = null;
        float minOverlap = Float.MAX_VALUE;

        for (Entity entity : entityManager.getAllEntities()) {
            if (entity instanceof Terrain) {
                // Skip terrain objects that are too far away
                if (Math.abs(entity.getX() - super.getX()) > CELL_WIDTH * 2 ||
                        Math.abs(entity.getY() - newY) > CELL_HEIGHT * 2) {
                    continue;
                }

                // Test collision with potential new position
                float charLeft = super.getX();
                float charRight = super.getX() + CHARACTER_WIDTH;
                float charTop = newY + CHARACTER_HEIGHT;
                float charBottom = newY;

                float terrainLeft = entity.getX();
                float terrainRight = entity.getX() + CELL_WIDTH;
                float terrainTop = entity.getY() + CELL_HEIGHT;
                float terrainBottom = entity.getY();

                boolean overlapX = (charLeft < terrainRight) && (charRight > terrainLeft);
                boolean overlapY = (charBottom < terrainTop) && (charTop > terrainBottom);

                if (overlapX && overlapY) {
                    // Calculate vertical overlap
                    float overlap = Math.min(charTop - terrainBottom, terrainTop - charBottom);

                    if (overlap < minOverlap) {
                        minOverlap = overlap;
                        collidedTerrain = entity;
                    }
                }
            }
        }

        if (collidedTerrain != null) {
            // Determine direction of collision (from top or from bottom)
            float characterCenter = super.getY() + CHARACTER_HEIGHT / 2;
            float terrainCenter = collidedTerrain.getY() + CELL_HEIGHT / 2;

            if (characterCenter > terrainCenter) {
                // Character is above terrain, place on top
                newY = collidedTerrain.getY() + CELL_HEIGHT;
            } else {
                // Character is below terrain, don't allow scrolling through
                newY = super.getY(); // Maintain current position
            }

            System.out.println("Collision resolved with terrain at " +
                    collidedTerrain.getX() + "," + collidedTerrain.getY());
        }

        // Apply resolved position
        super.setY(newY);

        // Check if character has fallen below screen
        if (super.getY() < -CELL_HEIGHT) {
            System.out.println("You fell below the screen!");
            soundManager.playSound("collision");
            if (points > ScoreManager.highScore) {
                ScoreManager.highScore = Math.round(points);
            }
            SceneManager.getInstance().setScene("GameOver");
        }

        // Update collision rectangle
        setRectangle();
    }


    @Override
    public boolean isCollided(iCollidable object) {
        if (object instanceof Entity) {
            return this.getRectangle().overlaps(((Entity) object).getRectangle());
        } else {
            return false;
        }

    }

    @Override
    public void onCollision(iCollidable object) {
        // To be improved

        // Collision with other objects
        if (object instanceof Object1) {
            if (getRemovalBoolean()) {
                return;
            }
            System.out.println("Player bounding box: X = " + getRectangle().getX() +
                    ", Y = " + getRectangle().getY() +
                    ", Width = " + getRectangle().getWidth() +
                    ", Height = " + getRectangle().getHeight());
            System.out.println("Object1 bounding box: X = " + ((Object1) object).getRectangle().getX() +
                    ", Y = " + ((Object1) object).getRectangle().getY() +
                    ", Width = " + ((Object1) object).getRectangle().getWidth() +
                    ", Height = " + ((Object1) object).getRectangle().getHeight());

            soundManager.playSound("Error");
            currentHealth = currentHealth - 20;
            entityManager.removeObject1((Entity) object);
            entityManager.spawnObject1Entities(1, PlayScene.getScrollSpeed());
            if (currentHealth <= 0) {
                if (points > ScoreManager.highScore) {
                    ScoreManager.highScore = Math.round(points);
                }
                SceneManager.getInstance().setScene("GameOver");
            }

        } else if (object instanceof Object2) {
            if (getRemovalBoolean()) {
                return;
            }
            System.out.println("Collided with object 2");
            soundManager.playSound("Correct");
            entityManager.removeObject2((Entity) object);
            entityManager.spawnObject2Entities(1, PlayScene.getScrollSpeed());
            points = points + 100;
        } else if (object instanceof Terrain) {
            System.out.println("Collided with terrain");
            soundManager.playSound("collision");
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
