package projekt.sach;

import java.util.HashSet;


import sk.upjs.jpaz2.PaneObject;

public interface Figurka extends PaneObject{
	//vrati mozne tahy na ktore sa moze pohnut figurka na doske
	public HashSet<Policko> zobrazMozneTahy(HraciaDoska doska) ;
	//figurka urobi tah na dane policko
	public void urobTah(int poziciaX, int poziciaY) ; 
	//vrati v akom time sa nachadza figurka
	public int getTim();
	//zrovna sa do stredu policka v ktorom ma byt
	public void zrovnajSa();
	public int getPolickoX();
	public int getPolickoY();
	public boolean getEnPassant();
	public int getPocetTahov();
	public void setPostion(int x, int y);
}
