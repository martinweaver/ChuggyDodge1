package com.weavernap.screens;

/**
 * Created by martinweaver on 01/01/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.weavernap.TweenAccessors.SpriteAccessor;
import com.weavernap.cdHelpers.AdsController;
import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.chuggydodge.CDGame;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen implements Screen {

    private AdsController adsController;
    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite;
    private CDGame game;

    public SplashScreen(CDGame game, AdsController adsController) {
        this.game = game;
        this.adsController = adsController;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1, 1, 1, 0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * .9f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale * 2, sprite.getHeight() * scale * 2);
        sprite.setPosition((width ) - (sprite.getWidth() / 2), (height / 2)
                - (sprite.getHeight() / 3));
        setupTween();
        batcher = new SpriteBatch();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen(SplashScreen.this.adsController));
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 1.5f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 1f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}