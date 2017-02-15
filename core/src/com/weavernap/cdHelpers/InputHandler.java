package com.weavernap.cdHelpers;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.weavernap.gameobjects.Chuggy;
import com.weavernap.gameworld.GameWorld;
import com.weavernap.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martinweaver on 27/12/2016.
 */

public class InputHandler implements InputProcessor {

    private Chuggy myChuggy;
    private GameWorld myWorld;

    private List<SimpleButton> menuButtons, retryButtons, achievementsButtons, leaderboardButtons;

    private SimpleButton playButton, retryButton, achievementsButton, leaderboardButton;

    private float scaleFactorX;
    private float scaleFactorY;
//    private AdsController adsController;

    // Ask for a reference to Chuggy when InputHandler is created.
    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        // myChuggy now represents the gameWorld's chuggy.
        this.myWorld = myWorld;
        myChuggy = myWorld.getChuggy();

        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 25, 54, 25, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(playButton);

        retryButtons = new ArrayList<SimpleButton>();
        retryButton = new SimpleButton(47, 130, 44, 22, AssetLoader.retryButtonUp,
                AssetLoader.retryButtonDown);
        retryButtons.add(retryButton);

        achievementsButtons = new ArrayList<SimpleButton>();
        this.achievementsButton = new SimpleButton(25.0f, 134.0f, 15.0f, 15.0f, AssetLoader.achievementsButton,
                AssetLoader.achievementsButton);
        achievementsButtons.add(achievementsButton);

        leaderboardButtons = new ArrayList<SimpleButton>();
        this.leaderboardButton = new SimpleButton(97.0f, 134.0f, 16.0f, 16.0f, AssetLoader.leaderboardButton,
                AssetLoader.leaderboardButton);
        leaderboardButtons.add(leaderboardButton);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
//            achievementsButton.isTouchDown(screenX, screenY);
//            leaderboardButton.isTouchDown(screenX, screenY);

        } else if (myWorld.isReady()) {
            myWorld.start(); // put this in touchup??

        } else if (myWorld.isRunning()) {
            myChuggy.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            retryButton.isTouchDown(screenX, screenY);
            achievementsButton.isTouchDown(screenX, screenY);
            leaderboardButton.isTouchDown(screenX, screenY);
        }

        return true; // Return true to say we handled the touch.;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }


//            if (this.achievementsButton.isTouchUp(screenX, screenY)) {
//                this.myWorld.getAchievementsGPGS();
//                return true;
//            }
//
//            if (this.leaderboardButton.isTouchUp(screenX, screenY));
//           // if (myWorld.adsController.getSignedInGPGS())
//            {
//                this.myWorld.getLeaderboardGPGS();
//                return true;
//            }



        }
        if (myWorld.isReady()) {

        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            if (retryButton.isTouchUp(screenX, screenY)) {

                myWorld.restart();
                //German guy's code Toastergame

                if (myWorld.getTimesToAd() <= 0) {
                    myWorld.resetTimesToAd();
                    myWorld.getAdsController().showOrLoadInterstitial(true);
                    return true;
                }
                //else
                {
                    myWorld.getAdsController().showOrLoadInterstitial(false);
                    myWorld.decrementTimesToAd(1);
                    return true;
                }

            }

            if (this.achievementsButton.isTouchUp(screenX, screenY)) {
                this.myWorld.getAchievementsGPGS();
            }
            if (this.leaderboardButton.isTouchUp(screenX, screenY)) {
                this.myWorld.getLeaderboardGPGS();
            }


        }
        return false;


    }

    @Override
    public boolean keyDown(int keycode) {

        // Can now use Space Bar to play the game
        if (keycode == Input.Keys.SPACE) {

            if (myWorld.isMenu()) {
                myWorld.ready();
            } else if (myWorld.isReady()) {
                myWorld.start();
            } else if (myWorld.isRunning()) {
                myChuggy.onClick();
            }

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                myWorld.restart();
            }

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }

    public List<SimpleButton> getRetryButtons() {
        return retryButtons;
    }

    public List<SimpleButton> getAchievementsButtons() {
        return achievementsButtons;
    }

    public List<SimpleButton> getLeaderboardButtons() {
        return leaderboardButtons;
    }
}
