package joueurs;

public enum TypeJeu {
	MASTERMIND ("Mastermind"), PLUSOUMOINS("Plus ou Moins");
	
	private String type = "";

	TypeJeu(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
