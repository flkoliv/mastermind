package ihm;

public interface Plateau {

	public void setMsgDev(String msg); //afficher le code chercher
	public void cleanProposition();
	public void setValues(String[][] results); 
	public void setProposition(String string);
	public void validerSaisie();
	public void actualiserAffichage();
}
