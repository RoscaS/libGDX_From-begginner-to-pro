package com.starfishcollector;

import com.framework.BaseGame;
import com.starfishcollector.screens.MenuScreen;

public class StarfishGame extends BaseGame {

	public void create() {
		super.create();
	    setActiveScreen(new MenuScreen());
    }
}
