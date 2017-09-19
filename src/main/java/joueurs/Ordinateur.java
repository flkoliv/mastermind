package joueurs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ihm.Options;

public class Ordinateur extends Joueur {

	private static final Logger logger = LogManager.getLogger();
	private int rechercheCode[][];

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
		}else if (typeJeu == TypeJeu.MASTERMIND) {
			
			String code="";
			int nbrChrCode = Options.getInstance().getlongueurCodeMaster();
			int nbrCouleurs = Options.getInstance().getNbrCouleursMaster();
			do {
				Integer c = (int) (Math.random()*10);
				if (c<nbrCouleurs) {
					code=code+c;
				}
			}while (code.length()<nbrChrCode);
			codeATrouver = code;
			logger.debug("Nouveau code Mastermind généré par l'ordinateur : " + code);
		}
		
		
	}

	/**
	 * créer un nouveau code en fonction des réponses précédentes
	 */
	@Override
	public void chercherNouveauCode() {
		if (!resultat.equals("")) { // si il y a déjà un résultat, mettre à jour le tableau de recherche
			System.out.println("lastResult = " + resultat);
			for (int i = 0; i < Options.getInstance().getlongueurCodePlus(); i++) {
				String c = "" + proposition.charAt(i);
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
						// plateau.validate();
						// plateau.repaint();
						Thread.sleep(200);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setProposition(plateau.getProposition());
			}
		};
		t.start();
	}

}
