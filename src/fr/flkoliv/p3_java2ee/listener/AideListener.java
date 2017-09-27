package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class AideListener implements ActionListener{

	JDialog j;
	
	public AideListener(JDialog j) {
		this.j=j;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		j.dispose();
		
	}

}
