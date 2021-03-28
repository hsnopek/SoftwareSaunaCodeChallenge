package hr.hsnopek.softwaresaunacodechallenge.common.model;

public class Coordinate {

	private int X;
	private int Y;

	public Coordinate(int x, int y) {
		X = x;
		Y = y;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}


	public boolean coordinateNotNull(Grid grid) {
		Character coordinateValue = grid.getCoordinateValue(this);
		return 	coordinateValue != null && coordinateValue != ' ' && (int) coordinateValue != 0;

	}
}
