package projekt.sach;

import java.util.ArrayList;
import java.util.HashSet;

import sk.upjs.jpaz2.TransitionEffect;

public class HraciaDoska {
	private MainScene scene;
	private int pocetTahov;
	private Figurka[][] figurky;
	private int timNaTahu;
	private Figurka oznacenaFigurka;
	private Figurka poslednaFigurka;
	private HashSet<Policko> mozneTahy;
	private HashSet<Indikator> indikatory;
	private boolean pauza;
	private ArrayList<Tah> tahy;
	private boolean isCustom;
	private int casTahuCierny;
	private int casTahuBiely;

	public HraciaDoska(MainScene scene) {
		this.scene = scene;
		figurky = new Figurka[8][8];
		tahy = new ArrayList<Tah>();
		mozneTahy = new HashSet<Policko>();
		indikatory = new HashSet<Indikator>();
		timNaTahu = 2;
		pauza = false;
		isCustom = false;
	}

	/**
	 * metoda vrati 0 ak na policku nikto nie je metoda vrati 1 ak je na policku
	 * super metoda vrati 2 ak je policku vasa figurka\ -1 ak policko neexistuje
	 * lezi mimo dosky
	 */
	public int obsadeniePolicka(int x, int y, int tim) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
			if (figurky[x][y] == null)
				return 0;
			if (tim != figurky[x][y].getTim())
				return 1;
			if (tim == figurky[x][y].getTim())
				return 2;
		}
		return -1;
	}

	// ako parameter dostane x a y poziciu na doske
	public void kliknutePolicko(int x, int y) {
		if (!pauza) {
			// ak hra nieje pauznuta prerata pozicie na policka
			int polickoX = (x - 15) / 100;
			int polickoY = (y - 15) / 100;

			// vytvori nove policko
			Policko kliknute = new Policko(polickoX, polickoY);
			// ak je dane policko mozny tah ozncenej figurky vykonna tah
			if (mozneTahy.contains(kliknute)) {
				// odstrani indikatory a vyprazdni mozne tahy
				mozneTahy.clear();
				for (Indikator indikator : indikatory) {
					indikator.odstranSa(scene);
				}
				indikatory.clear();
				pocetTahov++;
				// ulozi tah
				tahy.add(new Tah(oznacenaFigurka, polickoX, polickoY, oznacenaFigurka.getPolickoX(),
						oznacenaFigurka.getPolickoY(), oznacenaFigurka.getTim()));
				figurky[oznacenaFigurka.getPolickoX()][oznacenaFigurka.getPolickoY()] = null;
				// ak figurka moze vykona Enpassant skotroluje ci ho vykonala
				if (oznacenaFigurka.getEnPassant()) {
					if (timNaTahu == 2) {
						scene.remove(figurky[polickoX][polickoY + 1]);
						figurky[polickoX][polickoY + 1] = null;
					} else {
						scene.remove(figurky[polickoX][polickoY - 1]);
						figurky[polickoX][polickoY - 1] = null;
					}
				} else {
					// vykona bezny tah ak vezmeme krala ukonci hru
					if (figurky[polickoX][polickoY] != null
							&& new Kral(0, 0, 0, this).getClass().equals(figurky[polickoX][polickoY].getClass())) {
						vysledokPopUp(timNaTahu);
						return;
					}
					scene.remove(figurky[polickoX][polickoY]);
				}
				figurky[polickoX][polickoY] = oznacenaFigurka;
				oznacenaFigurka.urobTah(polickoX, polickoY);
				poslednaFigurka = oznacenaFigurka;
				oznacenaFigurka = null;

				zmenaTahu();
				return;
			}

			mozneTahy.clear();
			for (Indikator indikator : indikatory) {
				indikator.odstranSa(scene);
			}
			indikatory.clear();
			// ak nemame na danom policku je figurka nasho timu oznacime ju a vykreslime
			// mozne tahy
			if (figurky[polickoX][polickoY] != null) {
				if (figurky[polickoX][polickoY].getTim() == timNaTahu) {
					mozneTahy = figurky[polickoX][polickoY].zobrazMozneTahy(this);
					oznacenaFigurka = figurky[polickoX][polickoY];
				}
			}
			for (Policko policko : mozneTahy) {
				indikatory.add(new Indikator(policko.getX(), policko.getY(), scene));
			}
		}
	}

	// zmeny tim ktory je na tahu
	public void zmenaTahu() {
		if (timNaTahu == 1) {
			timNaTahu = 2;
		} else {
			timNaTahu = 1;
		}
	}

	// starte hru v standartom rezime teda bezne rozlozenie dosky
	public void standartZaciatok() {
		pocetTahov = 0;
		timNaTahu = 2;
		for (int i = 0; i < 8; i++) {
			figurky[i][1] = new Pesiak(1, i, 1, this);
			figurky[i][6] = new Pesiak(2, i, 6, this);
		}
		for (int i = 0; i < 2; i++) {
			figurky[0 + i * 7][0] = new Veza(1, 0 + i * 7, 0);
			figurky[0 + i * 7][7] = new Veza(2, 0 + i * 7, 7);
			figurky[1 + i * 5][0] = new Kon(1, 1 + i * 5, 0);
			figurky[1 + i * 5][7] = new Kon(2, 1 + i * 5, 7);
			figurky[2 + i * 3][0] = new Strelec(1, 2 + i * 3, 0);
			figurky[2 + i * 3][7] = new Strelec(2, 2 + i * 3, 7);
		}
		figurky[3][0] = new Kralovna(1, 3, 0);
		figurky[3][7] = new Kralovna(2, 3, 7);
		figurky[4][0] = new Kral(1, 4, 0, this);
		figurky[4][7] = new Kral(2, 4, 7, this);
		for (Figurka[] figurkas : figurky) {
			for (Figurka figurka : figurkas) {
				if (figurka != null) {
					scene.add(figurka);
					figurka.zrovnajSa();
				}
			}
		}
	}

	// starte hru z custom rezimu
	public void customStart() {
		for (Figurka[] figurkas : figurky) {
			for (Figurka figurka : figurkas) {
				if (figurka != null) {
					scene.add(figurka);
					figurka.zrovnajSa();
				}
			}
		}
	}

	// vymeni pesiaka za inu figurku
	public void evolveFigurka(int x, int y, Figurka obj) {
		scene.remove(figurky[x][y]);
		figurky[x][y] = obj;
		scene.add(figurky[x][y]);
		figurky[x][y].urobTah(x, y);
	}

	// popUp pre vymenu pesiaka
	public void evolvePopUp(int tim, int x, int y) {
		Evolve volba = new Evolve(tim, this, x, y);
		scene.add(volba);
		volba.setPosition(scene.getWidth() / 2 - 300, scene.getHeight() / 2 - 100);
		pauza = true;
	}

	// odstrani popUp
	public void clearPopUp(Evolve obj) {
		scene.remove(obj);
		pauza = false;
	}

	// zmaze vsetky figurky z dosky
	public void vycistiDosku() {
		scene.remove(oznacenaFigurka);
		for (int i = 0; i < figurky.length; i++) {
			for (int j = 0; j < figurky.length; j++) {
				scene.remove(figurky[i][j]);
				figurky[i][j] = null;
			}
		}
	}

	// vypise v popUpe vitaza hry
	public void vysledokPopUp(int tim) {
		Vysledok vys = new Vysledok(tim);
		scene.add(vys);
		vys.setPosition(scene.getWidth() / 2 - 300, scene.getHeight() / 2 - 100);
		pauza = true;
		timNaTahu = tim;
		scene.getStage().changeScene(FinalScene.NAME, TransitionEffect.FADE_OUT_BLACK_FADE_IN, 7000);
		vycistiDosku();
		standartZaciatok();
		scene.remove(vys);
		pauza = false;
	}

	// gettre a settre
	public Figurka getFigurka(int x, int y) {
		return figurky[x][y];
	}

	public Figurka getPoslednaFigurka() {
		return poslednaFigurka;
	}

	public void setFigurka(int x, int y, Figurka obj) {
		figurky[x][y] = obj;
	}

	public int getTimNaTahu() {
		return timNaTahu;
	}

	public int getPocetTahov() {
		return pocetTahov;
	}

	public Tah getPoslednyTah() {
		if (tahy.size() == 0) {
			return null;
		} else {
			return tahy.get(tahy.size() - 1);
		}
	}

	public ArrayList<Tah> getTahy() {
		return tahy;
	}

	public void setFigurky(Figurka[][] list) {
		figurky = list;
	}

	public boolean getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(boolean custom) {
		isCustom = custom;
	}

	public int getCasTahuCierny() {
		return casTahuCierny;
	}

	public void setCasTahuCierny(int casTahuCierny) {
		this.casTahuCierny = casTahuCierny;
	}

	public int getCasTahuBiely() {
		return casTahuBiely;
	}

	public void setCasTahuBiely(int casTahuBiely) {
		this.casTahuBiely = casTahuBiely;
	}

	public void pridajCas(int cas) {
		if (timNaTahu == 1) {
			casTahuCierny += cas;
		}
		if (timNaTahu == 2) {
			casTahuBiely += cas;
		}
	}

	public void pohniSFigurkou(int x, int y) {
		if (!pauza && scene.getJeStlacene() && oznacenaFigurka != null) {
			oznacenaFigurka.setPostion(x, y);
		}
	}

	public void pustiFigurku(int x, int y) {
		if (!pauza) {
			if (oznacenaFigurka != null) {
				// ak hra nieje pauznuta prerata pozicie na policka
				int polickoX = (x - 15) / 100;
				int polickoY = (y - 15) / 100;

				// vytvori nove policko
				Policko kliknute = new Policko(polickoX, polickoY);
				// ak je dane policko mozny tah ozncenej figurky vykonna tah
				if (mozneTahy.contains(kliknute)) {
					kliknutePolicko(x, y);
					// oznacenaFigurka.zrovnajSa();
				}else {
					oznacenaFigurka.zrovnajSa();
				}
			}
		}
	}

}
