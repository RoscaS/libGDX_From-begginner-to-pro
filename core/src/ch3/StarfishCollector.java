package ch3;

import ch2.Rock;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class StarfishCollector extends GameBeta {

    private Turtle turtle;
    private boolean win;
    private Starfish starfish;

    private final String rockClass = Rock.class.getCanonicalName();
    private final String starfishClass = Starfish.class.getCanonicalName();

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    @Override
    public void initialize() {

        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("ch3/water.jpg");
        ocean.setSize(800, 600);
        BaseActor.setWorldBounds(ocean);

        win = false;
        turtle = new Turtle(20, 20, mainStage);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 150, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);
    }

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

	public void update(float dt) {

        for (BaseActor rockActor : BaseActor.getList(mainStage,  rockClass)) {
            turtle.preventOverlap(rockActor);
            // rock.preventOverlap(turtle); // turtle pushes the rock !
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage,  starfishClass)) {
            Starfish starfish = (Starfish)starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.setCollected(true);
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirpool whirpool = new Whirpool(0, 0, mainStage);
                whirpool.centerAtActor(starfish);
                whirpool.setOpacity(0.25f);
            }
        }

        if (BaseActor.count(mainStage, starfishClass) == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
       	    youWinMessage.loadTexture("ch3/you-win.png");
       	    youWinMessage.centerAtPosition(400, 300);
       	    youWinMessage.setOpacity(0);
       	    youWinMessage.addAction(Actions.delay(1));
       	    youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
