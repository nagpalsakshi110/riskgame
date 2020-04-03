package project.riskgame.com.view.phase;

import java.util.Observable;
import java.util.Observer;

import project.riskgame.com.view.PhaseViewFrame;


/**
 * Class is responsible for displaying the phase of the player.
 * 
 * @author Kshitij
 * @author Sakshi
 * @version 1.1
 *
 */

public class PhaseView implements Observer {

	/**
	 * Object of PhaseViewframe.
	 *
	 */
	private PhaseViewFrame phaseViewFrame ;
	/**
	 * Variable message of string type.
	 *
	 */
	private String message;
	
	/**
	 * Instance of PhaseView class.
	 *
	 */
	private static PhaseView instance;
	
	/**
	 * constructor of PhaseView Class
	 */
	private PhaseView() {
	}

	/**
	 * This method returns the instance of PhaseView class
	 * 
	 * @return instance of PhaseView
	 */
	public static PhaseView getInstance() {
		
		if(instance == null){
            instance = new PhaseView();
        }
		
		return instance;
	}

	/**
	 * getter for Message.
	 * 
	 * @return message.
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * setter for Message
	 * @param message : message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * getter for the PhaseViewFrame.
	 *@return phaseViewFrame
	 */
	public PhaseViewFrame getPhaseViewFrame() {
		return phaseViewFrame;
	}


	/**
	 * setter for the PhaseViewFrame.
	 * @param phaseViewFrame : phaseViewFrame Object
	 */
	public void setPhaseViewFrame(PhaseViewFrame phaseViewFrame) {
		this.phaseViewFrame = phaseViewFrame;
	}

  
	
	/**
	 * This method updates the observer
	 * 
	 * @param object: Observable
	 * @param arg: Object 
	 */
	@Override
	public void update(Observable object, Object arg) {
		
		phaseViewFrame.getTextArea().append(message);
		message = "";
	}

	


}
