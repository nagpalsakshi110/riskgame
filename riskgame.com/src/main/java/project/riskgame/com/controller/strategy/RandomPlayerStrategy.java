package project.riskgame.com.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.controller.GamePlayValidator;
import project.riskgame.com.controller.PlayerController;
import project.riskgame.com.controller.RandomGenerator;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;

/**
 * @author Devdutt
 * @author Kshitij
 * @author Shubham
 * @author Dhaval
 *
 *
 * @version 1.3
 * 
 * 
 *  This class has implements the reinforce , attack , fortify as per Random Player
 * 
 */
public class RandomPlayerStrategy implements Strategy {

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
	 * Constructor for RandomPlayerStrategy
	 * @param gamePlayController: GamePlayController instance
	 */
	public RandomPlayerStrategy(GamePlayController gamePlayController) {

		this.playerController = PlayerController.getInstance();
		this.mapFileReader = MapFileReader.getInstance();
		this.gamePlayController = gamePlayController;

	}

	public RandomPlayerStrategy() {

	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * this method will reinforce as per random strategy
	 * 
	 */
	@Override
	public void reinforce(Player player) {

		List<String> sortedListOfCountriesByArmy = player.getCountriesOwned();
		int calculateReinforcementArmies = playerController.calculateReinforcementArmies(player);

		while (calculateReinforcementArmies != 0) {

			String randomCountry = RandomGenerator.getRandomObject(sortedListOfCountriesByArmy);
			Country country = mapFileReader.getCountryByID(randomCountry);

			int randomReinforceArmies = RandomGenerator.getRandomNumber(calculateReinforcementArmies, true);

			executePlayCommandList("reinforce " + country.getName() + " " + randomReinforceArmies);

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

			calculateReinforcementArmies = player.getReinforcementArmies();
		}

	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * this method will attack as per random strategy
	 * 
	 */
	@Override
	public void attack(Player player) {

		List<String> sortedListOfCountriesByArmy = player.getCountriesOwned();

		String attackingCountryID = RandomGenerator.getRandomObject(sortedListOfCountriesByArmy);
		Country attackingCountry = mapFileReader.getCountryByID(attackingCountryID);

		List<String> enemyCountries = playerController.getEnemyAdjacentCountriesRandom(player, attackingCountry);

		while (attackingCountry.getNumberofArmies() > 1) {

			if (enemyCountries.isEmpty()) {
				StringBuilder commandNoAttack = new StringBuilder();
				commandNoAttack.append("attack");
				commandNoAttack.append(" ");
				commandNoAttack.append("-noattack");

				executePlayCommandList(commandNoAttack.toString());
				return;

			}

			else {
				String randomCountryID = RandomGenerator.getRandomObject(enemyCountries);
				Country randomCountry = mapFileReader.getCountryByID(randomCountryID);

				StringBuilder command = new StringBuilder();
				command.append("attack");
				command.append(" ");
				command.append(attackingCountry.getName());
				command.append(" ");
				command.append(randomCountry.getName());
				command.append(" 3");
				command.append(" ");
				command.append("-allout");

				if (!GamePlayValidator.isAttackValid(player, attackingCountry.getName(), randomCountry.getName(), 3)) {
					StringBuilder commandNoAttack = new StringBuilder();
					commandNoAttack.append("attack");
					commandNoAttack.append(" ");
					commandNoAttack.append("-noattack");

					executePlayCommandList(commandNoAttack.toString());
					return;
				}

				executePlayCommandList(command.toString());

				if (randomCountry.getNumberofArmies() == 0) {
					int armiesToBeMoved = attackingCountry.getNumberofArmies() - 1;
					String attackMoveCommand = "attackmove" + " " + armiesToBeMoved;
					executePlayCommandList(attackMoveCommand);
				}

			}

			StringBuilder commandNoAttack = new StringBuilder();
			commandNoAttack.append("attack");
			commandNoAttack.append(" ");
			commandNoAttack.append("-noattack");

			executePlayCommandList(commandNoAttack.toString());
			return;

		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * this method will reinforce as per random strategy
	 * 
	 */
	@Override
	public void fortify(Player player) {
		String command = null;
		List<String> sortedListOfCountriesByArmy = player.getCountriesOwned();
		String attackingCountryID = RandomGenerator.getRandomObject(sortedListOfCountriesByArmy);
		Country FromCountry = mapFileReader.getCountryByID(attackingCountryID);

		List<String> neighborCountries = playerController.getPlayerOwnAdjacentCountriesRandom(player, FromCountry);

		if (neighborCountries.size() == 0 || FromCountry.getNumberofArmies() <= 1) {
			command = "fortify none";

		} else {
			String toCountry = RandomGenerator.getRandomObject(neighborCountries);
			Country toCountryFortify = mapFileReader.getCountryByID(toCountry);

			if (FromCountry.getNumberofArmies() > 1) {

				int numberOfFortificationArmies = FromCountry.getNumberofArmies() - 1;

				int armyForify = RandomGenerator.getRandomNumber(numberOfFortificationArmies, true);

				if (toCountryFortify != null) {
					command = "fortify " + FromCountry.getName() + " " + toCountryFortify.getName() + " " + armyForify;
				}
			}

		}
		executePlayCommandList(command);

	}


	/**
	 * this method executes the command passed
	 * @param command
	 */
	private void executePlayCommandList(String command) {
		ArrayList<String> playCommandList = new ArrayList<>();
		playCommandList.add(command);
		gamePlayController.executePlaycommandList(playCommandList);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * this method will return the name of the strategy
	 * 
	 */
	@Override
	public String getStrategyName() {
		return "random";

	}

}
