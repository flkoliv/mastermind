/**
 * 
 */
package ihm;

import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import listener.AideListener;

/**
 * Boite de dialogue d'aide. affiche le fichier aide.html
 * @author flkoliv
 *
 */
public class Aide extends JDialog {

	private static final Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = -2922777535126880597L;
	private JPanel panel;
	private JButton ok = new JButton("OK");
	private JEditorPane tp;

	public Aide(Main parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);

	}

	/**
	 * initialise la partie graphique
	 */
	public void initComponent() {
		panel= new JPanel();
		URL url;
		try {
			url = getClass().getResource("/aide.html");
			tp = new JEditorPane(url);
			logger.debug("chargement du fichier d'aide");
		} catch (IOException e1) {
			logger.debug("impossible de charger le fichier d'aide");
		}
		ok.addActionListener(new AideListener(this));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		tp.setEditable(false);
		panel.add(tp);
		panel.add(ok);
		this.setContentPane(panel);
	}

}
