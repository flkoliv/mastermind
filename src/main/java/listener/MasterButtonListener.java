package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ihm.PlateauMaster;

public class MasterButtonListener implements ActionListener {

	private PlateauMaster plateau;

	public MasterButtonListener(PlateauMaster p) {
		this.plateau = p;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (((JButton) arg0.getSource()).getText().equals("OK")) {
			plateau.validerSaisie();
		}else if (((JButton) arg0.getSource()).getText().equals("Effacer")) {
			plateau.cleanProposition();
		}
		// (JButton)arg0.getSource()

		

	}

}
