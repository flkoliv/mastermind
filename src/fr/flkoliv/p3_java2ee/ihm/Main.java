package ihm;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import joueurs.Humain;
import joueurs.Joueur;
import joueurs.Ordinateur;
import listener.MenuListener;


public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7364375700141591165L;
	private static final Logger logger = LogManager.getLogger();
	private Joueur joueur1 = new Humain();
	private Joueur joueur2 = new Ordinateur();
	private static Main INSTANCE;
	private static Options options = Options.getInstance();

	private Main() {
		logger.info("Lancement programme");
		joueur1.setAdversaire(joueur2);
		this.setTitle("Mettez votre logique à l'épreuve !");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/mastermind-icon.jpg");
		this.setIconImage(img.getImage());
		ImageIcon icone = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/mastermind.png");
		JLabel image = new JLabel(icone);
		this.add(image);
		this.setMinimumSize(new Dimension(620, 700));
		initialiseMenu();
		JPanel panneau = new JPanel();
		panneau.add(image);
		this.setContentPane(panneau);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		INSTANCE = new Main();
		for (int i = 0 ; i<args.length;i++) {
			if (args[i].equals("dev")) {
				options.setDev(true);
				options.sauvegardeConfig();
			}
		}
	}

	private void initialiseMenu() {
		logger.trace("initialisation interface graphique principale");
		JMenuBar menuBar = new JMenuBar();
		JMenu jeuxMenu = new JMenu("Jeux");
		JMenu optionsMenu = new JMenu("Options");
		JMenu aideMenu = new JMenu("Aide");
		JMenu plusOuMoins = new JMenu("Plus ou Moins");
		JMenu masterMind = new JMenu("Mastermind");
		JMenuItem challengerPlus = new JMenuItem("Challenger");
		challengerPlus.setName("challengerPlus");
		challengerPlus.addActionListener(new MenuListener(this));
		JMenuItem defenseurPlus = new JMenuItem("Defenseur");
		defenseurPlus.setName("defenseurPlus");
		defenseurPlus.addActionListener(new MenuListener(this));
		JMenuItem duelPlus = new JMenuItem("Duel");
		duelPlus.setName("duelPlus");
		duelPlus.addActionListener(new MenuListener(this));
		JMenuItem challengerMaster = new JMenuItem("Challenger");
		challengerMaster.setName("challengerMaster");
		challengerMaster.addActionListener(new MenuListener(this));
		JMenuItem defenseurMaster = new JMenuItem("Defenseur");
		defenseurMaster.setName("defenseurMaster");
		defenseurMaster.addActionListener(new MenuListener(this));
		JMenuItem duelMaster = new JMenuItem("Duel");
		duelMaster.setName("duelMaster");
		duelMaster.addActionListener(new MenuListener(this));
		JMenuItem aide = new JMenuItem("?");
		aide.setName("aide");
		aide.addActionListener(new MenuListener(this));
		JMenuItem options = new JMenuItem("Options");
		options.setName("options");
		options.addActionListener(new MenuListener(this));
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.setName("quitter");
		quitter.addActionListener(new MenuListener(this));
		plusOuMoins.add(challengerPlus);
		plusOuMoins.add(defenseurPlus);
		plusOuMoins.add(duelPlus);
		masterMind.add(challengerMaster);
		masterMind.add(defenseurMaster);
		masterMind.add(duelMaster);
		optionsMenu.add(options);
		jeuxMenu.add(plusOuMoins);
		jeuxMenu.add(masterMind);
		jeuxMenu.addSeparator();
		jeuxMenu.add(quitter);
		aideMenu.add(aide);
		menuBar.add(jeuxMenu);
		menuBar.add(optionsMenu);
		menuBar.add(aideMenu);
		this.setJMenuBar(menuBar);
	}

	public Joueur getJoueur1() {
		return joueur1;
	}

	public Joueur getJoueur2() {
		return joueur2;
	}

	public static Main getInstance() {
		return INSTANCE;
	}

	public static void setBackground() {
		ImageIcon icone = new ImageIcon("src/fr/flkoliv/p3_java2ee/ressources/mastermind.png");
		JLabel image = new JLabel(icone);
		INSTANCE.getContentPane().removeAll();
		INSTANCE.getContentPane().add(image);

		INSTANCE.repaint();
		INSTANCE.validate();
	}
}
