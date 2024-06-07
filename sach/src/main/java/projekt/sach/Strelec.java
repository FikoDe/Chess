package projekt.sach;

import java.util.HashSet;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;

public class Strelec extends Turtle implements Figurka {


	private int poziciaX;
	private int poziciaY;
	private int tim;
	private int pocetTahov;
	// pocet tahov druhe tahy 0 tah na x, 1 tah na y
	private final int[][] tahy;

	public Strelec(int tim, int poziciaX, int poziciaY) {
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.tim = tim;
		tahy = new int[4][2];
		nacitajTahy();
		if (tim == 2) {
			setShape(new ImageTurtleShape("/strelec_biely.png"));
		} else {
			setShape(new ImageTurtleShape("/strelec_cierny.png"));
		}

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
		for (int i = 0; i < tahy.length; i++) {
			int j = 1;
			while (doska.obsadeniePolicka(poziciaX + j * tahy[i][0], poziciaY + j * tahy[i][1], tim) == 0) {
				mozneTahy.add(new Policko(poziciaX + j * tahy[i][0], poziciaY + j * tahy[i][1]));
				j++;
			}
			if(doska.obsadeniePolicka(poziciaX + j * tahy[i][0], poziciaY + j * tahy[i][1], tim) == 1) {
				mozneTahy.add(new Policko(poziciaX + j * tahy[i][0], poziciaY + j * tahy[i][1]));
			}
		}
		return mozneTahy;
	}

	@Override
	public void urobTah(int poziciaX, int poziciaY) {
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
		tahy[1][0] = -1;
		tahy[1][1] = 1;
		tahy[2][0] = 1;
		tahy[2][1] = -1;
		tahy[3][0] = -1;
		tahy[3][1] = -1;

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
		this.setPosition(x, y);
	}
}
