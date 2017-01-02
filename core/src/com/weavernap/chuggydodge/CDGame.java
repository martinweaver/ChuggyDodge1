package com.weavernap.chuggydodge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.weavernap.cdHelpers.AssetLoader;
import com.weavernap.screens.SplashScreen;

public class CDGame extends Game {
	@Override
	public void create() {
		Gdx.app.log("CDGame", "created");
		AssetLoader.load();
		setScreen(new SplashScreen(this));

	}
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
