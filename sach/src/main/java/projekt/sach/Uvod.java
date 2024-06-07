package projekt.sach;

import sk.upjs.jpaz2.Turtle;

//pop up pre strucne vysvetlenie ovladania
public class Uvod extends PopUp{

	public Uvod(boolean jeCustom) {
		setHeight(200);
		setWidth(600);

		Turtle p = new Turtle();
		add(p);
		p.center();
		p.turn(90);
		p.setFont(p.getFont().deriveFont(20.0f));
		if(jeCustom) {
			p.printCenter("Ukoncenie Remizov - R");
			p.setPosition(300, 50);
			p.printCenter("Pohynanie figuriek - Klikanie mysov");
			p.setPosition(300, 150);
			p.printCenter("Vzdat Sa - V");
		}else {
			p.printCenter("Zmena timu - Space");
			p.setPosition(300, 50);
			p.printCenter("Zmena figurky na policku - Klikanie mysov");
			p.setPosition(300, 150);
			p.printCenter("Spustit hru - Enter");
		}
		remove(p);
		
	}
}