package project.riskgame.com.controller.builder;

import project.riskgame.com.controller.GamePlayController;


/**
 * The director class for constructing object of the  GamePlayControllerBuilder.
 *  
 * @author Devdutt
 * @version  1.3
 *
 */

public class GamePlayDirector {

	/**
	 * Instance of GamePlayControllerBuilder class.
	 */
	private GamePlayControllerBuilder builder;

	
	/**
	 * Setter method for builder.
	 * @param builder GamePlayControllerBuilder
	 */
	public void setBuilder(GamePlayControllerBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Method for saving the current game.
	 * 
	 * @throws Exception while saving the game
	 */
	public void saveLoadGame() throws Exception {
		
		
		flush();
		
		builder.saveLoadMapFileReader();
		builder.saveLoadPlayerController();
		builder.saveLoadStartupPhase();
		builder.saveLoadCurrentPlayer();
		builder.saveLoadCards();
		builder.saveLoadAttackerCountrySize();
		builder.saveLoadIsExchangeOpen();
		builder.printMessage();
		
	}

	/**
	 * Method for flushing the old information to load the current game information.
	 */
	private void flush() {
		
		if (builder instanceof LoadBuilder) {
			GamePlayController gamePlayController = getGamePlayController();
			gamePlayController.setMapFileReader(null);
			gamePlayController.setPlayerController(null);
			gamePlayController.setStartupPhase(null);
			gamePlayController.setCurrentPlayer(null);
			gamePlayController.setPlayerList(null);
			gamePlayController.setCards(null);
		}

	}


	/**
	 * Get method for builder.
	 * 
	 * @return GamePlayController object.
	 */
	public GamePlayController getGamePlayController() {
		return builder.getGamePlayController();
	}

}
