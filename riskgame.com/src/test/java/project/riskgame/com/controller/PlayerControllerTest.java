package project.riskgame.com.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.controller.strategy.AggressiveStrategy;
import project.riskgame.com.controller.strategy.CheaterStrategy;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.PhaseViewFrame;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This test class tests the methods related to the armies of the player.
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.1
 *
 */
public class PlayerControllerTest {

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
	private StartupPhase startupPhase;
	/**
	 * Instance of PlayerWorldDominationFrame frame.
	 */
	private PlayerWorldDominationFrame worldDominationFrame;
	/**
	 * Instance of PhaseView class.
	 */
	private PhaseView phaseView;
	/**
	 * Instance of PhaseViewFrame frame.
	 */
	private PhaseViewFrame phaseViewFrame;
	/**
	 * Instance of PlayersWorldDominationView class.
	 */
	private PlayersWorldDominationView playersWorldDominationView;
	/**
	 * Instance of GamePlayController class
	 */
	private static GamePlayController gamePlayController;

	/**
	 * This method will be executed before every test case and will initialize the
	 * objects
	 * 
	 * @throws Exception while reading map files
	 */
	@Before
	public void before() throws Exception {
		playerController = PlayerController.getInstance();
		startupPhase = new StartupPhase();
		mapFileReader = MapFileReader.getInstance();
		mapFileReader.readMapFile("risk.map");
		worldDominationFrame = new PlayerWorldDominationFrame();
		gamePlayController = new GamePlayController();
		startupPhase.addPlayer("Dhaval", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Devdutt", new CheaterStrategy(gamePlayController));
		startupPhase.addPlayer("Shubham", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Sakshi", new CheaterStrategy(gamePlayController));
		worldDominationFrame = new PlayerWorldDominationFrame();
		playersWorldDominationView = PlayersWorldDominationView.getInstance();
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		startupPhase.populateCountries();
		phaseView = PhaseView.getInstance();
		phaseViewFrame = new PhaseViewFrame();

	}

	/**
	 * This method tests the calculation of reinforcement armies
	 */
	@Test
	public void calculateReinforcementArmiesTest() {

		Player player = playerController.getPlayerByName("Dhaval");
		startupPhase.populateCountries();
		int countriesOwned = player.getCountriesOwned().size();
		int expectedReinforcementArmies = countriesOwned / 3;
		int ActualReinforcementArmies = playerController.calculateReinforcementArmies(player);
		assertEquals(expectedReinforcementArmies, ActualReinforcementArmies);
	}

	/**
	 * This method will test the validation of attack move.
	 */
	@Test
	public void attackmoveTest() {
		phaseView.setPhaseViewFrame(phaseViewFrame);
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		Player attacker = playerController.getPlayerByName("Dhaval");
		Player defender = playerController.getPlayerByName("Devdutt");
		attacker.getCountriesOwned().clear();
		defender.getCountriesOwned().clear();
		attacker.getCountriesOwned().add("39");
		defender.getCountriesOwned().add("40");
		Country defendingCountry = mapFileReader.getCountryByName("New-Guinea");
		Country attackingCountry = mapFileReader.getCountryByName("Indonesia");
		attackingCountry.setNumberofArmies(4);
		defendingCountry.setNumberofArmies(2);
		List<Integer> attackerDiceValues = new ArrayList<Integer>();
		List<Integer> defenderDiceValues = new ArrayList<Integer>();
		attackerDiceValues.add(6);
		attackerDiceValues.add(6);
		attackerDiceValues.add(6);
		defenderDiceValues.add(2);
		defenderDiceValues.add(2);
		playerController.setAttackerDiceValues(attackerDiceValues);
		playerController.setDefenderDiceValues(defenderDiceValues);
		playerController.setAttackingCountry(attackingCountry);
		playerController.setDefendingCountry(defendingCountry);
		playerController.beginBattle();
		playerController.attackMove(1);
		assertEquals(new Integer(1), defendingCountry.getNumberofArmies());
	}

	/**
	 * This method will test the fortification of armies.
	 */
	@Test
	public void fortificationOfArmiesTest() {
		phaseView.setPhaseViewFrame(phaseViewFrame);
		Player player = playerController.getPlayerByName("Dhaval");
		playerController.setCurrentPlayer(player);
		player.getCountriesOwned().add("39");
		player.getCountriesOwned().add("40");
		mapFileReader.getCountryByName("New-Guinea").setNumberofArmies(4);
		playerController.fortificationOfArmies(player, "New-Guinea", "Indonesia", 1);
		assertEquals(new Integer(3), mapFileReader.getCountryByName("New-Guinea").getNumberofArmies());

	}

	/**
	 * This method will test the winning player.
	 */
	@Test
	public void winingTest() {
		phaseView.setPhaseViewFrame(phaseViewFrame);
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		Player attacker = playerController.getPlayerByName("Dhaval");
		Player defender = playerController.getPlayerByName("Devdutt");
		attacker.getCountriesOwned().clear();
		defender.getCountriesOwned().clear();
		Country country = mapFileReader.getCountryByName("New-Guinea");
		mapFileReader.getCountryList().remove(country);
		for (Country countryOwn : mapFileReader.getCountryList()) {
			attacker.getCountriesOwned().add(countryOwn.getCountryID());
		}
		mapFileReader.getCountryList().add(country);
		defender.getCountriesOwned().add("40");
		Country defendingCountry = mapFileReader.getCountryByName("New-Guinea");
		Country attackingCountry = mapFileReader.getCountryByName("Indonesia");
		attackingCountry.setNumberofArmies(4);
		defendingCountry.setNumberofArmies(2);
		List<Integer> attackerDiceValues = new ArrayList<Integer>();
		List<Integer> defenderDiceValues = new ArrayList<Integer>();
		attackerDiceValues.add(6);
		attackerDiceValues.add(6);
		attackerDiceValues.add(6);
		defenderDiceValues.add(2);
		defenderDiceValues.add(2);
		playerController.setAttackerDiceValues(attackerDiceValues);
		playerController.setDefenderDiceValues(defenderDiceValues);
		playerController.setAttackingCountry(attackingCountry);
		playerController.setDefendingCountry(defendingCountry);
		playerController.beginBattle();
		assertEquals(42, attacker.getCountriesOwned().size());
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
