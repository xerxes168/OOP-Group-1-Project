package com.mygdx.game.lwjgl3;

import java.util.HashMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class SceneManager {
    private static SceneManager instance;
    private Game game;
    private HashMap<String, Screen> scenes;


    SceneManager(Game game) {
        this.game = game;
        this.scenes = new HashMap<>();
    }

    public static void initialize(Game game) {
        if (instance == null) {
            instance = new SceneManager(game);
        }
    }

    public static SceneManager getInstance() {
        return instance;
    }
    
    public Screen getScene(String name) {
        return scenes.get(name);
    }

    public void addScene(String name, Screen screen) {
        scenes.put(name, screen);
    }

    public void setScene(String name) {
        if (scenes.containsKey(name)) {
            game.setScreen(scenes.get(name));
        } else {
            System.out.println("Scene '" + name + "' not found!");
        }
    }
}


