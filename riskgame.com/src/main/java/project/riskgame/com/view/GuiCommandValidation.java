package project.riskgame.com.view;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * checks commands entered by the user are valid or not
 * 
 * @author Shubham
 * @author Kshitij
 * @version 1.0
 */

public class GuiCommandValidation {

	/**
	 *command entered by the player.
	 */
	private String command;

	/**
	 * This method checks the syntax of command entered
	 * 
	 * @param command     : User input command
	 * @param commandSet: Set of valid Commands for the game
	 * @return true if the command are valid, else false
	 */
	public static boolean checkEditorCommand(String command, HashSet<String> commandSet) {

		/* flag will be 0 if the command is invalid */
		int flag = 0;
		String[] commandString = command.split(" ");

		if (commandString.length <= 1) {
			if ((commandString[0].equals("validatemap") || commandString[0].equals("showmap")))
				flag = 1;
		} else if (commandSet.contains(commandString[0])) {

			flag = 1;

			switch (commandString[0]) {

			case "editcountry":
				if (!(commandString[1].equals("-add") || commandString[1].equals("-remove")))
					flag = 0;
				break;

			case "editcontinent":
				if (!(commandString[1].equals("-add") || commandString[1].equals("-remove")))
					flag = 0;
				break;

			case "editneighbor":
				if (!(commandString[1].equals("-add") || commandString[1].equals("-remove")))
					flag = 0;
				break;
			case "savemap":
				if (commandString.length != 2)
					flag = 0;
				break;
			case "editmap":
				if (commandString.length != 2)
					flag = 0;
				break;

			}

		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * This method checks the syntax of the options of the command entered by the
	 * user only when validateCommand() returns true
	 * 
	 * @param commandParts : array of strings obtained by splitting the user entered
	 *                     command
	 * @param command : command                    
	 */
	public void checkOption(String[] commandParts, String command) {
		this.command = command;
		/* flag = 0 if options are invalid */
		int flag = 1;

		for (int i = 0; i < commandParts.length; i++) {
			if (commandParts[i].charAt(0) == '-') {

				if (!(commandParts[i].equals("-add") || commandParts[i].equals("-remove"))) {
					flag = 0;
				}
			}
		}

		if (flag == 1)
			checkOptionLength(commandParts);
		else {
			MapEditorFrame.showError();
		}
	}

	/**
	 * This method checks the number of parameters passed to the options of the
	 * command
	 * 
	 * @param commandParts : array of strings obtained by splitting the user entered
	 *                     command
	 */
	private void checkOptionLength(String[] commandParts) {

		String[] splitByOptions = command.split("-");

		/*
		 * flag will be 0 if the number of parameters for the option will be not as
		 * specified
		 */
		int flag = 1;

		if (splitByOptions[0].contains("editcontinent") || splitByOptions[0].contains("editcountry")) {

			for (String str : splitByOptions) {

				str.trim();

				if (str.charAt(0) == 'a') {
					String individualOptionLength[] = str.split(" ");
					if (individualOptionLength.length != 3)
						flag = 0;
				}

				if (str.charAt(0) == 'r') {
					String individualOptionLength[] = str.split(" ");
					if (individualOptionLength.length != 2)
						flag = 0;
				}

			}

		} else if (splitByOptions[0].contains("editneighbor")) {

			for (String str : splitByOptions) {

				str.trim();

				if (str.charAt(0) == 'r' || str.charAt(0) == 'a') {
					String individualOptionLength[] = str.split(" ");
					if (individualOptionLength.length != 3)
						flag = 0;
				}

			}
		}

		if (flag == 1) {
			checkOptionParameters(commandParts);
		} else {
			MapEditorFrame.showError();
		}

	}

	/**
	 * This method checks the validity of parameters passed to the options ex.
	 * editcountry -add a b-remove c is invalid
	 * 
	 * @param commandParts: array of strings obtained by splitting the user entered
	 *                      command
	 */
	private void checkOptionParameters(String[] commandParts) {

		/* flag=0 if parameters are invalid */
		int flag = 1;

		for (int i = 0; i < commandParts.length; i++) {

			if (commandParts[i].equals("-add")) {

				String addCommandParamerters = commandParts[i + 1] + commandParts[i + 2];

				if (addCommandParamerters.contains("-add") || addCommandParamerters.contains("-remove")) {
					flag = 0;
				}

			}

			if (commandParts[i].equals("-remove")) {

				int numberOfArguments = 1;

				if (commandParts[0].equals("editneighbor")) {
					numberOfArguments = 2;
				}

				String removeCommandParameters = "";

				for (int j = 1; j <= numberOfArguments; j++) {
					removeCommandParameters += " " + commandParts[i + j];
				}

				if (removeCommandParameters.contains("-add") || removeCommandParameters.contains("-remove"))
					flag = 0;
			}

		}
		if (flag == 1)
			createCommandList(commandParts);
		else
			MapEditorFrame.showError();
	}

	/**
	 * This method creates the list of commands from the single command entered by
	 * the user
	 * 
	 * @param commandParts: array of strings obtained by splitting the user entered
	 *                      command
	 */
	private void createCommandList(String[] commandParts) {

		for (int i = 0; i < commandParts.length; i++) {

			if (commandParts[i].equals("-add")) {
				String addCommand = commandParts[0];
				for (int j = 0; j < 3; j++) {
					addCommand += " " + commandParts[i];
					i++;
				}
				MapEditorFrame.commandList.add(addCommand);
				i--;
			}

			else if (commandParts[i].equals("-remove")) {

				int numberOfArguments = 2;

				if (commandParts[0].equals("editneighbor"))
					numberOfArguments = 3;

				String removeCommand = commandParts[0];

				for (int j = 0; j < numberOfArguments; j++) {
					removeCommand += " " + commandParts[i];
					i++;
				}

				MapEditorFrame.commandList.add(removeCommand);
				i--;

			}
		}
	}

	/**
	 * This method validates the commands entered in the game play frame
	 * 
	 * @param playCommand    : The command entered by the user
	 * @param playCommandSet : The set of valid commands allowed for the game play
	 *                       phase
	 * @param singleCommandList : singlecommandList                     
	 * @return true if command is valid
	 */
	public static boolean validatePlayCommand(String playCommand, HashSet<String> playCommandSet,
			ArrayList<String> singleCommandList) {

		int flag = 1; /* flag =0 if the command is invalid */
		String[] commandParts = playCommand.split("\\s+");

		if (!playCommandSet.contains(commandParts[0]))
			return false;
		else {
			switch (commandParts[0]) {

			case "gameplayer":
				if (commandParts.length >= 3) {
					if (!(commandParts[1].equals("-add") || commandParts[1].equals("-remove")))
						flag = 0;
				} else {
					flag = 0;
				}
				break;

			case "fortify":
				if (commandParts.length == 2) {
					if (!commandParts[1].equals("none")) {
						flag = 0;
					}
				} else if (commandParts.length != 4) {
					flag = 0;
				} else if (!commandParts[3].matches("\\d+")) {
					flag = 0;
				}
				break;

			case "loadmap":
				if (commandParts.length != 2) {
					flag = 0;
				}
				break;

			case "reinforce":
				if (commandParts.length != 3) {
					flag = 0;
				} else if (!commandParts[2].matches("\\d+")) {
					flag = 0;
				}
				break;

			case "placearmy":
				if (commandParts.length != 2) {
					flag = 0;
				}
				break;
			case "attack":
				if (commandParts.length == 4) {
					if (!commandParts[3].matches("\\d+")) {
						flag = 0;
					}
				} else if (commandParts.length == 5) {
					if (!(commandParts[4].equals("-allout"))) {
						flag = 0;
					}
				} else if (commandParts.length == 2) {
					if (!(commandParts[1].equals("-noattack"))) {
						flag = 0;
					}
				}
				break;
			case "defend":
				if (commandParts.length == 2) {
					if (!commandParts[1].matches("\\d+")) {
						flag = 0;
					}
				} else if(commandParts.length!=2) {
					flag=0;
				}
				break;
			case "attackmove":
				if (commandParts.length == 2) {
					if (!commandParts[1].matches("\\d+")) {
						flag = 0;
					}
				} else if(commandParts.length!=2) {
					flag=0;
				}
				break;
				
			case "exchangecards":
				if (commandParts.length == 4) {
					if (!commandParts[1].matches("\\d+")) {
						flag = 0;
					}
				}if (commandParts.length == 2) {
					if (!commandParts[1].matches("-none")) {
						flag = 0;
					}
				}
				break;
			}
		}

		if (commandParts.length <= 1)
			if (!singleCommandList.contains(commandParts[0]))
				flag = 0;

		if (flag == 0)
			return false;
		else
			return true;
	}

}
