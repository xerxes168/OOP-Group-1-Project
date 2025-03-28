package com.mygdx.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected static final int CELL_WIDTH = Gdx.graphics.getWidth() / 12;
	protected static final int CELL_HEIGHT = Gdx.graphics.getWidth() / 12;
    private float xPosition;
    private float yPosition;
    private float speed;
    private float width;
    private float height;
    private Texture tex;
    private Rectangle rectangle;
    private boolean removeAfterCollision;

    Entity() {

    }

    Entity(float x, float y, float speed, String imgName) {
        this.xPosition = x;
        this.yPosition = y;
        this.speed = speed;
        this.tex = new Texture(Gdx.files.internal(imgName));
    }

    Entity(float x, float y, float speed, String imgName, float width, float height) {
        this.xPosition = x;
        this.yPosition = y;
        this.speed = speed;
        this.tex = new Texture(Gdx.files.internal(imgName));
        this.rectangle = new Rectangle(xPosition, yPosition, width, height);
    }

    public float getX() {
        return xPosition;
    }

    public void setX(float x) {
        this.xPosition = x;
    }

    public float getY() {
        return yPosition;
    }

    public void setY(float y) {
        this.yPosition = y;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float newSpeed) {
        this.speed = newSpeed;
    }

    public Texture getTex() {
        return tex;
    }

    public void setTex(String imgName) {
        tex = new Texture(Gdx.files.internal(imgName));
    }


    public void setRectangle() {
        rectangle.setPosition(xPosition, yPosition);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setWidth(float newWidth) {
        width = newWidth;
        ;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float newHeight) {
        height = newHeight;
    }

    public float getHeight() {
        return height;
    }

    public void setRemovalBoolean() {
        removeAfterCollision = true;
    }

    public boolean getRemovalBoolean() {
        return removeAfterCollision;
    }


    public void draw(SpriteBatch batch) {
        // Child Class will override
    }

    public void movement() {
        // Child Class will override
    }

}
