package com.weavernap.gameobjects;

import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.gameworld.GameWorld;

import java.util.Random;

public class ScrollHandler {

    // ScrollHandler will create all five objects that we need.
    private Kerb frontKerb, backKerb;
    private Chuggers chugger1, chugger2, chugger3;
    private Car car1, car2;

    public Random randCar;

    // ScrollHandler will use the constants below to determine
    // how fast we need to scroll and also determine
    // the size of the gap between each set of chuggers.


    // Capital letters are used by convention when naming constants.
    public static final int SCROLL_SPEED = -69;
    public static final int CHUGGER_GAP = 59;
    public static final int CAR_SPEED = 150;
    //  public static final int CAR_GAP = carGap;


    private GameWorld gameWorld;


    // Constructor receives a float that tells us where we need to create our
    // Kerb and Chugger objects.

    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontKerb = new Kerb(0, yPos, 136, 500, SCROLL_SPEED);
        backKerb = new Kerb(frontKerb.getTailX(), yPos, 136, 500, SCROLL_SPEED);

        chugger1 = new Chuggers(210, 0, 18, 18, SCROLL_SPEED, yPos);
        chugger2 = new Chuggers(chugger1.getTailX() + CHUGGER_GAP, 0, 18, 18, SCROLL_SPEED, yPos); //Are these 16 by 16 everywhere?
        chugger3 = new Chuggers(chugger2.getTailX() + CHUGGER_GAP, 0, 18, 18, SCROLL_SPEED, yPos);


        car1 = new Car(-10, 170, 40, 40, CAR_SPEED);
        car2 = new Car(car1.getTailX() - 200, 170, 40, 40, CAR_SPEED);




    }

    public void updateReady(float delta) {

        frontKerb.update(delta);
        backKerb.update(delta);

        // See update() for explanation
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

        car1.update(delta);
        car2.update(delta);

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


        if (car1.isScrolledRight()) {

            car1.reset(car2.getTailX() - 200 - getCarGap());

        } else if (car2.isScrolledRight()) {

            car2.reset(car1.getTailX() - 200 - getCarGap());

        }

    }

//

    public void stop() {
        frontKerb.stop();
        backKerb.stop();
        chugger1.stop();
        chugger2.stop();
        chugger3.stop();
        car1.stop();
        car2.stop();
    }

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

        return (chugger1.collides(chuggy) || chugger2.collides(chuggy) || chugger3.collides(chuggy)
                || car1.collides(chuggy) || car2.collides(chuggy));
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

    public Car getCar1() {
        return car1;
    }

    public Car getCar2() {
        return car2;
    }

    public int getCarGap()
    {
        Random randCar = new Random();

        int carGap = randCar.nextInt(900 - 150 + 1) + 150;
        return carGap;
    }

    public void onRestart() {
        frontKerb.onRestart(0, SCROLL_SPEED);
        backKerb.onRestart(frontKerb.getTailX(), SCROLL_SPEED);
        chugger1.onRestart(210, SCROLL_SPEED);
        chugger2.onRestart(chugger1.getTailX() + CHUGGER_GAP, SCROLL_SPEED);
        chugger3.onRestart(chugger2.getTailX() + CHUGGER_GAP, SCROLL_SPEED);
        car1.onRestart(-10, CAR_SPEED);
        car2.onRestart(car1.getTailX() - 200, CAR_SPEED);
    }

}