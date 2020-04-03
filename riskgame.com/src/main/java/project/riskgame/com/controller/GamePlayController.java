package project.riskgame.com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import project.riskgame.com.controller.builder.GamePlayControllerBuilder;
import project.riskgame.com.controller.builder.GamePlayDirector;
import project.riskgame.com.controller.builder.LoadBuilder;
import project.riskgame.com.controller.builder.SaveBuilder;
import project.riskgame.com.controller.phase.StartupPhase;
import project.riskgame.com.controller.strategy.AggressiveStrategy;
import project.riskgame.com.controller.strategy.BenevolentStrategy;
import project.riskgame.com.controller.strategy.CheaterStrategy;
import project.riskgame.com.controller.strategy.HumanStrategy;
import project.riskgame.com.controller.strategy.RandomPlayerStrategy;
import project.riskgame.com.controller.strategy.Strategy;
import project.riskgame.com.mapeditor.MapFileReader;
import project.riskgame.com.model.Cards;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;
import project.riskgame.com.view.CardExchangeViewFrame;
import project.riskgame.com.view.PhaseViewFrame;
import project.riskgame.com.view.PlayerWorldDominationFrame;
import project.riskgame.com.view.phase.CardExchangeView;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * This class is responsible for execution of all commands of player in gamePlay
 * phase.
 * 
 * @author Devdutt
 * @author Dhaval
 * @author Kshitij
 * @author shubham
 * @author Sakshi
 * @version 1.0
 *
 */
public class GamePlayController {

	/**
	 * MapFileReader instance
	 */
	private MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * message to be sent to GUI
	 */
	private static String message = "";
	/**
	 * PlayerController instance
	 */
	private PlayerController playerController = PlayerController.getInstance();
	/**
	 * StartupPhase instance
	 */
	private StartupPhase startupPhase;
	/**
	 * Player who controls the game at the moment
	 */
	private Player currentPlayer;
	/**
	 * list of players playing the game
	 */
	private List<Player> playerList;
	/**
	 * Cards instance
	 */
	private Cards cards = Cards.getInstance();
	/**
	 * this variable stores the size of attacker country list
	 */
	private int attackerCountrySize = 0;
	/**
	 * this variable indicates whether card exchange view is open or not
	 */
	private boolean isExchangeOpen = false;

	/**
	 * PlayersWorldDominationView instance
	 */
	private PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();
	/**
	 * PhaseView instance
	 */
	private PhaseView phaseView = PhaseView.getInstance();
	/**
	 * CardExchangeView instance
	 */
	private CardExchangeView cardexChangeView = CardExchangeView.getInstance();

	/**
	 * PhaseViewFrame instance
	 */
	private PhaseViewFrame phaseViewFrame;
	/**
	 * PlayerWorldDominationFrame instance
	 */
	private PlayerWorldDominationFrame worldDominationFrame;
	/**
	 * CardExchangeViewFrame instance
	 */
	private CardExchangeViewFrame cardExchangeViewFrame;

	/**
	 * Logger instance for creating log file
	 */
	private Logger logger = Logger.getLogger("Log");
	/**
	 * this variable indicates whether the game is in tournament mode or not
	 */
	private boolean tournamentMode = false;

	/**
	 * getter for tournamentMode
	 * @return true if in tournament mode, else false
	 */
	public boolean isTournamentMode() {
		return tournamentMode;
	}

	/**
	 * setter for tournamentMode
	 * @param tournamentMode: true if in tournament mode, else false
	 */
	public void setTournamentMode(boolean tournamentMode) {
		this.tournamentMode = tournamentMode;
	}

	/**
	 * getter for the current player
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * setter for current player
	 * @param currentPlayer : Player who controls the game at the moment
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * This is the constructor of GamePlayController to instantiate startupphase and
	 * playercontroller
	 */
	public GamePlayController() {
		startupPhase = new StartupPhase();
		playerList = playerController.getPlayerList();

	}

	/**
	 * This method will get the WorldDominationFrame.
	 * 
	 * @return Frame
	 */
	public PlayerWorldDominationFrame getWorldDominationFrame() {
		return worldDominationFrame;
	}

	/**
	 * This method sets the worldDominationFrame object
	 * 
	 * @param worldDominationFrame : WorldDominationFrame
	 */
	public void setWorldDominationFrame(PlayerWorldDominationFrame worldDominationFrame) {
		this.worldDominationFrame = worldDominationFrame;
	}

	/**
	 * This method will get the PhaseViewFrame.
	 * 
	 * @return phaseViewFrame
	 */
	public PhaseViewFrame getPhaseViewFrame() {
		return phaseViewFrame;
	}

