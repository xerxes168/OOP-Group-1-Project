package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScene extends AbstractScene implements Screen {

    // Entities
    private SpriteBatch batch;
    private Character player1;

    private EntityManager entityManager;
    private ShapeRenderer shapeRenderer;
    private CollisionManager collisionManager;

    private SoundManager soundManager;

    // Pause Variables
    private boolean isPaused = false;
    private boolean initialized = false;

    // Scrolling Items
    private ScrollingBackground scrollingBackground;
    private float scrollOffset = 0; // Offset for scrolling effect
    private static float scrollSpeed = 40; // Pixels per second 
    private BitmapFont font;
    private static PlayScene currentInstance;

    public static void setScrollSpeed(float speed) {
        scrollSpeed = speed;
    }

    public static float getScrollSpeed() {
        return scrollSpeed;
    }

    public static float getTopBoundary() {
        return currentInstance.camera.position.y + currentInstance.viewport.getWorldHeight() / 2;
    }

    public PlayScene(GameMaster game, SoundManager soundManager) {
        super(game);
        this.soundManager = soundManager;
        camera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        camera.update();
        font = new BitmapFont(Gdx.files.internal("ralewayFont/myfont.fnt"), Gdx.files.internal("ralewayFont/myfont.png"), false);
    }

    // All Show logic in here
    public void startGameState() {
        currentInstance = this;
        scrollingBackground = new ScrollingBackground("background.png", scrollSpeed);

        entityManager = new EntityManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        collisionManager = new CollisionManager();
        soundManager.playBackgroundMusic();

        // Create entities
        player1 = new Character(500, 50, 1, "character.png", Character.CELL_WIDTH, Character.CELL_HEIGHT, soundManager, entityManager);

        //create Object 1
        entityManager.spawnObject1Entities(3, scrollSpeed);
        //create Object 2
        entityManager.spawnObject2Entities(5, scrollSpeed);
        //create the terrain
        entityManager.spawnTerrainEntities(8, scrollSpeed);

        // Add them to entity manager
        entityManager.addCharacters(player1);

        isPaused = false;
        initialized = true;

    }


    @Override   // Similar to Create() in GameMaster
    // Add code into startGameState() not here
    public void show() {
        super.show();
        if (!initialized) {
            startGameState();
        }
    }


    @Override // Similar to Render() in GameMaster
    protected void draw(float delta) {

        if (!isPaused) {
            // All Game Logic in here

            DifficultyManager.updateDifficultyBasedOnScore((int) player1.points, delta);
            scrollingBackground.update(delta);

            // Camera Y never goes below the bottom of the background
            float minY = viewport.getWorldHeight() * 0.5f;
            if (camera.position.y < minY) {
                camera.position.y = minY;
            }


            ScreenUtils.clear(0, 0, 0.2f, 1);

            shapeRenderer.setProjectionMatrix(camera.combined);
            batch.setProjectionMatrix(camera.combined);
            scrollingBackground.draw(batch);

            // Grid scrolling
            //updateGridScroll(delta);
            //drawGrid();

            entityManager.movement();

            entityManager.updateEntitySpeeds(getScrollSpeed());

            // Draw and update entities
            entityManager.draw(batch);


            // Check collisions
            collisionManager.checkCollisions(entityManager.getAllEntities());

        }

        // Always draw the scene
        entityManager.draw(batch);

        // Check for pause
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            isPaused = !isPaused;
            SceneManager.getInstance().setScene("Pause");
        }

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            SceneManager.getInstance().setScene("Menu");
        }

        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        font.draw(batch, "Points: " + Math.round(player1.points),
                viewport.getWorldWidth() - 150, viewport.getWorldHeight() - 20);
        batch.end();

    }

    // Remove Game Pause after coming back from Menu
    public void updatePause() {
        this.isPaused = false;
    }

    // Restart Game
    public void restartGame() {
        initialized = false;
        setScrollSpeed(40);
        startGameState();

    }
    

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        camera.update();

        float cellHeight = viewport.getWorldHeight() / 12f;
        scrollOffset = scrollOffset % cellHeight;

        if (player1 != null) {
            float newY = Math.max(player1.getY(), viewport.getWorldHeight() / 6f);
            player1.setPosition(player1.getX(), newY);
        }

    }

    @Override
    public void dispose() {
        super.dispose();

        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (scrollingBackground != null) {
            scrollingBackground.dispose();
        }

        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}