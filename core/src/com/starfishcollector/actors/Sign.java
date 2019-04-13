package com.starfishcollector.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.framework.BaseActor;

public class Sign extends BaseActor {

    private String text; // text to be displayed
    private boolean viewing; // determine if sign text is being displayed

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    public Sign(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("sign.png");
        text = " ";
        viewing = false;
    }

	/*------------------------------*\
	|*				Getters			*|
	\*------------------------------*/

    public String getText() {
        return text;
    }

    public boolean isViewing() {
        return viewing;
    }

	/*------------------------------*\
	|*				Setters			*|
	\*------------------------------*/

    public void setText(String t) {
        text = t;
    }

    public void setViewing(boolean v) {
        viewing = v;
    }
}
