package com.starfishcollector.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.framework.BaseActor;
import com.framework.BaseGame;
import com.framework.BaseScreen;
import com.starfishcollector.StarfishGame;

public class MenuScreen extends BaseScreen {

    // audio
    private Music oceanSurf;

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("starfish-collector.png");

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
        startButton.addListener((Event e) -> {
            if (!(e instanceof InputEvent)) return false;
            if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;
            StarfishGame.setActiveScreen(new StoryScreen());
            return true;
        });

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        quitButton.addListener((Event e) -> {
            if (!(e instanceof InputEvent)) return false;
            if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown)) return false;
            Gdx.app.exit();
            return true;
        });

        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("audio/Ocean_Waves.ogg"));
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(.5f);
        oceanSurf.play();

        uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);
    }

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

    @Override
    public void update(float dt) { }

    @Override
    public boolean keyDown(int KeyCode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) StarfishGame.setActiveScreen(new StoryScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        return false;
    }

	/*------------------------------------------------------------------*\
	|*							Private Methods 						*|
	\*------------------------------------------------------------------*/

}
