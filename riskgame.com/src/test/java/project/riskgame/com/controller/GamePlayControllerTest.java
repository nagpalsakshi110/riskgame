package project.riskgame.com.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.controller.strategy.AggressiveStrategy;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.CardExchangeViewFrame;
import project.riskgame.com.view.PhaseViewFrame;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.CardExchangeView;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This is the testclass for GamePlayController
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.1
 *
 */
public class GamePlayControllerTest {

	/**
	 * instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader;
	/**
	 * instance of StartupPhase class.
	 */
	private StartupPhase startupPhase;
	/**
	 * instance of PlayerWorldDominationFrame class.
	 */
	private PlayerWorldDominationFrame worldDominationFrame;
	/**
	 * instance of PlayerController class.
	 */
	private static PlayerController playerController;
	/**
	 * instance of GamePlayController class.
	 */
	private GamePlayController gamePlayController;
	/**
	 * instance of PlayersWorldDominationView class.
	 */
	private PlayersWorldDominationView playersWorldDominationView;
	/**
	 * instance of PhaseView class.
	 */
	private PhaseView phaseView;
	/**
	 * instance of PhaseViewFrame frame.
	 */
	private PhaseViewFrame phaseViewFrame;
	/**
	 * instance of CardExchangeView class.
	 */
	private CardExchangeView cardexChangeView;
	/**
	 * instance of CardExchangeViewFrame frame.
	 */
	private CardExchangeViewFrame cardexChangeViewFrame;

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
		phaseViewFrame = new PhaseViewFrame();
		cardexChangeViewFrame = new CardExchangeViewFrame();
		gamePlayController = new GamePlayController();
		startupPhase.addPlayer("Dhaval", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Devdutt", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Shubham", new AggressiveStrategy(gamePlayController));
		startupPhase.addPlayer("Sakshi", new AggressiveStrategy(gamePlayController));
		playersWorldDominationView = PlayersWorldDominationView.getInstance();
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		phaseView = PhaseView.getInstance();
		phaseView.setPhaseViewFrame(phaseViewFrame);
		cardexChangeView = CardExchangeView.getInstance();
		cardexChangeView.setCardExchangeViewFrame(cardexChangeViewFrame);
		startupPhase.populateCountries();
		playersWorldDominationView = PlayersWorldDominationView.getInstance();

	}

	/**
	 * This method verifies whether the reinforce armies received from exchange card
	 * is correct or not
	 */
	@Test
	public void calculateExchangeReiforceArmiesTest() {
		Player currentPlayer = playerController.getPlayerByName("Shubham");
		gamePlayController.setCurrentPlayer(currentPlayer);
		playersWorldDominationView.setPlayerWorldDominationFrame(worldDominationFrame);
		ArrayList<String> cards = new ArrayList<String>();
		cards.add("artillery");
		cards.add("artillery");
		cards.add("artillery");
		cards.add("cavalry");
		cards.add("infantry");
		currentPlayer.setCards(cards);
		int exchangeReiforceArmiese = gamePlayController.calculateExchangeArmies(currentPlayer, "1", "2", "3");
		assertEquals(5, exchangeReiforceArmiese);

	}

	/**
	 * This method will be executed after every test case and will clear the
	 * playerList and countrylist, continentlist.
	 */
	@After
	public void after() {
		mapFileReader.clearAll();
		playerController.getPlayerList().clear();

	}

}
