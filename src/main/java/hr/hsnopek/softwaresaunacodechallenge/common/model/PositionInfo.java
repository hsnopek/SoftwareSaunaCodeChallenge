package hr.hsnopek.softwaresaunacodechallenge.common.model;

import hr.hsnopek.softwaresaunacodechallenge.common.enums.Direction;

public class PositionInfo {
	
	private Grid grid;
	
	private Coordinate currentCoordinate;
	private Coordinate up;
	private Coordinate below;
	private Coordinate left;
	private Coordinate right;

	public PositionInfo(Grid grid, Coordinate currentCoordinate) {
		this.grid = grid;
		
		this.currentCoordinate = currentCoordinate;		
		setRelativeCoordinates(currentCoordinate);

	}
	
	public Grid getGrid() {
		return grid;
	}
	public Coordinate getCurrentCoordinate() {
		return currentCoordinate;
	}
	
	public void setCurrentCoordinate(Coordinate currentCoordinate) {
		this.currentCoordinate = currentCoordinate;
		setRelativeCoordinates(currentCoordinate);

	}
	
	

	public Coordinate getCoordinateFromUp() {
		return up;
	}

	public Coordinate getCoordinateFromBelow() {
		return below;
	}

	public Coordinate getCoordinateToTheLeft() {
		return left;
	}

	public Coordinate getCoordinateToTheRight() {
		return right;
	}

	
	public Coordinate getRelativeCoordinate(Direction direction) {
		Coordinate relativeCoordinate = null;
		
		switch (direction) {
		case LEFT:
			relativeCoordinate = this.left;
			break;
		case UP:
			relativeCoordinate = this.up;
			break;
		case RIGHT:
			relativeCoordinate = this.right;
			break;
		case DOWN:
			relativeCoordinate = this.below;
			break;
		default:
			break;
		}
		
		return relativeCoordinate;
	}

	public Character getUpValue() {
		return grid.isCoordinateInsideGrid(up) ? grid.getCoordinateValue(up) : ' ';
	}

	public Character getBelowValue() {
		return grid.isCoordinateInsideGrid(below) ? grid.getCoordinateValue(below) : ' ';
	}

	public Character getLeftValue() {
		return grid.isCoordinateInsideGrid(left) ? grid.getCoordinateValue(left) : ' ';
	}

	public Character getRightValue() {
		return grid.isCoordinateInsideGrid(right) ? grid.getCoordinateValue(right) : ' ';
	}

	public Character getCurrentValue() {
		return grid.isCoordinateInsideGrid(currentCoordinate) ? grid.getCoordinateValue(currentCoordinate) : ' ';
	}

	private void setRelativeCoordinates(Coordinate currentCoordinate) {
		this.left = new Coordinate(currentCoordinate.getX(), currentCoordinate.getY()-1);
		this.up = new Coordinate(currentCoordinate.getX()-1, currentCoordinate.getY());
		this.right = new Coordinate(currentCoordinate.getX(), currentCoordinate.getY()+1);
		this.below = new Coordinate(currentCoordinate.getX()+1, currentCoordinate.getY());
	}

}
