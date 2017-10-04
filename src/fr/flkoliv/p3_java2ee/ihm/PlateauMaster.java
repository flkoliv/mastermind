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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import joueurs.Joueur;
import layout.TableLayout;
import listener.ChoixCouleurListener;
import listener.MasterButtonListener;

/**
 * Plateau de jeu Mastermind
 * 
 * @author flkoliv
 *
 */
public class PlateauMaster extends JPanel implements Plateau {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
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

	/**
	 * @param taille
	 *            nombre de propositions faisables
	 * @param longueurCode
	 *            longueur de la combinaison
	 * @param j
	 *            joueur propriétaire du plateau
	 */
	public PlateauMaster(int taille, int longueurCode, Joueur j) {
		this.emptyRow = 0;
		this.taille = taille;
		this.joueur = j;
		this.longueurCode = longueurCode;
		this.tableauJeu = j.getTableauJeu();
		dev = new JPanel();
		dev.setVisible(false);
		dev.setLayout(new BoxLayout(dev, BoxLayout.LINE_AXIS));
		dev.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Solution", 0, 0,
				new Font("Dialog", 1, 12), Color.BLACK));
		boutons = new JPanel();
		boutons.setLayout(new BoxLayout(boutons, BoxLayout.LINE_AXIS));
		okButton = new JButton("OK");
		okButton.addActionListener(new MasterButtonListener(this));
		effacerButton = new JButton("Effacer");
		effacerButton.addActionListener(new MasterButtonListener(this));
		boutons.add(okButton);
		boutons.add(Box.createRigidArea(new Dimension(10, 0)));
		boutons.add(effacerButton);
		boutonsCouleur = new JPanel();
		boutonsCouleur.setLayout(new BoxLayout(boutonsCouleur, BoxLayout.LINE_AXIS));
		for (int i = 0; i < Options.getInstance().getNbrCouleursMaster(); i++) {
			ImageIcon iconeA = new ImageIcon(getClass().getResource("/img/" + i + ".png"));
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + i);
			boutonsCouleur.add(imageA);
		}
		resultat = new JPanel();
		actualiserAffichage();
		JPanel res = new JPanel();
		res.add(resultat);

		dev.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultat.setAlignmentX(Component.RIGHT_ALIGNMENT);
		boutonsCouleur.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutons.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (!joueur.isHuman()) {
			boutons.setVisible(false);
			boutonsCouleur.setVisible(false);

		}
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(dev);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(res);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(boutonsCouleur);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(boutons);
		if (!joueur.isHuman()) {
			boutons.setVisible(false);
			boutonsCouleur.setVisible(false);
			this.add(Box.createRigidArea(new Dimension(0, 55)));
		}

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
		for (int i = 0; i < longueurCode; i++) {
			if (!msg.equals("") && msg != null) {
				ImageIcon ic = new ImageIcon(getClass().getResource("/img/" + msg.charAt(i) + ".png"));
				JLabel x = new JLabel(ic);
				dev.add(x);
				dev.setVisible(true);
			}
		}
		logger.debug("affichage du message dev : "+msg);

	}

	/**
	 * Actualise l'affichage à chaque changement de proposition ou de réponse
	 * 
	 * @see ihm.Plateau#actualiserAffichage()
	 */
	@Override
	public void actualiserAffichage() {
		resultat.removeAll(); // tout effacer pour mieux recommencer !
		double longueur = longueurCode;
		resultat.setSize(longueurCode * 30 + ((int) Math.ceil(longueur/2) * 15), taille * 30);
		double size[][] = new double[2][];// créer un tableau de tailles de case pour le TableLayout
		size[0] = new double[longueurCode + 1];
		size[1] = new double[taille];
		for (int i = 0; i < longueurCode; i++) {
			size[0][i] = 30;
		}
		for (int i = 0; i < taille; i++) {
			size[1][i] = 30;
		}

		size[0][longueurCode] = Math.ceil(longueur/2) * 15;

		resultat.setLayout(new TableLayout(size));
		for (int i = 0; i < tableauJeu.length; i++) {
			if (tableauJeu[i][0] == null || tableauJeu[i][0].equals("")) {// s'il n'y a pas de proposition dans le
																			// tableau
				for (int j = 0; j < longueurCode; j++) {
					ImageIcon ic = new ImageIcon(getClass().getResource("/img/vide.gif"));
					JLabel x = new JLabel(ic);

					x.setBackground(Color.decode("0XCCCCCC"));
					x.setOpaque(true);
					x.setBorder(BorderFactory.createLineBorder(Color.black));
					String s = j + "," + i;
					resultat.add(x, s);
				}
			} else { // s'il y a une proposition dans le tableau
				for (int j = 0; j < tableauJeu[i][0].length(); j++) {

					ImageIcon ic = new ImageIcon(getClass().getResource("/img/" + tableauJeu[i][0].charAt(j) + ".png"));
					JLabel x = new JLabel(ic);
					x.setBackground(Color.decode("0XCCCCCC"));
					x.setOpaque(true);
					x.setBorder(BorderFactory.createLineBorder(Color.black));
					String s = j + "," + i;
					resultat.add(x, s);
				}
				for (int j = tableauJeu[i][0].length(); j < longueurCode; j++) {

					ImageIcon ic = new ImageIcon(getClass().getResource("/img/vide.gif"));
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
					ImageIcon ic = new ImageIcon(getClass().getResource("/img/vide15.gif"));
					JLabel x = new JLabel(ic);
					result.add(x);
				} else if (j < tableauJeu[i][1].length()) {

					if (tableauJeu[i][1].charAt(j) == '=') {
						ImageIcon ic = new ImageIcon(getClass().getResource("/img/noir.png"));
						JLabel x = new JLabel(ic);
						x.setBackground(Color.decode("0XCCCCCC"));
						x.setOpaque(true);
						result.add(x);
					} else if (tableauJeu[i][1].charAt(j) == '-') {
						ImageIcon ic = new ImageIcon(getClass().getResource("/img/blanc.png"));
						JLabel x = new JLabel(ic);
						x.setBackground(Color.decode("0XCCCCCC"));
						x.setOpaque(true);
						result.add(x);
					}
				}
			}
			if (longueurCode % 2 != 0) {// si la longueur du code est impaire rajouter une case (purement esthétique)
				ImageIcon ic = new ImageIcon(getClass().getResource("/img/vide15.gif"));
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
	 * 
	 * @param name
	 *            la couleur sous forme de String (valeur numérique de 0-9)
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

	/**
	 * Efface la proposition pour pouvoir en faire une nouvelle (avant validation)
	 * 
	 * @see ihm.Plateau#cleanProposition()
	 */
	@Override
	public void cleanProposition() {
		tableauJeu[emptyRow][0] = "";
		actualiserAffichage();

	}

	/**
	 * valide la proposition (par l'appui sur le bouton "OK" du JPanel
	 * 
	 * @see ihm.Plateau#validerSaisie()
	 */
	@Override
	public void validerSaisie() {
		if (tableauJeu[emptyRow][0].length() == longueurCode) {
			emptyRow++;
			joueur.setTableauJeu(tableauJeu);
			joueur.faireProposition();

		}
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
	 * Mettre la proposition dans le tableau de jeu et actualiser l'affichage
	 * 
	 * @param string
	 *            chaîne de caractère représentant la proposition
	 * @see ihm.Plateau#setProposition(java.lang.String)
	 */
	@Override
	public void setProposition(String string) {
		tableauJeu[emptyRow][0] = string;
		actualiserAffichage();

	}

}
