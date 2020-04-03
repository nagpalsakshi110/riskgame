package project.riskgame.com.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

/**
 * This class will create the frame for PlayerWorldDomination view.
 * 
 * @author Kshitij
 * @version 1.1
 *
 */
public class PlayerWorldDominationFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	
	/**
	 * JPanel for PlayerWorldDominationFrame. 
	 */
	private JPanel contentPane;
	/**
	 * JTextArea for PlayerWorldDominationFrame. 
	 */
	private JTextArea textArea;
	
	/**
	 * getter for the TextArea.
	 *@return textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}


	
	/**
	 * setter for the TextArea.
	 * @param textArea : textArea
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}


	/**
	 * Create the frame.
	 */
	public PlayerWorldDominationFrame() {
		
		setBounds(100, 100, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea(100,100);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);
		
		JScrollPane jScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(jScrollPane);
		
	}
}
