package projekt.sach;

import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;


public class Evolve extends PopUp {
	private HraciaDoska doska;
	private int x;
	private int y;
	private int tim;

	//vykresli popUp
	public Evolve(int tim, HraciaDoska doska, int x, int y) {
		this.doska = doska;
		this.x = x;
		this.y = y;
		this.tim = tim;

		setHeight(200);
		setWidth(600);
		if (tim == 1) {
			Turtle p = new Turtle();
			add(p);
			p.setShape(new ImageTurtleShape("/kralovna_cierny.png"));
			p.setPosition(600 / 8, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/veza_cierny.png"));
			p.setPosition(600 / 8 + 600 / 4, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/kon_cierny.png"));
			p.setPosition(600 / 8 + 600 / 2, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/strelec_cierny.png"));
			p.setPosition(600 - 600 / 8, 100);
			p.stamp();
			remove(p);
		} else {
			Turtle p = new Turtle();
			add(p);
			p.setShape(new ImageTurtleShape("/kralovna_biely.png"));
			p.setPosition(600 / 8, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/veza_biely.png"));
			p.setPosition(600 / 8 + 600 / 4, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/kon_biely.png"));
			p.setPosition(600 / 8 + 600 / 2, 100);
			p.stamp();
			p.setShape(new ImageTurtleShape("/strelec_biely.png"));
			p.setPosition(600 - 600 / 8, 100);
			p.stamp();
			remove(p);
		}
	}

	//po kliknuti na danu figurku zmeni pesiaka na vybranu figurku
	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		switch (x / (600 / 4)) {
		case 0:
			doska.evolveFigurka(this.x, this.y, new Kralovna(tim, this.x, this.y));
			break;
		case 1:
			doska.evolveFigurka(this.x, this.y, new Veza(tim, this.x, this.y));
			break;
		case 2:
			doska.evolveFigurka(this.x, this.y, new Kon(tim, this.x, this.y));
			break;
		case 3:
			doska.evolveFigurka(this.x, this.y, new Strelec(tim, this.x, this.y));
			break;
		default:
			System.out.println("default");
		}

		doska.clearPopUp(this);
	}

}