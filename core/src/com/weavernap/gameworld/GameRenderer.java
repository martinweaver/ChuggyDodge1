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

    // Game Objects
    private Chuggy chuggy;
    private ScrollHandler scroller;
    private Kerb frontKerb, backKerb;
    private Chuggers chugger1, chugger2, chugger3;

    // Game Assets
    private TextureRegion bg, kerb, birdMid, skullUp, skullDown, bar, ready,
            zbLogo, gameOver, highScore, scoreboard, star, noStar, retry;
    private Animation birdAnimation;

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

    }

    private void initAssets() {
        bg = AssetLoader.bg;
        kerb = AssetLoader.kerb;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
        ready = AssetLoader.ready;
        zbLogo = AssetLoader.zbLogo;
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

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

        batcher.draw(skullUp, chugger1.getX() - 1,
                chugger1.getY() + chugger1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, chugger1.getX() - 1,
                chugger1.getY() + chugger1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, chugger2.getX() - 1,
                chugger2.getY() + chugger2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, chugger2.getX() - 1,
                chugger2.getY() + chugger2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, chugger3.getX() - 1,
                chugger3.getY() + chugger3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, chugger3.getX() - 1,
                chugger3.getY() + chugger3.getHeight() + 45, 24, 14);
    }

    private void drawChuggers() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
        batcher.draw(bar, chugger1.getX(), chugger1.getY(), chugger1.getWidth(),
                chugger1.getHeight());
        batcher.draw(bar, chugger1.getX(), chugger1.getY() + chugger1.getHeight() + 45,
                chugger1.getWidth(), midPointY + 66 - (chugger1.getHeight() + 45));

        batcher.draw(bar, chugger2.getX(), chugger2.getY(), chugger2.getWidth(),
                chugger2.getHeight());
        batcher.draw(bar, chugger2.getX(), chugger2.getY() + chugger2.getHeight() + 45,
                chugger2.getWidth(), midPointY + 66 - (chugger2.getHeight() + 45));

        batcher.draw(bar, chugger3.getX(), chugger3.getY(), chugger3.getWidth(),
                chugger3.getHeight());
        batcher.draw(bar, chugger3.getX(), chugger3.getY() + chugger3.getHeight() + 45,
                chugger3.getWidth(), midPointY + 66 - (chugger3.getHeight() + 45));
    }

    private void drawBirdCentered(float runTime) {
        batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), 59, chuggy.getY() - 15,
                chuggy.getWidth() / 2.0f, chuggy.getHeight() / 2.0f,
                chuggy.getWidth(), chuggy.getHeight(), 1, 1, chuggy.getRotation());
    }

    private void drawBird(float runTime) {

        if (chuggy.shouldntWalk()) {
            batcher.draw(birdMid, chuggy.getX(), chuggy.getY(),
                    chuggy.getWidth() / 2.0f, chuggy.getHeight() / 2.0f,
                    chuggy.getWidth(), chuggy.getHeight(), 1, 1, chuggy.getRotation());

        } else {
            batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), chuggy.getX(),
                    chuggy.getY(), chuggy.getWidth() / 2.0f,
                    chuggy.getHeight() / 2.0f, chuggy.getWidth(), chuggy.getHeight(),
                    1, 1, chuggy.getRotation());
        }

    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.zbLogo, 136 / 2 - 56, midPointY - 50,
                AssetLoader.zbLogo.getRegionWidth() / 1.2f,
                AssetLoader.zbLogo.getRegionHeight() / 1.2f);

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

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(11 / 255.0f, 16 / 255.0f, 45 / 25.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(24 / 255.0f, 18 / 255.0f, 22 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, 136, 43);

        drawKerb();

        drawChuggers();
        // The chuggy needs transparency, so we enable that again.
        batcher.enableBlending();

        drawSkulls();

		if (myWorld.isRunning()) {
			drawBird(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawBird(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawBird(runTime);
			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawBird(runTime);
			drawHighScore();
			drawRetry();
		}


        // End SpriteBatch
        batcher.end();

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
