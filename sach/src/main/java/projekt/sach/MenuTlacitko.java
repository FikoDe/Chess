package projekt.sach;

import java.awt.Color;

import sk.upjs.jpaz2.Pane;
import sk.upjs.jpaz2.Turtle;

public class MenuTlacitko extends Pane {
	public MenuTlacitko(String popisok) {
		setWidth(400);
		setHeight(100);
		Turtle p = new Turtle();
		//setBackgroundColor(Color.black);
		add(p);
		p.center();
		p.turn(90);
		p.setFont(p.getFont().deriveFont(30.0f));
		p.printCenter(popisok);
		remove(p);
		
		setBorderWidth(15);
	}
}
