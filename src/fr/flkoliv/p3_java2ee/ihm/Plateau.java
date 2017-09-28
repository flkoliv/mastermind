package ihm;

/**
 * Interface des plateau de jeu (Interface graphique de jeu) Utilisable par les
 * jeux mastermind et Plus ou Moins
 * 
 * @author flkoliv
 *
 */
public interface Plateau {

	/**
	 * Pour afficher la solution pendant le jeu
	 * 
	 * @param msg
	 *            solution à afficher en mode dev
	 */
	public void setMsgDev(String msg); // afficher le code chercher

	/**
	 * Effacer le champs de proposition
	 */
	public void cleanProposition();

	/**
	 * Rajouter des valeurs dans le tableau de jeu
	 * 
	 * @param results
	 *            tableau de jeu (propositions et resultats précédents)
	 */
	public void setValues(String[][] results);

	/**
	 * Faire une proposition de combinaison
	 * 
	 * @param string
	 *            combinaison
	 */
	public void setProposition(String string);

	/**
	 * valide la combinaison saisie
	 */
	public void validerSaisie();

	/**
	 * actualise l'affichage en cas de changement
	 */
	public void actualiserAffichage();
}
