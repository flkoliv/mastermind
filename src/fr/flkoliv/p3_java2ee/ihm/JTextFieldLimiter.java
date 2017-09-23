package ihm;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimiter extends PlainDocument {
	/**
	 * Classe pour empêcher des saisies autre q'un integer supérieure aux limites
	 * spécifiées dans un JTextField
	 */
	private static final long serialVersionUID = -8330008807748451055L;
	private int limit;
	private int nbrChr = 0;

	JTextFieldLimiter(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimiter(int limit, int longueur) {
		super();
		this.limit = limit;
		this.nbrChr = longueur;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		try {
			String newChaine = this.getText(0, this.getLength()) + str;
			Integer i = Integer.parseInt(newChaine);
			if ((nbrChr == 0 && i <= limit) || (nbrChr >= newChaine.length())) {
				super.insertString(offset, str, attr);
			}
		} catch (Exception e) {
			// ..On ne fait rien
		}
	}
}
