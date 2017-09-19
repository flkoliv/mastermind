package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ihm.Options;
import joueurs.Joueur;

public class OkPlateauListener implements ActionListener {
	private static final Logger logger = LogManager.getLogger();

	private JTextField jtf;
	private Joueur j;

	public OkPlateauListener(JTextField jtf, Joueur j) {
		this.jtf = jtf;
		this.j = j;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String prop = jtf.getText();
		if (prop.length() < Options.getInstance().getlongueurCodePlus()) {
			jtf.setText("");
			logger.debug("proposition pas assez longue : " + prop);
		} else {
			logger.debug("proposition ok");
			jtf.setText("");
			
			j.setProposition(prop);
			
		}

	}

}
