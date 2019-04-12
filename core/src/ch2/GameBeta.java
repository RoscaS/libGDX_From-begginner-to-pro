package ch2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBeta extends Game {

    protected Stage mainStage;

    public void create() {
        mainStage = new Stage();
        initialize();
    }

    public void renderer() {
        float dt = Gdx.graphics.getDeltaTime();

        // act method
        mainStage.act(dt);

        // user defined
        update(dt);

        // clear the screem
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw graphics
        mainStage.draw();
    }

    public abstract void initialize();
    public abstract void update(float dt);
}
