package project.riskgame.com.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.mapeditor.MapValidator;
import project.riskgame.com.model.Cards;
import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;


/**
 * This class gives information about the player and performs operations on each player
 *  
 * @author Devdutt
 * @author Dhaval 
 * @author Shubham
 * @author Kshitij
 * @author Sakshi
 * 
 * @version 1.0
 */
public class PlayerController {

	/**
	 * playerList of PlayerController
	 */
	private List<Player> playerList = new ArrayList<>();
	/**
	 * instance of MapFileReader class.
	 */
	private MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * variable for current player.
	 */
	private Player currentPlayer;
	/**
	 * instance of PlayerController class.
	 */
	private static PlayerController instance;
	/**
	 * instance of PhaseView class.
	 */
	private PhaseView phaseView = PhaseView.getInstance();
	/**
	 * list of defenders dice values.
	 */
	private List<Integer> attackerDiceValues;
	/**
	 * list of defenders dice values.
	 */
	private List<Integer> defenderDiceValues;
	/**
	 * variable attackingCountry.
	 */
	private Country attackingCountry;
	/**
	 * variable defendingCountry.
	 */
	private Country defendingCountry;
	/**
	 * instance of PlayersWorldDominationView class.
	 */
	private PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();
	/**
	 * HashMap to store the tournament results of a player.
	 */
	private HashMap<String, ArrayList<String>> tournamentResult = new LinkedHashMap<String,ArrayList<String>>();
	/**
	 * boolean variable winning condition to validate the condition.
	 */
	private boolean winningCondition= false;
	/**
	 * instance of Logger.
	 */
	private Logger logger = Logger.getLogger("Log"); 
	
	/**
	 * this method will check if it is a winning condition or not.
	 * 
	 * @return boolean value true if condition is winning condition, false otherwise.
	 */
	public boolean isWinningCondition() {
		return winningCondition;
	}



	
	/**
	 * Setter for winning condition.
	 * @param winningCondition: true if winning condition, else false
	 */
	public void setWinningCondition(boolean winningCondition) {
		this.winningCondition = winningCondition;
	}




	/**
	 * constructor of PlayerController Class
	 */
	private PlayerController(){	
	}
	
	
	

	/**
	 * getter for tournament result
	 * @return map file containing of the tournament
	 */
	public HashMap<String, ArrayList<String>> getTournamentResult() {
		return tournamentResult;
	}




	
	/**
	 * setter for tournament result
	 * @param tournamentResult: result of tournament
	 */
	public void setTournamentResult( HashMap<String, ArrayList<String>> tournamentResult) {
		this.tournamentResult = tournamentResult;
	}




	/**
	 * This method returns the instance of MapFileReader class
	 * 
	 * @return instance of MapFileReader
	 */
	public static PlayerController getInstance() {
		
		if(instance == null){
            instance = new PlayerController();
        }
		
		return instance;
	}
	
	/**
	 * this method gets the current player object
	 * @return current player object
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}


	/**
	 * this method sets current player object
	 * @param currentPlayer: current player object
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	/**
	 * getter for player list
	 * 
	 * @return list of players
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}


	/**
	 * setter for player list
	 * @param playerList : list of player
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	

	/**
	 * getter for attacking Country
	 * @return attacking country
	 */
	public Country getAttackingCountry() {
		return attackingCountry;
	}


