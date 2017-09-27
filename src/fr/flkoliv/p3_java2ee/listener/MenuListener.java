/**
 * 
 */
package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.Aide;
import ihm.Main;
import ihm.OptionsDialogBox;
import joueurs.ModeJeu;
import joueurs.TypeJeu;

/**
 * Listener du menu de la fenêtre principale du jeu
 * 
 * @author flkoliv
 *
 */
public class MenuListener implements ActionListener {

	private static final Logger logger = LogManager.getLogger();
	private Main fenetre;

	public MenuListener(Main main) {
		this.fenetre = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JMenuItem) e.getSource()).getName()) {
		case "challengerPlus":
			logger.info("Lancement jeu Plus ou moins en mode Challenger");
			fenetre.getJoueur1().commencer(TypeJeu.PLUSOUMOINS, ModeJeu.CHALLENGER);
			break;
		case "defenseurPlus":
			logger.info("Lancement jeu Plus ou moins en mode Défenseur");
			fenetre.getJoueur2().commencer(TypeJeu.PLUSOUMOINS, ModeJeu.DEFENSEUR);
			break;
		case "duelPlus":
			logger.info("Lancement jeu Plus ou moins en mode Duel");
			fenetre.getJoueur1().commencer(TypeJeu.PLUSOUMOINS, ModeJeu.DUEL);
			break;
		case "challengerMaster":
			logger.info("Lancement jeu mastermind en mode Challenger");
			fenetre.getJoueur1().commencer(TypeJeu.MASTERMIND, ModeJeu.CHALLENGER);
			break;
		case "defenseurMaster":
			logger.info("Lancement jeu mastermind en mode Défenseur");
			fenetre.getJoueur2().commencer(TypeJeu.MASTERMIND, ModeJeu.DEFENSEUR);
			break;
		case "duelMaster":
			logger.info("Lancement jeu mastermind en mode Duel");
			fenetre.getJoueur1().commencer(TypeJeu.MASTERMIND, ModeJeu.DUEL);
			break;
		case "options":
			logger.info("Lancement de la boite de dialogue options");
			new OptionsDialogBox(null, "Options", true);
			break;
		case "aide":
			logger.info("Lancement de l'aide");
			new Aide(Main.getInstance(), "Aide", true);
			break;
		case "quitter":
			logger.info("fin du programme");
			System.exit(0);
			break;
		}

	}

}
