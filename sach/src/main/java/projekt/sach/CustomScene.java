package projekt.sach;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.ImageTurtleShape;
import sk.upjs.jpaz2.Turtle;
import sk.upjs.jpaz2.theater.Scene;
import sk.upjs.jpaz2.theater.Stage;

public class CustomScene extends Scene {
	//deklaracia premenych
	public static final String NAME = "Custom";
	private int tim = 1;
	private Figurka[][] figurky;
	private HraciaDoska doska;
	private boolean existujePopUp;
	private PopUp popUp;

	//vytvorim scenu nakreslim pozadie a pridam pole
	public CustomScene(Stage stage) {
		super(stage);
		Turtle sachovnica = new Turtle();
		add(sachovnica);
		sachovnica.center();
		sachovnica.setShape(new ImageTurtleShape("/sachovnica.png"));
		sachovnica.stamp();
		remove(sachovnica);
		vypisCisel();
		setBackgroundColor(Color.white);
		figurky = new Figurka[8][8];
	}

	//pri starte vynulujem figurky
	//a popupnem navod 
	@Override
	public void start() {
		doska.vycistiDosku();
		for (int i = 0; i < figurky.length; i++) {
			for (int j = 0; j < figurky.length; j++) {
				figurky[i][j] = null;
			}
		}
		
		existujePopUp = true;
		popUp = new Uvod(false);
		add(popUp);
		popUp.setPosition(getWidth() / 2 - 300, getHeight() / 2 - 100);
	}

	@Override
	public void stop() {

	}


	public void setDoska(HraciaDoska doska) {
		this.doska = doska;
	}

	//klikom vypnem popup
	//zmenim figurku na ozncaenom policku
	@Override
	protected void onMousePressed(int x, int y, MouseEvent detail) {
		if(existujePopUp) {
			remove(popUp);
			existujePopUp = false;
		}else {
			if (x > 15 && x < 815 && y > 15 && y < 815) {
				int polickoX = (x - 15) / 100;
				int polickoY = (y - 15) / 100;
				pridajFigurku(polickoX, polickoY);

			}
		}
		
	}

	//postupne menim figurky 
	//porovnavam triedy objektov na pozicii na ktoru som klikol
	public void pridajFigurku(int x, int y) {
		if (figurky[x][y] == null) {
			figurky[x][y] = new Pesiak(tim, x, y, doska);
			add(figurky[x][y]);
			figurky[x][y].zrovnajSa();
		} else if (new Pesiak(0, 0, 0, null).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			figurky[x][y] = new Veza(tim, x, y);
			add(figurky[x][y]);
			figurky[x][y].zrovnajSa();
		} else if (new Veza(0, 0, 0).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			figurky[x][y] = new Strelec(tim, x, y);
			add(figurky[x][y]);
			figurky[x][y].zrovnajSa();
		} else if (new Strelec(0, 0, 0).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			figurky[x][y] = new Kon(tim, x, y);
			add(figurky[x][y]);
			figurky[x][y].zrovnajSa();
		} else if (new Kon(0, 0, 0).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			figurky[x][y] = new Kralovna(tim, x, y);
			add(figurky[x][y]);
			figurky[x][y].zrovnajSa();
		} else if (new Kralovna(0, 0, 0).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			if (existujeKral()) {
				figurky[x][y] = null;
			} else {
				figurky[x][y] = new Kral(tim, x, y, doska);
				add(figurky[x][y]);
				figurky[x][y].zrovnajSa();
			}
		} else if (new Kral(0, 0, 0, null).getClass().equals(figurky[x][y].getClass())) {
			remove(figurky[x][y]);
			figurky[x][y] = null;
		}
	}

	//zabranuje aby mal jeden tim dvoch kralov
	public boolean existujeKral() {
		for (int i = 0; i < figurky.length; i++) {
			for (int j = 0; j < figurky.length; j++) {
				if (figurky[i][j] != null
						&& new Kral(0, 0, 0, doska).getClass().equals(figurky[i][j].getClass())) {
					if (figurky[i][j].getTim() == tim) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//existuje kral pre konkretny tim
	//kontrolujem ci ma dany tim krala
	public boolean existujeKral(int tim) {
		for (int i = 0; i < figurky.length; i++) {
			for (int j = 0; j < figurky.length; j++) {
				if (figurky[i][j] != null
						&& new Kral(0, 0, 0, doska).getClass().equals(figurky[i][j].getClass())) {
					if (figurky[i][j].getTim() == tim) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	

	//menim tim alebo spustam hru
	@Override
	protected void onKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (tim == 1) {
				tim = 2;
			} else {
				tim = 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(existujeKral(1) && existujeKral(2)) {
				doska.setFigurky(figurky);
				doska.setIsCustom(true);
				getStage().changeScene(MainScene.NAME);
			}else if(!existujePopUp){
				popUp = new Warning();
				add(popUp);
				popUp.setPosition(getWidth() / 2 - 300, getHeight() / 2 - 100);
				existujePopUp = true;
			}
			
		}
	}

	//vykreslenie pozadia
	public void vypisCisel() {
		String[] pismena = { "A", "B", "C", "D", "E", "F", "G", "H" };
		Turtle kreslic = new Turtle();
		add(kreslic);
		kreslic.setPosition(65, 5);
		kreslic.setPenColor(Color.white);
		kreslic.setDirection(90);
		kreslic.penUp();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				kreslic.printCenter(pismena[j]);
				kreslic.step(100);
			}
			kreslic.setPosition(65, 820);
		}

		for (int i = 0; i < 2; i++) {
			kreslic.setPosition(5 + (820 * i), 65);
			for (int j = 9; j > 0; j--) {
				kreslic.printCenter(j - 1 + "");
				kreslic.turn(90);
				kreslic.step(100);
				kreslic.turn(-90);
			}
		}

		remove(kreslic);
	}
}