	/**
	 * setter for attacking country
	 * @param attackingCountry : attacking country object
	 */
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}


	/**
	 * getter for defending Country
	 * @return defending country
	 */
	public Country getDefendingCountry() {
		return defendingCountry;
	}


	/**
	 * setter for defending country
	 * @param defendingCountry : defending country object
	 */
	public void setDefendingCountry(Country defendingCountry) {
		this.defendingCountry = defendingCountry;
	}


	/**
	 * This method returns Player object from playerlist
	 * 
	 * @param playerName : name of player
	 * @return player object
	 */
	public Player getPlayerByName(String playerName) {

		for (Player player : playerList) {

			if (player.getName().equals(playerName)) {

				return player;
			}
		}
		return null;
	}

	/**
	 * This method will return the total assigned armies of the player
	 * @param player : Player object whose armies are to be calculated
	 * @return : Count of total armies
	 */
	public int getAssignedArmies(Player player) {
		int armies = 0;
		for (String countryID : player.getCountriesOwned()) {
			Country country = mapFileReader.getCountryByID(countryID);
			int count = country.getNumberofArmies();
			armies += count;
		}
		return armies;
	}

	
	/**
	 * This method will identify the player turn during game
	 */
	public void rotatePlayerList() {
		Collections.rotate(playerList, -1);
	}
	
	
	/**
	 * This method will calculate th control value of the continents owned by the player.
	 * 
	 * @param player: current player
	 * @return armies.
	 */
	public int calculateControlValue(Player player){
		
		int armies = 0;
		for (Continent continent : mapFileReader.getContinentList()) {

			if (player.getCountriesOwned().containsAll(continent.getListofCountires())) {
					armies = armies + continent.getControlValue();
			}	
		}
		return armies;	
	}
	
	
	 /**
	 * This method will set the continent list.
	 * 
	 * @param player: current player
	 * @return continentList.
	 */
	
