package com.weavernap.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.gameobjects.Chuggy;
import com.weavernap.gameobjects.ScrollHandler;

/**
 * Created by martinweaver on 27/12/2016.
 */

public class GameWorld {

    private Chuggy chuggy;
    private ScrollHandler scroller;
    private Rectangle road;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;

    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        chuggy = new Chuggy(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        road = new Rectangle(0, midPointY + 100, 136, 11);
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        chuggy.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        // Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.

        if (delta > .15f) {
            delta = .15f;
        }
        chuggy.update(delta);
        scroller.update(delta);

        if (scroller.collides(chuggy) && chuggy.isAlive()) {
            scroller.stop();
            chuggy.die();
            AssetLoader.dead.play();
            renderer.prepareTransition(255, 255, 255, .3f);

            AssetLoader.fall.play();
            currentState = GameState.GAMEOVER; //Should this be here?

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;

            }
        }
        if (Intersector.overlaps(chuggy.getBoundingCircle(), road)) {

            if (chuggy.isAlive()) {
                AssetLoader.dead.play();
                renderer.prepareTransition(255, 255, 255, .3f);
                AssetLoader.fall.play();
                chuggy.die();
            }
            scroller.stop();
            chuggy.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public Chuggy getChuggy() {
        return chuggy;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void restart() {
        score = 0;
        chuggy.onRestart(midPointY - 5);
        scroller.onRestart();
        ready();
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

}
