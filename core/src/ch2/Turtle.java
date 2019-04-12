package ch2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

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
            moveBy(-1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveBy(1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            moveBy(0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveBy(0, -1);
    }
}
