package com.weavernap.gameobjects;

/**
 * Created by martinweaver on 30/12/2016.
 */

public class Kerb  extends Scrollable {

    // When Kerb's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Kerb(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }


}