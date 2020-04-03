package project.riskgame.com.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


/**
 * This class will craete the frame for PhaseView.
 * 
 * @author Sakshi
 * @version 1.1
 *
 */
public class PhaseViewFrame extends JFrame {

	/**
	 * serialVersionUID fixed to 1L.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel for PhaseViewFrame.
	 */
	private JPanel contentPane;
	/**
	 * JTextArea for entering commands for gameplay.
	 */
	private JTextArea textArea;

	/**
	 * This method gets textArea
	 * @return tetArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * This method sets text area
	 * @param textArea textrea to be set
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}



	/**
	 * Create the frame.
	 */
	public PhaseViewFrame() {

		setBounds(100, 100, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea(100, 100);
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);

		JScrollPane jScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(jScrollPane);

	}
}
