package project.riskgame.com.controller.builder;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Cards;

/**
 * Class responsible for implementing the load operation on the file saved by a player.
 * 
 * @author Shubham
 * @author Kshitij
 * @version 1.3
 *
 */
public class LoadBuilder extends GamePlayControllerBuilder{

	/**
	 * Final variable to set the path of the files.
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/savedgames/";
	/**
	 * Instance of ObjectMapper.
	 */
	private ObjectMapper mapper;
	/**
	 * Name of the directory.
	 */
	private String dirName ;
	
	/**
	 * constructor of LoadBuilder class.
	 */
	public LoadBuilder() {
		mapper = new ObjectMapper();
	}
	
	
	/**
	 * method for loading an already saved map.
	 * 
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadMapFileReader() throws Exception {
		
		MapFileReader mapFileReader = mapper.readValue(new File(dirName+"/MapFileReader.json"), MapFileReader.class);
		getGamePlayController().setMapFileReader(mapFileReader);
		MapFileReader.setInstance(mapFileReader);
		
	}


	/**
	 * method for loading an already saved map and its player information and its phase.
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadPlayerController() throws Exception {
		
		PlayerController playerController = mapper.readValue(new File(dirName+"/PlayerController.json"), PlayerController.class);
	    PlayerController.setInstance(playerController);
		getGamePlayController().setPlayerController(playerController);
		getGamePlayController().setPlayerList(playerController.getPlayerList());
		
	}

	/**
	 *method for loading the start up phase state of current game.
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadStartupPhase() throws Exception {
		
		StartupPhase startupPhase = mapper.readValue(new File(dirName+"/StartupPhase.json"), StartupPhase.class);
		getGamePlayController().setStartupPhase(startupPhase);
	}

	/**
	 *method for loading the saved the current player
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadCurrentPlayer() throws Exception {
		getGamePlayController().setCurrentPlayer(getGamePlayController().getPlayerList().get(0));
	}

	/**
	 * method for loading the saved cards of the game.
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadCards() throws Exception {
	
		Cards cards = mapper.readValue(new File(dirName+"/LoadCards.json"), Cards.class);
		getGamePlayController().setCards(cards);
		Cards.setInstance(cards);
	}

	/**
	 * method for loading the size of the armies owned by the attacking country
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadAttackerCountrySize() throws Exception {
	
		int attackerCountrySize = mapper.readValue(new File(dirName+"/AttackerCountrySize.json"), Integer.class);
		getGamePlayController().setAttackerCountrySize(attackerCountrySize);
		
	}

	/**
	 * method for loading the state of exchange mode.
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadIsExchangeOpen() throws Exception {
	
		boolean isExchangeOpen = mapper.readValue(new File(dirName+"/IsExchangeOpen.json"), Boolean.class);
		getGamePlayController().setExchangeOpen(isExchangeOpen);
	}

	/**
	 *method for setting the name of directory
	 *
	 *@param directoryName: name of the directory 
	 */
	@Override
	public void setDirectoryName(String directoryName) {
		dirName = CLASSPATH + directoryName;
	}


	/**
	 * method for displaying that the file was loaded successfully.
	 */
	@Override
	public void printMessage() {
		System.out.println("successfully loaded the game");
	}


	
}
