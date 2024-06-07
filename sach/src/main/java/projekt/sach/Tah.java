package projekt.sach;

//ulozi tip figurky a tah odkial kam
public class Tah {
	private int tipFigurky;
	private int poziciaX;
	private int poziciaY;
	private int povodneX;
	private int povodneY;
	private int tim;

	public Tah(Figurka figurka, int poziciaX, int poziciaY, int povodneX, int povodneY, int tim) {
		if (new Pesiak(0, 0, 0, null).getClass().equals(figurka.getClass())) {
			tipFigurky = 1;
		} else if (new Veza(0, 0, 0).getClass().equals(figurka.getClass())) {
			tipFigurky = 2;
		} else if (new Strelec(0, 0, 0).getClass().equals(figurka.getClass())) {
			tipFigurky = 3;
		} else if (new Kon(0, 0, 0).getClass().equals(figurka.getClass())) {
			tipFigurky = 4;
		} else if (new Kralovna(0, 0, 0).getClass().equals(figurka.getClass())) {
			tipFigurky = 5;
		} else if (new Kral(0, 0, 0, null).getClass().equals(figurka.getClass())) {
			tipFigurky = 6;
		}
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.povodneX = povodneX;
		this.povodneY = povodneY;
		this.tim = tim;
	}

	@Override
	public String toString() {
		char[] x = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		String s;
		if (tim == 1) {
			s = "Čierny";
		}else {
			s = "Biely";
		}
		int y = 8 - poziciaY;
		int py = 8 - povodneY;
		switch (tipFigurky) {
		case 1:
			return s + " P [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		case 2:
			return s + " R [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		case 3:
			return s + " B [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		case 4:
			return s + " K [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		case 5:
			return s + " Q [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		case 6:
			return s + " K [" + x[povodneX] + ", " + py + "] => [" + x[poziciaX] + ", " + y + "]";
		default:
			return "Nenasiel sa tah";
		}
	}
}
