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

import project.riskgame.com.mapeditor.MapFileEditor;

/**
 * interface for mapFileEditor
 * @see MapFileEditor
 * 
 * @author Kshitij	
 * @author Dhaval
 * @version 1.0
 */
public class MapEditorFrame extends JFrame {

	/**
	 * serialVersionUID fixd to 1L.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Japnel for  MapEditorFrame.
	 */
	private JPanel contentPane;
	/**
	 * JTextField for entering the commands.
	 */
	private static JTextField textFieldCommand;
	/**
	 * Hashset for commands to be entered.
	 */
	private HashSet<String> commandSet= new HashSet<String>();
	/**
	 * command entered by player.
	 */
	private String command;
	/**
	 * Label for Mapeditor
	 */
	private JLabel lblMapEditor;
	/**
	 * boolean variable for validation of result.
	 */
	private boolean resultOfValidation;
	/**
	 * arraylist for savingg commandlist.
	 */
	static ArrayList<String> commandList=new ArrayList<String>();
	/**
	 * Object of mapFileEdito class.
	 */
	private MapFileEditor mapFileEditor = MapFileEditor.getInstance();
	
	/**
	 * this method gets the command
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	
	/**
	 * this method sets the command
	 * @param command : command in the map editor frame
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	

	/**
	 * Create the frame.
	 */
	public MapEditorFrame() {
		
		addCommandSet();
		setBounds(100, 100, 851, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldCommand = new JTextField();
		textFieldCommand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldCommand.setBounds(10, 322, 631, 35);
		contentPane.add(textFieldCommand);
		textFieldCommand.setColumns(10);
		
		JButton btnExecute = new JButton("Execute");
		
		btnExecute.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				executeButtonClicked();
	
			}
			
		});
				
		btnExecute.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnExecute.setBounds(655, 321, 163, 35);
		contentPane.add(btnExecute);
		
		lblMapEditor = new JLabel("Map Editor");
		lblMapEditor.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblMapEditor.setBounds(289, 87, 163, 47);
		contentPane.add(lblMapEditor);
	}
	
	
	/**
	 * this method performs the required actions after the execute button is clicked
	 */
	private void executeButtonClicked() {

		command = textFieldCommand.getText();
		command = command.trim();
		commandList.clear();
		
		resultOfValidation = GuiCommandValidation.checkEditorCommand(command,commandSet);
		
		GuiCommandValidation guiCommandValidation = new GuiCommandValidation();
		
		if (resultOfValidation) {

			String[] commandParts = command.split("\\s+");
			if ((commandParts[0].equals("editcountry") || commandParts[0].equals("editcontinent")
					|| commandParts[0].equals("editneighbor"))) {
				guiCommandValidation.checkOption(commandParts, command);
			} else {
				commandList.add(command);
			}
			
			System.out.println(commandList);
			
			String validationFromBackend="";
			
			validationFromBackend = mapFileEditor.executeCommands(commandList);
			
			if (validationFromBackend.equals("")) {
				textFieldCommand.setText("");
			}else {
				textFieldCommand.setText("");
				showError(validationFromBackend);
			}
				
			
		} else {
			showError();
		}

	}
	
	/**
	 * this method displays the pop up error message
	 */
	public static void showError() {
		
		JOptionPane.showMessageDialog(textFieldCommand, "Invalid Command");
	}
	

	/**
	 * this method displays the pop up error message
	 * @param backendErrorMessage: errormessage
	 */
	public static void showError(String backendErrorMessage) {
		JOptionPane.showMessageDialog(textFieldCommand, backendErrorMessage);
	}


	/**
	 * Initialize the commandSet with set of valid commands for Map Editor Frame
	 */
	private void addCommandSet() {
		
		commandSet.add("editcontinent");
		commandSet.add("editcountry");
		commandSet.add("editneighbor");
		commandSet.add("showmap");
		commandSet.add("savemap");
		commandSet.add("editmap");
		commandSet.add("validatemap");
		
	}

}
	
