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

    public int roadRunnerScore = 0;
    public int humptyBumptyScore = 0;


    private Circle boundingCircle;

    private Boolean isAlive;

    public Chuggy(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 50); //speed set (orig 0) as cant figure out how to swap direction without
        acceleration = new Vector2(0, 1);
        boundingCircle = new Circle();
        isAlive = true;
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

//        velocity.add(acceleration.scl(delta));
//
//        acceleration.scl(1/delta);

        if (velocity.y > 150) {
            velocity.y = 150;
        }
        if (velocity.y < -150) {
            velocity.y = -150;
        }

// CEILING CHECK
        if (position.y < 0) {
            position.y = 0;
            velocity.y = -velocity.y;
            acceleration.y = -acceleration.y;
            addHumptyBumptyScore();
        }

        if (position.y > 204) {
            position.y = 204;
            velocity.y = -velocity.y;
            acceleration.y = -acceleration.y;
            addRoadRunnerScore();
        }



        position.add(velocity.cpy().scl(delta));

//        position.add(velocity.scl(delta));
//
//        velocity.scl(1/delta);

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + (getHeight() / 2), position.y + (getWidth() / 2), 7.8f);

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 380 * delta;

            if (rotation < -30) {
                rotation = -30;
            }
        }

        // Rotate clockwise
        if (velocity.y > 1) {
            rotation += 380 * delta;
            if (rotation > 30) {
                rotation = 30;
            }

        }
    }


    public void addRoadRunnerScore() {
        roadRunnerScore++;
        System.out.println("Road Runner Score is: " + roadRunnerScore);
    }

    public void addHumptyBumptyScore() {
        humptyBumptyScore++;
        System.out.println("Humpty Bumpty Score is: " + humptyBumptyScore);
    }

    public int getRoadRunnerScore() {
        return roadRunnerScore;
    }


    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + 90;
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


//    public void decelerate() {
//        // We want chuggy to stop accelerating once it is chugged.
//        acceleration.y = 0;
//    }

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


