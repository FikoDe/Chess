package projekt.sach;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

/**
 * The scene with main content.
 */
public class MainScene extends Scene {

	/**
	 * Identifier (name) of this scene.
	 */
	public static final String NAME = "Main";
	
	private HraciaDoska doska;
	private boolean existujePopUp;
	private Uvod uvod;
	private boolean jeStlaceneTlacidlo;

	/**
	 * Constructs the scene.
	 * 
	 * @param stage
	 *            the stage where the scene will be shown.
	 */
	public MainScene(Stage stage) {
		super(stage);
		
		//doska = new HraciaDoska(this);
		//nakreslenie pozadia
		Turtle sachovnica = new Turtle();
		add(sachovnica);
		sachovnica.center();
		sachovnica.setShape(new ImageTurtleShape("/sachovnica.png"));
		sachovnica.stamp();
		remove(sachovnica);
		vypisCisel();
	}
	
	//spuspti hru podla rezimu ktory sme si zvolili
	@Override
	public void start() {
		jeStlaceneTlacidlo = false;
		if(doska.getIsCustom()) { 
			doska.customStart();
		}else {
			doska.vycistiDosku();
			doska.standartZaciatok();
		}
		existujePopUp = true;
		uvod = new Uvod(true);
		add(uvod);
		uvod.setPosition(getWidth() / 2 - 300, getHeight() / 2 - 100);
		doska.setCasTahuBiely(0);
		doska.setCasTahuCierny(0);
		setTickPeriod(100);
	}

	@Override
	protected void onMousePressed(int x, int y, MouseEvent detail) {
		jeStlaceneTlacidlo = true;
		if(existujePopUp) {
			remove(uvod);
			existujePopUp = false;
		}else {
			if(x > 15 && x < 815 && y > 15 && y < 815) {
				doska.kliknutePolicko(x ,y);	
			}
		}
		
	}
	
	@Override
	protected void onMouseDragged(int x, int y, java.awt.event.MouseEvent detail) {
		doska.pohniSFigurkou(x,y);
	}
	
	@Override
	protected void onMouseReleased(int x, int y, MouseEvent detail) {
		jeStlaceneTlacidlo = false;
		doska.pustiFigurku(x,y);
	}
	
	public void vypisCisel() {
		String[] pismena = {"A", "B", "C", "D", "E", "F", "G", "H"};
		Turtle kreslic =  new Turtle();
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
			kreslic.setPosition(5 + (820*i), 65);
			for (int j = 9; j > 0; j--) {
				kreslic.printCenter(j-1+"");
				kreslic.turn(90);
				kreslic.step(100);
				kreslic.turn(-90);
			}
		}
		
		remove(kreslic);
	}


	//po stlaceni tlacitok ukonci hru  
	//ako remizu alebo vyhru pre supera
	@Override
	protected void onKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			doska.vysledokPopUp(0);
		}else if(e.getKeyCode() == KeyEvent.VK_V) {
			doska.zmenaTahu();
			doska.vysledokPopUp(doska.getTimNaTahu());
		}
	}
	
	@Override
	public void stop() {
		
		
	}

	@Override
	protected void onTick() {
		doska.pridajCas((int)getTickPeriod());
	}
	public void setDoska(HraciaDoska doska) {
		this.doska = doska;
	}
	
	public boolean getJeStlacene() {
		return jeStlaceneTlacidlo;
	}
}
