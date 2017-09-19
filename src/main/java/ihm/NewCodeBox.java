package ihm;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import joueurs.Humain;
import listener.ChoixCouleurListener;
import listener.OkNewCodeBoxListener;

public class NewCodeBox extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4434370607469563328L;
	JPanel boutonsCouleur;
	JPanel choix;
	JPanel panel;
	String code = "";
	Humain humain;

	public NewCodeBox(Humain humain, String title, boolean modal) {
		super(Main.getInstance(), title, true);
		this.humain=humain;
		this.setSize(550, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		panel = new JPanel();
		this.initComponent();
		this.setContentPane(panel);
		this.setVisible(true);

	}

	public void initComponent() {

		boutonsCouleur = new JPanel();
		for (int i = 0; i < Options.getInstance().getNbrCouleursMaster(); i++) {
			ImageIcon iconeA = new ImageIcon("src/main/ressources/" + i + ".png");
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + i);
			boutonsCouleur.add(imageA);
		}
		choix = new JPanel();
		choix.setBorder(BorderFactory.createLineBorder(Color.black));
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new OkNewCodeBoxListener(this));

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(boutonsCouleur);
		panel.add(choix);
		panel.add(okButton);
	}

	public void addColor(String c) {
		if (code.length() < Options.getInstance().getlongueurCodeMaster()) {
			code = code + c;
			ImageIcon iconeA = new ImageIcon("src/main/ressources/" + c + ".png");
			JLabel imageA = new JLabel(iconeA);
			imageA.addMouseListener(new ChoixCouleurListener(this));
			imageA.setName("" + c);
			choix.add(imageA);
			this.getContentPane().validate();
			
		}
	}
	
	public void envoyerCode() {
		if (code.length() == Options.getInstance().getlongueurCodeMaster()) {
			humain.setCodeAtrouver(code);
			this.dispose();
		}
	}

}
