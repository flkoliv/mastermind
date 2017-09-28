package ihm;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import joueurs.Humain;
import listener.ChoixCouleurListener;
import listener.NewCodeBoxListener;

/**
 * Boite de dialogue pour obtenir une combinaison mastermind
 * 
 * @author flkoliv
 *
 */
public class NewCodeBox extends JDialog {

	private static final long serialVersionUID = 4434370607469563328L;
	JPanel boutonsCouleur;
	JPanel choix;
	JPanel panel;
	JPanel proposition;
	String code = "";
	Humain humain;

	public NewCodeBox(Humain humain, String title, boolean modal) {
		super(Main.getInstance(), title, true);
		this.humain = humain;
		this.setSize(450, 195);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		panel = new JPanel();
		this.initComponent();
		this.setContentPane(panel);
		this.setVisible(true);

	}

	/**
	 * Initialisation de la partie graphique
	 */
	public void initComponent() {

		boutonsCouleur = new JPanel();
		for (int i = 0; i < Options.getInstance().getNbrCouleursMaster(); i++) {
			ImageIcon iconeA = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/" + i + ".png");
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + i);
			boutonsCouleur.add(imageA);
		}
		choix = new JPanel();
		proposition = new JPanel();
		choix.setBorder(BorderFactory.createTitledBorder("Proposition :"));
		proposition.setBorder(BorderFactory.createTitledBorder("Sélectionnez votre combinaison :"));
		proposition.setMaximumSize(new Dimension(450, 80));

		JPanel boutons = new JPanel();
		JButton okButton = new JButton("OK");
		JButton effacerButton = new JButton("Effacer");
		boutons.add(okButton);
		boutons.add(effacerButton);
		okButton.addActionListener(new NewCodeBoxListener(this));
		okButton.setName("OK");
		effacerButton.addActionListener(new NewCodeBoxListener(this));
		effacerButton.setName("Effacer");
		proposition.add(boutonsCouleur);
		proposition.add(boutons);
		proposition.setLayout(new BoxLayout(proposition, BoxLayout.PAGE_AXIS));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(choix);
		panel.add(proposition);

	}

	/**
	 * Ajouter une couleur dans la combinaison proposée
	 * 
	 * @param c
	 *            couleur à ajouter
	 */
	public void addColor(String c) {
		if (code.length() < Options.getInstance().getlongueurCodeMaster()) {
			code = code + c;
			ImageIcon iconeA = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/" + c + ".png");
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + c);
			choix.add(imageA);
			this.getContentPane().validate();
		}
	}

	/**
	 * Effacer la proposition pour en refaire une
	 */
	public void effacer() {
		choix.removeAll();
		code = "";
		choix.repaint();
		this.getContentPane().validate();
	}

	/**
	 * Valider le code et l'attribuer au joueur comme combinaison à trouver
	 */
	public void envoyerCode() {
		if (code.length() == Options.getInstance().getlongueurCodeMaster()) {
			humain.setCodeAtrouver(code);
			this.dispose();
		}
	}

}
