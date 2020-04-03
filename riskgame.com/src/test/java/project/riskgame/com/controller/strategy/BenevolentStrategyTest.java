package project.riskgame.com.controller.strategy;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.PhaseViewFrame;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This test class for BenevolentStrategy
 * @author Dhaval
 * @version 1.3
 *
 */
public class BenevolentStrategyTest {

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
	 * Instance of BenevolentStrategy class
	 */
	private Strategy BenevolentStrategy;

	/**
	 * This method will be executed before every test case and will initialize the
	 * objects
	 * 
	 * @throws Exception while reading map files
	 */
	@Before
	public void before() throws Exception {
		playerController = PlayerController.getInstance();
		gamePlayController = new GamePlayController();
		startupPhase = gamePlayController.getStartupPhase();
		mapFileReader = MapFileReader.getInstance();
		mapFileReader.readMapFile("risk.map");
		worldDominationFrame = new PlayerWorldDominationFrame();
		BenevolentStrategy = new BenevolentStrategy(gamePlayController);
		startupPhase.addPlayer("Dhaval", BenevolentStrategy);
		worldDominationFrame = new PlayerWorldDominationFrame();
		playersWorldDominationView = PlayersWorldDominationView.getInstance();
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		startupPhase.populateCountries();
		phaseView = PhaseView.getInstance();
		phaseViewFrame = new PhaseViewFrame();
		phaseView.setPhaseViewFrame(phaseViewFrame);
		gamePlayController.setPhaseViewFrame(phaseViewFrame);

	}

	/**
	 * This method tests the calculation of reinforcement armies
	 */
	@Test
	public void reinforceTest() {
		Player player = playerController.getPlayerByName("Dhaval");
		gamePlayController.setCurrentPlayer(player);
		gamePlayController.getCurrentPlayer().setCurrentPhase("reinforce");
		List<Country> sortedListOfCountriesByArmy = playerController.getSortedListOfCountriesByArmy(player);
		int index = sortedListOfCountriesByArmy.size() - 1;
		Country weakestCountry = sortedListOfCountriesByArmy.get(index);
		BenevolentStrategy.reinforce(player);
		int ActualReinforcementArmies = weakestCountry.getNumberofArmies();
		assertEquals(15, ActualReinforcementArmies);

	}

	/**
	 *  This method will test correctness of attack
	 */
	@Test
	public void AttackTest() {
		Player player = playerController.getPlayerByName("Dhaval");
		gamePlayController.setCurrentPlayer(player);
		gamePlayController.getCurrentPlayer().setCurrentPhase("attack");
		List<Country> sortedListOfCountriesByArmy = playerController.getSortedListOfCountriesByArmy(player);
		int index = sortedListOfCountriesByArmy.size() - 1;
		Country weakestCountry = sortedListOfCountriesByArmy.get(index);
		BenevolentStrategy.attack(player);
		int actualArmies = weakestCountry.getNumberofArmies();
		assertEquals(1, actualArmies);

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
