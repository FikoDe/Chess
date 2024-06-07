package projekt.sach;

import sk.upjs.jpaz2.theater.*;

/**
 * The demo stage.
 */
public class DemoStage extends Stage {

	/**
	 * Constructs the stage.
	 */
	public DemoStage() {
		super("Sach", 830, 830);
	}

	@Override
	protected void initialize() {
		// initialize stage and add scenes
		IntroScene intro = new IntroScene(this);
		MainScene main = new MainScene(this);
		FinalScene posledna = new FinalScene(this);
		CustomScene custom = new CustomScene(this);
		HraciaDoska doska = new HraciaDoska(main);
		addScene(IntroScene.NAME, intro);
		addScene(MainScene.NAME, main);
		addScene(FinalScene.NAME, posledna);
		addScene(CustomScene.NAME, custom);
		//scenam pridam dosku na ktorej bude hra prebiehat
		main.setDoska(doska);
		posledna.setDoska(doska);
		custom.setDoska(doska);
	}
}