package com.mygdx.game.lwjgl3;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends Game {
	
	@Override
	public void create() {
		
        // Initialize SceneManager and SoundManager
		SoundManager soundManager = new SoundManager();
		SceneManager.initialize(this);

		// Add scenes
		SceneManager.getInstance().addScene("Menu", new MenuScene(this));
		SceneManager.getInstance().addScene("Play", new PlayScene(this, soundManager));
		SceneManager.getInstance().addScene("Setting", new SettingsScene(this, soundManager));
		SceneManager.getInstance().addScene("GameOver", new GameOverScene(this));
		SceneManager.getInstance().addScene("Pause", new PauseScene(this));

        // Start at Menu Screen
        SceneManager.getInstance().setScene("Menu");

	}

	@Override
	public void render()
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);
				
		// Call the current screen's render method
		super.render();			
	}

	
	@Override
	public void dispose() {
				
	}
}

