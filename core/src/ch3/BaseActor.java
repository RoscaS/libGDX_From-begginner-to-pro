package ch3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class BaseActor extends Actor {

    // Animation
    private Animation<TextureRegion> animation;
    private boolean animationPaused;
    private float elapsedTime;

    // Physics
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float deceleration;
    private float maxSpeed;

    // Collision
    private Polygon boundaryPolygon;

    private static Rectangle worldBounds;

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

    public BaseActor(float x, float y, Stage s) {
        super();

        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        deceleration = 0;
        maxSpeed = 1000;

        setPosition(x, y);
        s.addActor(this);
    }

    /*------------------------------------------------------------------*\
   	|*							Static methods							*|
   	\*------------------------------------------------------------------*/

    /*------------------------------*\
   	|*				Tools			*|
   	\*------------------------------*/

    /**
     * @param stage     target <b>Stage</b> of the extraction
     * @param className type of <b>BaseActor</b> to extract
     * @return <b>ArrayList</b> of <b>BaseActor</b>
     */
    public static ArrayList<BaseActor> getList(Stage stage, String className) {
        ArrayList<BaseActor> actors = new ArrayList<BaseActor>();
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Actor a : stage.getActors()) {
            if (theClass.isInstance(a)) {
                actors.add((BaseActor) a);
            }
        }
        return actors;
    }

    /**
     * @param stage     target of the counting
     * @param className type of <b>BaseActor</b> to count
     * @return count of <i>className</i> <b>BaseActor</b> on <b>Stage</b> <i>stage</i>
     */
    public static int count(Stage stage, String className) {
        return getList(stage, className).size();
    }

    /*------------------------------*\
   	|*				Collision		*|
   	\*------------------------------*/

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    /*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Getters			*|
	\*------------------------------*/

    public float getSpeed() {
        return velocityVec.len();
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

	/*------------------------------*\
	|*				Setters			*|
	\*------------------------------*/

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h / 2);

        if (boundaryPolygon == null) {
            setBoundaryRectangle();
        }
    }

    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public void setSpeed(float speed) {
        // if length is 0, assume motion angle is zero deg
        if (velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setOpacity(float opacity) {
        getColor().a = opacity;
    }

    /*------------------------------*\
   	|*				Tools   		*|
   	\*------------------------------*/

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    /**
     * Actor face the direction he is accelerating on
     */
    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor other) {
        centerAtPosition(
                other.getX() + other.getWidth() / 2,
                other.getY() + other.getHeight() / 2
        );
    }

    /*------------------------------*\
   	|*				Overriden		*|
   	\*------------------------------*/

    @Override
    public void act(float dt) {
        super.act(dt);
        if (!animationPaused) {
            elapsedTime += dt;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(),
                    getScaleY(), getRotation()
            );
        }
    }

    /*------------------------------*\
   	|*				Animation		*|
   	\*------------------------------*/

    /**
     * Create an animation from multiple image files.
     */
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration,
                                                           boolean loop) {

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (String name : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(name));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);

        if (animation == null) {
            setAnimation(anim);
        }
        return anim;
    }

    /**
     * Create an animation from a spriteSheet.
     */
    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols,
                                                           float frameDuration, boolean loop) {

        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);

        if (animation == null) {
            setAnimation(anim);
        }
        return anim;
    }

    /**
     * For consistency, display a still image using a one-frame animation.
     */
    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    /*------------------------------*\
   	|*				Physics 		*|
   	\*------------------------------*/

    public void applyPhysics(float dt) {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);
        float speed = getSpeed();
        // decelerate when not accelerating
        if (accelerationVec.len() == 0) {
            speed -= deceleration * dt;
        }
        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);
        // update velocity
        setSpeed(speed);
        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);
        // reset acceleration
        accelerationVec.set(0, 0);
    }

    /*------------------------------*\
   	|*				Collision  		*|
   	\*------------------------------*/

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2 * numSides];
        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2;     // x
            vertices[2 * i + 1] = h / 2 * MathUtils.sin(angle) + h / 2; // y
        }
        boundaryPolygon = new Polygon(vertices);
    }

    public boolean overlaps(BaseActor other) {
        Polygon p1 = this.getBoundaryPolygon();
        Polygon p2 = other.getBoundaryPolygon();

        // initial test to improve performance. Much lighter overhead to
        // check in first place if the rough rectangle intersect.
        if (!p1.getBoundingRectangle().overlaps(p2.getBoundingRectangle())) {
            return false;
        }

        return Intersector.overlapConvexPolygons(p1, p2);
    }

    /**
     * Can be used as a callable (no need for the returned value)
     */
    public Vector2 preventOverlap(BaseActor other) {
        Polygon p1 = this.getBoundaryPolygon();
        Polygon p2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if (!p1.getBoundingRectangle().overlaps(p2.getBoundingRectangle())) {
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygoneOverlap = Intersector.overlapConvexPolygons(p1, p2, mtv);

        if (!polygoneOverlap) {
            return null;
        }

        moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    /**
     * Check if any of the Actor's edges (left, right, top, bottom) have
     * passed beyond the corresponding edge of the screen.
     */
    public void boundToWorld() {
        if (getX() < 0) setX(0);
        if (getX() + getWidth() > worldBounds.width) setX(worldBounds.width - getWidth());
        if (getY() < 0) setY(0);
        if (getY() + getHeight() > worldBounds.height) setY(worldBounds.height - getHeight());
    }


    /*------------------------------*\
   	|*				Camera  		*|
   	\*------------------------------*/

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        // center camera on actor
        cam.position.set(getX() + getOriginX(), getY() + getOriginY(), 0);

        // bound camera to layout
        cam.position.x = MathUtils.clamp(
                cam.position.x, cam.viewportWidth / 2,
                worldBounds.width - cam.viewportWidth / 2
        );
        cam.position.y = MathUtils.clamp(
                cam.position.y, cam.viewportHeight / 2,
                worldBounds.height - cam.viewportHeight / 2
        );
    }

    /*------------------------------------------------------------------*\
	|*							Private Methods 						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Private Attributs 						*|
	\*------------------------------------------------------------------*/
}
