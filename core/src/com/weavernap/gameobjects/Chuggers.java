package com.weavernap.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

/**
 * Created by martinweaver on 30/12/2016.
 */

public class Chuggers extends Scrollable {

    private Random r;
    private Circle boundingChuggerTop, boundingChuggerCentre, boundingChuggerBottom;


    public static final int VERTICAL_GAP = 55;
    public static final int CHUGGER_WIDTH = 20;
    public static final int CHUGGER_HEIGHT = 21;

   // private float roadY;

    private boolean isScored = false;



    // When Chugger's (was Pipe) constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Chuggers(float x, float y, int width, int height, float scrollSpeed
            //,
                    //float roadY
    ) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();

        boundingChuggerTop = new Circle();
        boundingChuggerCentre = new Circle();
        boundingChuggerBottom = new Circle();

       // this.roadY = roadY;
    }

    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle



        boundingChuggerTop.set(position.x, position.y + height, 10f);
        boundingChuggerCentre.set(position.x, position.y + height + midChange + VERTICAL_GAP, 10f);
        boundingChuggerBottom.set(position.x, position.y + height + (2*VERTICAL_GAP) - 8, 10f);



    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(38) +15;
        int Low = -25;
        int High = 25;
        midChange = r.nextInt(High - Low) + Low;
        isScored = false;

    }
    public Circle getChuggerTop() {
        return boundingChuggerTop;
    }

    public Circle getChuggerCentre() {
        return boundingChuggerCentre;
    }

    public Circle getChuggerBottom() {
        return boundingChuggerBottom;
    }


    public boolean collides(Chuggy chuggy) {
        if (position.x < chuggy.getX() + chuggy.getWidth()) {
            return ( Intersector.overlaps(chuggy.getBoundingCircle(), boundingChuggerTop)
                    || Intersector.overlaps(chuggy.getBoundingCircle(), boundingChuggerCentre)
                    || Intersector.overlaps(chuggy.getBoundingCircle(), boundingChuggerBottom));
        }
        return false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }
}