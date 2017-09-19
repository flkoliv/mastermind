package ihm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Options {
	
	private Integer nbrEssaisPlus, nbrEssaisMaster, longueurCodePlus, longueurCodeMaster, nbrCouleursMaster;
	private Boolean dev;
	private static final Logger logger = LogManager.getLogger();
	private static Options INSTANCE = new Options();

	/**
	 * 
	 */
	private Options() {
		lectureConfig();
	}

	public Options(Integer nbrEssaisPlus, Integer nbrEssaisMaster, Integer longueurCodePlus, Integer longueurCodeMaster,
			Integer nbrCouleursMaster, Boolean dev) {
		this.nbrEssaisPlus = nbrEssaisPlus;
		this.nbrEssaisMaster = nbrEssaisMaster;
		this.longueurCodePlus = longueurCodePlus;
		this.longueurCodeMaster = longueurCodeMaster;
		this.nbrCouleursMaster = nbrCouleursMaster;
		this.dev = dev;
	}

	public void setDev(boolean b) {
		this.dev = b;
	}

	public void setNbrEssaisPlus(int i) {
		this.nbrEssaisPlus = i;
	}

	public void setNbrEssaisMaster(int i) {
		this.nbrEssaisMaster = i;
	}

	public void setlongueurCodePlus(int i) {
		this.longueurCodePlus = i;
	}

	public void setlongueurCodeMaster(int i) {
		this.longueurCodeMaster = i;
	}

	public void setNbrCouleursMaster(int i) {
		this.nbrCouleursMaster = i;
	}

	public boolean getDev() {
		return this.dev;
	}

	public int getNbrEssaisPlus() {
		return this.nbrEssaisPlus;
	}

	public int getNbrEssaisMaster() {
		return this.nbrEssaisMaster;
	}

	public int getlongueurCodePlus() {
		return this.longueurCodePlus;
	}

	public int getlongueurCodeMaster() {
		return this.longueurCodeMaster;
	}

	public int getNbrCouleursMaster() {
		return this.nbrCouleursMaster;
	}

	public void sauvegardeConfig() {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("src/main/ressources/config.properties");
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

	public void lectureConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("src/main/ressources/config.properties");
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
	
	public static Options getInstance() {
		return INSTANCE;
	}
	
	public void setOptions(Options o) {
		this.nbrEssaisPlus = o.getNbrEssaisPlus();
		this.nbrEssaisMaster = o.getNbrEssaisMaster(); 
		this.longueurCodePlus = o.getlongueurCodePlus();
		this.longueurCodeMaster=o.getlongueurCodeMaster();
		this.nbrCouleursMaster=o.getNbrCouleursMaster();
		this.dev=o.getDev();
	}

	

}
