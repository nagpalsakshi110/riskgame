package project.riskgame.com.controller;

import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.mapeditor.MapValidator;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;


/**
 * This class Validates the gameplay phase. 
 * 
 * @author shubham
 * @author Kshitij
 * @version 1.0
 *
 */
public class GamePlayValidator {

	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * Instance of PlayerController class.
	 */
	private static PlayerController playerController = PlayerController.getInstance();
	
	/**
	 * This method checks whether the player is exists or not in a playerlist
	 * 
	 * @param playerName : name of the player
	 * @return true if player exists else return false
	 */
	public static boolean isPlayerExists(String playerName) {
		Player player = playerController.getPlayerByName(playerName);
		if (player != null) {
			return true;
		}

		return false;
	}

	/**
	 * This method will check whether player has remaining reinforcement armies
	 * 
	 * @param player player whose reinforcement armies are to be checked
	 * @return if exists return true
	 */
	public static boolean hasReinforcementArmies(Player player) {
		if (player.getReinforcementArmies() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method checks if the player has the country or not
	 * 
	 * @param player : Player
	 * @param countryName : countryName
	 * @return true if the country belongs to the player; else returns false
	 */
	public static boolean playerHasCountry(Player player, String countryName) {

		Country country = mapFileReader.getCountryByName(countryName);
		if (country == null) {
			return false;
		}
		return player.getCountriesOwned().contains(country.getCountryID());
	}
	
	
	/**
	 * This method checks whether the attack is valid or not
	 * @param player : player who wants to attack
	 * @param fromCountryName : attacker country
	 * @param toCountryName : defender country
	 * @param numdice : number of dices
	 * @return true if attack is valid else return false
	 */
	public static boolean isAttackValid(Player player, String fromCountryName, String toCountryName ,int numdice) {
		
		if(!MapValidator.isCountryExists(fromCountryName)) {
			GamePlayController.setMessage(fromCountryName+" does not belong to map");
			return false;
		}
		
		if(!MapValidator.isCountryExists(toCountryName)) {
			GamePlayController.setMessage(toCountryName+" does not belong to map");
			return false;
		}
		
		if(!playerHasCountry(player, fromCountryName)) {
			GamePlayController.setMessage(fromCountryName+" does not belong to "+player.getName());
			return false;
		}
		
		if(playerHasCountry(player, toCountryName)) {
			GamePlayController.setMessage("cannot attack your own country");
			return false;
		}
		
		if(!MapValidator.isNeighbour(fromCountryName, toCountryName)) {
			GamePlayController.setMessage("Country "+fromCountryName+" and "+toCountryName+" are not neighbors");
			return false;
		}
		
		
		if(!isAttackDiceValid(fromCountryName , numdice)) {
			GamePlayController.setMessage("Invalid numdice entered");
			return false;
		}
		
		if(isAttackMovePossible(player)) {
			GamePlayController.setMessage("You have to move armies to the conquered country first.");
			return false;
		}
		
		return true;
		
	}
	
	
	public static boolean isAttackMovePossible(Player attacker) {
		for (String countryID : attacker.getCountriesOwned()) {
			Country playerCountry = mapFileReader.getCountryByID(countryID);
			if(playerCountry.getNumberofArmies()==0) {
				
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * This method checks if the diceAttack is valid. 
	 * 
	 * @param player
	 * @param country 
	 * @param numdice
	 * @return true if the dice attack is valid ; else returns false
	 */
	private static boolean isAttackDiceValid(String countryName, int numdice) {
		
		Country fromCountry = mapFileReader.getCountryByName(countryName);
		if (numdice <= 3) {
			if (fromCountry.getNumberofArmies() > numdice) {
				return true;
			}
		}
		return false;

	}
	
	public static boolean isDefendValid(int defendingarmies,int defenderNumDice) {
		
		if (defenderNumDice > defendingarmies) {
			GamePlayController.setMessage("Defending Value should be less than number of armies on the country");
			return false;
		}
		
		if (defenderNumDice > 2) {
			GamePlayController.setMessage("Defending Value entered is not valid");
			return false;
		}

		if (defenderNumDice <= 0) {
			GamePlayController.setMessage("Defending Value should be positive");
			return false;
		}

		return true;
	}
	
}
