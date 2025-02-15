package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
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


