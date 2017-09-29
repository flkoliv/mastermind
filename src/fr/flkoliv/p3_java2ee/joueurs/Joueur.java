package joueurs;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.Main;
import ihm.Options;
import ihm.Plateau;
import ihm.PlateauMaster;
import ihm.PlateauPlus;

/**
 * Classe abstraite joueur (avec les fonction de base pour jouer au 'plus ou
 * moins' et au 'mastermind')
 * 
 * @author flkoliv
 *
 */
public abstract class Joueur extends Observable implements Observer {

	protected Joueur adversaire;
	protected ModeJeu modeJeu;
	protected TypeJeu typeJeu;
	protected Plateau plateau;
	protected String proposition = "";
	protected String resultat = "";
	protected String codeATrouver = "";
	protected int toursRestants = 0;
	protected boolean gagnant = false;
	protected String[][] tableauJeu;
	protected int emptyRowTableauJeu;
	private static final Logger logger = LogManager.getLogger();

	public Joueur() {

	}

	/**
	 * Débuter la partie ! initialise les valeurs et la partie graphique et lance le
	 * jeu
	 * 
	 * @param t
	 *            type de jeu (Plus ou moins / mastermind)
	 * @param m
	 *            mode de jeu (duel, defenseur, challenger)
	 */
	public void commencer(TypeJeu t, ModeJeu m) {
		emptyRowTableauJeu = adversaire.emptyRowTableauJeu = 0;
		typeJeu = adversaire.typeJeu = t;
		modeJeu = adversaire.modeJeu = m;
		gagnant = adversaire.gagnant = false;
		resultat = adversaire.resultat = "";
		proposition = adversaire.proposition = "";
		if (typeJeu == TypeJeu.MASTERMIND) {
			tableauJeu = new String[Options.getInstance().getNbrEssaisMaster()][Options.getInstance()
					.getlongueurCodeMaster()];
			adversaire.tableauJeu = new String[Options.getInstance().getNbrEssaisMaster()][Options.getInstance()
					.getlongueurCodeMaster()];
			toursRestants = adversaire.toursRestants = Options.getInstance().getNbrEssaisMaster();
		} else if (typeJeu == TypeJeu.PLUSOUMOINS) {
			tableauJeu = new String[Options.getInstance().getNbrEssaisPlus()][Options.getInstance()
					.getlongueurCodePlus()];
			adversaire.tableauJeu = new String[Options.getInstance().getNbrEssaisPlus()][Options.getInstance()
					.getlongueurCodePlus()];
			toursRestants = adversaire.toursRestants = Options.getInstance().getNbrEssaisPlus();
		}
		initGraphique();
		if (m == ModeJeu.DUEL) {
			creerNouveauCode();
		}
		adversaire.creerNouveauCode(); // obtenir une combinaison à chercher
		if (Options.getInstance().getDev()) {// afficher les code si le mode développeur est selectionné
			plateau.setMsgDev(adversaire.codeATrouver);
			if (m == ModeJeu.DUEL) {
				adversaire.plateau.setMsgDev(codeATrouver);
			}
		}
		chercherCode();
	}

	/**
	 * Chercher la combinaison en fonction des propositions et des résultats
	 * précédents
	 */
	protected abstract void chercherCode();

	/**
	 * Obtenir une nouvelle combinaison à chercher
	 */
	public abstract void creerNouveauCode();

	/**
	 * Construire la réponse du jeu plus ou moins en fonction du code fourni
	 * 
	 * @param code
	 *            proposition de code
	 * @return réponse sous forme de signe (+, - ou =)
	 */
	public String construireReponsePlus(String code) {
		String result = "";
		for (int i = 0; i < codeATrouver.length(); i++) {
			int j = codeATrouver.charAt(i);
			int k = code.charAt(i);
			if (j == k) {
				result = result + "=";
			} else if (j > k) {
				result = result + "+";
			} else {
				result = result + "-";
			}
		}
		return result;

	}

