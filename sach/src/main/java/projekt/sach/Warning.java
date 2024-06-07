package projekt.sach;

import java.awt.Color;

import sk.upjs.jpaz2.Turtle;

//vypise upozornenie
public class Warning extends PopUp{
	public Warning() {
		setHeight(200);
		setWidth(600);

		Turtle p = new Turtle();
		add(p);
		p.center();
		p.turn(90);
		p.setFont(p.getFont().deriveFont(20.0f));
		p.setPenColor(Color.red);
		p.printCenter("Pridajte Krala obom timom");
		remove(p);
		
	}
}