public Set<String> getControlledContinent(Player player){
		
		Set<String> continentList = new HashSet<>();
	   
		for (Continent continent : mapFileReader.getContinentList()) {

			if (player.getCountriesOwned().containsAll(continent.getListofCountires())) {
					
				continentList.add(continent.getName());
			}	
		}
		return continentList ;	
	}
	
	
	/**
	 * This method will move armies between neighboring countries
	 * @param player player who wish to do fortification
	 * @param fromCountryName from country name where armies are to be moved
	 * @param toCountryName to country name where armies are to be added
	 * @param noOfArmies no of armies to be moved
	 * @return true if fortification is successful else return false
	 */
	public boolean fortificationOfArmies(Player player, String fromCountryName, String toCountryName, int noOfArmies) {
		
		boolean success = false;
		if (GamePlayValidator.playerHasCountry(player, fromCountryName) && GamePlayValidator.playerHasCountry(player, toCountryName)
				&& MapValidator.isPathExists(player,fromCountryName, toCountryName)) {

			Country fromCountry = mapFileReader.getCountryByName(fromCountryName);
			Country toCountry = mapFileReader.getCountryByName(toCountryName);

			if (fromCountry.getNumberofArmies() - noOfArmies >= 1 && noOfArmies >= 1) {
				
				int fromArmies = fromCountry.getNumberofArmies();
				int toArmies = toCountry.getNumberofArmies();
				toArmies = toArmies + noOfArmies;
				fromArmies = fromArmies - noOfArmies;
				
				fromCountry.addObserver(phaseView);
				phaseView.setMessage("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName+"\n" + currentPlayer.getName() + " fortification phase ended \n");
				//phaseView.setMessage(currentPlayer.getName() + " fortification phase ended \n");
				fromCountry.setNumberofArmies(fromArmies);
				//phaseView.setMessage(currentPlayer.getName() + " fortification phase ended \n");
				toCountry.setNumberofArmies(toArmies);
				fromCountry.deleteObserver(phaseView);
				
				success = true;
				
				System.out.println("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName);
				GamePlayController.setMessage("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName);
				
				
			} else {
				System.out.println("Country "+fromCountryName+" should have minimum one remaining army and "+noOfArmies+" should be positive");
				GamePlayController.setMessage("Country "+fromCountryName+" should have minimum one remaining army and "+noOfArmies+" should be positive");
				success = false;
			}
		} else {
			System.out.println("There don't exist path between "+ fromCountryName + " "+ toCountryName);
			GamePlayController.setMessage("There don't exist path between "+ fromCountryName + " "+ toCountryName);
			success = false;
		}
	
	return success;
	}
	
	

	
	/**
	 * This method will calculate reinforcement armies
	 * @param player player whose armies are to be calculated 
	 * @return no of armies
	 */
	public int calculateReinforcementArmies(Player player) {
		int size = player.getCountriesOwned().size();
		int reinforcementArmy = size / 3;

		if (size < 9) {
			reinforcementArmy = 3;
		}
		return reinforcementArmy;
	}
	
 	
	
	
 	/**
 	 * This method will assign reinforcement armies to the player
 	 * @param player to whom armies will be assigned
 	 * @param countryName country where armies will be placed
 	 * @param numberOfArmies number of armies to be assigned
 	 */
	public void assignReinforcementArmies(Player player, String countryName, int numberOfArmies) {

		if (GamePlayValidator.playerHasCountry(player, countryName)) {
			
			Country country = mapFileReader.getCountryByName(countryName);
			int count = country.getNumberofArmies();
			count = count + numberOfArmies;
			
			country.addObserver(phaseView);
			phaseView.setMessage("Successfully Assigned "+numberOfArmies+" reinforcement armies"+ " to "+countryName +"\n" +
					countryName + " has "+ count + " armie(s) after reinforcement \n");
			country.setNumberofArmies(count);
			country.deleteObserver(phaseView);
			
			System.out.println("Successfully Assigned "+numberOfArmies+" Reinforcement Armies"+ " to "+countryName);
			
			int remainingReinforcmentArmies = player.getReinforcementArmies();
			remainingReinforcmentArmies = remainingReinforcmentArmies - numberOfArmies;
			
			player.addObserver(phaseView);
			phaseView.setMessage("Player "+ player.getName() + " has: " + remainingReinforcmentArmies + " remaining reinforcement armies \n");
			player.setReinforcementArmies(remainingReinforcmentArmies);
			player.deleteObserver(phaseView);
			
			System.out.println("Player "+ player.getName() + " has: " + remainingReinforcmentArmies + " remaining reinforcement armies");
			GamePlayController.setMessage("Player "+ player.getName() + " has: " + remainingReinforcmentArmies + " remaining reinforcement armies");
			
		} else {
			System.out.println(countryName + " doesnt belong to player " + player.getName());
			GamePlayController.setMessage(countryName + " doesnt belong to player " + player.getName());
		}
	}

	/**
	 * This method returns the percentage of map controlled by the player
	 * @param player: player in the game
	 * @return percentage of map owned
	 */
	public double calculateMapControlledPercentage(Player player) {
		
		double numberOfCountriesOwned = player.getCountriesOwned().size();
		double totalNumberOfCountries = mapFileReader.getCountryList().size();
		double mapControlledPercentage = (numberOfCountriesOwned / totalNumberOfCountries) * 100;
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(mapControlledPercentage));

	}
	
	
	/**
	 * This method sets the instance of PlayerController Class after loading the game
	 * @param playerController: current player
	 */
	public static void setInstance(PlayerController playerController) {
		instance = playerController;
	}
	
	
	/**
	 * This method initializes the variables used for battle
	 * @param attacker : player who initiates attack
	 * @param fromCountryName: country from where the attack is initiated
	 * @param toCountryName: country under attack
	 * @param numDice: number of dice used by attacker
	 */
	public void initiateAttack(Player attacker, String fromCountryName, String toCountryName, int numDice) {
		
		attackingCountry = mapFileReader.getCountryByName(fromCountryName);
		defendingCountry = mapFileReader.getCountryByName(toCountryName);
		 
		
		attackerDiceValues = new ArrayList<Integer>();
		
		for (int i = 0; i < numDice; i++) {
			
			int randomDiceValue = RandomGenerator.getRandomNumber(6, true);
			attackerDiceValues.add(randomDiceValue);
			
		}
		
		attackerDiceValues.sort(Collections.reverseOrder());
		
		phaseView.getPhaseViewFrame().getTextArea().append(attacker.getName()+" has started attack from "+fromCountryName+" to "+toCountryName+" with "+numDice+" armies(s)\n");
		System.out.println(attacker.getName()+" has started attack from "+fromCountryName+" to "+toCountryName+" with "+numDice+" armies(s)");
	}
	
	
	
	/**
	 * This initializes the defender dice values
	 * @param numDice: number of dice used by defender
	 */
	public void defend(int numDice) {

		defenderDiceValues = new ArrayList<Integer>();
		
		for (int i = 0; i < numDice; i++) {
			int randomDiceValue = RandomGenerator.getRandomNumber(6, true);
			defenderDiceValues.add(randomDiceValue);
		}

		defenderDiceValues.sort(Collections.reverseOrder());

		Player defender =  getPlayerByCountryName(defendingCountry.getName());
				
		phaseView.getPhaseViewFrame().getTextArea().append(defender.getName()+" has started defending "+defendingCountry.getName()+" with "+numDice+" armie(s) \n");
		System.out.println(defender.getName()+" has started defending "+defendingCountry.getName()+" with "+numDice+" armie(s)");
	}
	
	/**
	 * Returns the player who is the owner of country
	 * @param countryName: name of the country
	 * @return owner of the country
	 */
	public Player getPlayerByCountryName(String countryName) {

		String countryID = mapFileReader.getCountryByName(countryName).getCountryID();

		for (Player player : playerList) {

			if (player.getCountriesOwned().contains(countryID)) {
				return player;
			}
		}

		return null;
	}

	/**
	 * executes the attack
	 */
	public void beginBattle() {
		
		phaseView.getPhaseViewFrame().getTextArea().append("Attacker Dice(s) " + attackerDiceValues + " Defender Dice(s) "+ defenderDiceValues+"\n");
		System.out.println("Attacker Dice(s) " + attackerDiceValues + " Defender Dice(s) "+ defenderDiceValues);
		
		Player defender = getPlayerByCountryName(defendingCountry.getName());
		Player attacker = getPlayerByCountryName(attackingCountry.getName());
		
		while(!attackerDiceValues.isEmpty() && !defenderDiceValues.isEmpty()) {
			
			if (attackerDiceValues.get(0) > defenderDiceValues.get(0)) {

				/* 1. delete first element from the both list */
				attackerDiceValues.remove(0);
				defenderDiceValues.remove(0);
				
				/* 2. remove one army from defending country*/
				int defendingCountryArmies = defendingCountry.getNumberofArmies();
				defendingCountryArmies--;
				
				defendingCountry.addObserver(phaseView);
				phaseView.setMessage(defender.getName()+" lost one army \n"+defendingCountry.getName()+" has "+defendingCountryArmies+" armie(s) remaining \n");
				defendingCountry.setNumberofArmies(defendingCountryArmies);
				defendingCountry.deleteObserver(phaseView);
				
				
				/*if this was the last army of the defender , then defender lost the country*/ 
				if(defendingCountryArmies == 0) {
					
					System.out.println(defender.getName() + " lost country "+ defendingCountry.getName());
					
					/* if defender was owner of that continent then remove the continent from defenders list */
					Set<String> controlledContinent = getControlledContinent(defender);
					if(controlledContinent.size() > 0) {
						for (String continentName : controlledContinent) {
							Continent continent = mapFileReader.getContinentByName(continentName);
							if(continent.getListofCountires().contains(defendingCountry.getCountryID())) {
								
								defender.getContinentOwned().remove(continentName);
							}
						}
					}
					
					
					
					/*remove this country from defender list*/  
					List<String> defenderCountriesOwned = defender.getCountriesOwned();
					defenderCountriesOwned.remove(defendingCountry.getCountryID());
					defender.addObserver(phaseView);
					phaseView.setMessage(defender.getName()+" lost the country "+defendingCountry.getName()+"\n");
					defender.setCountriesOwned(defenderCountriesOwned);
					defender.deleteObserver(phaseView);
				
					
					/*assign this country to attacker list*/ 
					List<String> attackerCountriesOwned = attacker.getCountriesOwned();
					attackerCountriesOwned.add(defendingCountry.getCountryID());
					attacker.addObserver(phaseView);
					phaseView.setMessage(attacker.getName()+" won the country " + defendingCountry.getName() + "\n");
					attacker.setCountriesOwned(attackerCountriesOwned);
					attacker.deleteObserver(phaseView);
					
					/* if attacker has won the continent assign this to attacker */
					attacker.setContinentOwned(getControlledContinent(attacker));
					
					/* if this was the last country of the defender */
					if(defender.getCountriesOwned().size() == 0) {
						
						/* assign the cards from defender to attacker */
						if (defender.getCards().size() > 0) {
							getCardsFromDefender(defender,attacker);
						}
						
						/* remove the player from the game */
						playerList.remove(defender);
						phaseView.getPhaseViewFrame().getTextArea().append(defender.getName()+ "has lost all the countries hence cannot continue to play the game\n");
						playersWorldDominationView.update(null, null);
					}
					
					/* if the attacker has won all the countries then he is the winner */
					if(attacker.getCountriesOwned().size() == mapFileReader.getCountryList().size()) {
						phaseView.getPhaseViewFrame().getTextArea().append("\n\n"+attacker.getName()+" has WON the game !!");
						winningCondition = true;
						List<String> mapFileNameList = new ArrayList<String>(getTournamentResult().keySet());
						if(!mapFileNameList.isEmpty()) {
							String mapFileName = mapFileNameList.get(mapFileNameList.size()-1);
							getTournamentResult().get(mapFileName).add(attacker.getName());
						}
						
					}
					
				}
				
				
				
				/* 3. subtract one army from defender total army of defender */
				int defendingPlayerArmies = defender.getTotalArmiesOwned();
				defendingPlayerArmies--;
				
				/*4. notify observers*/ 
				defender.addObserver(playersWorldDominationView);
				playersWorldDominationView.setMessage(defender.getName()+" lost one army in "+defendingCountry.getName()+"\n");
				defender.setTotalArmiesOwned(defendingPlayerArmies);
				defender.deleteObserver(playersWorldDominationView);
				
				
			} else {

				/*1 . delete element at 0 from the list*/
				attackerDiceValues.remove(0);
				defenderDiceValues.remove(0);
				
				/*2. remove one army from attacking country*/ 
				int attackingCountryArmies = attackingCountry.getNumberofArmies();
				attackingCountryArmies--;
				
				attackingCountry.addObserver(phaseView);
				phaseView.setMessage(attacker.getName()+" lost one army\n"+attackingCountry.getName()+" has "+attackingCountryArmies+" armie(s) remaining \n");
				attackingCountry.setNumberofArmies(attackingCountryArmies);
				defendingCountry.deleteObserver(phaseView);
				
				
				/* 3. subtract one army from attacker total army of attacker*/
				int attackingPlayerArmies = attacker.getTotalArmiesOwned();
				attackingPlayerArmies--;
				
				/*4. notify observers*/
				attacker.addObserver(playersWorldDominationView);
				playersWorldDominationView.setMessage(attacker.getName()+" lost one army in "+attackingCountry.getName()+"\n");
				attacker.setTotalArmiesOwned(attackingPlayerArmies);
				attacker.deleteObserver(playersWorldDominationView);
				
				
			}
			
		}

	}


	/**
	 * execute the attack in allout mode
	 * @param attacker: player initiating the attack
	 * @param fromCountryName: country from which attack is initiated
	 * @param toCountryName : country which is attacked
	 */
	public void allOutAttack(Player attacker, String fromCountryName, String toCountryName) {
		
		attackingCountry = mapFileReader.getCountryByName(fromCountryName);
		defendingCountry = mapFileReader.getCountryByName(toCountryName);
		
		while(attackingCountry.getNumberofArmies() > 1 && defendingCountry.getNumberofArmies() > 0 ) {
			
			int numDiceAttack = getAttackerNumDice(attackingCountry);
			initiateAttack(attacker, fromCountryName, toCountryName, numDiceAttack);
			int numDiceDefend = getDefenderNumDice(defendingCountry);
			defend(numDiceDefend);
			beginBattle();
			
		}
		
	}


	/**
	 * this method returns the valid number of dice to be used by defender
	 * @param defendingCountry: country which is attacked
	 * @return: number of dice
	 */
	private int getDefenderNumDice(Country defendingCountry) {
		return Math.min(2, defendingCountry.getNumberofArmies());
	}


	/**
	 * this method returns the valid number of dice to be used by attacker
	 * @param attackingCountry: country from which attack is initiated
	 * @return number of dice
	 */
	private int getAttackerNumDice(Country attackingCountry) {
		return Math.min(3, attackingCountry.getNumberofArmies()-1);
	}


	/**
	 * moves armies to the conquered country
	 * @param numArmies: number of armies
	 */
	public void attackMove(int numArmies) {
		attackMove(getPlayerByCountryName(attackingCountry.getName()), attackingCountry.getName(),
				defendingCountry.getName(), numArmies);

	}


	
	/**
	 * This method will implemment the attack by a player.
	 * 
	 * @param player
	 * @param fromCountryName
	 * @param toCountryName
	 * @param noOfArmies
	 */
	private void attackMove(Player player, String fromCountryName, String toCountryName, int noOfArmies) {

		if (GamePlayValidator.playerHasCountry(player, fromCountryName) && GamePlayValidator.playerHasCountry(player, toCountryName)
				&& MapValidator.isNeighbour(fromCountryName, toCountryName)) {

			Country fromCountry = mapFileReader.getCountryByName(fromCountryName);
			Country toCountry = mapFileReader.getCountryByName(toCountryName);

			if (fromCountry.getNumberofArmies() - noOfArmies >= 1 && noOfArmies >= 1) {
				
				int fromArmies = fromCountry.getNumberofArmies();
				int toArmies = toCountry.getNumberofArmies();
				toArmies = toArmies + noOfArmies;
				fromArmies = fromArmies - noOfArmies;
				
				fromCountry.addObserver(phaseView);
				phaseView.setMessage("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName+"\n");
				fromCountry.setNumberofArmies(fromArmies);
				toCountry.setNumberofArmies(toArmies);
				fromCountry.deleteObserver(phaseView);
				
				System.out.println("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName);
				GamePlayController.setMessage("Player "+player.getName()+" has sucessfully moved "+noOfArmies+" armies from " +fromCountryName+ " to "+toCountryName);
				
				
			} else {
				System.out.println("Country "+fromCountryName+" should have minimum one remaining army and "+noOfArmies+" should be positive");
				GamePlayController.setMessage("Country "+fromCountryName+" should have minimum one remaining army and "+noOfArmies+" should be positive");
			}
		} else {
			System.out.println("Country "+fromCountryName+" and "+toCountryName+" are not neighbors or doesnot belong to player "+player.getName());
			GamePlayController.setMessage("Country "+fromCountryName+" and "+toCountryName+" are not neighbors or doesnot belong to player "+player.getName());
		}
	
	}


	/**
	 * assign card to the player
	 * @param currentPlayer: attacker who won at least one country in his attack phase
	 */
	public void assignCard(Player currentPlayer) {
		
		Cards cards = Cards.getInstance();
		String randomCard = cards.getCardDeck().get(0);
		cards.getCardDeck().remove(0);
		List<String> attackerCards = currentPlayer.getCards();
		attackerCards.add(randomCard);
		
		currentPlayer.addObserver(phaseView);
		phaseView.setMessage(currentPlayer.getName()+" got the card " +randomCard+ "\n");
		currentPlayer.setCards(attackerCards);
		currentPlayer.deleteObserver(phaseView);
		
	}


	/**
	 * getter for attacker dice values
	 * @return list of attacker dice values
	 */
	public List<Integer> getAttackerDiceValues() {
		return attackerDiceValues;
	}


	/**
	 * setter for attacker dice values
	 * @param attackerDiceValues : list of attacker dice values
	 */
	public void setAttackerDiceValues(List<Integer> attackerDiceValues) {
		this.attackerDiceValues = attackerDiceValues;
	}


	/**
	 *getter for defender dice values 
	 * @return list of defender dice values
	 */
	public List<Integer> getDefenderDiceValues() {
		return defenderDiceValues;
	}


	/**
	 * setter for defender dice values
	 * @param defenderDiceValues : list of defender dice values
	 */
	public void setDefenderDiceValues(List<Integer> defenderDiceValues) {
		this.defenderDiceValues = defenderDiceValues;
	}
	
	
	/**
	 * This method gives cards of defender player to attacker player
	 * @param defender : defender player
	 * @param attacker : attacker player
	 */
	public void getCardsFromDefender(Player defender, Player attacker) {

		for (String card : defender.getCards()) {
			attacker.getCards().add(card);
		}

		defender.getCards().clear();
	}


	
	/**
	 * This method will get the strongest army of the player.
	 * 
	 * @param player: current player
	 * @return Country
	 */
	public Country getStrongestCountry(Player player) {

		List<Country> sortedList = getSortedListOfCountriesByArmy(player);

		for (Country country : sortedList) {
			if (!player.getCountriesOwned().containsAll(country.getAdjacentCountries())) {
				return country;
			}
		}

		return null;
	}

	
	
	/**
	 * This method will get the list of sorted armies.
	 * 
	 * @param player: current player
	 * @return countryList in sorted order.
	 */
	public List<Country> getSortedListOfCountriesByArmy(Player player) {
		
		List<String> localList = player.getCountriesOwned();
		List<Country> countryList= new ArrayList<>();
	
			for(String countryID : localList) {
				
				countryList.add(mapFileReader.getCountryByID(countryID));
			}
		
		Collections.sort(countryList, new Comparator<Country>() {

			@Override
			public int compare(Country c1, Country c2) {

				return c2.getNumberofArmies().compareTo(c1.getNumberofArmies());
			}

		});
		
		
		
		
		return countryList;
	}


	/**
	 * This method will execute the turns of the player.
	 * 
	 * @param currentPlayer: current player
	 */
	public void executeTurn(Player currentPlayer){

		
		logger.info("Current Player --  " +currentPlayer.getName());
		currentPlayer.getStrategy().reinforce(currentPlayer);
		currentPlayer.getStrategy().attack(currentPlayer);
		if(isWinningCondition()) {
			return;
		}
		currentPlayer.getStrategy().fortify(currentPlayer);
		
	}


	/**
	 * This method will get the adjacent player of the player.
	 * 
	 * @param player: owner of the country
	 * @param strongestCountry: strongest country
	 * @return Country
	 */
	public Country getEnemyAdjacentCountries(Player player, Country strongestCountry) {
		
			List<String> neighborCountries = strongestCountry.getAdjacentCountries();
			
			for (String  neighborCountry : neighborCountries) {
				
				if(player.getCountriesOwned().contains(neighborCountry)) {
					continue;
				} else {
					return mapFileReader.getCountryByID(neighborCountry);
				}
				
			}
		
		
		return null;
	}
	
	
	/**
	 * This will get the adjacent countries of the opponent player
	 * 
	 * @param player: owner of the country
	 * @param attackerCountry: attacking country
	 * @return list of enemyCountries.
	 */
	public List<String> getEnemyAdjacentCountriesRandom(Player player, Country attackerCountry) {
		
		List<String> neighborCountries = attackerCountry.getAdjacentCountries();
		List<String> enemyCountries = new ArrayList<String>();
		
		
		for (String  neighborCountry : neighborCountries) {
			
			if(player.getCountriesOwned().contains(neighborCountry)) {
				continue;
			} else {
				enemyCountries.add(neighborCountry);
				
			}
			
		}
	
	return enemyCountries;
}
	
	
	/**
	 * This method will get the adjacent countries owned by the player.
	 * 
	 * @param player: owner of the country
	 * @param attackerCountry: attacking country
	 * @return list of neigbours.
	 */
	public List<String> getPlayerOwnAdjacentCountriesRandom(Player player, Country attackerCountry) {
		
		List<String> neighborCountries = attackerCountry.getAdjacentCountries();
		List<String> neighborCountryList = new ArrayList<String>();
		
		
		for (String  neighborCountry : neighborCountries) {
			
			if(player.getCountriesOwned().contains(neighborCountry)) {
				neighborCountryList.add(neighborCountry);
			} 
			
		}
	
	return neighborCountryList;
}
	
}
