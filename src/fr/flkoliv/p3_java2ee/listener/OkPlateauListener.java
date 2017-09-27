package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ihm.Options;
import ihm.Plateau;

/**
 * Listener du plateau plus ou moins
 * 
 * @author flkoliv
 *
 */
public class OkPlateauListener implements ActionListener {
	private static final Logger logger = LogManager.getLogger();

	private JTextField jtf;
	private Plateau p;

	public OkPlateauListener(JTextField jtf, Plateau p) {
		this.jtf = jtf;
		this.p = p;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String prop = jtf.getText();
		if (prop.length() < Options.getInstance().getlongueurCodePlus()) {
			jtf.setText("");
			logger.debug("proposition pas assez longue : " + prop);
		} else {
			logger.debug("proposition ok");
			p.validerSaisie();
		}

	}

}
