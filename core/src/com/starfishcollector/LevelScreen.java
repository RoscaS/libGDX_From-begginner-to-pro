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
import com.framework.BaseActor;
import com.framework.BaseGame;
import com.framework.BaseScreen;
import com.framework.DialogBox;

public class LevelScreen extends BaseScreen {

    private Turtle turtle;
    private boolean win;

    private Label starfishLabel;
    private DialogBox dialogBox;

    private final String rockClass = Rock.class.getCanonicalName();
    private final String signClass = Sign.class.getCanonicalName();
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

        // add info signes
        Sign s1 = new Sign(20,400, mainStage);
        Sign s2 = new Sign(600,300, mainStage);
        s1.setText("West Starfish Bay");
        s2.setText("East Starfish Bay");

        // Ui initialization
        starfishLabel = new Label("Starfish left:", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener((Event e) -> {
            InputEvent ie = (InputEvent) e;
            if (ie.getType().equals(InputEvent.Type.touchDown))
                StarfishGame.setActiveScreen(new LevelScreen());
            return false;
        });

        dialogBox = new DialogBox(0,0, uiStage);
        dialogBox.setBackgroundColor( Color.TAN );
        dialogBox.setFontColor( Color.BROWN );
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
        uiTable.row();
        uiTable.add(dialogBox).colspan(3);
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

        // starfish collection
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

        // sign dialogbox proximity check
        for (BaseActor signActor : BaseActor.getList(mainStage, signClass)) {
            Sign sign = (Sign)signActor;
            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);

            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }

        // victory condition
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

    /*------------------------------------------------------------------*\
   	|*							Private Methods 						*|
   	\*------------------------------------------------------------------*/
}
