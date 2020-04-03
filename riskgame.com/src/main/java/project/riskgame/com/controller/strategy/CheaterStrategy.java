package project.riskgame.com.controller.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * Class responsible for cheater strategy
 * @author Kshitij
 * @version 1.3
 */
public class CheaterStrategy implements Strategy {

	/**
	 * PlayerController instance
	 */
	private PlayerController playerController;
	/**
	 * GamePlayController instance
	 */
	private GamePlayController gamePlayController;
	/**
	 * MapFileReader instance
	 */
	private MapFileReader mapFileReader;
	
	/**
	 * PlayersWorldDominationView instance
	 */
	private PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();
	/**
	 * PhaseView instance
	 */
	private PhaseView phaseView = PhaseView.getInstance();
	
	
	/**
	 * constructor for cheater strategy 
	 * @param gamePlayController: gamePlayController instance
	 */
	public CheaterStrategy(GamePlayController gamePlayController) {
		
		 this.playerController = PlayerController.getInstance();
		 this.mapFileReader= MapFileReader.getInstance();
		 this.gamePlayController = gamePlayController;
	}
	
	public CheaterStrategy() {
		

	}
	
	/**
	 *method for reinforcement in cheater strategy
	 *@param player: player with cheater strategy 
	 */
	@Override
	public void reinforce(Player player) {
		
		player.addObserver(phaseView);
		phaseView.setMessage("\n"+player.getName() + " in reinforce phase \n");
		player.setCurrentPhase("reinforce");
		player.deleteObserver(phaseView);

		
		List<Country> sortedListOfCountriesByArmy = playerController.getSortedListOfCountriesByArmy(player);
		
		for (Country country : sortedListOfCountriesByArmy) {
			
			int armies = country.getNumberofArmies() ;
			
			country.addObserver(phaseView);
			phaseView.setMessage("Successfully Assigned "+armies+" reinforcement armies"+ " to "+country.getName() +"\n" +
					country.getName() + " has "+ armies*2 + " armie(s) after reinforcement \n");
			country.setNumberofArmies(armies*2);
			country.deleteObserver(phaseView);
			
			System.out.println(country.getName() + " " + country.getNumberofArmies());

		}
		
		int armiesAfterReinforcement = playerController.getAssignedArmies(player);
		player.setTotalArmiesOwned(armiesAfterReinforcement);
		playersWorldDominationView.update(null, null);
		
		player.addObserver(phaseView);
		phaseView.setMessage("\n"+player.getName() + " in attack phase \n");
		player.setCurrentPhase("attack");
		player.deleteObserver(phaseView);
	}

	
	/**
	 *method for attack in cheater strategy
	 *@param player: player with cheater strategy
	 */
	@Override
	public void attack(Player player) {
		
		Set<String> listOfEnemyAdjacentCountries = new HashSet<String>();
		Player defender;
		for(String countryID: player.getCountriesOwned()) {
			listOfEnemyAdjacentCountries.addAll(playerController.getEnemyAdjacentCountriesRandom(player, mapFileReader.getCountryByID(countryID)));
		}
		
		for(String countryID : listOfEnemyAdjacentCountries) {
			Country defendingCountry = mapFileReader.getCountryByID(countryID);
			defender= playerController.getPlayerByCountryName(defendingCountry.getName());
			List<String> defenderCountryList = new ArrayList<String>();
			List<String> attackerCountryList = new ArrayList<String>();
			
			/*Removing  the country from the defender list*/
			defender.addObserver(phaseView);
			phaseView.setMessage(defender.getName() + " lost "+ defendingCountry.getName() + " to "+ player.getName()+ "\n");
			defenderCountryList=defender.getCountriesOwned();
			defenderCountryList.remove(countryID);
			defender.setCountriesOwned(defenderCountryList);
			defender.deleteObserver(phaseView);
			
			/*adding the country in the cheater's list*/
			player.addObserver(phaseView);
			phaseView.setMessage(player.getName() + " won "+ defendingCountry.getName() + "\n");
			attackerCountryList=player.getCountriesOwned();
			attackerCountryList.add(countryID);
			player.setCountriesOwned(attackerCountryList);
			player.deleteObserver(phaseView);
		}
		
		
		
		
		/* if attacker has won the continent assign this to attacker */
		player.setContinentOwned(playerController.getControlledContinent(player));
		
		
		
		
		
		List<Player> playerList = new ArrayList<Player>(gamePlayController.getPlayerList());
		
		for (Player newPlayer : playerList) {
			
			if(newPlayer.getCountriesOwned().size() == 0) {
				
				/* remove the player from the game */
				gamePlayController.getPlayerList().remove(newPlayer);
				phaseView.getPhaseViewFrame().getTextArea().append(newPlayer.getName()+ "has lost all the countries hence cannot continue to play the game\n");
				playersWorldDominationView.update(null, null);
			}
		}
		
		/*updating armies of the players*/
		for (Player localPlayer : gamePlayController.getPlayerList()) {
			int armiesAfterAttack = playerController.getAssignedArmies(localPlayer);
			localPlayer.setTotalArmiesOwned(armiesAfterAttack);
		}
		playersWorldDominationView.update(null, null);
		
		
		/* winning condition*/
		if(player.getCountriesOwned().size()== mapFileReader.getCountryList().size()) {
			playerController.setWinningCondition(true);
			phaseView.getPhaseViewFrame().getTextArea().append("\n\n"+player.getName()+" has WON the game !!");
			List<String> mapFileNameList = new ArrayList<String>(playerController.getTournamentResult().keySet());
			if(!mapFileNameList.isEmpty()) {
				String mapFileName = mapFileNameList.get(mapFileNameList.size()-1);
				playerController.getTournamentResult().get(mapFileName).add(player.getName());
			}
			
			
		}
		
		player.addObserver(phaseView);
		phaseView.setMessage("\n"+player.getName() + " in fortify phase \n");
		player.setCurrentPhase("fortify");
		player.deleteObserver(phaseView);
	}

	/**
	 *method for fortification in cheater strategy
	 *@param player: player with cheater strategy
	 */
	@Override
	public void fortify(Player player) {
		List<String> listOfCountriesToFortify = new ArrayList<String>();
		for(String countryID: player.getCountriesOwned()) {
			if(!playerController.getEnemyAdjacentCountriesRandom(player, mapFileReader.getCountryByID(countryID)).isEmpty()) {
				listOfCountriesToFortify.add(countryID);
			}
		}
		
		for(String countryID : listOfCountriesToFortify) {
			Country country = mapFileReader.getCountryByID(countryID);
			int countryArmies = country.getNumberofArmies();
			country.addObserver(phaseView);
			phaseView.setMessage("Successfully Assigned "+countryArmies+" fortification armies"+ " to "+country.getName() +"\n" +
					country.getName() + " has "+ countryArmies*2 + " armie(s) after fortification \n");
			country.setNumberofArmies(countryArmies*2);
			country.deleteObserver(phaseView);
			
		}
		
		int armiesAfterFortification = playerController.getAssignedArmies(player);
		player.setTotalArmiesOwned(armiesAfterFortification);
		playersWorldDominationView.update(null, null);
		
		playerController.rotatePlayerList();
		player = gamePlayController.getPlayerList().get(0);
		gamePlayController.setCurrentPlayer(player);
		player.setCurrentPhase("reinforce");

	}
	
	/**
	 *this method return the name of the strategy of the current player
	 *@return strategy name
	 */
	@Override
	public String getStrategyName() {
		return "cheater";

	}

	
}
