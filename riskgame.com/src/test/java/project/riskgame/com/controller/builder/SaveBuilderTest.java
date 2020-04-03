package project.riskgame.com.controller.builder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.riskgame.com.controller.GamePlayController;

/**
 * This class tests all the methods of saving of game if a player decides to quit
 * the game in middle and wants to save the current status of game.
 * 
 * @author Dhaval
 * @author Shubham
 * @version 1.3
 *
 */
public class SaveBuilderTest {

	/**
	 * Instance of GamePlayDirector class
	 */
	private GamePlayDirector director;
	/**
	 * Instance of GamePlayControllerBuilder class
	 */
	private GamePlayControllerBuilder saveBuilder;
	/**
	 * Instance of GamePlayController class
	 */
	private GamePlayController gamePlayController;
	/**
	 * String for directory name
	 */
	private String directoryName;	
	/**
	 * directory to check
	 */
	private String directoryToCheck;
	/**
	 * Instance of file class
	 */
	private File directory;

	/**
	 * This method will be executed before every test case and will initialize the
	 * objects
	 * 
	 * @throws Exception while saving files
	 */
	@Before
	public void before() throws Exception {
		director = new GamePlayDirector();
		saveBuilder = new SaveBuilder();
		directoryName = "saveLoadGameTest";
		gamePlayController = new GamePlayController();
		saveBuilder.setGamePlayController(gamePlayController);
		directoryToCheck = "src/main/java/project/riskgame/com/resources/savedgames/" + directoryName;
		directory = new File(directoryToCheck);
	}

	/**
	 * This method checks the correctness of savegame command
	 * @throws Exception while saving the file
	 */
	@Test
	public void saveGameTest() throws Exception {
		saveBuilder.setDirectoryName(directoryName);
		director.setBuilder(saveBuilder);
		director.saveLoadGame();
		String[] listOfFiles = directory.list();
		assertEquals(7, listOfFiles.length);
	}

	/**
	 * performs cleanup activity
	 * @throws Exception  if cleanup activity is not performed correctly
	 */
	@After
	public void after() throws Exception {
		deleteDirectoryRecursion(directory);
	}

	/**
	 * This method recursively deletes file from directory
	 * @param file : file object
	 * @throws IOException if could not able to delete directory
	 */
	void deleteDirectoryRecursion(File file) throws IOException {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteDirectoryRecursion(entry);
				}
			}
		}

		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}
}
