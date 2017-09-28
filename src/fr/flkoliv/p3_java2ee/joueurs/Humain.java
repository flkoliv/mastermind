package joueurs;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.NewCodeBox;
import ihm.Options;

/**
 * 
 * Classe de joueur humain
 * 
 * @author flkoliv
 *
 */
public class Humain extends Joueur {

	private static final Logger logger = LogManager.getLogger();

	public Humain() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see joueurs.Joueur#creerNouveauCode()
	 */
	@Override
	public void creerNouveauCode() {
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			String c = "";
			int nbrChiffres = Options.getInstance().getlongueurCodePlus();
			boolean b = true;
			do {
				try {
					c = JOptionPane.showInputDialog(null, "Veuillez donner un code de " + nbrChiffres + " chiffres!",
							"Nouveau Code", JOptionPane.QUESTION_MESSAGE);
					Integer.parseInt(c);
					if (c.length() == nbrChiffres) {
						b = false;
						logger.debug("nouveau code proposé : " + c);
					} else {
						b = true;
						logger.debug("code rentré n'ayant pas la bonne longueur");
					}
				} catch (Exception e) {
					logger.debug("code rentré ne contenant pas que des chiffres" + e);
				}
			} while (b);
			codeATrouver = c;
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			new NewCodeBox(this, "Nouvelle Combinaison", true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see joueurs.Joueur#chercherCode()
	 */
	@Override
	protected void chercherCode() {
		// Rien à faire ici... à l'humain de chercher son code et de le taper dans
		// l'interface

	}

	/**
	 * @param code
	 *            combinaison à trouver par l'adversaire
	 */
	public void setCodeAtrouver(String code) {
		codeATrouver = code;
	}

}