	/**
	 * This method will set the current frame as PhaseViewFrame.
	 * 
	 * @param phaseViewFrame : frame for phase view
	 */
	public void setPhaseViewFrame(PhaseViewFrame phaseViewFrame) {
		this.phaseViewFrame = phaseViewFrame;
	}

	/**
	 * This method will get the message once the command is processed.
	 * 
	 * @return message to be displayed
	 */
	public static String getMessage() {
		return message;
	}

	/**
	 * This method will set the message once the command is executed.
	 * 
	 * @param message: text to be displayed
	 */
	public static void setMessage(String message) {
		GamePlayController.message = message;
	}

	/**
	 * This method will display countrylist, continentlist, map and playerlist
	 */
	public void showmap() {

		System.out.println("Start showing map file ------------------------------------------ ");

		System.out.println(mapFileReader.getContinentList());
		System.out.println(mapFileReader.getCountryList());
		System.out.println("");
		System.out.println(mapFileReader.getMap());
		System.out.println("");
		System.out.println(playerController.getPlayerList());

		System.out.println("Map File Displayed successfully ----------------------------------");

	}

	/**
	 * This method gets list of command entered by the user in the gameplay phase
	 * 
	 * @param playCommandList : list of commands
	 * @return message : "" if successful execution of commands or error message
	 */
	public String executePlaycommandList(ArrayList<String> playCommandList) {

		/* set the message to blank before each execute command */
		setMessage("");

		for (String command : playCommandList) {
			try {
				executePlayCommand(command);
			} catch (Exception e) {
				System.out.println("Error occured : " + e.getClass().getName() + " - " + e.getMessage());
			}
		}

		return getMessage();
	}

