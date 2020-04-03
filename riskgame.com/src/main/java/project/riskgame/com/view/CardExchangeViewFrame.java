package project.riskgame.com.view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Scrollbar;


/**
 * This class will create the CardExchangeView Frame.
 * 
 * @author Shubham
 * @version 1.1
 *
 */

public class CardExchangeViewFrame extends JFrame {

	/**
	 * SerialversionUID variable which is of final type.
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JPanel for CardExchangeViewFrame. 
	 *
	 */
	private JPanel contentPane;
	/**
	 * JTextAreafor entering commands of cardexhance by player.
	 *
	 */
	private JTextArea textArea;

	/**
	 * getter for the TextArea.
	 * 
	 * @return textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}


	/**
	 * Setter for TeaxtArea.
	 * @param textArea : textarea
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}



	/**
	 * Create the frame.
	 */
	public CardExchangeViewFrame() {
		
		setBounds(100, 100, 715, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 49, 656, 559);
		contentPane.add(textArea);
		
		JLabel lblPhaseView = new JLabel("CardExchangeView");
		lblPhaseView.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblPhaseView.setBounds(74, 11, 282, 27);
		contentPane.add(lblPhaseView);
		
		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(672, 48, 17, 529);
		contentPane.add(scrollbar);
		
	
		
		
		
	}
}
