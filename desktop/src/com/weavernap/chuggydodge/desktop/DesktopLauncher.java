package com.weavernap.chuggydodge.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.weavernap.chuggydodge.CDGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Chuggy Dodge";
		config.width = 272;
		config.height = 408;
		new LwjglApplication(new CDGame(), config);
	}
}
