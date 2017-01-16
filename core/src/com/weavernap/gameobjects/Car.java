package com.weavernap.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by martinweaver on 08/01/2017.
 */

public class Car extends Scrollable {


    private Rectangle boundingCar1, boundingCar2;
    private Random r;

    public static final int CAR_WIDTH = 67;
    public static final int CAR_HEIGHT = 49;

    public Car(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

        r = new Random();

        boundingCar1 = new Rectangle();
        boundingCar2 = new Rectangle();
    }


    @Override
    public void update(float delta) {
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle


        boundingCar1.set(position.x  -120, position.y - height, (float) (CAR_WIDTH * 1.25), (float) (CAR_HEIGHT * 1.25));
        boundingCar2.set(position.x  -120, position.y - height, (float) (CAR_WIDTH * 1.25), (float) (CAR_HEIGHT * 1.25));
    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);

        height = getBusY();

    }


    public Rectangle getCar1() {
        return boundingCar1;
    }

    public Rectangle getCar2() {
        return boundingCar2;
    }



    public boolean collides(Chuggy chuggy) {
        if (position.x > chuggy.getX() + chuggy.getWidth()) {
            return ( Intersector.overlaps(chuggy.getBoundingCircle(), boundingCar1) ||
                    (Intersector.overlaps(chuggy.getBoundingCircle(), boundingCar2)));
        }
        return false;
    }

    public void onRestart(float x, float scrollSpeed) {
      //  position.x = x;
        velocity.x = scrollSpeed;
    }

}
