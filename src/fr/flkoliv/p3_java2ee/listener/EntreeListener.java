package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.Options;
import ihm.Plateau;

/**
 * Listener pour l'appui sur la touche entrée sur le plateau plus ou moins
 * 
 * @author flkoliv
 *
 */
public class EntreeListener implements KeyListener {

	private static final Logger logger = LogManager.getLogger();
	private Plateau p;

	public EntreeListener(Plateau p) {
		this.p = p;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextField jtf = (JTextField) arg0.getSource();
			String prop = jtf.getText();
			if (prop.length() < Options.getInstance().getlongueurCodePlus()) {// si la longueur n'est pas assez grande
				jtf.setText("");
				logger.debug("proposition pas assez longue : " + prop);
			} else { // si la taille est bonne
				logger.debug("proposition ok");
				p.validerSaisie();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {//pas utilisé
	}

	@Override
	public void keyPressed(KeyEvent arg0) {//pas utilisé
	}
}
