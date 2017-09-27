package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.NewCodeBox;

/**
 * Listener de la fenêtre de choix de la combinaison mastermind
 * 
 * @author flkolivf
 */
public class NewCodeBoxListener implements ActionListener {

	NewCodeBox n;

	public NewCodeBoxListener(NewCodeBox n) {
		this.n = n;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (((JButton) arg0.getSource()).getName() == "OK") {
			n.envoyerCode();
		} else if (((JButton) arg0.getSource()).getName() == "Effacer") {
			n.effacer();
		}
	}

}
