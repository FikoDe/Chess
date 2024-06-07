package projekt.sach;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;


import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;

public class Pesiak extends Turtle implements Figurka {
	private HraciaDoska doska;
	private int poziciaX;
	private int poziciaY;
	private int tim;
	private int pocetTahov;
	// pocet tahov druhe tahy 0 tah na x, 1 tah na y
	private final int[][] tahy;
	private boolean enPassant;

	public Pesiak(int tim, int poziciaX, int poziciaY, HraciaDoska doska) {
		this.doska = doska;
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.tim = tim;
		tahy = new int[6][2];
		nacitajTahy();
		if (tim == 2) {
			setShape(new ImageTurtleShape("/pesiak_biely.png"));
		} else {
			setShape(new ImageTurtleShape("/pesiak_cierny.png"));
		}
		enPassant = false;
		pocetTahov = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tahy);
		result = prime * result + Objects.hash(pocetTahov, poziciaX, poziciaY, tim);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pesiak other = (Pesiak) obj;
		return pocetTahov == other.pocetTahov && poziciaX == other.poziciaX && poziciaY == other.poziciaY
				&& tim == other.tim;
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
		if (doska.obsadeniePolicka(poziciaX + tahy[0][0], poziciaY + tahy[0][1], tim) == 0) {
			mozneTahy.add(new Policko(poziciaX + tahy[0][0], poziciaY + tahy[0][1]));
			if ((tim == 1 && poziciaY == 1) || tim == 2 && poziciaY == 6) {
				if (doska.obsadeniePolicka(poziciaX + tahy[1][0], poziciaY + tahy[1][1], tim) == 0) {
					mozneTahy.add(new Policko(poziciaX + tahy[1][0], poziciaY + tahy[1][1]));
				}
			}
		}
		for (int i = 2; i < 4; i++) {
			if (doska.obsadeniePolicka(poziciaX + tahy[i][0], poziciaY + tahy[i][1], tim) == 1) {
				mozneTahy.add(new Policko(poziciaX + tahy[i][0], poziciaY + tahy[i][1]));
			}
		}
		if ((tim == 2 && poziciaY == 3) || (tim == 1 && poziciaY == 4)) {
			for (int i = 4; i < 6; i++) {
				if (doska.obsadeniePolicka(poziciaX + tahy[i][0], poziciaY + tahy[i][1], tim) == 1
						&& doska.getFigurka(poziciaX + tahy[i][0], poziciaY + tahy[i][1]).getClass() == this.getClass()
						&& doska.getFigurka(poziciaX + tahy[i][0], poziciaY + tahy[i][1]).equals(doska.getPoslednaFigurka())
						&& doska.getFigurka(poziciaX + tahy[i][0], poziciaY + tahy[i][1]).getPocetTahov() == 1) {
					enPassant = true;
					if (tim == 1) {
						mozneTahy.add(new Policko(poziciaX + tahy[i][0], poziciaY + tahy[i][1] + 1));
					} else {
						mozneTahy.add(new Policko(poziciaX + tahy[i][0], poziciaY + tahy[i][1] - 1));
					}
				}
			}
		} else {
			enPassant = false;
		}
		return mozneTahy;
	}

	@Override
	public void urobTah(int poziciaX, int poziciaY) {
		this.poziciaX = poziciaX;
		this.poziciaY = poziciaY;
		this.setPosition(65 + 100 * this.poziciaX, 65 + 100 * this.poziciaY);
		if ((tim == 2 && poziciaY == 0) || (tim == 1 && poziciaY == 7)) {
			doska.evolvePopUp(tim, poziciaX, poziciaY);
		}
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
		if (tim == 1) {
			tahy[0][0] = 0;
			tahy[0][1] = 1;
			tahy[1][0] = 0;
			tahy[1][1] = 2;
			tahy[2][0] = 1;
			tahy[2][1] = 1;
			tahy[3][0] = -1;
			tahy[3][1] = 1;
		} else {
			tahy[0][0] = 0;
			tahy[0][1] = -1;
			tahy[1][0] = 0;
			tahy[1][1] = -2;
			tahy[2][0] = 1;
			tahy[2][1] = -1;
			tahy[3][0] = -1;
			tahy[3][1] = -1;
		}
		tahy[4][0] = 1;
		tahy[4][1] = 0;
		tahy[5][0] = -1;
		tahy[5][1] = 0;
	}

	@Override
	public boolean getEnPassant() {
		return enPassant;
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
