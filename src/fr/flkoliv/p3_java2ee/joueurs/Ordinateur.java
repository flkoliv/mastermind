package joueurs;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ihm.Options;

/**
 * Joueur de type ordinateur
 * 
 * @author flkoliv
 *
 */
public class Ordinateur extends Joueur {

	private static final Logger logger = LogManager.getLogger();
	private int rechercheCode[][];
	List<String> l = new LinkedList<String>();

	public Ordinateur() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see joueurs.Joueur#creerNouveauCode()
	 */
	@Override
	public void creerNouveauCode() {
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			int nbrChrCode = Options.getInstance().getlongueurCodePlus();

			double c = Math.random();
			String code = "";
			for (int i = 0; i < nbrChrCode; i++) {
				c = c * 10;
			}
			code = String.valueOf((int) c);
			while (code.length() < nbrChrCode) {
				code = "0" + code;
			}
			logger.debug("Nouveau code Plus ou Moins généré par l'ordinateur : " + code);
			codeATrouver = code;
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			String code = "";
			int nbrChrCode = Options.getInstance().getlongueurCodeMaster();
			int nbrCouleurs = Options.getInstance().getNbrCouleursMaster();
			do {
				Integer c = (int) (Math.random() * 10);
				if (c < nbrCouleurs) {
					code = code + c;
				}
			} while (code.length() < nbrChrCode);
			codeATrouver = code;
			logger.debug("Nouveau code Mastermind généré par l'ordinateur : " + code);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see joueurs.Joueur#chercherCode()
	 */
	@Override
	public void chercherCode() {
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			chercherCodePlus();
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			chercherCodeMaster();
		}
	}

	/**
	 * recherche le code du jeu plus ou moins en fonction des propositions et des
	 * réponses précédentes puis l'affiche dans le plateau de jeu
	 * 
	 */
	private void chercherCodePlus() {
		if (emptyRowTableauJeu != 0) {
			// si il y a déjà un résultat, mettre à jour le tableau de recherche avec de
			// nouvelles bornes
			String resultat = tableauJeu[emptyRowTableauJeu - 1][1];
			for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
				String c = "" + tableauJeu[emptyRowTableauJeu - 1][0].charAt(i);
				if (resultat.charAt(i) == '+') {
					rechercheCode[i][0] = Integer.parseInt(c);
				} else if (resultat.charAt(i) == '-') {
					rechercheCode[i][1] = Integer.parseInt(c);
				} else if (resultat.charAt(i) == '=') {
					rechercheCode[i][0] = Integer.parseInt(c);
					rechercheCode[i][1] = Integer.parseInt(c);
				}
			}
		} else {
			// initialise le tableau permettant la recherche de code au début de la partie
			rechercheCode = new int[Options.getInstance().getlongueurCodePlus()][2];
			for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
				rechercheCode[i][0] = 0;
				rechercheCode[i][1] = 10;
			}
		}
		Thread t = new Thread() { // thread pour l'affichage progressif dans le JTextField et ne pas bloquer l'IHM
			@Override
			public void run() {
				try {
					String s = "";
					for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
						s = s + ((rechercheCode[i][0] + rechercheCode[i][1]) / 2);
						plateau.setProposition(s);
						Thread.sleep(200); // temporisation pour la progressivité
					}
					tableauJeu[emptyRowTableauJeu][0] = s;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				plateau.validerSaisie();
			}
		};
		t.start();
	}

	/**
	 * Recherche la combinaison du jeu mastermind en fonction des réponses reçues
	 */
	private void chercherCodeMaster() {
		String prop = "";
		if (emptyRowTableauJeu == 0)// si le tableau est vide, vider la liste
			l.clear();
		if (l.isEmpty()) {
			// si la liste est vide, la remplir avec l'ensemble des combinaisons possibles
			// (donc 1er tour)
			double d = Math.pow(Options.getInstance().getNbrCouleursMaster(),
					Options.getInstance().getlongueurCodeMaster());
			int nbrMax = (int) d;

			for (int i = 0; i < nbrMax; i++) {
				String s = Integer.toString(i, Options.getInstance().getNbrCouleursMaster());
				while (s.length() < Options.getInstance().getlongueurCodeMaster()) {
					// pour rajouter les 0 devant les integers
					s = "0" + s;
				}
				l.add(s);
			}
			String code = ""; // fabrique une combinaison aléatoire à proposer pour le 1er tour
			int nbrChrCode = Options.getInstance().getlongueurCodeMaster();
			int nbrCouleurs = Options.getInstance().getNbrCouleursMaster();
			do {
				Integer c = (int) (Math.random() * 10);
				if (c < nbrCouleurs) {
					code = code + c;
				}
			} while (code.length() < nbrChrCode);
			prop = code;
		} else {
			String resultat = tableauJeu[emptyRowTableauJeu - 1][1];
			prop = tableauJeu[emptyRowTableauJeu - 1][0];
			for (int i = 0; i < l.size(); i++) {
				// enlever toutes les combinaisons de la liste qui ne réagissent pas comme la
				// proposition
				if (!construireReponseMaster(prop, l.get(i)).equals(resultat)) {
					l.remove(i);
					i--;
				}
			}
			// proposer une combinaison aléatoire contenue dans la liste (et donc encore
			// possible)
			Random r = new Random();
			int valeur = 0;
			if (l.size() > 0) {
				valeur = r.nextInt(l.size());
			}
			prop = l.get(valeur);
		}
		final String str = prop;
		Thread t = new Thread() {
			// thread pour ne pas bloquer l'IHM pendant l'affichage progressif de la
			// proposition
			@Override
			public void run() {
				try {
					String s = "";
					for (int i = 0; i < Options.getInstance().getlongueurCodeMaster(); i++) {
						s = s + str.charAt(i);
						plateau.setProposition(s);
						Thread.sleep(200);
					}
					tableauJeu[emptyRowTableauJeu][0] = s;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				plateau.validerSaisie();
			}
		};
		t.start();
	}

	/**
	 * @param code
	 *            proposition de combinaison
	 * @param codAtrouver
	 *            combinaison à trouver
	 * @return résultat mastermind (= bonne couleur à la bonne place / - bonne
	 *         couleur mais mauvaise place)
	 */
	public String construireReponseMaster(String code, String codAtrouver) {
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
			if (code.charAt(i) == codAtrouver.charAt(i)) {
				result = result + "=";
				tab[i] = false;
				tab2[i] = false;
			}
		}
		// 2nd tour pour vérifier les couleurs à la mauvaise place (-)
		for (int i = 0; i < code.length(); i++) {
			if (tab[i]) {
				for (int j = 0; j < code.length(); j++) {

					if (code.charAt(i) == codAtrouver.charAt(j) && tab2[j]) {
						result = result + "-";
						tab2[j] = false;
						j = code.length();
					}
				}
			}
		}
		return result;
	}

}
