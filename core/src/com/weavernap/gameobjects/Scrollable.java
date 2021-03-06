package com.weavernap.gameobjects;

/**
 * Created by martinweaver on 30/12/2016.
 */

import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Scrollable {

    // Protected is similar to private, but allows inheritance by subclasses.
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected int midChange;


    protected boolean isScrolledLeft;
    protected boolean isScrolledRight;


    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
        isScrolledRight = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // If the Scrollable object is no longer visible:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }

        if (position.x > 300) {
            isScrolledRight = true;
        }
    }

    // Reset: Should Override in subclass for more specific behavior.
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
        isScrolledRight = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    // Getters for instance variables
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    // Getters for instance variables
    public boolean isScrolledRight() {
        return isScrolledRight;
    }

    public float getTailX() {
        return position.x + width;
    }

        public int getBusY()
    {
        Random randBusY = new Random();

        int busY = randBusY.nextInt(42 - 35 ) + 35;
        return busY;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}