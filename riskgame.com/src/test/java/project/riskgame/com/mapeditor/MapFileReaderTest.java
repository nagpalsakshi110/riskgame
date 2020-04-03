package project.riskgame.com.mapeditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;

/**
 * This class tests functions for reading the map chosen by the player.
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.1
 */
public class MapFileReaderTest {

	/**
	 * Instance of MapFileReader class
	 */
	private static MapFileReader mapFileReader;

	/**
	 * Set the static members context before starting test cases of
	 * MapFileReaderTest class
	 */
	@BeforeClass
	public static void beforeClass() {
		mapFileReader = MapFileReader.getInstance();
	}

	/**
	 * this method test the reading of map file
	 * 
	 * @throws Exception on readMapFileTest
	 */
	@Test
	public void readMapFileTest() throws Exception {

		mapFileReader.readMapFile("risk.map");
		int size = mapFileReader.getContinentList().size();
		assertNotEquals(0, size);
		mapFileReader.clearAll();
	}

	/**
	 * this method tests the getCountryByName() for valid input
	 * 
	 * @throws Exception on getCountryByNameTestValid
	 */
	@Test
	public void getCountryByNameTestValid() throws Exception {

		mapFileReader.readMapFile("risk.map");
		Country country = mapFileReader.getCountryByName("Alaska");
		assertEquals("Alaska", country.getName());
		mapFileReader.clearAll();

	}

	/**
	 * this method tests the getCountryByName() for invalid input
	 * 
	 * @throws Exception on getCountryByNameTestInvalid
	 */
	@Test
	public void getCountryByNameTestInvalid() throws Exception {
		mapFileReader.readMapFile("risk.map");
		Country country = mapFileReader.getCountryByName("Europe");
		assertNull(country);
		mapFileReader.clearAll();
	}

	/**
	 * this method tests the getContinentByNameTestValid() for valid input
	 * 
	 * @throws Exception on getContinentByNameTestValid
	 */
	@Test
	public void getContinentByNameTestValid() throws Exception {

		mapFileReader.readMapFile("risk.map");
		Continent continent = mapFileReader.getContinentByName("Europe");
		assertEquals("Europe", continent.getName());
		mapFileReader.clearAll();

	}

	/**
	 * this method tests the getContinentByNameTestValid() for invalid input
	 * 
	 * @throws Exception on getContinentByNameTestInvalid
	 */
	@Test
	public void getContinentByNameTestInvalid() throws Exception {

		mapFileReader.readMapFile("risk.map");
		Continent continent = mapFileReader.getContinentByName("Australia");
		assertNull(continent);
		mapFileReader.clearAll();
	}

	/**
	 * this method reads invalid file and handles exception
	 */
	@Test
	public void readInvalidMap() {
		boolean thrown = false;
		try {
			mapFileReader.readMapFile("Invalid.map");
		} catch (Exception e) {
			System.out.println("Error occured : " + e.getClass().getName() + " - " + e.getMessage());
			thrown = true;
		}
		assertTrue(thrown);
		mapFileReader.clearAll();
	}

}
