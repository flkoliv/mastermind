package joueurs;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ihm.Options;

public class Ordinateur extends Joueur {

	private static final Logger logger = LogManager.getLogger();
	private int rechercheCode[][];
	List<String> l = new LinkedList<String>();

	public Ordinateur() {
		super();
	}

	/**
	 * Créer un code aléatoire à trouver par l'adversaire
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

	/**
	 * créer un nouveau code en fonction des réponses précédentes
	 */
	@Override
	public void chercherCode() {
		if (typeJeu == TypeJeu.PLUSOUMOINS) {
			chercherCodePlus();
		} else if (typeJeu == TypeJeu.MASTERMIND) {
			chercherCodeMaster();
		}
	}

	private void chercherCodePlus() {
		if (emptyRowTableauJeu != 0) { // si il y a déjà un résultat, mettre à jour le tableau de recherche
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
		} else {// initialise le tableau permettant la recherche de code au début de la partie
			rechercheCode = new int[Options.getInstance().getlongueurCodePlus()][2];
			for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
				rechercheCode[i][0] = 0;
				rechercheCode[i][1] = 10;
			}
		}
		Thread t = new Thread() { // thread pour l'affichage dans le JTextField et ne pas bloquer l'affichage
			public void run() {
				try {
					String s = "";
					for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
						s = s + ((rechercheCode[i][0] + rechercheCode[i][1]) / 2);
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

	private void chercherCodeMaster() {

		String prop  ="";
		if (emptyRowTableauJeu == 0) l.clear();
		if (l.isEmpty()) {
			double d = Math.pow(Options.getInstance().getNbrCouleursMaster(),
					Options.getInstance().getlongueurCodeMaster());
			int nbrMax = (int) d;

			for (int i = 0; i < nbrMax; i++) {
				String s = Integer.toString(i, Options.getInstance().getNbrCouleursMaster());
				while (s.length() < Options.getInstance().getlongueurCodeMaster()) {
					s = "0" + s;
				}
				l.add(s);
			}
			String code = "";
			int nbrChrCode = Options.getInstance().getlongueurCodeMaster();
			int nbrCouleurs = Options.getInstance().getNbrCouleursMaster();
			do {
				Integer c = (int) (Math.random() * 10);
				if (c < nbrCouleurs) {
					code = code + c;
				}
			} while (code.length() < nbrChrCode);
			prop = code;
			System.out.println("size : "+l.size());
		}else {
			String resultat = tableauJeu[emptyRowTableauJeu - 1][1];
			prop = tableauJeu[emptyRowTableauJeu - 1][0];
			for(int i = 0; i < l.size(); i++) {
				if (!construireReponseMaster(prop,l.get(i)).equals(resultat)) {
					l.remove(i);
					i--;
				}
			}
			Random r = new Random();
			int valeur=0;
			if (l.size()>0) {
				valeur =  r.nextInt(l.size());
			}
			System.out.println("size : "+l.size()+" / valeur : "+valeur);
			prop = l.get(valeur);

			
			
		}
		final String str = prop;
		Thread t = new Thread() { // thread pour l'affichage dans le JTextField et ne pas bloquer l'affichage
			
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

	public String construireReponseMaster(String code, String codAtrouver) {
		String result = "";
		boolean[] tab = new boolean[code.length()];
		boolean[] tab2 = new boolean[code.length()];
		for (int i = 0; i < code.length(); i++) {
			tab[i] = true;
			tab2[i] = true;
		}
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == codAtrouver.charAt(i)) {
				result = result + "=";
				tab[i] = false;
			}
		}
		for (int i = 0; i < code.length(); i++) {
			if (tab[i]) {
				for (int j = 0; j < code.length(); j++) {

					if (code.charAt(i) == codAtrouver.charAt(j)&&tab2[j]) {
						result = result + "-";
						tab2[j] = false;
						j=code.length();

					}

				}
			}

		}
		return result;
	}
	
	
}
