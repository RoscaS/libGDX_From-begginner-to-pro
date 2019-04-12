package ch2;

public class Shark extends ActorBeta {

    private final int maxY = 400;
    private final int minY = 100;
    private int direction = 1;
    private int coef = 1;

	/*------------------------------------------------------------------*\
	|*							Constructors							*|
	\*------------------------------------------------------------------*/

	public Shark() {
	    super();
    }

	/*------------------------------*\
	|*				Getters			*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Public Methods 							*|
	\*------------------------------------------------------------------*/

	@Override
    public void act(float dt) {
	    super.act(dt);

	    if (getY() >= maxY) {
	        direction = -1;
	        coef += 1;
        } else if (getY() <= minY) {
	        direction = 1;
        }

	    moveBy(0, coef * direction);
    }

	/*------------------------------------------------------------------*\
	|*							Private Attributs 						*|
	\*------------------------------------------------------------------*/
}