	/**
	 * This method call the methods to execute the command
	 * 
	 * @param command : gameplay command
	 * @throws Exception
	 */
	private void executePlayCommand(String command) throws Exception {

		String commandParts[] = command.split("\\s+");

		/* set the message to blank before each execute command */
		setMessage("");

		if (commandParts[0].equals("loadmap")) {

			if (!startupPhase.isMapLoaded()) {
				startupPhase.loadMap(commandParts[1]);
			} else {
				System.out.println("Map is already loaded");
				setMessage("Map is already loaded");
			}

		} else if (commandParts[0].equals("showmap")) {

			if (!startupPhase.isMapLoaded()) {
				System.out.println("Please Load the map first");
				setMessage("Please Load the map first");
				return;
			} else {
				showmap();
			}

		} else if (commandParts[0].equals("gameplayer")) {

			if (!startupPhase.isMapLoaded()) {
				System.out.println("Please Load the map first");
				setMessage("Please Load the map first");
				return;
			}

			if (currentPlayer != null) {
				System.out.println("Cannot add/remove player in this phase");
				setMessage("Cannot add/remove player in this phase");
				return;
			}

			if (startupPhase.isCountryPopulated()) {
				System.out.println("Cannot add/remove player once the contries are populated");
				setMessage("Cannot add/remove player once the contries are populated");
				return;
			}

			if (commandParts[1].equals("-add")) {

				/* Validation to be added of player exists or not */
				if (!GamePlayValidator.isPlayerExists(commandParts[2])) {

					Strategy strategy = null;
					if (commandParts[3].equals("human")) {
						strategy = new HumanStrategy();
					} else if (commandParts[3].equals("aggressive")) {
						strategy = new AggressiveStrategy(this);
					} else if (commandParts[3].equals("benevolent")) {

						strategy = new BenevolentStrategy(this);

					} else if (commandParts[3].equals("random")) {

						strategy = new RandomPlayerStrategy(this);

					} else if (commandParts[3].equals("cheater")) {

						strategy = new CheaterStrategy(this);
					}

					startupPhase.addPlayer(commandParts[2], strategy);
				} else {
					setMessage("Player " + commandParts[2] + " already exist");
					System.out.println("Player " + commandParts[2] + " already exist");
				}

			} else if (commandParts[1].equals("-remove")) {

				/* Validation to be added to check if player exists to be removed */
				if (GamePlayValidator.isPlayerExists(commandParts[2])) {
					startupPhase.removePlayer(commandParts[2]);
				} else {
					setMessage("Player " + commandParts[2] + " doesnot exist");
					System.out.println("Player " + commandParts[2] + " doesnot exist");
				}

			} else {

				setMessage("Invalid Command -- " + command);
				System.out.println("Invalid Command");

			}

		} else if (commandParts[0].equals("populatecountries")) {

			if (!startupPhase.isMapLoaded()) {
				System.out.println("Please Load the map first");
				setMessage("Please Load the map first");
				return;
			}

			if (startupPhase.isCountryPopulated()) {
				System.out.println("Countries are already populated");
				setMessage("Countries are already populated");
				return;
			}

			/* populates only if more than one players are added and map is loaded */
			if (playerList.size() > 1) {

				startupPhase.populateCountries();
				cards.prepareDeck();
				currentPlayer = playerList.get(0);
				playerController.setCurrentPlayer(playerList.get(0));

			} else {
				System.out.println("Please add more than one player to populatecountries");
				setMessage("Please add more than one player to populatecountries");
			}

		} else if (commandParts[0].equals("placearmy")) {

			currentPlayer = playerList.get(0);
			playerController.setCurrentPlayer(currentPlayer);

			if (!(currentPlayer.getCurrentPhase() == null || currentPlayer.getCurrentPhase().equals(""))) {
				System.out.println("All armies are already placed please move to next phase");
				setMessage("All armies are already placed please move to next phase");
				return;
			}

			if (startupPhase.isCountryPopulated()) {

				/* call method to place army */
				startupPhase.placeArmies(currentPlayer, commandParts[1]);
				playerController.rotatePlayerList();
			} else {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
			}

		} else if (commandParts[0].equals("placeall")) {

			currentPlayer = playerList.get(0);

			playerController.setCurrentPlayer(currentPlayer);

			if (!(currentPlayer.getCurrentPhase() == null || currentPlayer.getCurrentPhase().equals(""))) {
				System.out.println("All armies are already placed please move to next phase");
				setMessage("All armies are already placed please move to next phase");
				return;
			}

			if (startupPhase.isCountryPopulated()) {

				/* call placeall method */
				startupPhase.placeAll();
				Collections.sort(playerController.getPlayerList());
				currentPlayer = playerList.get(0);

			} else {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
			}

			/* if else to execute strategies */
			if (!isTournamentMode()) {
				while (!playerController.isWinningCondition()) {
					if (!currentPlayer.getStrategy().getStrategyName().equals("human")) {
						playerController.executeTurn(currentPlayer);
					} else {
						break;
					}
				}
			}

		} else if (commandParts[0].equals("reinforce")) {

			if (!startupPhase.isCountryPopulated()) {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
				return;
			}

			if (!(currentPlayer.getCurrentPhase().equals("reinforce"))) {
				System.out.println("Cannot reinforce in this phase");
				setMessage("Cannot reinforce in this phase");
				return;
			}

			if (isExchangeOpen) {
				System.out.println("Please handle card exchange phase first");
				setMessage("Please handle card exchange phase first");
				return;
			}

			int totalReinforcementArmies = currentPlayer.getReinforcementArmies();

			/* if currentPlayer status is not reinforce then set it to reinforce */
			if (totalReinforcementArmies == 0) {
				/*
				 * if (!isTournamentMode()) { clear the phase view frame when the player phase
				 * changes phaseViewFrame.getTextArea().setText(""); }
				 */

				currentPlayer.addObserver(phaseView);
				phaseView.setMessage("\n"+currentPlayer.getName() + " in reinforce phase \n");
				currentPlayer.setCurrentPhase("reinforce");
				currentPlayer.deleteObserver(phaseView);
			}

			if (totalReinforcementArmies == 0) {

				/* observer to display view phaseView , playersWorldDominationView */
				totalReinforcementArmies = playerController.calculateReinforcementArmies(currentPlayer);

				currentPlayer.addObserver(phaseView);
				phaseView.setMessage(
						currentPlayer.getName() + " has " + totalReinforcementArmies + " reinforcement armies \n");
				currentPlayer.setReinforcementArmies(totalReinforcementArmies);
				currentPlayer.deleteObserver(phaseView);

				currentPlayer.addObserver(playersWorldDominationView);
				currentPlayer.setTotalArmiesOwned(totalReinforcementArmies + currentPlayer.getTotalArmiesOwned());
				currentPlayer.deleteObserver(playersWorldDominationView);

				/*
				 * logic to calculate control value
				 */				
				int controlValueArmies = playerController.calculateControlValue(currentPlayer);

				if (controlValueArmies > 0) {

					Set<String> continentSet = playerController.getControlledContinent(currentPlayer);

					currentPlayer.addObserver(playersWorldDominationView);
					currentPlayer.setTotalArmiesOwned(controlValueArmies + currentPlayer.getTotalArmiesOwned());
					currentPlayer.setContinentOwned(continentSet);
					currentPlayer.deleteObserver(playersWorldDominationView);

					currentPlayer.addObserver(phaseView);
					int reinforcementArmies = currentPlayer.getReinforcementArmies() + controlValueArmies;

					phaseView.setMessage(currentPlayer.getName() + " has controlled over" + continentSet
							+ " and has received " + controlValueArmies
							+ " reinforcement armies using Continent Control Value\n " + currentPlayer.getName()
							+ " has now " + reinforcementArmies + " reinforcement armies \n");

					currentPlayer.setReinforcementArmies(reinforcementArmies);
					currentPlayer.deleteObserver(phaseView);

				}

				int hasCards = playerHasCards(currentPlayer);

				if (hasCards >= 3) {
					activateCardExchangeView();
					isExchangeOpen = true;
					return;
				}

			}

			int numberOfReinforcementArmies = Integer.parseInt(commandParts[2]);

			/* call reinforce method */
			if (currentPlayer.getReinforcementArmies() >= numberOfReinforcementArmies) {
				playerController.assignReinforcementArmies(currentPlayer, commandParts[1], numberOfReinforcementArmies);

				if (currentPlayer.getReinforcementArmies() == 0) {

					currentPlayer.addObserver(phaseView);
					phaseView.setMessage(currentPlayer.getName() + " reinforce phase ended\n");
					currentPlayer.setCurrentPhase("attack");
					currentPlayer.deleteObserver(phaseView);

					System.out.println(currentPlayer.getName() + " reinforce phase ended");

				}

			} else {
				System.out.println("Number of ReinforcementArmies is more than available");
				setMessage("Number of ReinforcementArmies is more than available");
			}

		} else if (commandParts[0].equals("exchangecards")) {

			int hasCards = playerHasCards(currentPlayer);

			if (commandParts[1].equals("-none") && hasCards < 5) {

				cardExchangeViewFrame.dispose();
				isExchangeOpen = false;

			} else if (commandParts[1].equals("-none") && hasCards >= 5) {

				System.out.println("You have more than 5 cards, Please exchange cards first");
				setMessage("You have more than 5 cards, Please exchange cards first");
				return;

			} else {

				if (exchangeCards(currentPlayer, commandParts[1], commandParts[2], commandParts[3])) {
					cardExchangeViewFrame.dispose();
					if (currentPlayer.getCards().size() < 5) {
						isExchangeOpen = false;
					}
				}
			}
		}

		else if (commandParts[0].equals("attack")) {

			if (!startupPhase.isCountryPopulated()) {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
				return;
			}

			if (!(currentPlayer.getCurrentPhase().equals("attack"))) {
				System.out.println("Cannot attack in this phase");
				setMessage("Cannot attack in this phase");
				return;
			}

			if (playerController.getAttackingCountry() == null) {

				currentPlayer.addObserver(phaseView);
				phaseView.setMessage("\n"+currentPlayer.getName() + " in attack phase \n");
				currentPlayer.setCurrentPhase("attack");
				currentPlayer.deleteObserver(phaseView);

				attackerCountrySize = currentPlayer.getCountriesOwned().size();
			}

			if (commandParts.length > 2) {

				String fromCountryName = commandParts[1];
				String toCountryName = commandParts[2];
				int attackerNumDice = Integer.parseInt(commandParts[3]);

				if (GamePlayValidator.isAttackValid(currentPlayer, fromCountryName, toCountryName, attackerNumDice)) {

					if (commandParts.length == 4) {
						playerController.initiateAttack(currentPlayer, fromCountryName, toCountryName, attackerNumDice);
					}

					if (commandParts.length == 5) {
						playerController.allOutAttack(currentPlayer, fromCountryName, toCountryName);
					}

				}

			}

			if (commandParts.length == 2) {

				if (GamePlayValidator.isAttackMovePossible(currentPlayer)) {
					GamePlayController.setMessage("You have to move armies to the conquered country first.");
					return;
				}

				currentPlayer.addObserver(phaseView);
				phaseView.setMessage(currentPlayer.getName() + " attack phase ended \n");
				currentPlayer.setCurrentPhase("fortify");
				currentPlayer.deleteObserver(phaseView);

				playerController.setAttackingCountry(null);
				playerController.setDefendingCountry(null);

				if (currentPlayer.getCountriesOwned().size() > attackerCountrySize) {
					playerController.assignCard(currentPlayer);
				}

				System.out.println(currentPlayer.getName() + " attack phase ended");

			}

		} else if (commandParts[0].equals("defend")) {

			if (!startupPhase.isCountryPopulated()) {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
				return;
			}

			if (!(currentPlayer.getCurrentPhase().equals("attack"))) {
				System.out.println("Cannot defend in this phase");
				setMessage("Cannot defend in this phase");
				return;
			}

			if (playerController.getAttackingCountry() == null) {
				System.out.println("There should be an attack on your country before you defened");
				setMessage("There should be an attack on your country before you defened");
				return;
			}

			Country country = playerController.getDefendingCountry();
			int defendingarmies = country.getNumberofArmies();
			int defenderNumDice = Integer.parseInt(commandParts[1]);

			if (GamePlayValidator.isDefendValid(defendingarmies, defenderNumDice)) {

				playerController.defend(defenderNumDice);
				playerController.beginBattle();

			}

		} else if (commandParts[0].equals("attackmove")) {

			if (GamePlayValidator.isAttackMovePossible(currentPlayer)) {
				playerController.attackMove(Integer.parseInt(commandParts[1]));
			} else {
				System.out.println("Invalid command at this stage");
				setMessage("Invalid command at this stage");
				return;
			}

		} else if (commandParts[0].equals("fortify")) {

			if (!startupPhase.isCountryPopulated()) {
				System.out.println("Please populate countries first");
				setMessage("Please populate countries first");
				return;
			}

			if (!currentPlayer.getCurrentPhase().equals("fortify")) {
				System.out.println("Cannot fortify in this phase");
				setMessage("Cannot fortify in this phase");
				return;
			}
			
			currentPlayer.addObserver(phaseView);
			phaseView.setMessage("\n"+currentPlayer.getName() + " in fortify phase \n");
			currentPlayer.setCurrentPhase("fortify");
			currentPlayer.deleteObserver(phaseView);

			if (commandParts[1].equals("none")) {

				/* change first player's phase to initial */
				
				currentPlayer.addObserver(phaseView);
				phaseView.setMessage(currentPlayer.getName() + " fortification phase ended \n");
				currentPlayer.setCurrentPhase("reinforce");
				System.out.println(currentPlayer.getName() + " fortify phase ended");
				
				/* get next player and change its phase to initial */
				playerController.rotatePlayerList();
				currentPlayer = playerList.get(0);

				currentPlayer.setCurrentPhase("reinforce");
				currentPlayer.deleteObserver(phaseView);

				setMessage("Fortification completed");

			} else {

				int noOfArmies = Integer.parseInt(commandParts[3]);

				/* call fortify method */
				if (playerController.fortificationOfArmies(currentPlayer, commandParts[1], commandParts[2],
						noOfArmies)) {

					/* change first player's phase to initial */
					currentPlayer.setCurrentPhase("reinforce");
					System.out.println(currentPlayer.getName() + " fortify phase ended");

					/* get next player and change its phase to initial */
					playerController.rotatePlayerList();
					currentPlayer = playerList.get(0);
					currentPlayer.setCurrentPhase("reinforce");

				}

			}

			if (!isTournamentMode()) {
				Player lastPlayer = playerList.get(playerList.size() - 1);

				if (lastPlayer.getStrategy().getStrategyName().equals("human")) {
					while (!playerController.isWinningCondition()) {
						if (!currentPlayer.getStrategy().getStrategyName().equals("human")) {
							playerController.executeTurn(currentPlayer);
						} else {
							break;
						}
					}
				}
			}

		} else if (commandParts[0].equals("exit")) {
			System.exit(0);
		} else if (commandParts[0].equals("loadgame")) {

			GamePlayDirector director = new GamePlayDirector();
			GamePlayControllerBuilder loadBuilder = new LoadBuilder();
			loadBuilder.setDirectoryName(commandParts[1]);
			loadBuilder.setGamePlayController(this);
			director.setBuilder(loadBuilder);
			director.saveLoadGame();

		} else if (commandParts[0].equals("savegame")) {

			GamePlayDirector director = new GamePlayDirector();
			GamePlayControllerBuilder saveBuilder = new SaveBuilder();
			saveBuilder.setGamePlayController(this);
			saveBuilder.setDirectoryName(commandParts[1]);
			director.setBuilder(saveBuilder);
			director.saveLoadGame();

		} else if (commandParts[0].equals("tournament")) {

			setTournamentMode(true);

			ArrayList<String> listOfMapFiles = new ArrayList<>();
			ArrayList<String> listOfStrategies = new ArrayList<>();
			int numberOfGames = 0;
			int maxNumberOfTurns = 0;
			int i = 2;

			if (commandParts[1].equals("-M")) {

				while (!commandParts[i].equals("-P")) {
					listOfMapFiles.add(commandParts[i]);

					i++;
				}

				i++;
				while (!commandParts[i].equals("-G")) {
					listOfStrategies.add(commandParts[i]);

					i++;
				}

				i++;
				while (!commandParts[i].equals("-D")) {

					numberOfGames = Integer.parseInt(commandParts[i]);
					i++;
				}
				i++;
				maxNumberOfTurns = Integer.parseInt(commandParts[i]);
			}

			startTournament(listOfMapFiles, listOfStrategies, numberOfGames, maxNumberOfTurns);
			logger.info(""+playerController.getTournamentResult());
			playerController.getTournamentResult().clear();

		} else {
			setMessage("Invalid Command -- " + command);
			System.out.println("Invalid Command");
		}

		logger.info("successfully executed -- " + command);

	}

