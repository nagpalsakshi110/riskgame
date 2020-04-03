package project.riskgame.com.mapeditor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;

/**
 * This class tests the methods of MapFileValidator class
 * 
 * @author Shubham
 * @author Devdutt
 * @version 1.1
 */
public class MapValidatorTest {

	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader;

	/**
	 * This method will be executed before test cases and will initialize the
	 * mapFileReader object
	 */
	@BeforeClass
	public static void beforeClass() {
		mapFileReader = MapFileReader.getInstance();
	}

	/**
	 * This method is validating the maps added in the resource.
	 * 
	 * @throws Exception on ValidateMapTest.
	 */
	@Test
	public void validateAllMapsTest() throws Exception {

		System.out.println("testing -- risk.map");
		mapFileReader.readMapFile("risk.map");
		assertTrue(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_11.map");
		mapFileReader.readMapFile("test_11.map");
		assertFalse(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_12.map");
		mapFileReader.readMapFile("test_12.map");
		assertTrue(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_21.map");
		mapFileReader.readMapFile("test_21.map");
		assertTrue(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_22.map");
		mapFileReader.readMapFile("test_22.map");
		assertFalse(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_31.map");
		mapFileReader.readMapFile("test_31.map");
		assertTrue(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_32.map");
		mapFileReader.readMapFile("test_32.map");
		assertFalse(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_41.map");
		mapFileReader.readMapFile("test_41.map");
		assertTrue(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

		System.out.println("testing -- test_42.map");
		mapFileReader.readMapFile("test_42.map");
		assertFalse(MapValidator.validateMap(mapFileReader.getMap()));
		mapFileReader.clearAll();

	}

	/**
	 * This method validates if the country already exists in the countryList.
	 * 
	 * @throws Exception on isCountryExistTest.
	 */
	@Test
	public void isCountryExistsTest() throws Exception {
		mapFileReader.readMapFile("test_31.map");
		String countryName = "Sri-Lanka";
		boolean result = MapValidator.isCountryExists(countryName);
		assertTrue(result);
		mapFileReader.clearAll();
	}

	/**
	 * This method validates if the continent already exists in the continentList.
	 * 
	 * @throws Exception on reading map files
	 */
	@Test
	public void isContinentExistsTest() throws Exception {
		mapFileReader.readMapFile("test_31.map");
		String continentName = "F";
		boolean result = MapValidator.isContinentExists(continentName);
		assertFalse(result);
		mapFileReader.clearAll();
	}

	/**
	 * This method validates if the continent value added by the player is valid
	 * 
	 * @throws Exception on controlValueValidTest.
	 */
	@Test
	public void isControlValueValidTest() throws Exception {
		mapFileReader.readMapFile("test_31.map");
		String controlValue = "-1";
		boolean result = MapValidator.isControlValueValid(controlValue);
		assertFalse(result);
		mapFileReader.clearAll();
	}

	/**
	 * This method validates if the neighbor entered by the player is a valid
	 * 
	 * @throws Exception on isNeighbourTest.
	 */
	@Test
	public void isNeighbourTest() throws Exception {
		mapFileReader.readMapFile("test_31.map");
		String countryName = "Bangladesh";
		String neighborName = "France";
		boolean result = MapValidator.isNeighbour(countryName, neighborName);
		assertTrue(result);
		mapFileReader.clearAll();
	}

	/**
	 * this method checks whether continent is subgraph or not.
	 * 
	 * @throws Exception on checkContinentIsValidSubgraph.
	 */
	@Test
	public void checkContinentIsValidSubgraph() throws Exception {
		mapFileReader.readMapFile("testcase.txt");

		Continent continentInvalidSubgraph = mapFileReader.getContinentByID("2");
		assertFalse(validateSubgraph(continentInvalidSubgraph));

		Continent continentValidSubgraph = mapFileReader.getContinentByID("1");
		assertTrue(validateSubgraph(continentValidSubgraph));
		mapFileReader.clearAll();
	}

	/**
	 * This method will check whether the continent is valid subgraph or not
	 * 
	 * @param continent continent which is to be checked
	 * @return if subgraph return true else false
	 */
	public static boolean validateSubgraph(Continent continent) {

		ArrayList<String> isVisited = new ArrayList<String>();
		ArrayList<String> listOfCountries = new ArrayList<String>(continent.getListofCountires());
		int component = 0;

		for (String string : listOfCountries) {

			if (!isVisited.contains(string)) {
				component++;
				dfsSubgraph(string, isVisited);

			}
		}

		if (component == 1) {
			return true;
		}

		return false;

	}

	/**
	 * checks use DFS to check for the connectivity
	 * 
	 * @param countryName country in which dfs is applied
	 * @param isVisited   is visited add country to the list
	 */
	public static void dfsSubgraph(String countryName, ArrayList<String> isVisited) {

		isVisited.add(countryName);
		Country country = mapFileReader.getCountryByID(countryName);
		List<String> adjacentCountries = country.getAdjacentCountries();
		for (String adjacentCountry : adjacentCountries) {
			if (isNeighborInSameContinent(adjacentCountry, country.getName())) {

				if (!isVisited.contains(adjacentCountry)) {
					dfsSubgraph(adjacentCountry, isVisited);
				}
			}
		}
	}

	/**
	 * this method checks whether countries are neighbor and in the same continent
	 * 
	 * @param adjacentCountry neighbor country
	 * @param countryName     country whose adjacency is to be checked
	 * @return true if condition is satisfied else return false
	 */
	public static boolean isNeighborInSameContinent(String adjacentCountry, String countryName) {
		boolean check = false;
		Country adjacentcountry = mapFileReader.getCountryByID(adjacentCountry);
		Country country = mapFileReader.getCountryByName(countryName);

		if (adjacentcountry.getContinentID().equals(country.getContinentID())) {
			check = true;
		}

		if (MapValidator.isNeighbour(adjacentcountry.getName(), countryName) && check) {
			return true;
		}
		return false;
	}

}
