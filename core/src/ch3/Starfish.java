package ch3;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Starfish extends BaseActor {

    public Starfish(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("ch3/starfish.png");
        Action spin = Actions.rotateBy(30, 1);
        this.addAction(Actions.forever(spin));
    }
}
