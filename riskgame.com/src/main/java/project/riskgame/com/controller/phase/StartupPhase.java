package project.riskgame.com.controller.phase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.controller.RandomGenerator;
import project.riskgame.com.controller.strategy.Strategy;
import project.riskgame.com.mapeditor.DominationToConquestAdapter;
import project.riskgame.com.mapeditor.IMapFileReader;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.phase.PlayersWorldDominationView;


/**
 * Class responsible for the startup phase. 
 * 
 * @author Shubham
 * @author Devdutt
 * @author Dhaval
 * @version 1.1
 *
 */
public class StartupPhase {
	
	/**
	 * Final classpath of for location of games.
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/";
	/**
	 * Boolean variable to validate if the country is populated or not.
	 */
	private Boolean isCountryPopulated = false;
	/**
	 * Boolean variable to validate if the map is loaded or not.
	 */
	private Boolean isMapLoaded = false;
	/**
	 * Object of MapFileReader class using getInstance method.
	 */
	private MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * Object of PlayerController class using getInstance method.
	 */
	private PlayerController playerController = PlayerController.getInstance();
	/**
	 * Object of PlayersWorldDominationView class using getInstance method.
	 */
	private PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();
	
	
	/**
	 * getter for isCountryPopulated
	 * @return true if isCountryPopulated
	 */
	public boolean isCountryPopulated() {
		return isCountryPopulated;
	}


	/**
	 * setter for isCountryPopulated
	 * @param isCountryPopulated checks if the country is populated
	 */
	public void setCountryPopulated(boolean isCountryPopulated) {
		this.isCountryPopulated = isCountryPopulated;
		
	}


	/**
	 * getter for isMapLoaded
	 * @return true if isMapLoaded
	 */
	public boolean isMapLoaded() {
		return isMapLoaded;
	}


	/**
	 * setter for isMapLoaded
	 * @param isMapLoaded sets the isMapLoaded
	 */
	public void setMapLoaded(boolean isMapLoaded) {
		this.isMapLoaded = isMapLoaded;
	}
	
	
	/**
	 * This method will check if the mentioned map exists or not
	 * @param fileName : name of the map to be loaded
	 * @throws Exception on reading map file
	 */
	public void loadMap(String fileName) throws Exception  {

		String path = CLASSPATH + fileName;
		File file = new File(path);

		if (file.exists()) {
			
			if(fileName.endsWith(".map")){
				mapFileReader.readMapFile(fileName);
			}
			else {
				IMapFileReader dominationToConquestAdapter = new DominationToConquestAdapter();
				dominationToConquestAdapter.readMapFile(fileName);	
			}
			setMapLoaded(true);
		} else {
			System.out.println("Invalid File Name");
			GamePlayController.setMessage("Invalid File Name");
		}

	}
	
	
	
	/**
	 * This method assign countries randomly to the players
	 */
	public void populateCountries() {
		
		System.out.println("Started populating countries");
		
		
		List<Country> localCountryList = new ArrayList<Country>(mapFileReader.getCountryList());
		
		while(!localCountryList.isEmpty()) {
			
			for (Player player : playerController.getPlayerList()) {
				
				if(!localCountryList.isEmpty()) {
					Country randomCountry = RandomGenerator.getRandomObject(localCountryList);
					player.getCountriesOwned().add(randomCountry.getCountryID());
					localCountryList.remove(randomCountry);
				}
				
				
			}
		}
		
		/* set total army owned and assign 1 army by default */
		setTotalArmiesForEachPlayerAndAssignInitalArmy(playerController.getPlayerList());
		
		setCountryPopulated(true);
		
		setContinentsOwnedByPlayer();
		
		
		/* Displaying the updates in PWDV frame */
		playersWorldDominationView.update(null, null);

		System.out.println("Successfully populated countries");
		GamePlayController.setMessage("Successfully populated countries");
	}
	
	
	
	/**
	 * This method prepare set for continent ownership for each player
	 */
	public void setContinentsOwnedByPlayer() {
		
		for (Player player : playerController.getPlayerList()) {
			
			Set<String> continentList = playerController.getControlledContinent(player);
			player.setContinentOwned(continentList);		
			
		}
			
	}


	
	/**
	 *  This method will add player to game
	 * @param playerName: name of player
	 * @param strategy Strategy object
	 */
	public void addPlayer(String playerName,Strategy strategy) {

		if (playerController.getPlayerList().size() == mapFileReader.getCountryList().size()) {
			GamePlayController.setMessage("number of players can't be more than number of countries");
			System.out.println("number of players can't be more than number of countries");
		} else {
			Player player = new Player();
			String maxplayerId;
			if (playerController.getPlayerList().isEmpty()) {
				maxplayerId = "1";
			} else {
				Player maxPlayer = Collections.max(playerController.getPlayerList());
				maxplayerId = String.valueOf(Integer.parseInt(maxPlayer.getPlayerId()) + 1);
			}
			player.setPlayerId(maxplayerId);
			player.setName(playerName);
			player.setStrategy(strategy);
			
			playerController.getPlayerList().add(player);

			System.out.println("Player "+playerName+" added successfully");
			GamePlayController.setMessage("Player(s) added successfully");
			
			
		}
	}
	

