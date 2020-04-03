package project.riskgame.com.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import project.riskgame.com.controller.GamePlayController;
import project.riskgame.com.view.phase.PhaseView;
import project.riskgame.com.view.phase.PlayersWorldDominationView;

/**
 * Interface to the user to play game
 * 
 * @author Sakshi
 * @author Kshitij
 * @version 1.0
 */
public class GamePlayFrame extends JFrame {

	/**
	 * variable serialVersionUID set to 1L
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JPanel for GamePlayFrame.
	 */
	private JPanel contentPane;
	/**
	 * JTextField for entering commands of gameplay phase.
	 */
	private JTextField textPlayCommand;
	/**
	 * playCommandSet for storing the play commands.
	 */
	private HashSet<String> playCommandSet = new HashSet<String>();
	/**
	 * playCommand of the game player commands.
	 */
	private String playCommand;
	/**
	 * resultOfValidation boolean variable for validation of the result.
	 */
	private boolean resultOfValidation;
	/**
	 * playCommand list for storing the commands of game play phase.
	 */
	private ArrayList<String> playCommandList = new ArrayList<String>();
	/**
	 * singleCommandList for storing the list of game play commands.
	 */
	private ArrayList<String> singleCommandList = new ArrayList<String>();
	/**
	 * Object of GamePlayController class.
	 */
	private GamePlayController gamePlayController = new GamePlayController();
	/**
	 * Object of PlayerWorldDominationFrame class.
	 */
	private PlayerWorldDominationFrame playerWorldDominationFrame;
	/**
	 * Object of PhaseViewFrame class.
	 */
	private PhaseViewFrame phaseViewFrame;
	/**
	 * Object of phaseView class using its getInstance method.
	 */
	private PhaseView phaseView = PhaseView.getInstance();
	/**
	 * Object of PlayersWorldDominationView class using its getInstance method.
	 */
	private PlayersWorldDominationView playersWorldDominationView = PlayersWorldDominationView.getInstance();
	
	/**
	 * Create the frame.
	 */
	public GamePlayFrame() {
		
		addplayCommandSet();
		setBounds(100, 100, 906, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblGameplay = new JLabel("Game Play");
		lblGameplay.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblGameplay.setBounds(289, 87, 163, 47);
		contentPane.add(lblGameplay);

		textPlayCommand = new JTextField();
		textPlayCommand.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textPlayCommand.setBounds(26, 329, 666, 35);
		contentPane.add(textPlayCommand);
		textPlayCommand.setColumns(10);

		JButton btnGo = new JButton("Execute");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					executeButtonClicked();
			}
		});
		btnGo.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGo.setBounds(713, 328, 133, 35);
		contentPane.add(btnGo);
		
		
		/* Initializing PhaseViewFrame */
		phaseViewFrame = new PhaseViewFrame();
		phaseViewFrame.setVisible(true);
		phaseViewFrame.setTitle("Phase View");
		phaseView.setPhaseViewFrame(phaseViewFrame);
		
		/* Initializing PWDV */
		playerWorldDominationFrame = new PlayerWorldDominationFrame();
		playerWorldDominationFrame.setVisible(true);
		playerWorldDominationFrame.setTitle("Player World Domination View");
		playersWorldDominationView.setPlayerWorldDominationFrame(playerWorldDominationFrame);
		
		
		
		gamePlayController.setPhaseViewFrame(phaseViewFrame);
		gamePlayController.setWorldDominationFrame(playerWorldDominationFrame);
	}

	/**
	 * This method initializes the command set for the game play phase
	 */
	private void addplayCommandSet() {

		playCommandSet.add("showmap");
		playCommandSet.add("loadmap");
		playCommandSet.add("gameplayer");
		playCommandSet.add("populatecountries");
		playCommandSet.add("placearmy");
		playCommandSet.add("placeall");
		playCommandSet.add("reinforce");
		playCommandSet.add("fortify");
		playCommandSet.add("exchangecards");
		playCommandSet.add("attack");
		playCommandSet.add("defend");
		playCommandSet.add("exchangecards");
		playCommandSet.add("attackmove");
		playCommandSet.add("savegame");
		playCommandSet.add("loadgame");
		playCommandSet.add("exit");
		playCommandSet.add("tournament");

		singleCommandList.add("showmap");
		singleCommandList.add("populatecountries");
		singleCommandList.add("placeall");
		singleCommandList.add("exit");
		singleCommandList.add("tournament");
	}

	
	/**
	 * This method is called when the execute button is clicked
	 */
	private void executeButtonClicked() {
		
		playCommand = textPlayCommand.getText();
		playCommand = playCommand.trim();
		addplayCommandSet();
		playCommandList.clear();

		resultOfValidation = GuiCommandValidation.validatePlayCommand(playCommand, playCommandSet, singleCommandList);

		if (resultOfValidation) {
			
			String[] commandParts = playCommand.split("\\s+");
			
			if (commandParts[0].equals("gameplayer")) {
				
				createPlayCommandList(commandParts);
				
			} else {
				playCommandList.add(playCommand);
			}

			String validationFromBackend = "";
			System.out.println(playCommandList);
			validationFromBackend= gamePlayController.executePlaycommandList(playCommandList);
			if (validationFromBackend.equals("")) {
				textPlayCommand.setText("");
			}
			else {
				textPlayCommand.setText("");
				JOptionPane.showMessageDialog(textPlayCommand, validationFromBackend);
			}
			
		} else {
			showError();
		}

	}

	
	/**
	 * This is a custom method for creating the command list for the command 'gameplayer'
	 * @param commandParts: The array of String formed by splitting the command
	 */
	private void createPlayCommandList(String[] commandParts) {

		for (int i = 0; i < commandParts.length; i++) {

			if (commandParts[i].equals("-add")) {
				String addCommand = commandParts[0];
				for (int j = 0; j < 3; j++) {
					addCommand += " " + commandParts[i];
					i++;
				}
				playCommandList.add(addCommand);
				i--;
			}

			else if (commandParts[i].equals("-remove")) {

				int numberOfArguments = 2;

				
				String removeCommand = commandParts[0];

				for (int j = 0; j < numberOfArguments; j++) {
					removeCommand += " " + commandParts[i];
					i++;
				}

				playCommandList.add(removeCommand);
				i--;

			}
		}
	}
	
	/**
	 * this method displays the pop up error message
	 */
	private void showError() {
		JOptionPane.showMessageDialog(textPlayCommand, "Invalid Command");
	}
}
