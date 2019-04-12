package com.mygdx.frombegginertopro.desktop;

import ch3.StarfishGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new StarfishGame();
		LwjglApplication launcher = new LwjglApplication(myGame, "Starfish Collector", 800, 600);
	}
}
