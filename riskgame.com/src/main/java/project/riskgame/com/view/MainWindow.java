package project.riskgame.com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * interface to the user to start a new game or edit a map
 * 
 * @author Sakshi
 * @version 1.0
 */
public class MainWindow {
	
	/**
	 * frame of the MainWindow.
	 */
	private JFrame frame;
	
	
	/**
	 * This method gets frame
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * This method sets frame
	 * @param frame : frame 
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JButton mapEditorBtn = new JButton("Map Editor");
		JButton newGamebtn = new JButton("New Game");
		newGamebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GamePlayFrame gamePlay =  new GamePlayFrame();
				gamePlay.setVisible(true);
				gamePlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(false);
				
			}
		});
		newGamebtn.setBounds(149, 82, 112, 23);
		frame.getContentPane().add(newGamebtn);
		
		/**
		 * Action Listener method for mapEditor button.
		 */
		mapEditorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MapEditorFrame mapEditor = new MapEditorFrame();
				mapEditor.setVisible(true);
				MainWindow window = new MainWindow();
				window.getFrame().setVisible(false);
			}
		});
		mapEditorBtn.setBounds(149, 155, 112, 23);
		frame.getContentPane().add(mapEditorBtn);
	}
}
