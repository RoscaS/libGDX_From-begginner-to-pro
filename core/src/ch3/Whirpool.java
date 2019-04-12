package ch3;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Whirpool extends BaseActor {

    public Whirpool(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("ch3/whirlpool.png", 2, 5, 0.1f, false);
    }

    public void act(float dt) {
        super.act(dt);
        if (isAnimationFinished()) {
            remove();
        }
    }

}
