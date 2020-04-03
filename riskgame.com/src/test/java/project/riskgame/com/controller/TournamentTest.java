package project.riskgame.com.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.view.PhaseViewFrame;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This is the testclass for tournament
 * 
 * @author Dhaval
 * @version 1.0
 */
public class TournamentTest {

	/**
	 * Instance of PlayerController class.
	 */
	private static PlayerController playerController;
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
		gamePlayController = new GamePlayController();
		worldDominationFrame = new PlayerWorldDominationFrame();
		worldDominationFrame = new PlayerWorldDominationFrame();
		playersWorldDominationView = PlayersWorldDominationView.getInstance();
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		phaseView = PhaseView.getInstance();
		phaseViewFrame = new PhaseViewFrame();
		phaseView.setPhaseViewFrame(phaseViewFrame);
		gamePlayController.setPhaseViewFrame(phaseViewFrame);
		gamePlayController.setTournamentMode(true);

	}

	/**
	 * This method test the tournament result between cheater and random player
	 */
	@Test
	public void tournamentTestForRandom() {
		ArrayList<String> listOfMapFiles = new ArrayList<>();
		ArrayList<String> listOfStrategies = new ArrayList<>();
		listOfMapFiles.add("risk.map");
		listOfStrategies.add("cheater");
		listOfStrategies.add("random");
		gamePlayController.startTournament(listOfMapFiles, listOfStrategies, 2, 5);
		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();
		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {
			ArrayList<String> gameResult = entry.getValue();
			assertEquals(2, gameResult.size());
		}
	}

	/**
	 * This method test the tournament result between cheater and benevolent player
	 */
	@Test
	public void tournamentTestForCheater() {
		ArrayList<String> listOfMapFiles = new ArrayList<>();
		ArrayList<String> listOfStrategies = new ArrayList<>();
		listOfMapFiles.add("risk.map");
		listOfStrategies.add("cheater");
		listOfStrategies.add("benevolent");
		gamePlayController.startTournament(listOfMapFiles, listOfStrategies, 1, 5);
		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();
		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {
			ArrayList<String> gameResult = entry.getValue();
			assertEquals(1, gameResult.size());
		}
	}

	/**
	 * This method test the tournament result between aggressive and benevolent
	 * player
	 */
	@Test
	public void tournamentTestForAggressive() {
		ArrayList<String> listOfMapFiles = new ArrayList<>();
		ArrayList<String> listOfStrategies = new ArrayList<>();
		listOfMapFiles.add("risk.map");
		listOfStrategies.add("aggressive");
		listOfStrategies.add("benevolent");

		gamePlayController.startTournament(listOfMapFiles, listOfStrategies, 2, 5);
		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();
		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {
			ArrayList<String> gameResult = entry.getValue();
			assertEquals(2, gameResult.size());
		}
	}

	/**
	 * This method test the tournament result between two benevolent players
	 */
	@Test
	public void tournamentTestForDrawBenevolent() {
		ArrayList<String> listOfMapFiles = new ArrayList<>();
		ArrayList<String> listOfStrategies = new ArrayList<>();
		listOfMapFiles.add("risk.map");
		listOfStrategies.add("benevolent");
		listOfStrategies.add("benevolent");
		gamePlayController.startTournament(listOfMapFiles, listOfStrategies, 1, 1);
		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();
		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {
			ArrayList<String> gameResult = entry.getValue();
			assertEquals("Draw", gameResult.get(0));
		}
	}

	/**
	 * This method test the tournament result between two random players
	 */
	@Test
	public void tournamentTestForDrawRandom() {
		ArrayList<String> listOfMapFiles = new ArrayList<>();
		ArrayList<String> listOfStrategies = new ArrayList<>();
		listOfMapFiles.add("risk.map");
		listOfStrategies.add("random");
		listOfStrategies.add("random");
		gamePlayController.startTournament(listOfMapFiles, listOfStrategies, 1, 1);
		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();
		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {
			ArrayList<String> gameResult = entry.getValue();
			assertEquals("Draw", gameResult.get(0));
		}
	}

	/**
	 * This method clears the result after each tournament
	 */
	@After
	public void after() {

		playerController.getTournamentResult().clear();
		gamePlayController.setTournamentMode(false);

	}

}
