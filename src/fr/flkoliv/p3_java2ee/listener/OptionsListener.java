package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import ihm.Options;
import ihm.OptionsDialogBox;

/**
 * Listener de la boite de dialogue options
 * 
 * @author flkoliv
 *
 */
public class OptionsListener implements ActionListener{

	OptionsDialogBox optionsDialogBox;

	public OptionsListener(OptionsDialogBox optionsDialogBox) {
		this.optionsDialogBox = optionsDialogBox;

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	JButton j = (JButton)arg0.getSource();
	switch (j.getText()) {
	case "OK":
		try {
			Options.getInstance().setOptions(optionsDialogBox.getOptions());
			Options.getInstance().sauvegardeConfig();
			optionsDialogBox.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Attention au valeurs rentrées !", "Attention",
					JOptionPane.WARNING_MESSAGE);

		}
		break;
	case "Annuler":
		optionsDialogBox.dispose();
		break;
	}
		
	}

}
