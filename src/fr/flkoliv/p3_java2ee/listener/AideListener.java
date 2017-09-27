package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * Listener de la DialogBox Aide (pour la fermer en appuyant sur OK)
 * 
 * @author flkoliv
 *
 */
public class AideListener implements ActionListener {

	private JDialog j;

	public AideListener(JDialog j) {
		this.j = j;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		j.dispose(); // ferme la dialogBox aide
	}

}
