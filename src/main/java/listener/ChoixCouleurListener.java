package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import ihm.PlateauMaster;

public class ChoixCouleurListener implements MouseListener {

	PlateauMaster plateau;
	
	public ChoixCouleurListener(PlateauMaster p) {
		this.plateau = p;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(((JLabel)arg0.getSource()).getName());
		plateau.ajouter(((JLabel)arg0.getSource()).getName());
	}

}
