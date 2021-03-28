package hr.hsnopek.softwaresaunacodechallenge.common.model;

public class Results {

	private String letters;
	private String pathAsCharacters;
	
	public Results(String letters, String pathAsCharacters) {
		this.letters = letters;
		this.pathAsCharacters = pathAsCharacters;
	}

	public String getLetters() {
		return letters;
	}

	public String getPathAsCharacters() {
		return pathAsCharacters;
	}
	
}
