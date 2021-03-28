package hr.hsnopek.softwaresaunacodechallenge.common.enums;

public enum SpecialCharacters {

	LETTER_A('A'), LETTER_B('B'), LETTER_C('C'), LETTER_D('D'), START('@'), END('x'), CORNER('+');
	
    public final char value;

    private SpecialCharacters(char value) {
        this.value = value;
    }


    public static SpecialCharacters valueOf(char value) {
        for (SpecialCharacters sc : values()) {
            if (sc.value == value) {
                return sc;
            }
        }
        return null;
    }
    
	public static boolean isLetter(Character currentValue) {
		SpecialCharacters character = SpecialCharacters.valueOf(currentValue);
		
		if(character == null) 
			return false;
		
		switch (character) {
		case LETTER_A:
			return true;
		case LETTER_B:
			return true;
		case LETTER_C:
			return true;	
		case LETTER_D:
			return true;
		default:
			break;
		}
		return false;
	}
}
