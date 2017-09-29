/**
 * 
 */
package ihm;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import listener.AideListener;

/**
 * Boite de dialogue d'aide. Affiche le fichier "aide.html"
 * 
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
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);

	}

	/**
	 * Initialise la partie graphique
	 */
	public void initComponent() {
		panel = new JPanel();
		
		URL url;
		try {
			url = getClass().getResource("/aide/aide.html");
			tp = new JEditorPane(url);
			
			logger.debug("chargement du fichier d'aide");
		} catch (IOException e1) {
			logger.debug("impossible de charger le fichier d'aide");
		}
		
		panel.setLayout(new BorderLayout());
		ok.addActionListener(new AideListener(this));
		tp.setEditable(false);
		panel.add(new JScrollPane(tp),BorderLayout.CENTER);
		panel.add(ok, BorderLayout.SOUTH );
		
		this.setContentPane(panel);
	}

}
