package project.riskgame.com.mapeditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;

/**
 * This class tests functions for editing the map chosen by the player.
 * 
 * @author Dhaval
 * @author Devdutt
 * @version 1.1
 */
public class MapFileEditorTest {

	/**
	 * Instance of MapFileEditor class.
	 */
	private static MapFileEditor mapFileEditor;

	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader;

	/**
	 * This method will be executed before test cases and will initialize the
	 * objects
	 */
	@Before
	public void before() {
		mapFileEditor = MapFileEditor.getInstance();
		mapFileReader = MapFileReader.getInstance();

	}

	/**
	 * This method tests whether the map is saved after editing it.
	 * 
	 * @throws Exception on saving map
	 */
	@Test
	public void saveMapTest() throws Exception {

		mapFileEditor.editMap("test_forDFS.map");
		String command = "editcontinent -add Dhaval 5";
		mapFileEditor.editContinent(command);
		String command1 = "editcountry -add Japan Dhaval";
		mapFileEditor.editCountry(command1);
		mapFileEditor.saveMap("test_forDFS.map");
		mapFileReader.readMapFile("test_forDFS.map");
		Country country = mapFileReader.getCountryByName("Japan");
		assertEquals("Japan", country.getName());
		mapFileReader.clearAll();

		mapFileEditor.editMap("test_forDFS.map");
		String commandRemoveCountry = "editcountry -remove Japan";
		mapFileEditor.editCountry(commandRemoveCountry);
		mapFileEditor.saveMap("test_forDFS.map");
		mapFileReader.readMapFile("test_forDFS.map");
		assertNull(mapFileReader.getCountryByName("Japan"));
		mapFileReader.clearAll();
	}

	/**
	 * This method tests save functionality of conquest map
	 * 
	 * @throws Exception on saving map
	 */
	@Test
	public void saveMapConquestTest() throws Exception {

		mapFileEditor.editMap("test_conquest.txt");
		String command = "editcontinent -add Asia 5";
		mapFileEditor.editContinent(command);
		String command1 = "editcountry -add India Asia";
		mapFileEditor.editCountry(command1);
		List<String> listCommand = new ArrayList<String>();
		listCommand.add("savemap test_conquest.txt");
		mapFileEditor.executeCommands(listCommand);
		File file = new File("src/main/java/project/riskgame/com/resources/test_conquest.txt");
		assertTrue(file.exists());
	}

	/**
	 * This method tests read functionality of conquest map
	 * 
	 * @throws Exception while reading map
	 */
	@Test
	public void readMapConquestTest() throws Exception {
		mapFileReader.readMapFileConquest("conquest.txt");
		Country country = mapFileReader.getCountryByName("Alaska");
		assertEquals("Alaska", country.getName());

	}

	/**
	 * This method tests whether the countryList is updated after editing.
	 * 
	 * @throws Exception on updating country list
	 */
	@Test
	public void updateCountryList() throws Exception {

		mapFileEditor.editMap("test_31.map");
		String command = "editcountry -add Baluchistan A";
		mapFileEditor.editCountry(command);
		java.util.List<Country> countryList = mapFileReader.getCountryList();
		mapFileReader.updateCountryList(countryList);
		Country country = mapFileReader.getCountryByName("Baluchistan");
		assertEquals("Baluchistan", country.getName());

	}

	/**
	 * This method tests whether the continentList is updated after editing.
	 * 
	 * @throws Exception on updating continent list
	 */
	@Test
	public void updateContinentListTest() throws Exception {

		mapFileEditor.editMap("test_31.map");
		String command = "editcontinent -add F 5";
		mapFileEditor.editContinent(command);
		java.util.List<Country> countryList = mapFileReader.getCountryList();
		java.util.List<Continent> continentList = mapFileReader.getContinentList();
		mapFileReader.updateContinentList(countryList, continentList);
		Continent continent = mapFileReader.getContinentByName("F");
		assertEquals("F", continent.getName());
	}

	/**
	 * This method will be executed after every test case and will clear the
	 * playerList.
	 */
	@After
	public void after() {
		mapFileReader.clearAll();

	}

}
