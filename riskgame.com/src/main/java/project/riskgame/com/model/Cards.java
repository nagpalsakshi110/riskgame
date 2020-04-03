package project.riskgame.com.model;

import java.util.ArrayList;
import java.util.Collections;

import project.riskgame.com.mapeditor.MapFileReader;


/**
 * Model information about cards.
 * 
 * @author Shubham
 * @version 1.1
 *
 */
public class Cards {

	/**
	 * list of cardDeck.
	 */
	private ArrayList<String> cardDeck = new ArrayList<String>();
	/**
	 * Instance of MapFileReader class.
	 */
	private static MapFileReader mapFileReader = MapFileReader.getInstance();
	
	/**
	 * array of types of cards.
	 */
	private String cardTypes[] = { "artillery", "infantry", "cavalry" };
	private static Cards instance; 
	
	/**
	 * Constructor for the Cards class.
	 */
	private Cards() {
		
	}
	
	/**
	 * Setter method for instance.
	 * 
	 * @param card: cards
	 */
	public static void setInstance(Cards card) {
		
		instance = card;
		
	}
	/**
	 * This method returns the instance of Cards class
	 * 
	 * @return instance of Cards class
	 */
	public static Cards getInstance() {
		
		if(instance == null){
            instance = new Cards();
        }
		
		return instance;
	}

	/**
	 * This method prepares carddeck.
	 */
	public void prepareDeck() {
		int numberofCountries = mapFileReader.getCountryList().size();
		int j = 0;
		for (int i = 1; i <= numberofCountries; i++) {
			if (j > 2) {
				j = 0;
			}
			cardDeck.add(cardTypes[j]);
			j++;
		}

		Collections.shuffle(cardDeck);
	}
	
	/**
	 * This method gets the list of cardDeck.
	 * @return List of cardDeck.
	 */
	public ArrayList<String> getCardDeck() {
		return cardDeck;
	}

	
	/**
	 * Setter method for list of cardDeck.
	 * @param cardDeck : cardDeck
	 */
	public void setCardDeck(ArrayList<String> cardDeck) {
		this.cardDeck = cardDeck;
	}

}
