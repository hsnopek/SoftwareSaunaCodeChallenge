package hr.hsnopek.softwaresaunacodechallenge.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hr.hsnopek.softwaresaunacodechallenge.common.enums.Direction;
import hr.hsnopek.softwaresaunacodechallenge.common.enums.SpecialCharacters;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.EndOfMapReachedException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleEndsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleStartsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoEndException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoStartException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.TForksException;
import hr.hsnopek.softwaresaunacodechallenge.common.model.PositionInfo;
import hr.hsnopek.softwaresaunacodechallenge.common.model.Coordinate;
import hr.hsnopek.softwaresaunacodechallenge.common.model.CoordinateHistory;
import hr.hsnopek.softwaresaunacodechallenge.common.model.Grid;
import hr.hsnopek.softwaresaunacodechallenge.common.model.Results;

public class MapParser {

	public Results parse(String map) {
		
		List<CoordinateHistory> coordinateHistory = new ArrayList<>();

		try {
			Grid grid = new Grid(map);
			
			Coordinate startPos = grid.locateCharacterPosition(SpecialCharacters.START);
			Coordinate endPos = grid.locateCharacterPosition(SpecialCharacters.END);
			
			PositionInfo positionInfo = new PositionInfo(grid, startPos);
			
			try {
				advance(grid, positionInfo, endPos, coordinateHistory);
			} catch (EndOfMapReachedException e) {
				
			}
			
			coordinateHistory.add(new CoordinateHistory(endPos, getLastDirection(coordinateHistory)));
			
			return new Results(
					getVisitedLetters(grid, coordinateHistory), 
					this.convertPathToCharacters(grid, coordinateHistory));
			
		} catch(NoStartException ns) {
			throw ns;
		} catch(NoEndException ne) {
			throw ne;
		} catch(MultipleStartsException mse) {
			throw mse;
		} catch(MultipleEndsException mee) {
			throw mee;
		} catch(TForksException tfe) {
			throw tfe;
		} catch (EndOfMapReachedException eomre) {
			throw eomre;
		}
	}
	
	private String getVisitedLetters(Grid grid, List<CoordinateHistory> coordinateHistory) {
		return coordinateHistory.stream()
				.map( history -> grid.getCoordinateValue(history.getCoordinate()))
				.filter( x -> SpecialCharacters.isLetter(x))
				.distinct()
				.map(String::valueOf)
				.collect(Collectors.joining(""));
	}
		
	private void advance(Grid grid , PositionInfo positionInfo, Coordinate endPos, List<CoordinateHistory> coordinateHistory) {
		
		Coordinate nextCoordinate = null;
						
		while(!grid.isEndCoordinate(positionInfo.getCurrentCoordinate(), endPos)) {
			
			//checkTFork(grid, positionInfo);
			nextCoordinate = findNextCoordinate(grid, positionInfo, coordinateHistory);
			positionInfo.setCurrentCoordinate(nextCoordinate);
			
			advance(grid, positionInfo, endPos, coordinateHistory);
		} 
		
		throw new EndOfMapReachedException("End of map reached.");

	}

	private Coordinate findNextCoordinate(Grid grid, PositionInfo positionInfo, List<CoordinateHistory> coordinateHistory) {
				
		Direction direction = calculateNextDirection(grid, positionInfo, coordinateHistory);
		Coordinate relativeCoordinate = positionInfo.getRelativeCoordinate(direction);
		
		Direction prevDirection = calculatePreviousDirection(positionInfo, relativeCoordinate);
		coordinateHistory.add(new CoordinateHistory(positionInfo.getCurrentCoordinate(), prevDirection));
		
		return relativeCoordinate;
	}
	

	private Direction calculateNextDirection(Grid grid, PositionInfo positionInfo, List<CoordinateHistory> coordinateHistory) {

		// pass straight through intersection
		boolean isIntersection = checkIntersection(grid, positionInfo);
		if(isIntersection)
			return getLastDirection(coordinateHistory);
		
		// Map all valid directions with (key,value) -> (direction, isDirectionValid)
		Map<Direction, Boolean> viableDirections = mapAllViableDirections(grid, positionInfo, coordinateHistory);
		
		if(viableDirections.entrySet().size() > 1)
			throw new TForksException("T Fork exception!");
		
		return viableDirections.keySet().stream().findFirst().orElse(null);
	}
	
	

	
	private Direction calculatePreviousDirection(PositionInfo positionInfo, Coordinate nextPosition) {
		Coordinate currentPosition = positionInfo.getCurrentCoordinate();
		if((nextPosition.getX() > currentPosition.getX()))
			return Direction.DOWN;
		else if((nextPosition.getX() < currentPosition.getX()))
			return Direction.UP;
		else if((nextPosition.getY() > currentPosition.getY()))
			return Direction.RIGHT;
		else 
			return Direction.LEFT;
	}

	private Direction getLastDirection(List<CoordinateHistory> coordinateHistory) {
		if(!coordinateHistory.isEmpty()) {
			Direction lastDirection = coordinateHistory.get(coordinateHistory.size()-1).getDirection();
			return lastDirection;
		}
		return null;
	}
	
