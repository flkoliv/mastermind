package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer; 
import joueurs.Joueur;
import listener.EntreeListener;
import listener.OkPlateauListener;



public class PlateauPlus extends JPanel implements Plateau {

	private static final long serialVersionUID = 1213633100918578601L;
	private JTextField jtf;
	private JButton okButton;
	private JPanel saisie;
	private JLabel intitule;
	//private int taille;
	private int emptyRow = 0;
	private JTable tab;
	private Joueur joueur;
	private String [][] tableauJeu;
	private String title[] = { "Proposition", "Résultat" };

	public PlateauPlus(int taille, int longueurCode, Joueur j) {
		//this.taille = taille;
		this.joueur = j;
		saisie = new JPanel();
		saisie.setLayout(new BoxLayout(saisie, BoxLayout.LINE_AXIS));
		saisie.setMaximumSize(new Dimension(275, 25));
		jtf = new JTextField();
		jtf.setDocument(new JTextFieldLimiter(0, longueurCode));
		jtf.addKeyListener(new EntreeListener(this));
		okButton = new JButton("OK");
		okButton.setMaximumSize(new Dimension(40, 20));
		okButton.addActionListener(new OkPlateauListener(jtf,this));

		if (joueur.isHuman()) {
			intitule = new JLabel("Votre proposition : ");

		} else {
			intitule = new JLabel("Proposition Ordinateur : ");
			jtf.setEditable(false);
			okButton.setVisible(false);
		}
		saisie.add(intitule);
		saisie.add(Box.createRigidArea(new Dimension(10, 0)));
		saisie.add(jtf);
		saisie.add(Box.createRigidArea(new Dimension(10, 0)));
		saisie.add(okButton);
		
		//Object[][] tableau = new Object[this.taille][2];
		tableauJeu = joueur.getTableauJeu();
		tab = new JTable(tableauJeu, title);
		tab.setFont(new Font("Courier New", Font.BOLD, 17));
		tab.setEnabled(false);
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer(); 
		custom.setHorizontalAlignment(JLabel.CENTER); 
		for (int i=0 ; i<tab.getColumnCount() ; i++) tab.getColumnModel().getColumn(i).setCellRenderer(custom); 
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		this.add(saisie, BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		JScrollPane jsp = new JScrollPane(tab);
		jsp.setMaximumSize(new Dimension(275, 550));
		this.add(jsp, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(275, 700));
		this.setVisible(true);
		
	}

	

	
	/**
	 * @return retourne la proposition de code inscrite dans le champs de texte
	 */
	/*public String getProposition() {
		return jtf.getText();
	}*/

	/**
	 * Efface le champs de texte du panel
	 */
	public void cleanProposition() {
		jtf.setText("");
	}

	/**
	 * @param string
	 *            Rempli le champs de texte du panel avec une proposition
	 */
	public void setProposition(String string) {
		jtf.setText(string);
	}

	
	@Override
	public void setMsgDev(String msg) {
		intitule.setText("Code:" + msg);
	}

	@Override
	public void setValues(String[][] results) {
		tableauJeu=results;
		actualiserAffichage();
	}




	@Override
	public void validerSaisie() {
		tableauJeu[emptyRow][0]=jtf.getText();
		this.emptyRow++;
		joueur.setTableauJeu(tableauJeu);
		actualiserAffichage();
		cleanProposition();
		joueur.faireProposition();
	}




	@Override
	public void actualiserAffichage() {
		for (int i = 0;i<tableauJeu.length;i++) {
			tab.setValueAt(tableauJeu[i][0], i, 0);
			tab.setValueAt(tableauJeu[i][1], i, 1);
		}
		
		
		
	}

}
