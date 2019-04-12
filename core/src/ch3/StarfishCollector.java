package ch3;

public class StarfishCollector extends GameBeta {

    private Turtle turtle;
    private Starfish startFish;
    private BaseActor ocean;

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    @Override
    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("ch3/water.jpg");
        ocean.setSize(800, 600);

        startFish = new Starfish(380, 380, mainStage);
        turtle = new Turtle(20, 20, mainStage);
    }

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

	public void update(float dt) {
	    // code will be added later
    }

	/*------------------------------------------------------------------*\
	|*							Private Methods 						*|
	\*------------------------------------------------------------------*/
}
