package projekt.sach;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;

public class Indikator extends Turtle {
	
	public Indikator(int x, int y, MainScene scene) {
		setShape(new ImageTurtleShape("/indikator.png"));
		scene.add(this);
		setPosition(65 + 100 * x, 65 + 100 * y);
	}
	//indikator sa odstrani
	public void odstranSa(MainScene scene) {
		scene.remove(this);
	}
	
	
}