	/**
	 * this method executes the tournament according to the parameters
	 * @param listOfMapFiles : list of maps on which the tournament to be played on
	 * @param listOfStrategies: list of strategies on which the tournament to be played on 
	 * @param numberOfGames : number of games
	 * @param maxNumberOfTurns : number of turns
	 */
	public void startTournament(ArrayList<String> listOfMapFiles, ArrayList<String> listOfStrategies,
			int numberOfGames, int maxNumberOfTurns) {

		int turns = 0;
		for (String mapFileName : listOfMapFiles) {

			ArrayList<String> listOfResults = new ArrayList<String>();

			playerController.getTournamentResult().put(mapFileName, listOfResults);

			for (int i = 1; i <= numberOfGames; i++) {

				List<String> gamePlayerList = new ArrayList<>();

				turns = maxNumberOfTurns;
				ArrayList<String> playCommandList = new ArrayList<>();

				playCommandList.add("loadmap " + mapFileName);

				int k = 1;
				for (String strategyName : listOfStrategies) {

					StringBuilder command = new StringBuilder();
					command.append("gameplayer -add");
					command.append(" ");
					command.append("Player_" + k);
					command.append(" ");
					command.append(strategyName);
					gamePlayerList.add("Player_" + k);
					k++;

					playCommandList.add(command.toString());

				}

				playCommandList.add("populatecountries");
				playCommandList.add("placeall");

				executePlaycommandList(playCommandList);

				boolean winningCondition = false;

				while (turns != 0) {

					int playerListSize = playerController.getPlayerList().size();

					while (playerListSize != 0 && !winningCondition) {
						playerController.executeTurn(currentPlayer);
						int playerListSize1 = playerController.getPlayerList().size();

						if (playerListSize1 == 1) {
							winningCondition = true;
							System.out.println(getPlayerList());
							logger.info(currentPlayer.getName()+" won game "+i+" in "+mapFileName);
						}

						if (playerListSize1 < playerListSize) {
							playerListSize = playerListSize1;
						}

						playerListSize--;

					}
					turns--;
				}

				/* check if draw */

				if (isDraw()) {
					playerController.getTournamentResult().get(mapFileName).add("Draw");
				}

				cleanUpAfterGame(gamePlayerList);

			}

		}

		logger.info("RESULT OF TOURNAMENT -------------------------------------------");

		HashMap<String, ArrayList<String>> tournamentResult = playerController.getTournamentResult();

		for (Entry<String, ArrayList<String>> entry : tournamentResult.entrySet()) {

			String mapName = entry.getKey();
			ArrayList<String> winnerList = entry.getValue();

			System.out.print(mapName + " = [ ");

			for (int i = 0; i < winnerList.size(); i++) {
				int game = i + 1;
				System.out.print("Game" + game + " = " + winnerList.get(i) + " , ");
			}

			System.out.print("]\n");
		}

		setTournamentMode(false);
	}

