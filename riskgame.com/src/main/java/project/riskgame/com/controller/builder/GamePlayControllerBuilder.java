package project.riskgame.com.controller.builder;

import java.io.IOException;

import project.riskgame.com.controller.GamePlayController;

/**
 * Builder class implementing the abstact mehtods for loading and saving a game.
 * 
 * @author Devdutt	
 * @version 1.3
 *
 */

public abstract class GamePlayControllerBuilder {

	/**
	 * Instance of GamePlayController class.
	 */
	private GamePlayController gamePlayController;

	/**
	 * Get method of GamePlayController class.
	 * @return object of GamePlayController class.
	 */
	public GamePlayController getGamePlayController() {
		return gamePlayController;
	}

	
	/**
	 * Setter method of GamePlayController class.
	 * @param gamePlayController:GamePlayController instance 
	 */
	public void setGamePlayController(GamePlayController gamePlayController) {
		this.gamePlayController = gamePlayController;
	}

	/**
	 * Abstract method for saving an already loaded map.
	 *@throws Exception while implementation.
	 */
	abstract public void saveLoadMapFileReader() throws Exception;
	/**
	 *Abstract method for saving an already loaded map and its player information and its phase.
	 *@throws Exception while implementation.
	 */
	abstract public void saveLoadPlayerController() throws Exception;
	/**
	 *Abstract method for saving the start up phase state of current game.
	 *@throws Exception while implementation.
	 */
	abstract public void saveLoadStartupPhase() throws  Exception;
	/**
	 *Abstract method for loading the saved the current player
	 *@throws IOException while implementation.
	 */
	abstract public void saveLoadCurrentPlayer() throws IOException, Exception;
	/**
	 *Abstract method for saving the loaded cards of the game.
	 *@throws IOException while implementation.
	 */
	abstract public void saveLoadCards() throws IOException, Exception;
	/**
	 *Abstract method for saving the size odf the armies owned by the attacking country
	 *@throws IOException while implementation.
	 */
	abstract public void saveLoadAttackerCountrySize() throws IOException, Exception;
	/**
	 *Abstract method for saving the state of exchange mode.
	 *@throws IOException while implementation.
	 */
	abstract public void saveLoadIsExchangeOpen() throws IOException, Exception;
	/**
	 *Abstract method for setting the name of directory
	 *
	 *@param directoryName: name of the directory 
	 *@throws Exception while implementation.
	 */
	abstract public void setDirectoryName(String directoryName) throws Exception;
	/**
	 *Abstract method for printing the message to be displayed.
	 */
	abstract public void printMessage();

}
