package ch3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MenuScreen extends BaseScreen {

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

	public void initialize() {
	    BaseActor ocean = new BaseActor(0, 0, mainStage);
	    ocean.loadTexture("ch3/water.jpg");
	    ocean.setSize(800, 600);

	    BaseActor title = new BaseActor(0, 0, mainStage);
	    title.loadTexture("ch3/starfish-collector.png");
	    title.centerAtPosition(400, 300);
	    title.moveBy(0, 100);

	    BaseActor start = new BaseActor(0, 0, mainStage);
	    start.loadTexture("ch3/message-start.png");
	    start.centerAtPosition(400, 300);
	    start.moveBy(0, -100);
    }

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

	public void update(float dt) {
	    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
	        StarfishGame.setActiveScreen(new LevelScreen());
        }
    }
}
