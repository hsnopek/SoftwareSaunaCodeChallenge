package hr.hsnopek.softwaresaunacodechallenge.common.model;

import java.util.ArrayList;
import java.util.List;

import hr.hsnopek.softwaresaunacodechallenge.common.enums.SpecialCharacters;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleEndsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleStartsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoEndException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoStartException;

public class Grid {
	
	private char[][] grid;

	public Grid() {}
	
	public Grid(char[][] grid) {
		this.grid = grid;
	}
	
	public Grid(String map) {
		this.grid = transferMapToGrid(map);
		//System.out.println(String.format("Loading grid with %d rows and %d columns", this.getRowCount()+1, this.getColumnCount(0)+1));
	}


	public char[][] toCharArray() {
		return grid;
	}
	
	public int getRowCount(){
		return this.grid.length-1;
	}
	
	public int getColumnCount(int rowIndex){
		char[] row = this.grid[rowIndex];
		return row.length-1;
	}
	
	public char[][] transferMapToGrid(String map){
		
		String[] lines = map.split(System.getProperty("line.separator"));
		int numOfRows = lines.length;
		int numOfColumns = getLargestColumnLengthFromLineList(lines);
		
		char[][] grid = new char[numOfRows][numOfColumns];
		
		int i=1;
		
		for (String line : lines) {
			mapLineRowToGridRow(grid, line, i-1);
			i++;
		}
		
		return grid;
	}
	
	public int getLargestColumnLengthFromLineList(String[] lines) {
		
		int largestColumnLength = 0;
		
		for (String line : lines) {
			
			if (line.length() > largestColumnLength)
				largestColumnLength = line.length();
		}
		
		return largestColumnLength+1;	
	}
	
	public void mapLineRowToGridRow(char[][] grid, String line, int rowNum) {
		
		char[] bytes = line.toCharArray();
		
		for(int i=0; i<bytes.length; i++) {
			grid[rowNum][i] = bytes[i];
		}
	}
	
	public boolean isCoordinateInsideGrid(Coordinate coordinate) {				
		if(
				(coordinate.getX() >= 0 && coordinate.getX() <= this.getRowCount()) && 
				(coordinate.getY() >= 0 && coordinate.getY() <= this.getColumnCount(coordinate.getX()))
		) {
			return true;
		} else {
			return false;
		}
	}
	

	public Coordinate locateCharacterPosition(SpecialCharacters specialCharacter) {
		List<Coordinate> coordinateList = new ArrayList<>();
		
		
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid[x].length; y++) {
            	if (this.grid[x][y] == specialCharacter.value)
            		coordinateList.add(new Coordinate(x,y));
            }
        }
        
        if (coordinateList.isEmpty() && specialCharacter.equals(SpecialCharacters.START))
        	throw new NoStartException(String.format("Map has no '%s' position.", specialCharacter.value));
        else if(coordinateList.isEmpty() && specialCharacter.equals(SpecialCharacters.END))
        	throw new NoEndException(String.format("Map has no '%s' position.", specialCharacter.value));
        else if (coordinateList.size() > 1 && specialCharacter.equals(SpecialCharacters.START))
        	throw new MultipleStartsException(String.format("There are more than '%s' positions in map.", specialCharacter.value));
        else if (coordinateList.size() > 1 && specialCharacter.equals(SpecialCharacters.END))
        	throw new MultipleEndsException(String.format("There are more than '%s' positions in map.", specialCharacter.value));

		return coordinateList.stream().findFirst().get();
	}


	public boolean isEndCoordinate(Coordinate currentPosition, Coordinate endPos) {
		return  currentPosition.getX() == endPos.getX() && currentPosition.getY() == endPos.getY() ? true : false;
	}

	public void printMatrix(Coordinate currentPosition) {
		
	    for(int row=0; row < this.grid.length; row++) {
	       for(int col=0; col<this.grid[row].length; col++) {
	    	   if(row == currentPosition.getX() && col == currentPosition.getY()) {
	    		   System.out.print("[" + this.grid[row][col] + "] ");
	    	   }
	    	   else
	    		   System.out.print(this.grid[row][col] + " ");
	       }
	       System.out.println();
	    }
	}
	
	public Character getCoordinateValue(Coordinate coordinate){
		if(isCoordinateInsideGrid(coordinate))
			return this.grid[coordinate.getX()][coordinate.getY()];
		else
			return null;
	}
}
