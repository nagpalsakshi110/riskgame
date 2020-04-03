package project.riskgame.com.view.phase;

import java.util.Observable;
import java.util.Observer;

import project.riskgame.com.view.CardExchangeViewFrame;

/**
 * This class will give the frame for exchanging of cards.
 * 
 * @author Dhaval
 * @version 1.1
 *
 */


public class CardExchangeView implements Observer {

	/**
	 * Instance of CaradExchnageViewframe.
	 *
	 */
	private CardExchangeViewFrame cardExchangeViewFrame ;
	/**
	 * Variable message of string type.
	 *
	 */
	private String message;
	/**
	 * Instance of CardExchangeView.
	 *
	 */
	public static CardExchangeView cardInstance;
	

	/**
	 * This method returns CardExchangeViewFrame
	 * @return cardExchangeViewFrame
	 */
	public CardExchangeViewFrame getCardExchangeViewFrame() {
		return cardExchangeViewFrame;
	}


	/**
	 * This method sets CardExchangeViewFrame to current frame
	 * @param cardExchangeViewFrame frame to be set
	 */
	public void setCardExchangeViewFrame(CardExchangeViewFrame cardExchangeViewFrame) {
		this.cardExchangeViewFrame = cardExchangeViewFrame;
	}


	/**
	 * This method returns the message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * This method sets the message
	 * @param message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * constructor of CardExchangeView Class
	 */
	private CardExchangeView ()
	{
		
	}
	

	/**
	 * This method returns the instance of CardExchangeView class
	 * 
	 * @return instance cardInstance. 
	 */
	public static CardExchangeView getInstance()
	{
		if(cardInstance==null)
		{
			cardInstance = new CardExchangeView();
		}
		return cardInstance;
	}

	/**
	 * This method updates the observer.
	 * 
	 * @param o: Observable
	 * @param arg: Object 
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		cardExchangeViewFrame.getTextArea().append(message);
		message = "";
		
	}
	
}
