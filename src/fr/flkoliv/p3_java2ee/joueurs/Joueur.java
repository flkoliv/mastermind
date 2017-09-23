package joueurs;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import ihm.Main;
import ihm.Options;
import ihm.Plateau;
import ihm.PlateauMaster;
import ihm.PlateauPlus;

/**
 * 
 */

/**
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

	public Joueur() {

	}

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
		adversaire.creerNouveauCode();
		if (Options.getInstance().getDev()) {// afficher les code si le mode développeur est selectionné
			plateau.setMsgDev(adversaire.codeATrouver);
			if (m == ModeJeu.DUEL) {
				adversaire.plateau.setMsgDev(codeATrouver);
			}
		}
		chercherCode();
	}

	protected abstract void chercherCode();

	public abstract void creerNouveauCode();

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

	public String construireReponseMaster(String code) {
		String result = "";
		boolean[][] tab = new boolean[2][code.length()];
		for (int i = 0; i < code.length(); i++) {
			tab[0][i] = true;
			tab[1][i] = true;
			
		}
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == codeATrouver.charAt(i)) {
				result = result + "=";
				tab[0][i] = false;
				tab[1][i] = false;
			}
		}
		for (int i = 0; i < code.length(); i++) {
			if (tab[0][i]) {
				for (int j = 0; j < code.length(); j++) {
					if (tab[1][j]) {
						if (code.charAt(i) == codeATrouver.charAt(j)) {
							result = result + "-";
							tab[1][j] = false;

						}
					}
				}
			}
			
		}
		return result;
	}

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

	public void setResult(String reponse) {
		toursRestants--;
		System.out.println(reponse);
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

	public boolean isHuman() {
		if (this.getClass() == Humain.class) {
			return true;
		} else {
			return false;
		}
	}

	public void setAdversaire(Joueur j) {
		if (j.adversaire != this) {
			j.adversaire = this;
			j.addObserver(this);
		}
		this.adversaire = j;
		this.addObserver(j);
	}

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

	public void faireProposition() {
		emptyRowTableauJeu++;
		setChanged();
		notifyObservers(tableauJeu[emptyRowTableauJeu - 1][0]);
	}

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
		message = message + "\n Voulez-vous recommencer la partie ?";
		int o = JOptionPane.showConfirmDialog(null, message, "Attention", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (o == 0) {
			if (modeJeu == ModeJeu.DUEL) {
				adversaire.commencer(typeJeu, modeJeu);// recommencer la partie
			} else {
				commencer(typeJeu, modeJeu);
			}
		} else {
			Main.setBackground();
		}
	}

	public String[][] getTableauJeu() {
		return tableauJeu;
	}

	public void setTableauJeu(String[][] tab) {
		this.tableauJeu = tab;
	}

	

}
