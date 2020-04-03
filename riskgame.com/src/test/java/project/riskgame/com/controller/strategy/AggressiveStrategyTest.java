package project.riskgame.com.controller.strategy;

import static org.junit.Assert.assertEquals;

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
 * This test class for AggressiveStrategy
 * @author Dhaval
 * @version 1.3
 *
 */
public class AggressiveStrategyTest {

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
	 * Instance of AggressiveStrategy class
	 */
	private Strategy AggressiveStrategy;

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
		AggressiveStrategy = new AggressiveStrategy(gamePlayController);
		startupPhase.addPlayer("Dhaval", AggressiveStrategy);
		startupPhase.addPlayer("Devdutt", AggressiveStrategy);
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
		Country strongestCountry = playerController.getStrongestCountry(player);
		int expectedReinforcementArmies = playerController.calculateReinforcementArmies(player)
				+ strongestCountry.getNumberofArmies();
		AggressiveStrategy.reinforce(player);
		int actualReinforcementArmies = strongestCountry.getNumberofArmies();
		assertEquals(expectedReinforcementArmies, actualReinforcementArmies);

	}

	/**
	 * This method will be executed after every test case and will clear the
	 * playerList and mapfileReader object
	 */
	@After
	public void after() {
		mapFileReader.clearAll();
		playerController.getPlayerList().clear();

	}
}
