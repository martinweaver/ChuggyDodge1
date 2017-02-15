package com.weavernap.chuggydodge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.weavernap.cdHelpers.*;
import com.weavernap.cdHelpers.AdsController;
import com.weavernap.screens.SplashScreen;

public class CDGame extends Game {
	private AdsController adsController;


	@Override
	public void create() {
		Gdx.app.log("CDGame", "created");
		AssetLoader.load();
		setScreen(new SplashScreen (this, this.adsController));
//		if (adsController.isWifiConnected()) {
		//	adsController.showBannerAd();

		//}

	}


	public CDGame(AdsController adsController){
		//if (adsController != null) {
			this.adsController = adsController;
		//} else {
		//	this.adsController = new DummyAdsController();
	//	}
}





	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