	/**
	 * This method will remove player from the game
	 * 
	 * @param playerName : name of player
	 */
	public void removePlayer(String playerName) {

		Player player = playerController.getPlayerByName(playerName);
		playerController.getPlayerList().remove(player);
		System.out.println("Player "+playerName+" removed successfully");
		GamePlayController.setMessage("Player(s) removed successfully");
	}

	
	
	/**
	 * This method automatically place all remaining unplaced armies for all players randomly
	 */
	public void placeAll(){
		
		System.out.println("started assigning remaining armies for all players");
		
		for (Player player : playerController.getPlayerList()) {
			
			
			
			while(player.getTotalArmiesOwned() - playerController.getAssignedArmies(player) > 0) {

				String randomCountryID = RandomGenerator.getRandomObject(player.getCountriesOwned());
				Country randomCountry = mapFileReader.getCountryByID(randomCountryID);
				int newArmyCount = randomCountry.getNumberofArmies() + 1;
				randomCountry.setNumberofArmies(newArmyCount);
				
			}
			
		}
		
		System.out.println("successfully placed remaining armies");
		GamePlayController.setMessage("successfully placed remaining armies\n" + "player " + playerController.getPlayerList().get(0).getName() + " is in now reinforcement phase");
		System.out.println("player " + playerController.getPlayerList().get(0).getName() + " is in now reinforcement phase");
		
		
		
		/* set the current phase of a player to reinforce if all the armies of all player are placed */
		playerController.getCurrentPlayer().setCurrentPhase("reinforce");
		

	}
	
	
	/**
	 * This method will place armies in the country mentioned by the user
	 * @param player : Player who will place the army
	 * @param countryName : country where armies will be placed
	 */
	public void placeArmies(Player player, String countryName) {
		int flag = 0;
		int count = 0;

		for (String string : player.getCountriesOwned()) {
			
			Country country = mapFileReader.getCountryByID(string);
			
			if (country.getName().equals(countryName)) {
				count = country.getNumberofArmies();
				count = count + 1;
				country.setNumberofArmies(count);
				flag = 1;
			}
			
		}

		if (flag == 1) {
			
			System.out.println("Player "+player.getName()+" has succesfully assigned one army to "+countryName);
			GamePlayController.setMessage("Player "+player.getName()+" has succesfully assigned one army to "+countryName);
			
			if(playerController.getCurrentPlayer().getTotalArmiesOwned() - playerController.getAssignedArmies(playerController.getCurrentPlayer()) == 0) {
				/* set the current phase of a player to initial if all the armies player are placed */
				
				playerController.getCurrentPlayer().setCurrentPhase("reinforce");
			}
			
		}else {
			System.out.println(countryName+" doesnt belong to player "+ player.getName());
			GamePlayController.setMessage(countryName+" doesnt belong to player "+ player.getName());
			
			/* if country name is Invalid then get the original turn back to player */
			Collections.rotate(playerController.getPlayerList(), 1);
		}
			

	}
	
	
	/**
	 * This method sets armies for the players and assign initial one army in each country
	 * @param playerList player list to which armies are to be assigned
	 */
	private void setTotalArmiesForEachPlayerAndAssignInitalArmy(List<Player> playerList) {
		
		int totalArmies = getTotalArmies(playerList.size());
		
		for (Player player : playerList) {
			
			player.setTotalArmiesOwned(totalArmies);
			
			for (String countryID : player.getCountriesOwned()) {
				
				Country country = mapFileReader.getCountryByID(countryID);
				country.setNumberofArmies(country.getNumberofArmies()+1);
				
			}
			
		}
	}
	
	/**
	 * This method gets initial armies depending on the number of players
	 * 
	 * @param size number of players
	 * @return number of armies
	 */
	private int getTotalArmies(int size) {

		int count = 0;
		
		if(size == 2) {
			count = 40;
		}else if(size == 3) {
			count = 35;
		}else if(size == 4) {
			count = 30;
		}else if(size == 5){
			count = 25;
		}else if(size == 6) {
			count = 20;
		}else if(size == 7) {
			count = 15;
		}else if(size == 8) {
			count = 10;
		}else if(size == 9) {
			count = 5;
		}
		
		return count;
	}


}
