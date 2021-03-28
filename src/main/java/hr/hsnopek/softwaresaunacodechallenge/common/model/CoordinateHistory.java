package hr.hsnopek.softwaresaunacodechallenge.common.model;

import hr.hsnopek.softwaresaunacodechallenge.common.enums.Direction;

public class CoordinateHistory {

	private Coordinate coordinate;
	private Direction direction;
	
	public CoordinateHistory(Coordinate coordinate, Direction direction) {
		super();
		this.coordinate = coordinate;
		this.direction = direction;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public Direction getDirection() {
		return direction;
	}
	
	
}
