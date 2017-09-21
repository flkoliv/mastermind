package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import ihm.NewCodeBox;
import ihm.PlateauMaster;

public class ChoixCouleurListener implements MouseListener {

	PlateauMaster plateau;
	NewCodeBox newCodeBox;
	
	public ChoixCouleurListener(PlateauMaster p) {
		this.plateau = p;
	}

	public ChoixCouleurListener(NewCodeBox newCodeBox) {
		this.newCodeBox = newCodeBox;
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
		if (plateau!=null) {
			plateau.ajouter(((JLabel)arg0.getSource()).getName());
		}
		if (newCodeBox!=null) {
			newCodeBox.addColor(((JLabel)arg0.getSource()).getName());
		}
	}

}
