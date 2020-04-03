package project.riskgame.com.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;

/**
 * Class responsible for aggressive strategy
 * @author kshitij
 *@version 1.3
 */
public class AggressiveStrategy implements Strategy {
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
	 * constructor of AggressiveStrategy
	 * @param gamePlayController object of gamePlayController
	 */
	public AggressiveStrategy(GamePlayController gamePlayController){
		
		 this.playerController = PlayerController.getInstance();
		 this.mapFileReader= MapFileReader.getInstance();
		 this.gamePlayController = gamePlayController;		 
	}
	
	
	public AggressiveStrategy(){
		
	}
	
	
	/**
	 * reinforce method for aggressive strategy
	 * @param player object of the player
	 */
	@Override
	public void reinforce(Player player) {
	
		int calculateReinforcementArmies = playerController.calculateReinforcementArmies(player);
		Country strongestCountry = playerController.getStrongestCountry(player);
		
		int reinforceArmy = calculateReinforcementArmies;
		
		while(reinforceArmy > 0) {
			executePlayCommandList("reinforce "+strongestCountry.getName()+" "+reinforceArmy);
			int hascards = gamePlayController.playerHasCards(player);
			
			while (hascards >= 3) {

				
				List<String> cards = player.getCards();

				String cardnum1 = "artillery";
				List<String> card1 = new ArrayList<>();
				String cardnum2 = "cavalry";
				List<String> card2 = new ArrayList<>();
				List<String> card3 = new ArrayList<>();

				for (int i = 0; i < hascards; i++) {

					if (cards.get(i).equals(cardnum1)) {
						card1.add(String.valueOf(i + 1));
					}

					else if (cards.get(i).equals(cardnum2)) {
						card2.add(String.valueOf(i + 1));

					} else {
						card3.add(String.valueOf(i + 1));
					}

				}

				if (card1.size() >= 3) {

					executePlayCommandList(
							"exchangecards " + " " + card1.get(0) + " " + card1.get(1) + " " + card1.get(2));
				}

				else if (card2.size() >= 3) {

					executePlayCommandList(
							"exchangecards " + " " + card2.get(0) + " " + card2.get(1) + " " + card2.get(2));
				}

				else if (card3.size() >= 3) {

					executePlayCommandList(
							"exchangecards " + " " + card3.get(0) + " " + card3.get(1) + " " + card3.get(2));
				}

				else if (card1.isEmpty() || card2.isEmpty() || card3.isEmpty()) {

					executePlayCommandList("exchangecards " + " " + "-none");
					break;
				}

				else {
					executePlayCommandList(
							"exchangecards " + " " + card1.get(0) + " " + card2.get(0) + " " + card3.get(0));
				}

				hascards = gamePlayController.playerHasCards(player);
			}
			reinforceArmy = player.getReinforcementArmies();
		}
		
		
	}
	
	
	/**
	 *method for attack in aggressive strategy
	 *@param player: player with cheater strategy
	 */
	@Override
	public void attack(Player player) {

		Country strongestCountry = playerController.getStrongestCountry(player);

 		while (strongestCountry != null && strongestCountry.getNumberofArmies() > 0) {

 			
			Country enemyAdjacentCountry = playerController.getEnemyAdjacentCountries(player, strongestCountry);

			StringBuilder command = new StringBuilder();
			command.append("attack");
			command.append(" ");
			command.append(strongestCountry.getName());
			command.append(" ");
			command.append(enemyAdjacentCountry.getName());
			command.append(" " + Math.min(3, strongestCountry.getNumberofArmies()-1));
			command.append(" ");
			command.append("-allout");
			
			
			if(strongestCountry.getNumberofArmies() == 1 ) {
				StringBuilder commandNoAttack = new StringBuilder();
				commandNoAttack.append("attack");
				commandNoAttack.append(" ");
				commandNoAttack.append("-noattack");

				executePlayCommandList(commandNoAttack.toString());
				return;
			}

			executePlayCommandList(command.toString());

			if (enemyAdjacentCountry.getNumberofArmies() == 0) {
				int armiesToBeMoved = strongestCountry.getNumberofArmies() - 1;
				String attackMoveCommand = "attackmove" + " " + armiesToBeMoved;
				executePlayCommandList(attackMoveCommand);
			}

			strongestCountry = playerController.getStrongestCountry(player);
			

		}

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
	 * fortify method for aggressive strategy
	 * @param player object of the player
	 */
	@Override
	public void fortify(Player player) {

		Country neighbor = null;
		Country toCountry = null;
		List<Country> listOfSortedCountries = playerController.getSortedListOfCountriesByArmy(player);
		String command = null;
		while (neighbor == null) {
			for (Country strongestCountry : listOfSortedCountries) {

				for (String countryID : strongestCountry.getAdjacentCountries()) {
					if (player.getCountriesOwned().contains(countryID)) {
						neighbor = mapFileReader.getCountryByID(countryID);
						toCountry = strongestCountry;
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
		
		if(neighbor.getNumberofArmies() <= 1) {
			command = "fortify none";
		}
		
		executePlayCommandList(command);
		
	}
	
	/**
	 *this method return the name of the strategy of the current player
	 *@return strategy name
	 */
	@Override
	public String getStrategyName() {
		return "aggressive";

	}

	


	

}
