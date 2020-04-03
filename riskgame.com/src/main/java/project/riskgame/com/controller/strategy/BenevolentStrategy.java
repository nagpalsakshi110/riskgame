package project.riskgame.com.controller.strategy;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;

/**
 * Class responsible for benevolent strategy
 * @author s_sawana
 * @version 1.3
 */
public class BenevolentStrategy implements Strategy {

	/**
	 * object of PlayerController
	 */
	private PlayerController playerController;
	
	/**
	 * object of GamePlayController
	 */
	private GamePlayController gamePlayController;
	
	/**
	 * object of MapFileReader
	 */
	private MapFileReader mapFileReader;
	
	
	/**
	 * constructor of BenevolentStrategy
	 * @param gamePlayController object of gamePlayController
	 */
	public BenevolentStrategy(GamePlayController gamePlayController) {
		
		 this.playerController = PlayerController.getInstance();
		 this.mapFileReader= MapFileReader.getInstance();
		 this.gamePlayController = gamePlayController;
	}
	
	public BenevolentStrategy() {
		
	}
	
	/**
	 * reinforce method for benevolent strategy
	 * @param player object of the player
	 */
	@Override
	public void reinforce(Player player) {
		
		List<Country> sortedListOfCountriesByArmy = playerController.getSortedListOfCountriesByArmy(player);
		
	
		
		int index = sortedListOfCountriesByArmy.size() -1;
		Country weakestCountry = sortedListOfCountriesByArmy.get(index);
		
		int calculateReinforcementArmies = playerController.calculateReinforcementArmies(player);
		executePlayCommandList("reinforce "+weakestCountry.getName()+" "+calculateReinforcementArmies);
	
	}

	/**
	 * This method call executePlaycommandList
	 * @param command command to be executed
	 */
	private void executePlayCommandList(String command) {
		ArrayList<String> playCommandList = new ArrayList<>();
		playCommandList.add(command);
		gamePlayController.executePlaycommandList(playCommandList);
	}
	
	/**
	 * attack method for benevolent strategy
	 * @param player object of the player
	 */
	@Override
	public void attack(Player player) {
		
		StringBuilder command = new StringBuilder();
		command.append("attack");
		command.append(" ");
		command.append("-noattack");

		executePlayCommandList(command.toString());
		
	}

	/**
	 * fortify method for benevolent strategy
	 * @param player object of the player
	 */
	@Override
	public void fortify(Player player) {
		
		Country neighbor = null;
		Country toCountry = null;

		List<Country> listOfSortedCountries = playerController.getSortedListOfCountriesByArmy(player);
		
		Collections.reverse(listOfSortedCountries);
		
		//int index = listOfSortedCountries.size() -1;
		//Country weakestCountry = listOfSortedCountries.get(index);
		String command = null;
		
		while (neighbor == null) {
			
				for(Country weakestCountry : listOfSortedCountries) {
					for (String countryID : weakestCountry.getAdjacentCountries()) {
						if (player.getCountriesOwned().contains(countryID)) {
							neighbor = mapFileReader.getCountryByID(countryID);
							toCountry = weakestCountry;
							break;
						}

					}
					
					if(neighbor !=null ) {
						break;
					}
				}
				
			
			break;
		}
		
		if(neighbor == null) {
			command = "fortify none";
			executePlayCommandList(command);
			return;
		}
		
		int numberOfFortificationArmies = neighbor.getNumberofArmies()-1;
		
		if(neighbor!=null) {
			command= "fortify "+neighbor.getName()+" "+toCountry.getName()+" "+numberOfFortificationArmies;
		}
		
		if(neighbor.getNumberofArmies()<=1) {
			command = "fortify none";
		}
		
		
		executePlayCommandList(command);
	}
	
	/**
	 * this method gets the strategy name
	 * @return strategy name
	 */
	@Override
	public String getStrategyName() {
		return "benevolent";

	}

	
	

}
