package projekt.sach;

import java.util.HashSet;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;

public class Kral extends Turtle implements Figurka {
	private HraciaDoska doska;
	private int poziciaX;
	private int poziciaY;
	private int tim;
	private int pocetTahov;
	// pocet tahov druhe tahy 0 tah na x, 1 tah na y
	private final int[][] tahy;

	public Kral(int tim, int poziciaX, int poziciaY, HraciaDoska doska) {
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.tim = tim;
		tahy = new int[10][2];
		nacitajTahy();
		if (tim == 2) {
			setShape(new ImageTurtleShape("/kral_biely.png"));
		} else {
			setShape(new ImageTurtleShape("/kral_cierny.png"));
		}

		this.doska = doska;
		pocetTahov = 0;
	}

	@Override
	public int getPolickoX() {
		return poziciaX;
	}

	@Override
	public int getPolickoY() {
		return poziciaY;
	}

	@Override
	public HashSet<Policko> zobrazMozneTahy(HraciaDoska doska) {
		HashSet<Policko> mozneTahy = new HashSet<Policko>();
		for (int i = 0; i < 8; i++) {
			if (doska.obsadeniePolicka(poziciaX + tahy[i][0], poziciaY + tahy[i][1], tim) == 1
					|| doska.obsadeniePolicka(poziciaX + tahy[i][0], poziciaY + tahy[i][1], tim) == 0) {
				mozneTahy.add(new Policko(poziciaX + tahy[i][0], poziciaY + tahy[i][1]));
			}
		}

		if (pocetTahov == 0) {
			if (doska.getFigurka(7, this.poziciaY) != null 
					&& doska.getFigurka(7, this.poziciaY).getClass().equals(new Veza(0,0,0).getClass())
					&& doska.getFigurka(7, this.poziciaY).getPocetTahov() == 0
					&& doska.obsadeniePolicka(poziciaX + 1, poziciaY, tim) == 0
					&& doska.obsadeniePolicka(poziciaX + 2, poziciaY, tim) == 0) {
				mozneTahy.add(new Policko(poziciaX + tahy[8][0], poziciaY + tahy[8][1]));
			}
			if (doska.getFigurka(0, this.poziciaY) != null 
					&& doska.getFigurka(0, this.poziciaY).getClass().equals(new Veza(0,0,0).getClass())
					&& doska.getFigurka(0, this.poziciaY).getPocetTahov() == 0
					&& doska.obsadeniePolicka(poziciaX - 1, poziciaY, tim) == 0
					&& doska.obsadeniePolicka(poziciaX - 2, poziciaY, tim) == 0
					&& doska.obsadeniePolicka(poziciaX - 3, poziciaY, tim) == 0) {
				mozneTahy.add(new Policko(poziciaX + tahy[9][0], poziciaY + tahy[9][1]));
			}
		}
		return mozneTahy;
	}

	@Override
	public void urobTah(int poziciaX, int poziciaY) {
		if (this.poziciaX - poziciaX == -2) {
			doska.getFigurka(7, this.poziciaY).urobTah(poziciaX - 1, poziciaY);
			doska.setFigurka(5, this.poziciaY, doska.getFigurka(7, this.poziciaY));
			doska.setFigurka(7, this.poziciaY, null);
		} else if (this.poziciaX - poziciaX == 2) {
			doska.getFigurka(0, this.poziciaY).urobTah(poziciaX + 1, poziciaY);
			doska.setFigurka(3, this.poziciaY, doska.getFigurka(0, this.poziciaY));
			doska.setFigurka(0, this.poziciaY, null);
		}
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.setPosition(65 + 100 * this.poziciaX, 65 + 100 * this.poziciaY);
		pocetTahov++;

	}

	public void zrovnajSa() {
		this.setPosition(65 + 100 * this.poziciaX, 65 + 100 * this.poziciaY);
	}

	@Override
	public int getTim() {
		return tim;
	}

	private void nacitajTahy() {
		tahy[0][0] = 1;
		tahy[0][1] = 1;
		tahy[1][0] = 1;
		tahy[1][1] = -1;
		tahy[2][0] = 1;
		tahy[2][1] = 0;
		tahy[3][0] = 0;
		tahy[3][1] = -1;
		tahy[4][0] = 0;
		tahy[4][1] = 1;
		tahy[5][0] = -1;
		tahy[5][1] = -1;
		tahy[6][0] = -1;
		tahy[6][1] = 1;
		tahy[7][0] = -1;
		tahy[7][1] = 0;
		tahy[8][0] = 2;
		tahy[8][1] = 0;
		tahy[9][0] = -2;
		tahy[9][1] = 0;
	}

	@Override
	public boolean getEnPassant() {
		return false;
	}

	@Override
	public int getPocetTahov() {
		// TODO Auto-generated method stub
		return pocetTahov;
	}

	@Override
	public void setPostion(int x, int y) {
		super.setPosition(x, y);
	}
}
