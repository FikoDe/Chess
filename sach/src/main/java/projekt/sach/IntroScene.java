package projekt.sach;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

/**
 * The scene with introduction.
 */
public class IntroScene extends Scene {

	/**
	 * Identifier (name) of this scene.
	 */
	public static final String NAME = "Intro";
	private Turtle oznacenie;
	/**
	 * Constructs the scene.
	 * 
	 * @param stage
	 *            the stage where the scene will be shown.
	 */
	public IntroScene(Stage stage) {
		super(stage);
		
		oznacenie = new Turtle();
		if (Math.random() > 0.5) {
			oznacenie.setShape(new ImageTurtleShape("/kral_biely.png"));
		} else {
			oznacenie.setShape(new ImageTurtleShape("/kral_cierny.png"));
		}
	}

	@Override
	public void start() {
		// draw message using a random color
		clear();

		Turtle painter = new Turtle();
		painter.setPenColor(new Color(0, 0, 0));
		MenuTlacitko standart = new MenuTlacitko("Standardna hra");
		add(standart);
		standart.setPosition(getWidth() / 2 - 200, 115);
		MenuTlacitko custom = new MenuTlacitko("Custom hra");
		add(custom);
		custom.setPosition(getWidth() / 2 - 200, 315);
		MenuTlacitko exit = new MenuTlacitko("EXIT");
		add(exit);
		exit.setPosition(getWidth() / 2 - 200, 515);
		add(painter);
		painter.center();	
		painter.setShape(new ImageTurtleShape("/sachovnica.png"));
		painter.stamp();

		remove(painter);
	}

	@Override
	public void stop() {

	}

	//ak klikneme do hornej polovice spustime standartnu hru
	//ak klikneme do spodnej polovice spustime vlastnu specialnu hru
	@Override
	protected void onMousePressed(int x, int y, MouseEvent detail) {
		if(x > 215 && x < 615) {
			if(y > 115 && y < 215) {
				getStage().changeScene(MainScene.NAME, TransitionEffect.MOVE_RIGHT, 1000);
			}else if(y > 315 && y < 415) {
				getStage().changeScene(CustomScene.NAME, TransitionEffect.MOVE_RIGHT, 1000);
			}else if(y > 515 && y < 615) {
				System.exit(0);
			}
		}
	}
	
	@Override
	protected void onKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		}
	}
	
	@Override
	protected void onMouseMoved(int x, int y, MouseEvent detail) {
		if(x > 215 && x < 615) {
			if(y > 115 && y < 215) {
				add(oznacenie);
				oznacenie.setPosition(165,165);
			}else if(y > 315 && y < 415) {
				add(oznacenie);
				oznacenie.setPosition(165,365);
			}else if(y > 515 && y < 615) {
				add(oznacenie);
				oznacenie.setPosition(165,565);
			}else {
				remove(oznacenie);
			}
		}else {
			remove(oznacenie);
		}
	}
}
