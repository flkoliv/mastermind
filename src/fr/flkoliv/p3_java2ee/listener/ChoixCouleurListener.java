package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import ihm.NewCodeBox;
import ihm.PlateauMaster;

/**
 * Listener pour le plateau mastermind et pour la fenêtre de proposition d'une
 * combinaison mastermind. Détecte les clic sur les images des pions de couleur.
 * 
 * @author flkoliv
 *
 */
public class ChoixCouleurListener implements MouseListener {

	PlateauMaster plateau;
	NewCodeBox newCodeBox;

	/**
	 * Constructeur pour les clics provenant du plateau mastermind
	 * 
	 * @param p
	 *            plateau mastermind
	 */
	public ChoixCouleurListener(PlateauMaster p) {
		this.plateau = p;
	}

	/**
	 * Constructeur pour les clics provenant de la dialogBox de création d'une
	 * combinaison mastermind
	 * 
	 * @param newCodeBox
	 *            dialogBox de création d'une combinaison mastermind
	 */
	public ChoixCouleurListener(NewCodeBox newCodeBox) {
		this.newCodeBox = newCodeBox;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {// non utilisé
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {// non utilisé
	}

	@Override
	public void mouseExited(MouseEvent arg0) {// non utilisé
	}

	@Override
	public void mousePressed(MouseEvent arg0) {// non utilisé
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (plateau != null) { // si le clique provient du plateau mastermind
			plateau.ajouter(((JLabel) arg0.getSource()).getName());
		}
		if (newCodeBox != null) { // si le clic provient de la fenêtre de choix de combinaison
			newCodeBox.addColor(((JLabel) arg0.getSource()).getName());
		}
	}

}
