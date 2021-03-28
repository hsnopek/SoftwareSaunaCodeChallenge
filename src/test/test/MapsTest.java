package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.hsnopek.softwaresaunacodechallenge.common.model.Results;
import hr.hsnopek.softwaresaunacodechallenge.common.util.FileUtils;
import hr.hsnopek.softwaresaunacodechallenge.parser.MapParser;

class MapsTest {
	
	public MapsTest() {}

	private MapParser mapParser;
	private Results results;
	private String map;

	@BeforeEach
	void testSetUp() {
    	mapParser = new MapParser();
	}
	
	@AfterEach
	void log() {
    	mapParser = new MapParser();
    	
		try {
			System.out.println(FileUtils.read(this.map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Result:");
		System.out.println(String.format("Letters: %s", results.getLetters()));
		System.out.println(String.format("Paths as characters: %s", results.getPathAsCharacters()));
		System.out.println();

		System.out.println("Expected result:");
		
		System.out.println(String.format("Letters: %s", results.getLetters()));
		System.out.println(String.format("Paths as characters: %s", results.getPathAsCharacters()));

	}

	@Test
	void testMap1() {
   		try {
   			System.out.println("Map 1 - a basic example");
   			System.out.println();
   			
   			String map = "maps/map1.txt";
   			this.map = map;

			Results results = this.mapParser.parse(FileUtils.read(map));
			this.results = results;
			this.map = "maps/map1.txt";
			
			final String map1Letters = "ACB";
			final String map1PathAsCharacters = "@---A---+|C|+---+|+-B-x";
			
			assertEquals(map1Letters, results.getLetters());
			assertEquals(map1PathAsCharacters, results.getPathAsCharacters());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testMap2() {
   		try {
   			
   			System.out.println();
   			System.out.println();

   			System.out.println("Map 2 - go straight through intersections");
   			System.out.println();
   			
   			String map = "maps/map2.txt";
   			this.map = map;

			Results results = this.mapParser.parse(FileUtils.read(map));
			this.results = results;

			final String map2Letters = "ABCD";
			final String map2PathAsCharacters = "@|A+---B--+|+--C-+|-||+---D--+|x";
			
			assertEquals(map2Letters, results.getLetters());
			assertEquals(map2PathAsCharacters, results.getPathAsCharacters());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testMap3() {
   		try {
   			
   			System.out.println();
   			System.out.println();

   			System.out.println("Map 3 - letters may be found on turns");
   			System.out.println();

   			String map = "maps/map3.txt";
   			this.map = map;

			Results results = this.mapParser.parse(FileUtils.read(map));
			this.results = results;

			final String map3Letters = "ACB";
			final String map3PathAsCharacters = "@---A---+|||C---+|+-B-x";
			
			assertEquals(map3Letters, results.getLetters());
			assertEquals(map3PathAsCharacters, results.getPathAsCharacters());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testMap4() {
   		try {
   			
   			System.out.println();
   			System.out.println();

   			System.out.println("Map 4 - do not collect letters twice");
   			System.out.println();

   			String map = "maps/map4.txt";
   			this.map = map;

			Results results = this.mapParser.parse(FileUtils.read(map));
			this.results = results;

			final String map4Letters = "ABCD";
			final String map4PathAsCharacters = "@--A-+|+-+|A|+--B--+C|+-+|+-C-+|D|x";
			
			assertEquals(map4Letters, results.getLetters());
			assertEquals(map4PathAsCharacters, results.getPathAsCharacters());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testMap5() {
   		try {
   			
   			System.out.println();
   			System.out.println();

   			System.out.println("Map 5 - keep direction, even in a compact space");
   			System.out.println();
   			
   			String map = "maps/map5.txt";
   			this.map = map;

			Results results = this.mapParser.parse(FileUtils.read(map));
			this.results = results;

			final String map5Letters = "ABCD";
			final String map5PathAsCharacters = "@A+++A|+-B-+C+++C-+Dx";
			
			assertEquals(map5Letters, results.getLetters());
			assertEquals(map5PathAsCharacters, results.getPathAsCharacters());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
