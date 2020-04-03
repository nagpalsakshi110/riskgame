package project.riskgame.com.view.phase;

import java.util.Observable;
import java.util.Observer;

import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.PlayerWorldDominationFrame;

/**
 * Singleton class which will be responsible for displaying the Domination of
 * player in the map.
 * 
 * @author Sakshi
 * @version 1.1
 *
 */


public class PlayersWorldDominationView implements Observer {

	/**
	 * String type variable named message.
	 *
	 */
	private String message;
	/**
	 * Instance of PlayerWorldDominationFrame class.
	 *
	 */
	private PlayerWorldDominationFrame playerWorldDominationFrame;


	/**
	 * getter for the PlayerWorldDominationFrame.
	 *@return playerWorldDominationFrame
	 */
	public PlayerWorldDominationFrame getPlayerWorldDominationFrame() {
		return playerWorldDominationFrame;
	}

	
	/**
	 * setter for playerWorldDominationFrame
	 * @param playerWorldDominationFrame : playerWorldDominationFrame
	 */
	public void setPlayerWorldDominationFrame(PlayerWorldDominationFrame playerWorldDominationFrame) {
		this.playerWorldDominationFrame = playerWorldDominationFrame;
	}


	/**
	 * getter for the Message
	 *@return message
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * setter for message.
	 * @param message : message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	public static PlayersWorldDominationView playersWorldDominationView;

	/**
	 * constructor of PlayersWorldDominationView Class.
	 */
	private PlayersWorldDominationView() {
	}

	/**
	 * This method returns the instance of PlayersWorldDominationView class
	 * 
	 * @return instance of playersWorldDominationView
	 */
	public static PlayersWorldDominationView getInstance() {

		if (playersWorldDominationView == null) {
			playersWorldDominationView = new PlayersWorldDominationView();
		}

		return playersWorldDominationView;
	}


	/**
	 * This method updates the observer.
	 * 
	 * @param object: Observable
	 * @param arg: Object 
	 */
	@Override
	public void update(Observable object, Object arg) {
		
		/* clearing playerWorldDominationFrame  */
		playerWorldDominationFrame.getTextArea().setText("");
		
		PlayerController playerController= PlayerController.getInstance();
		
		for (Player player : playerController.getPlayerList()) {
			double mapControlledPercentage = playerController.calculateMapControlledPercentage(player);
			int armiesOwned = player.getTotalArmiesOwned();
			playerWorldDominationFrame.getTextArea().append(
					player.getName() + " - " + mapControlledPercentage + "%  & " + armiesOwned + " armies" + "\n");
		}
		
		for (Player player : playerController.getPlayerList()) {
			if(player.getContinentOwned().size()>0) {
				playerWorldDominationFrame.getTextArea().append(
						player.getName() + " Continent(s) owned- "+player.getContinentOwned()+ "\n");
			}
		}
		

	}

	
}
