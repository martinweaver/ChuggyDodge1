package com.weavernap.cdHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AssetLoader {

    public static Texture texture, logoTexture, textureChuggy, textureChugger, kerbTexture, cdLogoTexture, busTexture, tapTapChuggyTexture,
                            tipVideosTexture;
    public static TextureRegion logo,  retryButtonUp, retryButtonDown,leaderboardButton, achievementsButton, playButtonUp, playButtonDown,
            ready, highScore, scoreboard,
            retry,
            chuggyForward, chuggyBack, chuggy, chuggerUp, chugger, chuggerDown,
            chuggerTop, chuggerCentre, chuggerBottom, kerb, cdLogo, carPic,
            busOne, busTwo, busThree, smile2, smile3, smile4, smile5, smile42, smile100, rate, share,
            facebook, tapTapChuggy, tipVideos;

    public static Animation chuggerAnimation, chuggyAnimation, busAnimation;


    public static Sound  coin, flap, fall, lunch;
    public static BitmapFont font, shadow, whiteFont;

    public static Preferences prefs;


    public static void load() {

        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 2100, 500);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        textureChuggy = new Texture(Gdx.files.internal("data/texturechuggy.png"));
        textureChuggy.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        textureChugger = new Texture(Gdx.files.internal("data/texturechugger.png"));
        textureChugger.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        kerbTexture = new Texture(Gdx.files.internal("data/kerbTexture.png"));
        kerbTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        kerb = new TextureRegion(kerbTexture, 0, 0, 159, 500);
        kerb.flip(false, true);


        busTexture = new Texture(Gdx.files.internal("data/bustexture.png"));
        busTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        carPic = new TextureRegion(busTexture, 0, 0, 100, 50);
        carPic.flip(false, true);

        cdLogoTexture = new Texture(Gdx.files.internal("data/cdlogo.png"));
        cdLogoTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        cdLogo = new TextureRegion(cdLogoTexture, 0, 0, 118, 46);
        cdLogo.flip(false, true);



        tapTapChuggyTexture = new Texture(Gdx.files.internal("data/taptapchuggy.png"));
        tapTapChuggyTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        tapTapChuggy = new TextureRegion(tapTapChuggyTexture, 0, 0, 830, 519);
        tapTapChuggy.flip(false, true);

        tipVideosTexture = new Texture(Gdx.files.internal("data/tipVideos.png"));
        tipVideosTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        tipVideos = new TextureRegion(tipVideosTexture, 0, 0, 480, 230);
        tipVideos.flip(false, true);


        playButtonUp = new TextureRegion(texture, 214, 182, 55, 27);
        playButtonDown = new TextureRegion(texture, 214, 155, 55, 27);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        retryButtonUp = new TextureRegion(texture, 213, 80, 44, 22);
        retryButtonDown = new TextureRegion(texture, 213, 103, 44, 22);
        retryButtonUp.flip(false, true);
        retryButtonDown.flip(false, true);

        achievementsButton = new TextureRegion(texture, 190, 133, 16, 16);
        achievementsButton.flip(false, true);

        leaderboardButton = new TextureRegion(texture, 215, 131, 25, 21);
        leaderboardButton.flip(false, true);



        ready = new TextureRegion(texture, 161, 63, 97, 17);
        ready.flip(false, true);


        highScore = new TextureRegion(texture, 160, 30, 93, 31);
        highScore.flip(false, true);


        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);



        scoreboard = new TextureRegion(texture, 6, 1, 158, 203);
        scoreboard.flip(false, true);



        smile2 = new TextureRegion(texture, 160, 1, 33, 30);
        smile2.flip(false, true);

        smile3 = new TextureRegion(texture, 193, 1, 33, 30);
        smile3.flip(false, true);

        smile4 = new TextureRegion(texture, 226, 1, 33, 30);
        smile4.flip(false, true);

        smile5 = new TextureRegion(texture, 259, 1, 33, 30);
        smile5.flip(false, true);

        smile42 = new TextureRegion(texture, 260, 30, 36, 41);
        smile42.flip(false, true);

        smile100 = new TextureRegion(texture, 262, 79, 34, 34);
        smile100.flip(false, true);

        rate = new TextureRegion(texture, 160, 80, 48, 23);
        rate.flip(false, true);

        share = new TextureRegion(texture, 160, 103, 37, 24);
        share.flip(false, true);

        facebook = new TextureRegion(texture, 161, 154, 43, 43);
        facebook.flip(false, true);



        chuggyForward = new TextureRegion(textureChuggy, 74, 8, 12, 17);
        chuggyForward.flip(false, true);

        chuggy = new TextureRegion(textureChuggy, 42, 8, 12, 17);
        chuggy.flip(false, true);

        chuggyBack = new TextureRegion(textureChuggy, 10, 8, 12, 17);
        chuggyBack.flip(false, true);

        TextureRegion[] chuggys = {chuggyForward, chuggy, chuggyBack};
        chuggyAnimation = new Animation(0.13f, chuggys);
        chuggyAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        chuggerUp = new TextureRegion(textureChugger, 71, 8, 17, 16);
        chuggerUp.flip(false, true);

        chugger = new TextureRegion(textureChugger, 39, 8, 17, 16);
        chugger.flip(false, true);

        chuggerDown = new TextureRegion(textureChugger, 7, 8, 17, 16);
        chuggerDown.flip(false, true);

        TextureRegion[] chuggers = {chuggerUp, chugger, chuggerDown};
        chuggerAnimation = new Animation(0.13f, chuggers);
        chuggerAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        chuggerTop = new TextureRegion(textureChugger, 70, 8, 17, 17);
        chuggerTop.flip(false, true);
        chuggerCentre = new TextureRegion(textureChugger, 40, 8, 17, 17);
        chuggerCentre.flip(false, true);
        chuggerBottom = new TextureRegion(textureChugger, 8, 8, 17, 17);
        chuggerBottom.flip(false, true);

        busOne = new TextureRegion(busTexture, 0, 0, 67, 49);
        busOne.flip(false, true);

        busTwo = new TextureRegion(busTexture, 100, 0, 67, 49);
        busTwo.flip(false, true);

        busThree = new TextureRegion(busTexture, 200, 0, 67, 49);
        busThree.flip(false, true);

        TextureRegion[] buses = {busOne, busTwo, busThree};
        busAnimation = new Animation(0.2f, buses);
        busAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);




     //   dead = Gdx.audio.newSound(Gdx.files.internal("data/chugged8.wav")); //orig dead
        flap = Gdx.audio.newSound(Gdx.files.internal("data/no15H10.wav")); // flap
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin4H10.wav")); // coin
        fall = Gdx.audio.newSound(Gdx.files.internal("data/chugged6H10.wav")); // fall
        lunch = Gdx.audio.newSound(Gdx.files.internal("data/lunch3H10.wav")); // fall

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        whiteFont.getData().setScale(.1f, -.1f);

        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("Chugger Dodge");

// Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
        flap.dispose();
        coin.dispose();
        fall.dispose();
        lunch.dispose();


        font.dispose();
        shadow.dispose();

        logoTexture.dispose();
        textureChuggy.dispose();
        textureChugger.dispose();
        kerbTexture.dispose();
        cdLogoTexture.dispose();
        busTexture.dispose();
        tapTapChuggyTexture.dispose();

    }

}
