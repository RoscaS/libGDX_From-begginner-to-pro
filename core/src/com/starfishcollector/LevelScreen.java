package com.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen extends BaseScreen {

    private Turtle turtle;
    private boolean win;

    private Label starfishLabel;

    private final String rockClass = Rock.class.getCanonicalName();
    private final String starfishClass = Starfish.class.getCanonicalName();

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    @Override
    public void initialize() {

        // initialize ocean texture
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        // initialize member attrs
        win = false;
        turtle = new Turtle(20, 20, mainStage);

        // add starfish actors to stage
        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        // add rock actors to stage
        new Rock(200, 150, mainStage);
        new Rock(100, 150, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        // initialize "starfish left" label
        starfishLabel = new Label("Starfish left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        starfishLabel.setPosition(20, 520);
        uiStage.addActor(starfishLabel);

        // "button restart" style
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTexture = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTexture);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        // initialize "button restart
        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);
        restartButton.setPosition(720, 520);
        uiStage.addActor(restartButton);

        restartButton.addListener((Event e) -> {
            if (!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown)) {
                return false;
            }
            StarfishGame.setActiveScreen(new LevelScreen());
            return false;
        });

    }

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

    public void update(float dt) {

        // update label
        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, starfishClass));

        for (BaseActor rockActor : BaseActor.getList(mainStage, rockClass)) {
            turtle.preventOverlap(rockActor);
            // rock.preventOverlap(turtle); // turtle pushes the rock !
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage, starfishClass)) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
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
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
