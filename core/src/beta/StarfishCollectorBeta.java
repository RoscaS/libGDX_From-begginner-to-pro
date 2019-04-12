package beta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class StarfishCollectorBeta extends GameBeta {

    private Shark shark;
    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta ocean;
    private ActorBeta winMessage;
    private ActorBeta gameOverMessage;

    private boolean win;

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

	@Override
    public void initialize() {
        ocean = new ActorBeta();
        ocean.setTexture(new Texture(Gdx.files.internal("ch2/water.jpg")));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture(Gdx.files.internal("ch2/starfish.png")));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal("ch2/turtle-1.png")));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);

        shark = new Shark();
        shark.setTexture(new Texture(Gdx.files.internal("ch2/sharky.png")));
        shark.setPosition(150, 150);
        mainStage.addActor(shark);

        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture(Gdx.files.internal("ch2/you-win.png")));
        winMessage.setPosition(180, 180);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

        gameOverMessage = new ActorBeta();
        gameOverMessage.setTexture(new Texture(Gdx.files.internal("ch2/game-over.png")));
        gameOverMessage.setPosition(180, 180);
        gameOverMessage.setVisible(false);
        mainStage.addActor(gameOverMessage);

        // shark.addAction(Actions.forever(Actions.rotateBy(1)));

        win = false;
    }

    @Override
    public void update(float dt) {
        // check win condition
        if (turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
        }
        if (turtle.overlaps(shark)) {
            turtle.remove();
            gameOverMessage.setVisible(true);
        }
    }
}
