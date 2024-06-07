package projekt.sach;


import sk.upjs.jpaz2.Turtle;

//popUp vypise vysledok hry
public class Vysledok extends PopUp{

	public Vysledok(int tim) {
		setHeight(200);
		setWidth(600);

		Turtle p = new Turtle();
		add(p);
		p.center();
		p.turn(90);
		p.setFont(p.getFont().deriveFont(30.0f));
		if(tim == 0) {
			p.printCenter("***Remiza***");
		}else if(tim == 2) {
			p.printCenter("***Biely Vyhral***");
		}else {
			p.printCenter("***Čierny Vyhral***");
		}
		
		remove(p);
		
	}
}