	/**
	 * Construit la réponse du jeu mastermind à une combinaison proposée.
	 * 
	 * @param code
	 *            proposition de combinaison
	 * @return résultat sous la forme =couleur à la bonne place -couleur à la
	 *         mauvaise place
	 */
	public String construireReponseMaster(String code) {
		String result = "";
		boolean[] tab = new boolean[code.length()];
		boolean[] tab2 = new boolean[code.length()];

		// initialise les tableaux (pour empecher l'utilisation si déja comparé)
		for (int i = 0; i < code.length(); i++) {
			tab[i] = true;
			tab2[i] = true;
		}

		// premier tours pour verifier les couleurs à la bonne place (=)
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == codeATrouver.charAt(i)) {
				result = result + "=";
				tab[i] = false;
				tab2[i] = false;
			}
		}

		// 2nd tour pour vérifier les couleurs à la mauvaise place (-)
		for (int i = 0; i < code.length(); i++) {
			if (tab[i]) {
				for (int j = 0; j < code.length(); j++) {

					if (code.charAt(i) == codeATrouver.charAt(j) && tab2[j]) {
						result = result + "-";
						tab2[j] = false;
						j = code.length();

					}

				}
			}

		}
		return result;
	}

	/*
	 * (non-Javadoc) actions à faire en cas de notification reçue
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		String prop = (String) arg1;
		if (codeATrouver.equals(prop)) {
			adversaire.gagnant = true;
		}
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			adversaire.setResult(construireReponsePlus(prop));
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			adversaire.setResult(construireReponseMaster(prop));
		}

	}

	/**
	 * Permet d'afficher et d'enregistrer la réponse donnée par l'adversaire et de
	 * vérifier si un joueur est gagnant
	 * 
	 * @param reponse
	 *            donnée par l'adversaire à une proposition
	 */
	public void setResult(String reponse) {
		toursRestants--;
		tableauJeu[emptyRowTableauJeu - 1][1] = reponse;
		this.plateau.setValues(tableauJeu);
		if (modeJeu == ModeJeu.DUEL) {
			if (toursRestants == adversaire.toursRestants && (gagnant || adversaire.gagnant || toursRestants == 0)) {
				finPartie();
			} else {
				adversaire.chercherCode();
			}
		} else {
			if (gagnant || toursRestants == 0) {
				finPartie();
			} else {
				chercherCode();
			}
		}
	}

	/**
	 * Vérifie si le joueur est humain
	 * 
	 * @return vrai si le joueur est humain, faux si c'est l'ordinateur
	 */
	public boolean isHuman() {
		if (this.getClass() == Humain.class) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Définir l'adversaire d'un joueur et permettre que les adversaires puissent
	 * s'observer
	 * 
	 * @param j
	 *            joueur
	 */
	public void setAdversaire(Joueur j) {
		if (j.adversaire != this) {
			j.adversaire = this;
			j.addObserver(this);
		}
		this.adversaire = j;
		this.addObserver(j);
	}

	/**
	 * Initialiser l'interface graphique (les plateaux) en fonction du type de jeu
	 * et du mode.
	 */
	protected void initGraphique() {
		Main.getInstance().getContentPane().removeAll();
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			plateau = new PlateauPlus(Options.getInstance().getNbrEssaisPlus(),
					Options.getInstance().getlongueurCodePlus(), this);
			Main.getInstance().getContentPane().add((Component) plateau);
			if (modeJeu == ModeJeu.DUEL) {
				adversaire.plateau = new PlateauPlus(Options.getInstance().getNbrEssaisPlus(),
						Options.getInstance().getlongueurCodePlus(), adversaire);
				Main.getInstance().getContentPane().add((Component) adversaire.plateau);
			}
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			plateau = new PlateauMaster(Options.getInstance().getNbrEssaisMaster(),
					Options.getInstance().getlongueurCodeMaster(), this);
			Main.getInstance().getContentPane().add((Component) plateau);
			if (modeJeu == ModeJeu.DUEL) {
				adversaire.plateau = new PlateauMaster(Options.getInstance().getNbrEssaisMaster(),
						Options.getInstance().getlongueurCodeMaster(), adversaire);
				Main.getInstance().getContentPane().add((Component) adversaire.plateau);
			}
		}
		Main.getInstance().getContentPane().validate();
		Main.getInstance().repaint();
	}

	/**
	 * Envoyer une notification à l'adversaire pour le prévenir d'une proposition de
	 * combinaison
	 */
	public void faireProposition() {
		emptyRowTableauJeu++;
		setChanged();
		notifyObservers(tableauJeu[emptyRowTableauJeu - 1][0]);
	}

	/**
	 * Actions en cas de fin de partie. Affichage d'un message personnalisé
	 */
	public void finPartie() {
		String message = "";
		if (modeJeu == ModeJeu.CHALLENGER) {
			if (gagnant) {
				message = "Vous avez gagné !";
			} else {
				message = "Vous avez perdu !";
			}
		} else if (modeJeu == ModeJeu.DEFENSEUR) {
			if (gagnant) {
				message = "L'ordinateur a gagné !";
			} else {
				message = "L'ordinateur a perdu !";
			}
		} else if (modeJeu == ModeJeu.DUEL) {
			if (gagnant && adversaire.gagnant) {
				message = "Gagné Ex Aequo!";
			} else if ((isHuman() && gagnant) || (adversaire.isHuman() && adversaire.gagnant)) {
				message = "Vous avez gagné !";
			} else {
				message = "Vous avez perdu !";
			}
		}
		logger.info(message);
		message = message + "\n Voulez-vous recommencer la partie ?";

		int o = JOptionPane.showConfirmDialog(null, message, "Attention", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (o == 0) {
			if (modeJeu == ModeJeu.DUEL) {
				adversaire.commencer(typeJeu, modeJeu);// recommencer la partie
			} else {
				commencer(typeJeu, modeJeu);
			}
			logger.info("la partie recommence");
		} else {
			Main.setBackground(); // retour sur la fenetre de début
			logger.info("fin de partie");
		}
	}

	/**
	 * @return le tableau de jeu (toutes les proposition et les réponses reçues)
	 */
	public String[][] getTableauJeu() {
		return tableauJeu;
	}

	/**
	 * @param tab
	 *            le tableau de jeu (toutes les proposition et les réponses reçues)
	 */
	public void setTableauJeu(String[][] tab) {
		this.tableauJeu = tab;
	}

}