	/**
	 * this method checks if the result of the game is draw
	 * @return true if draw, else false
	 */
	private boolean isDraw() {

		for (Player player : playerController.getPlayerList()) {

			if (player.getCountriesOwned().size() == mapFileReader.getCountryList().size()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * clears the list for next game
	 * @param gamePlayerList: list of player participating in the tournament
	 */
	public void cleanUpAfterGame(List<String> gamePlayerList) {

		playerController.getPlayerList().clear();
		mapFileReader.clearAll();
		startupPhase.setMapLoaded(false);
		startupPhase.setCountryPopulated(false);
		setCurrentPlayer(null);
		playerController.setWinningCondition(false);

	}

	/**
	 * This method will check if the player has a card.
	 * 
	 * @param player: current player
	 * @return true if the player has a card.
	 */
	public int playerHasCards(Player player) {

		int numberOfCards = player.getCards().size();

		if (numberOfCards >= 3) {
			return numberOfCards;
		}
		return numberOfCards;

	}

	/**
	 * This method will get the armies from exchange.
	 * 
	 * @param player:   player who receives armies
	 * @param cardNum1: identifier of the first card
	 * @param cardNum2: identifier of the second card
	 * @param cardNum3: identifier of the third card
	 * @return armiesReceivedFromExchange
	 */
	public int getArmiesFromExchange(Player player, int cardNum1, int cardNum2, int cardNum3) {

		int numberOfCards = player.getCards().size();
		int armiesReceivedFromExchange = 0;
		if (numberOfCards >= 3) {

			for (int i = 0; i < numberOfCards; i++) {

				System.out.println(i + " " + player.getCards().get(i));
			}

			String cardName1 = player.getCards().get(cardNum1);
			String cardName2 = player.getCards().get(cardNum2);
			String cardName3 = player.getCards().get(cardNum3);

			if (cardName1.equals(cardName2) && cardName2.equals(cardName3) && cardName1.equals(cardName3)) {
				armiesReceivedFromExchange = calculateExchangeArmies(player, cardName1, cardName2, cardName3);
			}

			else if (!cardName1.equals(cardName2) && !cardName2.equals(cardName3) && !cardName1.equals(cardName3)) {

				armiesReceivedFromExchange = calculateExchangeArmies(player, cardName1, cardName2, cardName3);
			}
		}

		return armiesReceivedFromExchange;

	}

	/**
	 * This method will calculate the number of exchanged armies.
	 * 
	 * @param player     : player
	 * @param cardName1: identifier of the first card
	 * @param cardName2: identifier of the second card
	 * @param cardName3: identifier of the third card
	 * @return number of armies from exchange
	 */
	public int calculateExchangeArmies(Player player, String cardName1, String cardName2, String cardName3) {

		int armiesReceivedFromExchange = 0;
		int numberOfExchange = player.getExchangeCards();

		if (numberOfExchange == 0) {
			armiesReceivedFromExchange = 5;
		} else {
			armiesReceivedFromExchange = 5 * (numberOfExchange + 1);
		}

		int reinforcementArmies = player.getReinforcementArmies() + armiesReceivedFromExchange;
		int totalArmiesOwned = player.getTotalArmiesOwned() + armiesReceivedFromExchange;

		currentPlayer.addObserver(playersWorldDominationView);
		player.setTotalArmiesOwned(totalArmiesOwned);
		currentPlayer.deleteObserver(playersWorldDominationView);

		currentPlayer.addObserver(cardexChangeView);
		currentPlayer.addObserver(phaseView);
		phaseView.setMessage(currentPlayer.getName() + " has received " + armiesReceivedFromExchange
				+ " reinforcement armies using cardexchange \n" + currentPlayer.getName() + " has now "
				+ reinforcementArmies + " reinforcement armies \n");
		cardexChangeView.setMessage(currentPlayer.getName() + " has received " + armiesReceivedFromExchange
				+ " reinforcement armies using cardexchange \n");
		player.setReinforcementArmies(reinforcementArmies);
		player.setExchangeCards(++numberOfExchange);
		currentPlayer.deleteObserver(phaseView);
		currentPlayer.deleteObserver(cardexChangeView);

		cards.getCardDeck().add(cardName1);
		cards.getCardDeck().add(cardName2);
		cards.getCardDeck().add(cardName3);

		player.getCards().remove(cardName1);
		player.getCards().remove(cardName2);
		player.getCards().remove(cardName3);

		return armiesReceivedFromExchange;
	}

	/**
	 * This method initiates the cardexchange
	 * 
	 * @param player : Player who wants to exchangecards
	 * @param num1   : card num1
	 * @param num2   : card num2
	 * @param num3   : card num3
	 * @return true if cards are successfully exchanged
	 */
	public boolean exchangeCards(Player player, String num1, String num2, String num3) {

		int armiesReceivedFromExchange = 0;

		int numberOfCards = currentPlayer.getCards().size();

		int cardNum1 = Integer.parseInt(num1) - 1;
		int cardNum2 = Integer.parseInt(num2) - 1;
		int cardNum3 = Integer.parseInt(num3) - 1;

		if (cardNum1 >= numberOfCards || cardNum2 >= numberOfCards || cardNum3 >= numberOfCards) {

			setMessage("Please enter valid numbers for card exchange ");
			System.out.println("Please enter valid numbers for card exchange ");
			return false;
		}

		armiesReceivedFromExchange = getArmiesFromExchange(currentPlayer, cardNum1, cardNum2, cardNum3);
		if (armiesReceivedFromExchange != 0) {
			System.out.println(currentPlayer.getName() + " has received " + armiesReceivedFromExchange + " armies");

		} else {
			setMessage("card exchange not possible");
			System.out.println("card exchange not possible");
			return false;
		}

		return true;
	}

	/**
	 * This method activates the CardExchangeView
	 */
	public void activateCardExchangeView() {

		int count = 0;
		cardExchangeViewFrame = new CardExchangeViewFrame();
		cardExchangeViewFrame.setVisible(true);
		cardExchangeViewFrame.setTitle("Card Exchange View");
		cardexChangeView.setCardExchangeViewFrame(cardExchangeViewFrame);

		int numberOfCards = currentPlayer.getCards().size();
		List<String> cards = currentPlayer.getCards();

		cardExchangeViewFrame.getTextArea().append(currentPlayer.getName() + " has " + numberOfCards + " cards \n");

		for (int i = 0; i < numberOfCards; i++) {
			cardExchangeViewFrame.getTextArea().append(++count + " " + cards.get(i) + "\n");
		}

	}

	/**
	 * getter for mapFileReader
	 * @return mapFileReader instance
	 */
	public MapFileReader getMapFileReader() {
		return mapFileReader;
	}

	/**
	 * setter for mapFileReader
	 * @param mapFileReader: MapFileReader instance
	 */
	public void setMapFileReader(MapFileReader mapFileReader) {
		this.mapFileReader = mapFileReader;
	}

	/**
	 * getter for player controller
	 * @return playerController instance
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}

	/**
	 * setter for playerController
	 * @param playerController : playerController instance
	 */
	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}

	/**
	 * getter for startupPhase
	 * @return StartupPhase instance
	 */
	public StartupPhase getStartupPhase() {
		return startupPhase;
	}

	/**
	 * setter for startupPhase
	 * @param startupPhase : startupPhase instance
	 */
	public void setStartupPhase(StartupPhase startupPhase) {
		this.startupPhase = startupPhase;
	}

	/**
	 * getter for player list
	 * @return list of players
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * setter for player list
	 * @param playerList : list of players
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * getter for phase view
	 * @return phase view instance
	 */
	public PhaseView getPhaseView() {
		return phaseView;
	}

	/**
	 * setter for player instance
	 * @param phaseView : phase view instance
	 */
	public void setPhaseView(PhaseView phaseView) {
		this.phaseView = phaseView;
	}

	/**
	 * getter for CardExchangeView
	 * @return CardExchangeView instance
	 */
	public CardExchangeView getCardexChangeView() {
		return cardexChangeView;
	}

	/**
	 * setter for CardExchangeView 
	 * @param cardexChangeView: CardExchangeView instance
	 */
	public void setCardexChangeView(CardExchangeView cardexChangeView) {
		this.cardexChangeView = cardexChangeView;
	}

	/**
	 * getter for cards
	 * @return Cards instance
	 */
	public Cards getCards() {
		return cards;
	}

	/**
	 * setter for cards
	 * @param cards: Cards instance
	 */
	public void setCards(Cards cards) {
		this.cards = cards;
	}

	/**
	 * getter for CardExchangeViewFrame
	 * @return CardExchangeViewFrame instance
	 */
	public CardExchangeViewFrame getCardExchangeViewFrame() {
		return cardExchangeViewFrame;
	}

	/**
	 * setter for CardExchangeViewFrame
	 * @param cardExchangeViewFrame: CardExchangeViewFrame instance
	 */
	public void setCardExchangeViewFrame(CardExchangeViewFrame cardExchangeViewFrame) {
		this.cardExchangeViewFrame = cardExchangeViewFrame;
	}

	/**
	 * getter for attackerCountrySize
	 * @return size of attacker country list
	 */
	public int getAttackerCountrySize() {
		return attackerCountrySize;
	}

	/**
	 * setter for setAttackerCountrySize
	 * @param attackerCountrySize : size of attacker country list
	 */
	public void setAttackerCountrySize(int attackerCountrySize) {
		this.attackerCountrySize = attackerCountrySize;
	}

	/**
	 * indicates whether card exchange view is open or not
	 * @return true if open, else false
	 */
	public boolean isExchangeOpen() {
		return isExchangeOpen;
	}

	/**
	 * setter for card exchange
	 * @param isExchangeOpen : true if open, else false
	 */
	public void setExchangeOpen(boolean isExchangeOpen) {
		this.isExchangeOpen = isExchangeOpen;
	}

	/**
	 * getter for PlayersWorldDominationView
	 * @return PlayersWorldDominationView instance
	 */
	public PlayersWorldDominationView getPlayersWorldDominationView() {
		return playersWorldDominationView;
	}

	/**
	 * setter for PlayersWorldDominationView
	 * @param playersWorldDominationView: PlayersWorldDominationView instance
	 */
	public void setPlayersWorldDominationView(PlayersWorldDominationView playersWorldDominationView) {
		this.playersWorldDominationView = playersWorldDominationView;
	}

}
