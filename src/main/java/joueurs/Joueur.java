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
	
	
	public Joueur() {

	}

	public void commencer(TypeJeu t,ModeJeu m) {
		typeJeu = adversaire.typeJeu = t;
		modeJeu = adversaire.modeJeu = m;
		gagnant = adversaire.gagnant = false;
		resultat = adversaire.resultat = "";
		proposition = adversaire.proposition = "";
		if (typeJeu == TypeJeu.MASTERMIND) {
			tableauJeu=new String[Options.getInstance().getNbrEssaisMaster()][Options.getInstance().getlongueurCodeMaster()];
			toursRestants = adversaire.toursRestants = Options.getInstance().getNbrEssaisMaster();
		}else if (typeJeu == TypeJeu.PLUSOUMOINS) {
			tableauJeu=new String[Options.getInstance().getNbrEssaisPlus()][Options.getInstance().getlongueurCodePlus()];
			toursRestants = adversaire.toursRestants =Options.getInstance().getNbrEssaisPlus();	
		}
		initGraphique();
		if (m == ModeJeu.DUEL) {
			creerNouveauCode();
		}
		adversaire.creerNouveauCode();
		if (Options.getInstance().getDev()) {
			plateau.setMsgDev(adversaire.codeATrouver);
			if (m == ModeJeu.DUEL) {
				adversaire.plateau.setMsgDev(codeATrouver);
			}
		}
		chercherNouveauCode();
	}

	
	protected abstract void chercherNouveauCode();

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau p) {
		plateau = p;
	}

	public abstract void creerNouveauCode();

	public void proposerCode() {
		proposition = plateau.getProposition();
		plateau.cleanProposition();
		setChanged();
		notifyObservers(proposition);
	}

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
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i)==codeATrouver.charAt(i)) {
				result=result+"=";
			}else {
				for (int j = 0; j < code.length(); j++) {
					if (code.charAt(i)==codeATrouver.charAt(j)) {
						result=result+"-";
					}
				}
			}
		}
		return result;
	}

	public void update(Observable arg0, Object arg1) {
		String prop = (String) arg1;
		if (codeATrouver.equals(prop)) {
			adversaire.aGagne();
		}
		if (typeJeu==TypeJeu.PLUSOUMOINS) {
			adversaire.setResult(construireReponsePlus(prop));
		}else if (typeJeu==TypeJeu.MASTERMIND) {
			adversaire.setResult(construireReponseMaster(prop));
		}

	}

	public void setResult(String reponse) {
		toursRestants--;
		resultat = reponse;
		plateau.setResult(proposition, resultat);
		if (modeJeu == ModeJeu.DUEL) {
			if (toursRestants == adversaire.toursRestants && (gagnant || adversaire.gagnant || toursRestants == 0)) {
				finPartie();
			} else {
				adversaire.chercherNouveauCode();
			}

		} else

		{
			if (gagnant || toursRestants == 0) {
				finPartie();
			} else {
				chercherNouveauCode();
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

	public Joueur getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(Joueur j) {
		if (j.getAdversaire() != this) {
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
				adversaire.setPlateau(new PlateauPlus(Options.getInstance().getNbrEssaisPlus(),
						Options.getInstance().getlongueurCodePlus(), adversaire));
				Main.getInstance().getContentPane().add((Component) adversaire.getPlateau());
			}

		} else if (typeJeu == TypeJeu.MASTERMIND) {
			plateau = new PlateauMaster(Options.getInstance().getNbrEssaisMaster(),
					Options.getInstance().getlongueurCodeMaster(), this);
			Main.getInstance().getContentPane().add((Component) plateau);
			if (modeJeu == ModeJeu.DUEL) {
				adversaire.setPlateau(new PlateauMaster(Options.getInstance().getNbrEssaisMaster(),
						Options.getInstance().getlongueurCodeMaster(), adversaire));
				Main.getInstance().getContentPane().add((Component) adversaire.getPlateau());
			}
		}
		Main.getInstance().getContentPane().validate();
		Main.getInstance().repaint();
	}

	public void setProposition(String prop) {

		proposition = prop;
		setChanged();
		notifyObservers(this.proposition);

	}

	public void aGagne() {
		gagnant = true;
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
				adversaire.commencer(typeJeu,modeJeu);// recommencer la partie
			} else {
				commencer(typeJeu,modeJeu);
			}
		} else {
			Main.setBackground();
		}
	}
	
	public String[][] getTableauJeu(){
		return tableauJeu;
		
	}
	
	public void setTableaJeu(String[][] tab) {
		this.tableauJeu=tab;
	}
	
}
