package joueurs;

public enum ModeJeu {
	CHALLENGER("Challenger"), DEFENSEUR("Defenseur"), DUEL("Duel");

	private String mode = "";

	ModeJeu(String mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return mode;
	}

}
