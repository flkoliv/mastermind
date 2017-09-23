package ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import joueurs.Joueur;
import layout.TableLayout;
import listener.MasterButtonListener;
import listener.ChoixCouleurListener;

public class PlateauMaster extends JPanel implements Plateau {

	private static final long serialVersionUID = 1L;
	private int longueurCode;
	private int taille;
	private JButton okButton;
	private JButton effacerButton;
	private Joueur joueur;
	private JPanel resultat;
	private JPanel boutons;
	private JPanel boutonsCouleur;
	private JPanel dev;
	private String[][] tableauJeu;

	private int emptyRow;

	public PlateauMaster(int taille, int longueurCode, Joueur j) {
		this.emptyRow = 0;
		this.taille = taille;
		this.joueur = j;
		this.longueurCode = longueurCode;
		this.tableauJeu = j.getTableauJeu();
		dev = new JPanel();
		dev.setVisible(false);
		dev.setLayout(new BoxLayout(dev, BoxLayout.LINE_AXIS));
		dev.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Solution",0,0,new Font("Dialog", 1, 12),Color.BLACK));
		boutons = new JPanel();
		boutons.setLayout(new BoxLayout(boutons, BoxLayout.LINE_AXIS));
		okButton = new JButton("OK");
		okButton.addActionListener(new MasterButtonListener(this));
		effacerButton = new JButton("Effacer");
		effacerButton.addActionListener(new MasterButtonListener(this));
		boutons.add(okButton);
		boutons.add(Box.createRigidArea(new Dimension(20, 0)));
		boutons.add(effacerButton);
		boutonsCouleur = new JPanel();
		boutonsCouleur.setLayout(new BoxLayout(boutonsCouleur, BoxLayout.LINE_AXIS));
		for (int i = 0; i < Options.getInstance().getNbrCouleursMaster(); i++) {
			ImageIcon iconeA = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/" + i + ".png");
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + i);
			boutonsCouleur.add(imageA);
		}
		resultat = new JPanel();
		actualiserAffichage();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(boutons);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(boutonsCouleur);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(resultat);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(dev);
	}

	/**
	 * Affiche la combinaison secrète à des fin de débogage
	 * @param msg la combinaison à afficher sous forme de chaîne de caractère de valeurs numériques (0-9)
	 */
	@Override
	public void setMsgDev(String msg) {
		for (int i=0;i<longueurCode;i++) {
			ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/" + msg.charAt(i) + ".png");
			JLabel x = new JLabel(ic);
			dev.add(x);
			dev.setVisible(true);
		}

	}

	/**
	 * Actualise l'affichage à chaque changement de proposition ou de réponse
	 */
	public void actualiserAffichage() {
		resultat.removeAll();
		double size[][] = new double[2][];//créer un tableau de tailles de case pour le TableLayout
		size[0] = new double[longueurCode + 1];
		size[1] = new double[taille];
		for (int i = 0; i < longueurCode; i++) {
			size[0][i] = 30;
		}
		for (int i = 0; i < taille; i++) {
			size[1][i] = 30;
		}
		size[0][longueurCode] = longueurCode * 15 / 2;

		resultat.setLayout(new TableLayout(size));
		for (int i = 0; i < tableauJeu.length; i++) {
			if (tableauJeu[i][0] == null || tableauJeu[i][0].equals("")) {// s'il n'y a pas de proposition dans le
																			// tableau
				for (int j = 0; j < longueurCode; j++) {
					ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/vide.gif");
					JLabel x = new JLabel(ic);

					x.setBackground(Color.decode("0XCCCCCC"));
					x.setOpaque(true);
					x.setBorder(BorderFactory.createLineBorder(Color.black));
					String s = j + "," + i;
					resultat.add(x, s);
				}
			} else { // s'il y a une proposition dans le tableau
				for (int j = 0; j < tableauJeu[i][0].length(); j++) {

					ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/" + tableauJeu[i][0].charAt(j) + ".png");
					JLabel x = new JLabel(ic);
					x.setBackground(Color.decode("0XCCCCCC"));
					x.setOpaque(true);
					x.setBorder(BorderFactory.createLineBorder(Color.black));
					String s = j + "," + i;
					resultat.add(x, s);
				}
				for (int j = tableauJeu[i][0].length(); j < longueurCode; j++) {

					ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/vide.gif");
					JLabel x = new JLabel(ic);
					x.setBackground(Color.decode("0XCCCCCC"));
					x.setOpaque(true);
					x.setBorder(BorderFactory.createLineBorder(Color.black));
					String s = j + "," + i;
					resultat.add(x, s);
				}
			}
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(2, Math.round(longueurCode / 2)));
			for (int j = 0; j < longueurCode; j++) {
				if (tableauJeu[i][1] == null || tableauJeu[i][1].equals("") || j >= tableauJeu[i][1].length()) {
					ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee//ressources/vide15.gif");
					JLabel x = new JLabel(ic);
					result.add(x);
				} else if (j < tableauJeu[i][1].length()) {

					if (tableauJeu[i][1].charAt(j) == '=') {
						ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/noir.png");
						JLabel x = new JLabel(ic);
						x.setBackground(Color.decode("0XCCCCCC"));
						x.setOpaque(true);
						result.add(x);
					} else if (tableauJeu[i][1].charAt(j) == '-') {
						ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/blanc.png");
						JLabel x = new JLabel(ic);
						x.setBackground(Color.decode("0XCCCCCC"));
						x.setOpaque(true);
						result.add(x);
					}
				}
			}
			if (longueurCode % 2 != 0) {// si la longueur du code est impaire rajouter une case (purement esthétique)
				ImageIcon ic = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/vide15.gif");
				JLabel x = new JLabel(ic);
				result.add(x);
			}
			result.setBorder(BorderFactory.createLineBorder(Color.black));
			String str = longueurCode + "," + i;
			resultat.add(result, str);
		}
		resultat.validate();
	}

	
	/**
	 * ajoute une couleur au tableau lors d'un clic
	 * @param name la couleur sous forme de String (valeur numérique de 0-9)
	 */
	public void ajouter(String name) {
		if (tableauJeu[emptyRow][0] == null) {
			tableauJeu[emptyRow][0] = "";
		}
		if (tableauJeu[emptyRow][0].length() < longueurCode) {
			tableauJeu[emptyRow][0] = tableauJeu[emptyRow][0] + name;
			actualiserAffichage();
		}
	}

	@Override
	/**
	 * Efface la proposition pour pouvoir en faire une nouvelle (avant validation)
	 */
	public void cleanProposition() {
		tableauJeu[emptyRow][0] = "";
		actualiserAffichage();

	}

	@Override
	/**
	 * valide la proposition (par l'appui sur le bouton ok du JPanel
	 */
	public void validerSaisie() {
		if (tableauJeu[emptyRow][0].length() == longueurCode) {
			emptyRow++;
			joueur.setTableauJeu(tableauJeu);
			joueur.faireProposition();

		}
	}

	@Override
	/**
	 * récupère les résultat et actualise l'affichage
	 * @param results tableau de proposition et de résultats
	 */
	public void setValues(String[][] results) {
		tableauJeu = results;
		actualiserAffichage();

	}

	@Override
	/**
	 * mettre la proposition dans le tableau de jeu et actualiser l'affichage
	 * @param string chaîne de caractère représentant la proposition
	 */
	public void setProposition(String string) {
		tableauJeu[emptyRow][0] = string;
		actualiserAffichage();

	}

}
