package com.weavernap.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.weavernap.cdHelpers.AssetLoader;

/**
 * Created by martinweaver on 27/12/2016.
 */

public class Chuggy {


    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation; // For handling Chuggy rotation
    private int width;
    private int height;

    private float originalY;

    private Circle boundingCircle;

    private Boolean isAlive;

    public Chuggy(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.originalY = y;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 50); //speed set (orig 0) as cant figure out how to swap direction without
        acceleration = new Vector2(0, 1);
        boundingCircle = new Circle();
        isAlive = true;
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        if (velocity.y > 500) {
            velocity.y = 500;
        }
        if (velocity.y < -500) {
            velocity.y = -500;
        }

// CEILING CHECK
        if (position.y < 0) {
            position.y = 0;
            velocity.y = -velocity.y;
        }

        position.add(velocity.cpy().scl(delta));

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 480 * delta;

            if (rotation < -55) {
                rotation = -55;
            }
        }

        // Rotate clockwise
        if (velocity.y > 1) {
            rotation += 480 * delta;
            if (rotation > 55) {
                rotation = 55;
            }

        }
    }

    //    public boolean isWalkingRight() {
//        return velocity.y > 1;
//    } LOOK AT DAY 9 FOR USE OF THIS METHOD. CAN IT BE USED WHEN CHUGGED?


    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public boolean shouldntWalk() {
        return velocity.y == 0 || !isAlive; //THIS NEEDS LOOKING AT. 0??
    }

    public void onClick() {
        if (isAlive) {
            AssetLoader.flap.play();
            velocity.y = -velocity.y; // click and hold +10 ??
            acceleration.y = -acceleration.y;
        }
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {

        velocity.y = 0;
        acceleration.y = 0;
        isAlive = false;
    }


    public void decelerate() {
        // We want chuggy to stop accelerating once it is chugged.
        acceleration.y = 0;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 50;
        acceleration.x = 0;
        acceleration.y = 1;
        isAlive = true;
    }
}


