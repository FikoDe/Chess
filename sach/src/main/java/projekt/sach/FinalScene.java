package projekt.sach;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import sk.upjs.jpaz2.Turtle;
import sk.upjs.jpaz2.theater.Scene;
import sk.upjs.jpaz2.theater.Stage;

//vypise statystiky poslednej hry ale aj celkove
//tahy zapise do suboru
public class FinalScene extends Scene {

	/**
	 * Identifier (name) of this scene.
	 */
	public static final String NAME = "Final";

	private HraciaDoska doska;

	/**
	 * Constructs the scene.
	 * 
	 * @param stage the stage where the scene will be shown.
	 */
	public FinalScene(Stage stage) {
		super(stage);

	}

	@Override
	protected void onMousePressed(int x, int y, MouseEvent detail) {
		getStage().changeScene(IntroScene.NAME);
	}

	@Override
	public void start() {
		clear();
		Turtle p = new Turtle();
		add(p);

		p.center();
		p.turn(90);
		p.setFont(p.getFont().deriveFont(50.0f));
		if (doska.getTimNaTahu() == 0) {
			p.printCenter("***Remiza***");
		} else if (doska.getTimNaTahu() == 2) {
			p.printCenter("***Biely Vyhral***");
		} else {
			p.printCenter("***Čierny Vyhral***");
		}
		p.setFont(p.getFont().deriveFont(30.0f));
		p.setPosition(30, getHeight() / 2 + 100);
		if (doska.getPoslednyTah() != null) {
			p.print(doska.getPoslednyTah().toString());
		}
		p.setPosition(345, getHeight() / 2 + 100);
		p.print("Cas bieleho na tahu: " + doska.getCasTahuBiely() / 60000 + "m" +doska.getCasTahuBiely() % 60000 / 1000
				+ "s");
		p.setPosition(30, getHeight() / 2 + 150);
		p.print("Pocet Tahov: " + doska.getPocetTahov());
		p.setPosition(345, getHeight() / 2 + 150);
		p.print("Cas cierneho na tahu: " + doska.getCasTahuCierny() / 60000 + "m" +doska.getCasTahuCierny() % 60000 / 1000
				+ "s");
		remove(p);
		zapis();
		statistiky();

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public void setDoska(HraciaDoska doska) {
		this.doska = doska;
	}

	// zapis vsetkych tahov do suboru
	public void zapis() {
		ArrayList<Tah> list = doska.getTahy();
		try (FileWriter fw = new FileWriter(new File("poslednaHra.txt"))) {
			if (list.size() != 0) {
				for (int i = 0; i < list.size() - 1; i++) {
					fw.write(list.get(i).toString() + "\n");
				}
				fw.write(list.get(list.size() - 1).toString());
			}
		} catch (IOException e) {
			System.out.println("Nepodarilo sa vytvorit subor");
		}

	}

	// zapise statisiky vysledkov hier
	// a pripise hru ktora sa dohrala
	public void statistiky() {
		try (Scanner sc = new Scanner(new File("statistiky.txt"))) {
			Turtle p = new Turtle();
			sc.next();
			sc.next();
			int ph = sc.nextInt();
			ph++;
			sc.next();
			sc.next();
			int vb = sc.nextInt();
			sc.next();
			sc.next();
			int vc = sc.nextInt();
			sc.next();
			int rm = sc.nextInt();
			if (doska.getTimNaTahu() == 2) {
				vb++;
			} else if (doska.getTimNaTahu() == 1) {
				vc++;
			} else {
				rm++;
			}
			add(p);
			p.turn(90);
			p.setFont(p.getFont().deriveFont(30.0f));
			p.setPosition(30, getHeight() / 2 + 200);
			p.print("Pocet hier: " + ph + " Vyhry biely: " + vb + " Vyhry cierny: " + vc + " Remizi: " + rm);
			remove(p);
			if (doska.getPocetTahov() != 0) {
				try (FileWriter fw = new FileWriter(new File("statistiky.txt"))) {
					fw.write("pocet hier: " + ph + "\n");
					fw.write("vyhry biely: " + vb + "\n");
					fw.write("vyhry biely: " + vc + "\n");
					fw.write("remizi: " + rm);
				} catch (Exception e) {
					System.err.println("Nepodarilo sa nacitat subor");
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Nepodarilo sa nacitat subor na precitanie");
		}
	}
}