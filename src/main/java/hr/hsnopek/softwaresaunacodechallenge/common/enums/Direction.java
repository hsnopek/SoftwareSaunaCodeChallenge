package hr.hsnopek.softwaresaunacodechallenge.common.enums;

public enum Direction {

	UP("UP"), 
	DOWN("DOWN"), 
	LEFT("LEFT"), 
	RIGHT("RIGHT");
	
    public final String value;

    private Direction(String value) {
        this.value = value;
    }


    public static Direction valueOfDirection(String value) {
        for (Direction d : values()) {
            if (d.value.equals(value)) {
                return d;
            }
        }
        return null;
    }

}
