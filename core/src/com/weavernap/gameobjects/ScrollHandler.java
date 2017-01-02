package com.weavernap.gameobjects;

import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.gameworld.GameWorld;

public class ScrollHandler {

    // ScrollHandler will create all five objects that we need.
    private Kerb frontKerb, backKerb;
    private Chuggers chugger1, chugger2, chugger3;

    // ScrollHandler will use the constants below to determine
    // how fast we need to scroll and also determine
    // the size of the gap between each pair of chuggers.

    // Capital letters are used by convention when naming constants.
    public static final int SCROLL_SPEED = -59;
    public static final int CHUGGER_GAP = 49;

    private GameWorld gameWorld;


    // Constructor receives a float that tells us where we need to create our
    // Kerb and Chugger objects.

    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontKerb = new Kerb(0, yPos, 143, 11, SCROLL_SPEED);
        backKerb = new Kerb(frontKerb.getTailX(), yPos, 143, 11, SCROLL_SPEED);

        chugger1 = new Chuggers(210, 0, 22, 60, SCROLL_SPEED, yPos);
        chugger2 = new Chuggers(chugger1.getTailX() + CHUGGER_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        chugger3 = new Chuggers(chugger2.getTailX() + CHUGGER_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }

    public void updateReady(float delta) {

        frontKerb.update(delta);
        backKerb.update(delta);

        // Same with grass
        if (frontKerb.isScrolledLeft()) {
            frontKerb.reset(backKerb.getTailX());

        } else if (backKerb.isScrolledLeft()) {
            backKerb.reset(frontKerb.getTailX());

        }

    }

    public void update(float delta) {

        frontKerb.update(delta);
        backKerb.update(delta);
        chugger1.update(delta);
        chugger2.update(delta);
        chugger3.update(delta);

        // Check if any of the chuggers are scrolled left,
        // and reset accordingly
        if (chugger1.isScrolledLeft()) {
            chugger1.reset(chugger3.getTailX() + CHUGGER_GAP);
        } else if (chugger2.isScrolledLeft()) {
            chugger2.reset(chugger1.getTailX() + CHUGGER_GAP);

        } else if (chugger3.isScrolledLeft()) {
            chugger3.reset(chugger2.getTailX() + CHUGGER_GAP);
        }

        // Same with kerb
        if (frontKerb.isScrolledLeft()) {
            frontKerb.reset(backKerb.getTailX());

        } else if (backKerb.isScrolledLeft()) {
            backKerb.reset(frontKerb.getTailX());

        }

    }


    public void stop() {
        frontKerb.stop();
        backKerb.stop();
        chugger1.stop();
        chugger2.stop();
        chugger3.stop();}

    // Return true if ANY pipe hits the bird.
    public boolean collides(Chuggy chuggy) {
        if (!chugger1.isScored()
                && chugger1.getX() + (chugger1.getWidth() / 2) < chuggy.getX()
                + chuggy.getWidth()) {
            addScore(1);
            chugger1.setScored(true);
            AssetLoader.coin.play();
        } else if (!chugger2.isScored()
                && chugger2.getX() + (chugger2.getWidth() / 2) < chuggy.getX()
                + chuggy.getWidth()) {
            addScore(1);
            chugger2.setScored(true);
            AssetLoader.coin.play();

        } else if (!chugger3.isScored()
                && chugger3.getX() + (chugger3.getWidth() / 2) < chuggy.getX()
                + chuggy.getWidth()) {
            addScore(1);
            chugger3.setScored(true);
            AssetLoader.coin.play();

        }

        return (chugger1.collides(chuggy) || chugger2.collides(chuggy) || chugger3.collides(chuggy));
    }


    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }
    // The getters for our five instance variables
    public Kerb getFrontKerb() {
        return frontKerb;
    }

    public Kerb getBackKerb() {
        return backKerb;
    }

    public Chuggers getChugger1() {
        return chugger1;
    }

    public Chuggers getChugger2() {
        return chugger2;
    }

    public Chuggers getChugger3() {
        return chugger3;
    }

    public void onRestart() {
        frontKerb.onRestart(0, SCROLL_SPEED);
        backKerb.onRestart(frontKerb.getTailX(), SCROLL_SPEED);
        chugger1.onRestart(210, SCROLL_SPEED);
        chugger2.onRestart(chugger1.getTailX() + CHUGGER_GAP, SCROLL_SPEED);
        chugger3.onRestart(chugger2.getTailX() + CHUGGER_GAP, SCROLL_SPEED);
    }

}