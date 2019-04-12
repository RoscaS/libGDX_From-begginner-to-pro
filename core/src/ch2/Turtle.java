package ch2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Turtle extends ActorBeta {

    /*------------------------------------------------------------------*\
   	|*							Constructors							*|
   	\*------------------------------------------------------------------*/

    public Turtle() {
        super();
    }

    /*------------------------------------------------------------------*\
   	|*							Public Methods 							*|
   	\*------------------------------------------------------------------*/

    @Override
    public void act(float dt) {
        super.act(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveBy(-5, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveBy(5, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            moveBy(0, 5);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveBy(0, -5);
    }
}