	private Coordinate getLastCoordinate(List<CoordinateHistory> coordinateHistory) {
		if(!coordinateHistory.isEmpty()) {
			Coordinate lastCoordinate = coordinateHistory.get(coordinateHistory.size()-1).getCoordinate();
			return lastCoordinate;
		}
		return null;
	}


	private Map<Direction, Boolean> mapAllViableDirections(Grid grid, PositionInfo positionInfo, List<CoordinateHistory> coordinateHistory) {
		Map<Direction, Boolean> characters = new LinkedHashMap<Direction, Boolean>();
		
		Arrays.asList(Direction.values()).forEach( direction -> {
			boolean coordinateValid = checkRelativePathValid(grid, positionInfo, direction, coordinateHistory);
			characters.put(direction, coordinateValid);
		});

		return characters.entrySet()
				.stream()
				.filter( direction -> direction.getValue() == true)
				.collect(Collectors.toMap( e -> e.getKey(), e -> e.getValue()));
	}

	private boolean checkRelativePathValid(Grid grid, PositionInfo positionInfo, Direction direction,
			List<CoordinateHistory> coordinateHistory) {
		
		Coordinate relativeCoordinate = positionInfo.getRelativeCoordinate(direction);
		Character relativeCoordinateValue = grid.getCoordinateValue(relativeCoordinate);
		
		boolean isPathValid = 
				grid.isCoordinateInsideGrid(relativeCoordinate) && 
				!coordinateAlreadyVisited(coordinateHistory, relativeCoordinate) && 
				relativeCoordinate.coordinateNotNull(grid) &&
				connectionBetweenPathsExists(grid, positionInfo, direction, coordinateHistory) &&
				relativePathConnectedToCurrentPath(direction, positionInfo.getCurrentValue(), relativeCoordinateValue)
				? true : false;
		return isPathValid;
	}


	private boolean relativePathConnectedToCurrentPath(Direction direction, char currentCharValue, char directionCharValue) {
		return 
				(direction == Direction.DOWN && directionCharValue == '-' && currentCharValue != '|') || 
				(direction == Direction.UP && directionCharValue == '-' && currentCharValue != '|') 
				? false : true;
	}


	
	public boolean connectionBetweenPathsExists(Grid grid, PositionInfo positionInfo, Direction direction,
			List<CoordinateHistory> coordinateHistory) {
						
		Coordinate nextCoordinate = positionInfo.getRelativeCoordinate(direction);

		Character currentValue = positionInfo.getCurrentValue();
		Character relativeValue = grid.getCoordinateValue(nextCoordinate);
				
		if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)){
			
			if(
					(currentValue == '-' && relativeValue == '-') || 
					(currentValue == '-' && relativeValue == '+') || 
					(currentValue == '+' && relativeValue == '-') ||
					(currentValue == '+' && relativeValue == '-') 
			) {
				return false;
			}
			
		} else if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
			if(currentValue == '|' && relativeValue == '|') {
				return false;
			}
		}

		return true;
	}
	
	private boolean checkIntersection(Grid grid, PositionInfo positionInfo) {

		if (
				(positionInfo.getUpValue() != ' ' && (int) positionInfo.getUpValue() != 0) &&
				(positionInfo.getBelowValue() != ' '  && (int) positionInfo.getBelowValue() != 0) && 
				(positionInfo.getLeftValue() != ' '  && (int) positionInfo.getLeftValue() != 0) &&
				(positionInfo.getRightValue() != ' '  && (int) positionInfo.getRightValue() != 0)
				
		)
			return true;
		return false;
	}
	
	boolean coordinateAlreadyVisited(List<CoordinateHistory> coordinateHistory, Coordinate coordinate){
		
		boolean alreadyVisited = false;
		
		if (!coordinateHistory.isEmpty()) {
			Coordinate lastCoordinate = getLastCoordinate(coordinateHistory);
			alreadyVisited = lastCoordinate.getX() == coordinate.getX() && lastCoordinate.getY() == coordinate.getY();
		}
		return alreadyVisited;
	}
	
	private String convertPathToCharacters(Grid grid, List<CoordinateHistory> coordinateHistory) {
		return coordinateHistory.stream()
				.map(history -> grid.getCoordinateValue(history.getCoordinate()).toString())
				.collect(Collectors.joining(""));
	}
	



//	private void checkTFork(Grid grid, PositionInfo positionInfo) {
//		
//		if(positionInfo.getCurrentValue() == '+') {
//						
//			if ( ( (positionInfo.getUpValue() != null && (int) positionInfo.getUpValue() != 0)
//					&& positionInfo.getUpValue() == '|')
//					&& ((positionInfo.getBelowValue() != null && (int) positionInfo.getBelowValue() != 0)
//							&& positionInfo.getBelowValue() == '|')
//				)
//				throw new TForksException("T Fork exception!");
//			else if (
//					((positionInfo.getLeftValue() != null && (int) positionInfo.getLeftValue() != 0)
//					&& positionInfo.getLeftValue() == '-')
//					&& ((positionInfo.getRightValue() != null && (int) positionInfo.getRightValue() != 0)
//							&& positionInfo.getRightValue() == '-')
//					)
//				throw new TForksException("T Fork exception!");
//		}
//	}
}
