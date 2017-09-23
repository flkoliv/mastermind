package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ihm.NewCodeBox;

public class OkNewCodeBoxListener implements ActionListener {

	NewCodeBox n;
	
	
	public OkNewCodeBoxListener(NewCodeBox n) {
		this.n = n;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		n.envoyerCode();
		
	}

}
