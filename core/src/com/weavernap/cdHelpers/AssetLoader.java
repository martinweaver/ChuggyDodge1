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

    public static Texture texture, logoTexture, textureChuggy, textureChugger, kerbTexture, cdLogoTexture;
    public static TextureRegion logo, zbLogo, playButtonUp, playButtonDown,
            ready, gameOver, highScore, scoreboard, star, noStar, retry,
            chuggyForward, chuggyBack, chuggy, chuggerUp, chugger, chuggerDown,
            chuggerTop, chuggerCentre, chuggerBottom, kerb, cdLogo, carPic;

    public static Animation chuggerAnimation, chuggyAnimation;


    public static Sound dead, coin, flap, fall;
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


        carPic = new TextureRegion(kerbTexture, 0, 0, 100, 50);
        carPic.flip(false, true);

        cdLogoTexture = new Texture(Gdx.files.internal("data/cdlogo.png"));
        cdLogoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        cdLogo = new TextureRegion(cdLogoTexture, 0, 40, 120, 55);
        cdLogo.flip(false, true);


        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreboard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        noStar = new TextureRegion(texture, 165, 70, 10, 10);

        star.flip(false, true);
        noStar.flip(false, true);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

//		zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
//		zbLogo.flip(false, true);


//        bg = new TextureRegion(texture, 0, 0, 136, 43);
//        bg.flip(false, true);


        chuggyForward = new TextureRegion(textureChuggy, 74, 8, 17, 17);
        chuggyForward.flip(false, true);

        chuggy = new TextureRegion(textureChuggy, 42, 8, 17, 17);
        chuggy.flip(false, true);

        chuggyBack = new TextureRegion(textureChuggy, 10, 8, 17, 17);
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

//        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
//        // Create by flipping existing skullUp
//        skullDown = new TextureRegion(skullUp);
//        skullDown.flip(false, true);

        chuggerTop = new TextureRegion(textureChugger, 70, 8, 17, 17);
        chuggerTop.flip(false, true);
        chuggerCentre = new TextureRegion(textureChugger, 40, 8, 17, 17);
        chuggerCentre.flip(false, true);
        chuggerBottom = new TextureRegion(textureChugger, 8, 8, 17, 17);
        chuggerBottom.flip(false, true);

//        bar = new TextureRegion(texture, 136, 16, 22, 3);
//        bar.flip(false, true);

        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        whiteFont.getData().setScale(.1f, -.1f);

        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("ChuggyDodge");

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
        dead.dispose();
        flap.dispose();
        coin.dispose();

        font.dispose();
        shadow.dispose();

        logoTexture.dispose();
        textureChuggy.dispose();
        textureChugger.dispose();
        kerbTexture.dispose();
        cdLogoTexture.dispose();
       // carTexture.dispose(); add once car is drawn
    }

}
