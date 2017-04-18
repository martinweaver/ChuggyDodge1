package com.weavernap.cdHelpers;


import com.badlogic.gdx.Gdx;
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


    private List<SimpleButton> menuButtons, retryButtons, achievementsButtons, leaderboardButtons,
            rateButtons, shareButtons, facebookButtons, tipButtons;

    private SimpleButton playButton, retryButton, achievementsButton, leaderboardButton,
            rateButton, shareButton, facebookButton, tipButton;

    private float scaleFactorX;
    private float scaleFactorY;
//    private AdsController adsController;

    // Ask for a reference to Chuggy when InputHandler is created.
    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        // myChuggy now represents the gameWorld's chuggy.
        this.myWorld = myWorld;
        myChuggy = myWorld.getChuggy();

        //   int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                132, 54, 25, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        menuButtons.add(playButton);

        retryButtons = new ArrayList<SimpleButton>();
        retryButton = new SimpleButton(47, 122, 44, 22, AssetLoader.retryButtonUp,
                AssetLoader.retryButtonDown);
        retryButtons.add(retryButton);

        achievementsButtons = new ArrayList<SimpleButton>();
        this.achievementsButton = new SimpleButton(25.0f, 126.0f, 15.0f, 15.0f, AssetLoader.achievementsButton,
                AssetLoader.achievementsButton);
        achievementsButtons.add(achievementsButton);

        leaderboardButtons = new ArrayList<SimpleButton>();
        this.leaderboardButton = new SimpleButton(97.0f, 126.0f, 16.0f, 16.0f, AssetLoader.leaderboardButton,
                AssetLoader.leaderboardButton);
        leaderboardButtons.add(leaderboardButton);

        rateButtons = new ArrayList<SimpleButton>();
        this.rateButton = new SimpleButton(44.5f, 145.0f, 48.0f, 23.0f, AssetLoader.rate,
                AssetLoader.rate);
        rateButtons.add(rateButton);

        tipButtons = new ArrayList<SimpleButton>();
        this.tipButton = new SimpleButton(54f, 148.0f, 30.0f, 14.79f, AssetLoader.tipVideos,
                AssetLoader.tipVideos);
        tipButtons.add(tipButton);

        shareButtons = new ArrayList<SimpleButton>();
        this.shareButton = new SimpleButton(3.0f, 3.0f, 37.0f, 24.0f, AssetLoader.share,
                AssetLoader.share);
        shareButtons.add(shareButton);

        facebookButtons = new ArrayList<SimpleButton>();
        this.facebookButton = new SimpleButton(112.0f, 5.0f, 20.0f, 20.0f, AssetLoader.facebook,
                AssetLoader.facebook);
        facebookButtons.add(facebookButton);


    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);

        } else if (myWorld.isReady()) {
            myWorld.start(); // put this in touchup??

        } else if (myWorld.isRunning()) {
            myChuggy.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            retryButton.isTouchDown(screenX, screenY);
            achievementsButton.isTouchDown(screenX, screenY);
            leaderboardButton.isTouchDown(screenX, screenY);
          //  rateButton.isTouchDown(screenX, screenY);
            shareButton.isTouchDown(screenX, screenY);
            facebookButton.isTouchDown(screenX, screenY);
           // tipButton.isTouchDown(screenX, screenY);
        }

        if (myWorld.isGameOver()) {

            tipButton.isTouchDown(screenX, screenY);
        }

        if (myWorld.isHighScore()) {

            rateButton.isTouchDown(screenX, screenY);

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
            if (this.shareButton.isTouchUp(screenX, screenY)) {
                this.myWorld.getOnInviteClicked();
            }
            if (this.facebookButton.isTouchUp(screenX, screenY)) {
                Gdx.net.openURI("https://www.facebook.com/chuggerdodge");
            }

        }

        if (myWorld.isGameOver()) {
            if (myWorld.getScore() < 10 && this.tipButton.isTouchUp(screenX, screenY)) {
                Gdx.net.openURI("https://www.youtube.com/watch?v=4OB8eDb9Z0U&list=PLElAkR7oAB3Biwe4pNYGm2JJL7AKkwfVI");
            }
        }

        if (myWorld.isHighScore()) {
            if (myWorld.getScore() > 19 && this.rateButton.isTouchUp(screenX, screenY)) {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.weavernap.chuggerdodge");
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
    public List<SimpleButton> getRateButtons() {
        return rateButtons;
    }
    public List<SimpleButton> getShareButtons() {
        return shareButtons;
    }
    public List<SimpleButton> getFacebookButtons() {
        return facebookButtons;
    }

    public List<SimpleButton> getTipButtons() {
        return tipButtons;
    }
}
