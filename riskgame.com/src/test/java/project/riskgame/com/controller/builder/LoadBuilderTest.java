package project.riskgame.com.controller.builder;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.GamePlayController;

/**
 * This class validates the methods for loading the previously saved game.
 * 
 * @author Kshitij
 * @author Devdutt
 * @version 1.3
 *
 */
public class LoadBuilderTest {

	/**
	 * Instance of GamePlayDirector class
	 */
	private GamePlayDirector director;
	/**
	 * Instance of GamePlayControllerBuilder class
	 */
	private GamePlayControllerBuilder loadBuilder;
	/**
	 * Instance of GamePlayController class
	 */
	private GamePlayController gamePlayController;
	/**
	 * String for directory name
	 */
	private String directoryName;

	/**
	 * This method will be executed before every test case and will initialize the
	 * objects
	 * 
	 * @throws Exception while reading map files
	 */
	@Before
	public void before() throws Exception {
		director = new GamePlayDirector();
		loadBuilder = new LoadBuilder();
		directoryName = "risk";
		gamePlayController = new GamePlayController();
		loadBuilder.setGamePlayController(gamePlayController);
	}

	/**
	 * This method checks the correctness of gameload command
	 * @throws Exception while loading the map
	 */
	@Test
	public void LoadGameTest() throws Exception {
		loadBuilder.setDirectoryName(directoryName);
		director.setBuilder(loadBuilder);
		director.saveLoadGame();
		assertNotNull(gamePlayController.getMapFileReader());
	}

}
