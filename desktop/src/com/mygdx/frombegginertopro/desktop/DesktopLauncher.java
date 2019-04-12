package com.mygdx.frombegginertopro.desktop;

import ch2.StarfishCollectorBeta;
import ch3.StarfishCollector;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new StarfishCollector(), config);
	}
}
