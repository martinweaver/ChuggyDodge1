package com.weavernap.gameworld;

import com.weavernap.cdHelpers.AdsController;
import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.gameobjects.Chuggy;
import com.weavernap.gameobjects.ScrollHandler;

import java.util.Random;

/**
 * Created by martinweaver on 27/12/2016.
 */

public class GameWorld {

    private Chuggy chuggy;
    private ScrollHandler scroller;
    private int score = 0;
//    private int roadRunnerScore = 0;
    private float runTime = 0;
   // private int midPointY;
    private GameRenderer renderer;

    private GameState currentState;

    public AdsController adsController;


    private Random rnd = new Random();
    private int timesToAd = 0;





    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public GameWorld(AdsController adsController) {
        currentState = GameState.MENU;
      //  this.midPointY = midPointY;
        this.adsController = adsController;
        chuggy = new Chuggy(33, 120, 17, 12); //wtf does 120 do??
        // The kerb bg should start 150 (was 66) pixels below the midPointY
        scroller = new ScrollHandler(this);
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

        if (delta > .05f) {  //was .15f
            delta = .05f;
        }
        chuggy.update(delta);
        scroller.update(delta);



        if (scroller.collides(chuggy) && chuggy.isAlive()) {
            scroller.stop();
            chuggy.die();


            AssetLoader.fall.play();


            renderer.prepareTransition(255, 255, 255, .3f);

            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;

            }

            this.adsController.unlockAchievementGPGS(this.score);
            this.adsController.submitScoreGPGS(this.score);

            this.adsController.submitRoadRunnerScoreGPGS(chuggy.roadRunnerScore);
            this.adsController.unlockRoadRunnerAchievementsGPGS(chuggy.roadRunnerScore);

            this.adsController.submitHumptyBumptyScoreGPGS(chuggy.humptyBumptyScore);
            this.adsController.unlockHumptyBumptyAchievementsGPGS(chuggy.humptyBumptyScore);
        }


    }

    public AdsController getAdsController() {

        return this.adsController;
    }


    public void getAchievementsGPGS() {
        this.adsController.getAchievementsGPGS();
    }





    public void getOnInviteClicked() {
        this.adsController.getOnInviteClicked();
    }






    public void getLeaderboardGPGS() {
        this.adsController.getGPGSLeaderboard();
    }

    public Chuggy getChuggy() {
        return chuggy;
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
        chuggy.roadRunnerScore = 0;
        chuggy.humptyBumptyScore = 0;
        chuggy.onRestart(120); //wtf does this number do???
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


    //From http://code-4-games.blogspot.co.uk/2015/02/teil-2-admob-in-einem-libgdx-android.html

    public int getTimesToAd() {
        return timesToAd;

    }

    public void resetTimesToAd() {
        this.timesToAd = rnd.nextInt(5) + 4;

    }

    public void decrementTimesToAd(int decrement) {
        this.timesToAd = timesToAd - decrement;
    }


//

}
