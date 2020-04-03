package project.riskgame.com.controller;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.controller.strategy.AggressiveStrategy;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;

/**
 * This class will test the GamePlay commands.
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.1
 *
 */
public class GamePlayValidatorTest {

	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader;
	/**
	 * Instance of StartupPhase class.
	 */
	private StartupPhase startupPhase;
	/**
	 * Instance of PlayerController class.
	 */
	private static PlayerController playerController;
	/**
	 * Instance of GamePlayController class
	 */
	private static GamePlayController gamePlayController;

	/**
	 * This method will be executed before test cases and will initialize the
	 * objects
	 * 
	 * @throws Exception while reading map files
	 */
	@Before
	public void before() throws Exception {
		mapFileReader = MapFileReader.getInstance();
		playerController = PlayerController.getInstance();
		mapFileReader.readMapFile("risk.map");
		startupPhase = new StartupPhase();
		gamePlayController = new GamePlayController();
		startupPhase.addPlayer("Dhaval", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Shubham", new AggressiveStrategy(gamePlayController));
	}

	/**
	 * This method will test if the attack is valid or not.
	 */
	@Test
	public void isAttackValidTest() {

		Player attacker = playerController.getPlayerByName("Dhaval");
		Player defender = playerController.getPlayerByName("Shubham");
		attacker.getCountriesOwned().add("39");
		Country attackerCountry = mapFileReader.getCountryByName("Indonesia");
		attackerCountry.setNumberofArmies(4);
		defender.getCountriesOwned().add("40");
		boolean actualResult = GamePlayValidator.isAttackValid(attacker, "Indonesia", "New-Guinea", 3);
		assertTrue(actualResult);

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
