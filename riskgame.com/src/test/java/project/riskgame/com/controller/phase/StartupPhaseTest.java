package project.riskgame.com.controller.phase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.controller.strategy.AggressiveStrategy;
import project.riskgame.com.controller.strategy.CheaterStrategy;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This test class tests the methods related to startPhase of Player
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.1
 *
 */
public class StartupPhaseTest {

	/**
	 * Instance of PlayerController class.
	 */
	private static PlayerController playerController;
	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader;
	/**
	 * Instance of StartupPhase class.
	 */
	private StartupPhase startupPhase = new StartupPhase();
	/**
	 * Instance of PlayerWorldDominationFrame frame.
	 */
	private PlayerWorldDominationFrame worldDominationFrame;
	/**
	 * Instance of GamePlayController class
	 */
	private static GamePlayController gamePlayController;
	/**
	 * Instance of PlayersWorldDominationView class.
	 */
	private static PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();

	/**
	 * This method will be executed before every test case and will initialize the
	 * objects
	 * 
	 * @throws Exception while reading map files
	 */
	@Before
	public void before() throws Exception {
		playerController = PlayerController.getInstance();
		mapFileReader = MapFileReader.getInstance();
		mapFileReader.readMapFile("risk.map");
		worldDominationFrame = new PlayerWorldDominationFrame();
		gamePlayController = new GamePlayController();
		startupPhase.addPlayer("Dhaval", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Devdutt", new CheaterStrategy(gamePlayController));
		startupPhase.addPlayer("Shubham", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Sakshi", new CheaterStrategy(gamePlayController));
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		startupPhase.populateCountries();
	}

	/**
	 * This method tests if the country is populated by any player.
	 */
	@Test
	public void populateCountriesTest() {
		Player player = playerController.getPlayerByName("Dhaval");
		assertNotEquals(0, player.getCountriesOwned().size());
	}

	/**
	 * This method tests number of assigned armies to a player.
	 */
	@Test
	public void getAssignedArmiesTest() {
		Player player = playerController.getPlayerByName("Devdutt");
		assertNotEquals(0, playerController.getAssignedArmies(player));
	}

	/**
	 * This method tests the placement of the armies by the player.
	 */
	@Test
	public void placeArmiesTest() {
		Player player = playerController.getPlayerByName("Dhaval");
		String countryID = player.getCountriesOwned().get(1);
		Country country = mapFileReader.getCountryByID(countryID);
		int armiesBefore = country.getNumberofArmies();

		playerController.setCurrentPlayer(player);
		startupPhase.placeArmies(player, country.getName());
		int armiesAfter = country.getNumberofArmies();
		assertEquals(armiesBefore + 1, armiesAfter);

	}

	/**
	 * This method tests the command of playeAll armies by the player.
	 */
	@Test
	public void placeAllTest() {
		Player player = playerController.getPlayerByName("Dhaval");
		playerController.setCurrentPlayer(player);
		startupPhase.placeAll();
		int totalArmiesOwned = player.getTotalArmiesOwned();
		int totalArmiesAssigned = playerController.getAssignedArmies(player);
		assertEquals(totalArmiesOwned, totalArmiesAssigned);

	}

	/**
	 * This method will be executed after every test case and will clear the
	 * playerList.
	 */
	@After
	public void after() {
		mapFileReader.clearAll();
		playerController.getPlayerList().clear();

	}

}
