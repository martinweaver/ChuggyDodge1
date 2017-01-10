package com.weavernap.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.weavernap.TweenAccessors.Value;
import com.weavernap.TweenAccessors.ValueAccessor;
import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.cdHelpers.InputHandler;
import com.weavernap.gameobjects.Car;
import com.weavernap.gameobjects.Chuggers;
import com.weavernap.gameobjects.Chuggy;
import com.weavernap.gameobjects.Kerb;
import com.weavernap.gameobjects.ScrollHandler;
import com.weavernap.ui.SimpleButton;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by martinweaver on 27/12/2016.
 */

public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;

    public static final float CHUGGY_WIDTH = 12 * 1.2f;
    public static final float CHUGGY_HEIGHT = 17 * 1.2f;

    // Game Objects
    private Chuggy chuggy;
    private ScrollHandler scroller;
    private Kerb frontKerb, backKerb;
    private Chuggers chugger1, chugger2, chugger3; //Refers to 1st, 2nd & 3rd to appear on screen
    private Car car1, car2;

    // Game Assets
    private TextureRegion kerb;
    private TextureRegion ready;
    private TextureRegion gameOver;
    private TextureRegion highScore;
    private TextureRegion scoreboard;
    private TextureRegion star;
    private TextureRegion noStar;
    private TextureRegion retry;
    private TextureRegion chuggyMid;

    private Animation chuggyAnimation, chuggerAnimation, busAnimation;


    // Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private Color transitionColor;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        // The word "this" refers to this instance.
        // We are setting the instance variables' values to be that of the
        // parameters passed in from GameScreen.
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();


		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

    private void initGameObjects() {
        chuggy = myWorld.getChuggy();
        scroller = myWorld.getScroller();
        frontKerb = scroller.getFrontKerb();
        backKerb = scroller.getBackKerb();
        chugger1 = scroller.getChugger1();
        chugger2 = scroller.getChugger2();
        chugger3 = scroller.getChugger3();
        car1 = scroller.getCar1();
        car2 = scroller.getCar2();

    }

    private void initAssets() {

        kerb = AssetLoader.kerb;
        chuggyAnimation = AssetLoader.chuggyAnimation;
        chuggyMid = AssetLoader.chuggy;

        chuggerAnimation = AssetLoader.chuggerAnimation;

        busAnimation = AssetLoader.busAnimation;

        ready = AssetLoader.ready;
        gameOver = AssetLoader.gameOver;
        highScore = AssetLoader.highScore;
        scoreboard = AssetLoader.scoreboard;
        retry = AssetLoader.retry;
        star = AssetLoader.star;
        noStar = AssetLoader.noStar;
    }

    private void drawKerb() {
        // Draw the kerb
        batcher.draw(kerb, frontKerb.getX(), frontKerb.getY(),
                frontKerb.getWidth(), frontKerb.getHeight());
        batcher.draw(kerb, backKerb.getX(), backKerb.getY(),
                backKerb.getWidth(), backKerb.getHeight());
    }


    private void drawChuggers(float runTime) {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger1.getChuggerTop().x - 11, chugger1.getChuggerTop().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger1.getChuggerCentre().x - 11, chugger1.getChuggerCentre().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger1.getChuggerBottom().x - 11, chugger1.getChuggerBottom().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);

        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger2.getChuggerTop().x - 11, chugger2.getChuggerTop().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger2.getChuggerCentre().x - 11, chugger2.getChuggerCentre().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger2.getChuggerBottom().x - 11, chugger2.getChuggerBottom().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);

        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger3.getChuggerTop().x - 11, chugger3.getChuggerTop().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger3.getChuggerCentre().x - 11, chugger3.getChuggerCentre().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
        batcher.draw((TextureRegion) chuggerAnimation.getKeyFrame(runTime), chugger3.getChuggerBottom().x - 11, chugger3.getChuggerBottom().y - 10,
                Chuggers.CHUGGER_WIDTH, Chuggers.CHUGGER_HEIGHT);
    }

    private void drawCar(float runTime){
        batcher.draw((TextureRegion) busAnimation.getKeyFrame(runTime), car1.getCar1().x , car1.getCar1().y - 0,
                (float) (Car.CAR_WIDTH *1.3), (float) (Car.CAR_HEIGHT * 1.3));
        batcher.draw((TextureRegion) busAnimation.getKeyFrame(runTime), car2.getCar2().x  , car2.getCar2().y - 0,
                (float) (Car.CAR_WIDTH * 1.3), (float) (Car.CAR_HEIGHT *1.3));
    }



    private void drawChuggyCentered(float runTime) {
        batcher.draw((TextureRegion) chuggyAnimation.getKeyFrame(runTime), 59, chuggy.getY() - 15,
                CHUGGY_WIDTH/2, CHUGGY_HEIGHT/2,
                CHUGGY_WIDTH, CHUGGY_HEIGHT, 1, 1, chuggy.getRotation());
    }

    private void drawChuggy(float runTime) {

        if (chuggy.shouldntWalk()) {
            batcher.draw(chuggyMid, chuggy.getX(), chuggy.getY(),
                    CHUGGY_WIDTH/2, CHUGGY_HEIGHT/2,
                    CHUGGY_WIDTH, CHUGGY_HEIGHT, 1, 1, chuggy.getRotation());

        } else {
            batcher.draw((TextureRegion) chuggyAnimation.getKeyFrame(runTime), chuggy.getX(),
                    chuggy.getY(), CHUGGY_WIDTH/2,
                    CHUGGY_HEIGHT/2, CHUGGY_WIDTH, CHUGGY_HEIGHT,
                    1, 1, chuggy.getRotation());
        }

    }





    private void drawMenuUI() {
        batcher.draw(AssetLoader.cdLogo, 136 / 2 - 60, midPointY - 75,
                AssetLoader.cdLogo.getRegionWidth() / 1.1f,
                AssetLoader.cdLogo.getRegionHeight() / 1.1f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

	private void drawScoreboard() {
		batcher.draw(scoreboard, 22, midPointY - 30, 97, 37);

		batcher.draw(noStar, 25, midPointY - 15, 10, 10);
		batcher.draw(noStar, 37, midPointY - 15, 10, 10);
		batcher.draw(noStar, 49, midPointY - 15, 10, 10);
		batcher.draw(noStar, 61, midPointY - 15, 10, 10);
		batcher.draw(noStar, 73, midPointY - 15, 10, 10);

		if (myWorld.getScore() > 2) {
			batcher.draw(star, 73, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 17) {
			batcher.draw(star, 61, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 50) {
			batcher.draw(star, 49, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 80) {
			batcher.draw(star, 37, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 120) {
			batcher.draw(star, 25, midPointY - 15, 10, 10);
		}

		int length = ("" + myWorld.getScore()).length();

		AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
				104 - (2 * length), midPointY - 20);

		int length2 = ("" + AssetLoader.getHighScore()).length();
		AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
				104 - (2.5f * length2), midPointY - 3);

	}

	private void drawRetry() {
		batcher.draw(retry, 36, midPointY + 10, 66, 14);
	}

	private void drawReady() {
		batcher.draw(ready, 36, midPointY - 50, 68, 14);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
	}

	private void drawScore() {
		int length = ("" + myWorld.getScore()).length();
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
				68 - (3 * length), midPointY - 82);
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
				68 - (3 * length), midPointY - 83);
	}

	private void drawHighScore() {
		batcher.draw(highScore, 22, midPointY - 50, 96, 14);
	}

	public void render(float delta, float runTime) {


        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);





        // Begin SpriteBatch
        batcher.begin();
        batcher.disableBlending();

        drawKerb();

        // The chuggy needs transparency, so we enable that again.
        batcher.enableBlending();




		if (myWorld.isRunning()) {
			drawChuggy(runTime);
            drawChuggers(runTime);

            drawCar(runTime);

            drawScore();
		} else if (myWorld.isReady()) {
			drawChuggy(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawChuggyCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
            drawChuggers(runTime);
            drawCar(runTime);
            drawChuggy(runTime);
            drawScoreboard();

			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawChuggy(runTime);
			drawHighScore();
			drawRetry();
		}


        // End SpriteBatch
        batcher.end();


//        shapeRenderer.begin(ShapeType.Line);
//
//
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(chuggy.getBoundingCircle().x, chuggy.getBoundingCircle().y, chuggy.getBoundingCircle().radius);
//
//
//
//        // Chugger Top for Chuggers 1st 2nd and 3rd Shows collision detection
//        shapeRenderer.circle(chugger1.getChuggerTop().x, chugger1.getChuggerTop().y,
//                chugger1.getChuggerTop().radius);
//
//        shapeRenderer.circle(chugger2.getChuggerTop().x, chugger2.getChuggerTop().y,
//                chugger2.getChuggerTop().radius);
//
//        shapeRenderer.circle(chugger3.getChuggerTop().x, chugger3.getChuggerTop().y,
//                chugger3.getChuggerTop().radius);
//
//
//        // Chugger Centre for Chuggers 1 2 and 3
//        shapeRenderer.circle(chugger1.getChuggerCentre().x, chugger1.getChuggerCentre().y,
//                chugger1.getChuggerCentre().radius);
//
//        shapeRenderer.circle(chugger2.getChuggerCentre().x, chugger2.getChuggerCentre().y,
//                chugger2.getChuggerCentre().radius);
//
//        shapeRenderer.circle(chugger3.getChuggerCentre().x, chugger3.getChuggerCentre().y,
//                chugger3.getChuggerCentre().radius);
//
//
//        // Chugger Bottom for Chuggers 1 2 and 3
//        shapeRenderer.circle(chugger1.getChuggerBottom().x, chugger1.getChuggerBottom().y,
//                chugger1.getChuggerBottom().radius);
//
//        shapeRenderer.circle(chugger2.getChuggerBottom().x, chugger2.getChuggerBottom().y,
//                chugger2.getChuggerBottom().radius);
//
//        shapeRenderer.circle(chugger3.getChuggerBottom().x, chugger3.getChuggerBottom().y,
//                chugger3.getChuggerBottom().radius);
//
//
//
//        shapeRenderer.rect(car1.getCar1().x, car1.getCar1().y, car1.getCar1().width, car1.getCar1().height);
//
//        shapeRenderer.rect(car2.getCar2().x, car2.getCar2().y, car2.getCar2().width, car2.getCar2().height);
//
//        shapeRenderer.end();

       drawTransition(delta);

    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

}
