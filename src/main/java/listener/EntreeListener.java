package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ihm.Options;
import joueurs.Joueur;

public class EntreeListener implements KeyListener {

	private static final Logger logger = LogManager.getLogger();
	private Joueur j;

	public EntreeListener(Joueur j) {
		this.j=j;
	}
	
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextField jtf = (JTextField) arg0.getSource();
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

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}
}
