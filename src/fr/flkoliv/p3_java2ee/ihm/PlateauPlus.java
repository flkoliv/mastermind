package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import joueurs.Joueur;
import listener.EntreeListener;
import listener.OkPlateauListener;

/**
 * Plateau de jeu Plus ou Moins
 * 
 * @author flkoliv
 *
 */
public class PlateauPlus extends JPanel implements Plateau {

	private static final long serialVersionUID = 1213633100918578601L;
	private static final Logger logger = LogManager.getLogger();
	private JTextField jtf;
	private JButton okButton;
	private JPanel saisie;
	private JLabel intitule;
	private int emptyRow = 0;
	private JTable tab;
	private Joueur joueur;
	private String[][] tableauJeu;
	private String title[] = { "Proposition", "Résultat" };

	/**
	 * @param taille
	 *            nombre de propositions faisables
	 * @param longueurCode
	 *            longueur de la combinaison
	 * @param j
	 *            jouer propriétaire du plateau
	 */
	public PlateauPlus(int taille, int longueurCode, Joueur j) {
		this.joueur = j;
		saisie = new JPanel();
		saisie.setLayout(new BoxLayout(saisie, BoxLayout.LINE_AXIS));
		saisie.setMaximumSize(new Dimension(275, 25));
		jtf = new JTextField();
		jtf.setDocument(new JTextFieldLimiter(0, longueurCode));
		jtf.addKeyListener(new EntreeListener(this));
		okButton = new JButton("OK");
		okButton.setMaximumSize(new Dimension(40, 20));
		okButton.addActionListener(new OkPlateauListener(jtf, this));
		if (joueur.isHuman()) {
			intitule = new JLabel("Votre proposition : ");
		} else {
			intitule = new JLabel("Proposition Ordinateur : ");
			jtf.setEditable(false);
			okButton.setVisible(false);
		}
		saisie.add(intitule);
		saisie.add(Box.createRigidArea(new Dimension(10, 0)));
		saisie.add(jtf);
		saisie.add(Box.createRigidArea(new Dimension(10, 0)));
		saisie.add(okButton);

		tableauJeu = joueur.getTableauJeu();
		tab = new JTable(tableauJeu, title);
		tab.setFont(new Font("Courier New", Font.BOLD, 17));
		tab.setEnabled(false);
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer();
		custom.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tab.getColumnCount(); i++)
			tab.getColumnModel().getColumn(i).setCellRenderer(custom);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		this.add(saisie, BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		JScrollPane jsp = new JScrollPane(tab);
		jsp.setMaximumSize(new Dimension(275, 550));
		this.add(jsp, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(275, 700));
		this.setVisible(true);

	}

	/**
	 * Efface la proposition
	 * 
	 * @see ihm.Plateau#cleanProposition()
	 */
	@Override
	public void cleanProposition() {
		jtf.setText("");
	}

	/**
	 * récupère la proposition dans le JtextField du plateau
	 * 
	 * @param string
	 *            chaîne de caractère représentant la proposition
	 * @see ihm.Plateau#setProposition(java.lang.String)
	 */
	@Override
	public void setProposition(String string) {
		jtf.setText(string);
	}

	/**
	 * Affiche la combinaison secrète à des fin de débogage
	 * 
	 * @param msg
	 *            la combinaison à afficher sous forme de chaîne de caractère de
	 *            valeurs numériques (0-9)
	 * @see ihm.Plateau#setMsgDev(java.lang.String)
	 * 
	 */
	@Override
	public void setMsgDev(String msg) {
		intitule.setText("Code:" + msg);
	}

	/**
	 * Récupère les résultat et actualise l'affichage
	 * 
	 * @param results
	 *            tableau de proposition et de résultats
	 * @see ihm.Plateau#setValues(java.lang.String[][])
	 */
	@Override
	public void setValues(String[][] results) {
		tableauJeu = results;
		actualiserAffichage();
	}

	/**
	 * valide la proposition (par l'appui sur le bouton "OK" du JPanel
	 * 
	 * @see ihm.Plateau#validerSaisie()
	 */
	@Override
	public void validerSaisie() {
		tableauJeu[emptyRow][0] = jtf.getText();
		this.emptyRow++;
		joueur.setTableauJeu(tableauJeu);
		actualiserAffichage();
		cleanProposition();
		joueur.faireProposition();
	}

	/**
	 * Actualise l'affichage à chaque changement de proposition ou de réponse
	 * 
	 * @see ihm.Plateau#actualiserAffichage()
	 */
	@Override
	public void actualiserAffichage() {
		for (int i = 0; i < tableauJeu.length; i++) {
			tab.setValueAt(tableauJeu[i][0], i, 0);
			tab.setValueAt(tableauJeu[i][1], i, 1);
		}

	}

}
