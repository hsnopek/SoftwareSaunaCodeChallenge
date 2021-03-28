package test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleEndsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.MultipleStartsException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoEndException;
import hr.hsnopek.softwaresaunacodechallenge.common.exceptions.NoStartException;
import hr.hsnopek.softwaresaunacodechallenge.common.util.FileUtils;
import hr.hsnopek.softwaresaunacodechallenge.parser.MapParser;

class InvalidMapsTest {

	public InvalidMapsTest() {}
	
	private MapParser mapParser;
	private String map;
	private String exception;

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

		System.out.println("Result: " + exception);
		System.out.println();

		System.out.println("Expected result: Error");
		System.out.println();

	}

	@Test
	void testMap6() {
		System.out.println();
		System.out.println("Map 6 - no start");
		System.out.println();

		String map = "invalid_maps/map6.txt";
		this.map = map;
		Throwable thrown = assertThrows(NoStartException.class, () -> this.mapParser.parse(FileUtils.read(map)), "Error.");
		this.exception = thrown.getClass().getTypeName();
		
	}

	@Test
	void testMap7() {
		System.out.println();
		System.out.println("Map 7 - no end");
		System.out.println();

		String map = "invalid_maps/map7.txt";
		this.map = map;
		Throwable thrown = assertThrows(NoEndException.class, () -> this.mapParser.parse(FileUtils.read(map)), "Error.");
		this.exception = thrown.getClass().getTypeName();
	}

	@Test
	void testMap8() {
		System.out.println();
		System.out.println("Map 7 - multiple starts");
		System.out.println();

		String map = "invalid_maps/map8.txt";
		this.map = map;
		Throwable thrown = assertThrows(MultipleStartsException.class, () -> this.mapParser.parse(FileUtils.read(map)), "Error.");
		this.exception = thrown.getClass().getTypeName();
	}

	@Test
	void testMap9() {
		
		System.out.println();
		System.out.println("Map 9 - multiple ends");
		System.out.println();

		String map = "invalid_maps/map9.txt";
		this.map = map;
		Throwable thrown = assertThrows(MultipleEndsException.class, () -> this.mapParser.parse(FileUtils.read(map)), "Error.");
		this.exception = thrown.getClass().getTypeName();

	}

	@Test
	void TestMap10() {
		
		System.out.println();
		System.out.println("Map 10 - T forks");
		System.out.println();

		String map = "invalid_maps/map10.txt";
		this.map = map;
		
		Throwable thrown = assertThrows(RuntimeException.class, () -> this.mapParser.parse(FileUtils.read(map)), "Error.");
		this.exception = thrown.getClass().getSimpleName();

	}


}
