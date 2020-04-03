package project.riskgame.com.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import project.riskgame.com.controller.strategy.Strategy;

/**
 * model information about player
 * 
 * @author Shubham
 * @version 1.0
 */
public class Player extends Observable implements Comparable<Player> {

	/**
	 * variable to store playerId
	 */
	private String playerId;
	/**
	 * variable to store name
	 */
	private String name;
	/**
	 *  list to store countriesOwned
	 */
	private List<String> countriesOwned = new ArrayList<>();
	/**
	 *  Hashset to store continentOwned
	 */
	private Set<String> continentOwned = new HashSet<>();
	/**
	 * variable to store totalArmiesOwned
	 */
	private int totalArmiesOwned;
	/**
	 * variable to store reinforcementArmies
	 */
	private int reinforcementArmies;
	/**
	 *  variable to store currentPhase
	 */
	private String currentPhase;
	/**
	 * variable to store exchangeCards.
	 */
	private int exchangeCards;
	/**
	 * List of cards.
	 */
	private List<String> cards = new ArrayList<>();
	/**
	 * instance of Strategy class.
	 */
	private Strategy strategy;
	
	
	/**
	 * Setter for Strategy class.
	 * @param strategy: strategy object
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	
	/**
	 * getter for strategy
	 * @return strategy object
	 */
	public Strategy getStrategy() {
		return strategy;
	}



	/**
	 * getter for ExchangeCards.
	 * 
	 * @return exchangeCards.
	 */
	public int getExchangeCards() {
		return exchangeCards;
	}

	
	/**
	 * setter for exchangeCards
	 * @param exchangeCards : exchangeCards
	 */
	public void setExchangeCards(int exchangeCards) {
		this.exchangeCards = exchangeCards;
	}


	/**
	 * getter for list of cards.
	 * 
	 * @return list of cards.
	 */
	public List<String> getCards() {
		return cards;
	}

	
	/**
	 * setter for list of cards
	 * @param cards : cards
	 */
	public void setCards(List<String> cards) {
		this.cards = cards;
		setChanged();
		notifyObservers();
	}

	/**
	 * this method gets the current phase of the player "initial" or "reinforce" of "fortify"
	 * @return currentPhase 
	 */
	public String getCurrentPhase() {
		return currentPhase;
	}

	/**
	 * this method sets the current phase of the player "initial" or "reinforce" of "fortify"
	 * @param currentPhase : current phase of the player
	 */
	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
		setChanged();
		notifyObservers();
	}

	/**
	 * this method gets the no. of reinforcements the player has
	 * @return no. of reinforcement armies
	 */
	public int getReinforcementArmies() {
		return reinforcementArmies;
	}

	/**
	 *  this method sets the no. of reinforcements the player has
	 * @param reinforcementArmies : number of reinforcements
	 */
	public void setReinforcementArmies(int reinforcementArmies) {
		this.reinforcementArmies = reinforcementArmies;
		setChanged();
		notifyObservers();
	}

	/**
	 * this method gets the player's ID
	 * @return player's ID
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * this method sets the player's ID
	 * @param playerId : player's ID
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * this method gets the player name
	 * @return name of the player object
	 */
	public String getName() {
		return name;
	}

	/**
	 * this method sets the player name
	 * @param name: name for the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * this gets the list of the countries owned by the player
	 * @return list of countries owned by the player
	 */
	public List<String> getCountriesOwned() {
		return countriesOwned;
	}

	/**
	 * this sets the list of the countries owned by the player
	 * @param countriesOwned : list of countries owned by the player 
	 */
	public void setCountriesOwned(List<String> countriesOwned) {
		this.countriesOwned = countriesOwned;
		setChanged();
		notifyObservers();
	}

	/**
	 * this method gets the total no. of armies owned by the player
	 * @return total Armies Owned
	 */
	public int getTotalArmiesOwned() {
		return totalArmiesOwned;
	}

	/**
	 * this method sets the total no. of armies owned by the player
	 * @param totalArmiesOwned total number of armies owned by player
	 */
	public void setTotalArmiesOwned(int totalArmiesOwned) {
		this.totalArmiesOwned = totalArmiesOwned;
		setChanged();
		notifyObservers();
	}

	/**
	 *This method compares player objects by playerID
	 *@param object : player object
	 *@return : playerID
	 */
	@Override
	public int compareTo(Player object) {
		Integer player = Integer.parseInt(this.playerId);
		Integer id = Integer.parseInt(object.getPlayerId());
		return player.compareTo(id);
	}

	/**
	 *This method display player object
	 *@return String that contains information about player object
	 */
	@Override
	public String toString() {
		return "\nPlayer [playerId=" + playerId + ", name=" + name + ", countriesOwned=" + countriesOwned
				+ ",continentOwned =" + continentOwned 
				+ ", totalArmiesOwned=" + totalArmiesOwned + ", cards=" + cards + "]\n";
	}

	/**
	 * This method returns player continentOwned list
	 * @return continentOwned : player continentOwned list
	 */
	public Set<String> getContinentOwned() {
		return continentOwned;
	}

	/**
	 * This method sets player continentOwned list
	 * @param continentOwned : player continentOwned list
	 */
	public void setContinentOwned(Set<String> continentOwned) {
		this.continentOwned = continentOwned;
		setChanged();
		notifyObservers();
	}

	
	
}
