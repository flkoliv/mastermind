package ihm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Options du jeu. Permets de les lire et écrire dans un fichier properties
 * (singleton)
 * 
 * @author flkoliv
 *
 */
public class Options {

	private Integer nbrEssaisPlus, nbrEssaisMaster, longueurCodePlus, longueurCodeMaster, nbrCouleursMaster;
	private Boolean dev;
	private static final Logger logger = LogManager.getLogger();
	private static Options INSTANCE = new Options();

	/**
	 * Lit le fichier properties lors de la création de l'objet
	 */
	private Options() {
		lectureConfig();
	}

	/**
	 * @param nbrEssaisPlus
	 *            Nombres d'essais pour le jeu Plus ou Moins
	 * @param nbrEssaisMaster
	 *            Nombres d'essais pour le jeu mastermind
	 * @param longueurCodePlus
	 *            longueur de la combinaison du jeu Plus ou Moins
	 * @param longueurCodeMaster
	 *            longueur de la combinaison du mastermind
	 * @param nbrCouleursMaster
	 *            Nombre de couleurs utilisés par le mastermind
	 * @param dev
	 *            mode développeur (pour afficher le résultat dès le début du jeu)
	 */
	public Options(Integer nbrEssaisPlus, Integer nbrEssaisMaster, Integer longueurCodePlus, Integer longueurCodeMaster,
			Integer nbrCouleursMaster, Boolean dev) {
		this.nbrEssaisPlus = nbrEssaisPlus;
		this.nbrEssaisMaster = nbrEssaisMaster;
		this.longueurCodePlus = longueurCodePlus;
		this.longueurCodeMaster = longueurCodeMaster;
		this.nbrCouleursMaster = nbrCouleursMaster;
		this.dev = dev;
	}

	/**
	 * @param b
	 *            mode développeur (pour afficher le résultat dès le début du jeu)
	 */
	public void setDev(boolean b) {
		this.dev = b;
	}

	/**
	 * @param i
	 *            Nombres d'essais pour le jeu Plus ou Moins
	 */
	public void setNbrEssaisPlus(int i) {
		this.nbrEssaisPlus = i;
	}

	/**
	 * @param i
	 *            Nombres d'essais pour le jeu mastermind
	 */
	public void setNbrEssaisMaster(int i) {
		this.nbrEssaisMaster = i;
	}

	/**
	 * @param i
	 *            longueur de la combinaison du jeu Plus ou Moins
	 */
	public void setlongueurCodePlus(int i) {
		this.longueurCodePlus = i;
	}

	/**
	 * @param i
	 *            longueur de la combinaison du mastermind
	 */
	public void setlongueurCodeMaster(int i) {
		this.longueurCodeMaster = i;
	}

	/**
	 * @param i
	 *            Nombre de couleurs utilisés par le mastermind
	 */
	public void setNbrCouleursMaster(int i) {
		this.nbrCouleursMaster = i;
	}

	/**
	 * @return mode développeur (pour afficher le résultat dès le début du jeu)
	 */
	public boolean getDev() {
		return this.dev;
	}

	/**
	 * @return Nombres d'essais pour le jeu Plus ou Moins
	 */
	public int getNbrEssaisPlus() {
		return this.nbrEssaisPlus;
	}

	/**
	 * @return Nombres d'essais pour le jeu mastermind
	 */
	public int getNbrEssaisMaster() {
		return this.nbrEssaisMaster;
	}

	/**
	 * @return longueur de la combinaison du jeu Plus ou Moins
	 */
	public int getlongueurCodePlus() {
		return this.longueurCodePlus;
	}

	/**
	 * @return longueur de la combinaison du jeu mastermind
	 */
	public int getlongueurCodeMaster() {
		return this.longueurCodeMaster;
	}

	/**
	 * @return Nombre de couleurs utilisés par le mastermind
	 */
	public int getNbrCouleursMaster() {
		return this.nbrCouleursMaster;
	}

	/**
	 * Sauvegarde les options dans le fichier config.properties
	 */
	public void sauvegardeConfig() {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("config.properties");
			prop.setProperty("nbrEssaisPlus", nbrEssaisPlus.toString());
			prop.setProperty("tailleCodePlus", longueurCodePlus.toString());
			prop.setProperty("nbrEssaisMaster", nbrEssaisMaster.toString());
			prop.setProperty("longueurCodeMaster", longueurCodeMaster.toString());
			prop.setProperty("nbrCouleursMaster", nbrCouleursMaster.toString());
			prop.setProperty("dev", dev.toString());
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * lit les options dans le fichier config.properties
	 */
	public void lectureConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			nbrEssaisPlus = Integer.parseInt(prop.getProperty("nbrEssaisPlus"));
			nbrEssaisMaster = Integer.parseInt(prop.getProperty("nbrEssaisMaster"));
			longueurCodePlus = Integer.parseInt(prop.getProperty("tailleCodePlus"));
			longueurCodeMaster = Integer.parseInt(prop.getProperty("longueurCodeMaster"));
			nbrCouleursMaster = Integer.parseInt(prop.getProperty("nbrCouleursMaster"));
			dev = Boolean.parseBoolean(prop.getProperty("dev"));
			logger.debug("lecture du fichier config.properties...");
		} catch (IOException ex) {
			// ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				nbrEssaisPlus = 10;
				nbrEssaisMaster = 10;
				longueurCodePlus = 4;
				longueurCodeMaster = 4;
				nbrCouleursMaster = 6;
				dev = false;
				sauvegardeConfig();
				logger.debug("lecture du fichier config.properties impossible. Utilisation de valeurs par défaut.");
			}
		}

	}

	/**
	 * @return l'instance de l'objet option (singleton)
	 */
	public static Options getInstance() {
		return INSTANCE;
	}

	/**
	 * @param o
	 *            objet Options
	 */
	public void setOptions(Options o) {
		this.nbrEssaisPlus = o.getNbrEssaisPlus();
		this.nbrEssaisMaster = o.getNbrEssaisMaster();
		this.longueurCodePlus = o.getlongueurCodePlus();
		this.longueurCodeMaster = o.getlongueurCodeMaster();
		this.nbrCouleursMaster = o.getNbrCouleursMaster();
		this.dev = o.getDev();
	}

}
