/**
 * 
 */
package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.Aide;
import ihm.Main;
import ihm.OptionsDialogBox;
import joueurs.ModeJeu;
import joueurs.TypeJeu;

/**
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
			fenetre.getJoueur1().commencer(TypeJeu.PLUSOUMOINS,ModeJeu.CHALLENGER);
			break;
		case "defenseurPlus":
			fenetre.getJoueur2().commencer(TypeJeu.PLUSOUMOINS,ModeJeu.DEFENSEUR);
			break;
		case "duelPlus":
			fenetre.getJoueur1().commencer(TypeJeu.PLUSOUMOINS,ModeJeu.DUEL);
			break;
		case "challengerMaster":
			fenetre.getJoueur1().commencer(TypeJeu.MASTERMIND,ModeJeu.CHALLENGER);
			break;
		case "defenseurMaster":
			fenetre.getJoueur2().commencer(TypeJeu.MASTERMIND,ModeJeu.DEFENSEUR);
			break;
		case "duelMaster":
			fenetre.getJoueur1().commencer(TypeJeu.MASTERMIND,ModeJeu.DUEL);
			break;
		case "options":
			new OptionsDialogBox(null, "Options", true);
			break;
		case "aide":
			//JOptionPane.showMessageDialog(null, "Aide", "Attention", JOptionPane.WARNING_MESSAGE);
			new Aide(Main.getInstance(),"Aide",true);
			break;
		case "quitter":
			System.exit(0);
			break;
		}

	}

}
