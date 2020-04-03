package project.riskgame.com.model;

import java.util.List;
import java.util.Observable;

/**
 *model information about country
 *
 *@author Dhaval
 *@version 1.0
 */
public class Country extends Observable implements Comparable<Country> {

	/**
	 * variable to store id of a country.
	 */
	private String countryID;
	/**
	 * variable to store name.
	 */
	private String name;
	/**
	 * variable to numberofArmies.
	 */
	private Integer numberofArmies;
	/**
	 * list to store adjacentCountriesIDs.
	 */
	private List<String> adjacentCountriesIDs;
	/**
	 * variable to storecontinentID.
	 */
	private String continentID;
	
	
	/**
	 * constructor od Country class.
	 */
	public Country() {
		this.numberofArmies = 0;
	}
	
	/**
	 * This method returns getAdjacentCountriesIDs
	 * @return List : getAdjacentCountriesIDs
	 */
	public List<String> getAdjacentCountriesIDs() {
		return adjacentCountriesIDs;
	}
	
	/**
	 * This method setadjacentCountriesIDs
	 * @param adjacentCountriesIDs : adjacentCountriesIDs
	 */
	public void setAdjacentCountriesIDs(List<String> adjacentCountriesIDs) {
		this.adjacentCountriesIDs = adjacentCountriesIDs;
	}
	
	/**
	 * this method gets country ID
	 * @return country ID
	 */
	public String getCountryID() {
		return countryID;
	}
	/**
	 * this method sets country ID
	 * @param countryID : ID of the country
	 */
	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}
	/**
	 * this method gets countryName
	 * @return name of the country
	 */
	public String getName() {
		return name;
	}
	/**
	 * this method sets countryName
	 * @param name : name of the country
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * this method gets the numberofArmies
	 * @return number of armies
	 */
	public Integer getNumberofArmies() {
		return numberofArmies;
	}
	/**
	 * this method sets the numberofArmies
	 * @param numberofArmies : armies assigned to the country
	 */
	public void setNumberofArmies(Integer numberofArmies) {
		this.numberofArmies = numberofArmies;
		setChanged();
		notifyObservers();
		
	}
	/**
	 * this method gets the list of adjacent countries IDs
	 * @return list of adjacent countries IDs
	 */
	public List<String> getAdjacentCountries() {
		return adjacentCountriesIDs;
	}
	/**
	 * this method sets the list of adjacent countries
	 * @param adjacentCountriesIDs : list of adjacent countries
	 */
	public void setAdjacentCountries(List<String> adjacentCountriesIDs) {
		this.adjacentCountriesIDs = adjacentCountriesIDs;
	}
	/**
	 * this method gets the continentID in which the country resides
	 * @return continentID
	 */
	public String getContinentID() {
		return continentID;
	}
	/**
	 * this method sets the continentID in which the country resides
	 * @param continentID : ID of the continent
	 */
	public void setContinentID(String continentID) {
		this.continentID = continentID;
	}
	
	/**
	 *This method display country object
	 *@return String that contains information about country object
	 */
	@Override
	public String toString() {
		return "\nCountry [countryID=" + countryID + ", name=" + name + ", numberofArmies=" + numberofArmies
				+ ", adjacentCountries=" + adjacentCountriesIDs + ", continentID=" + continentID + "]\n";
	}
	
	/**
	 *This method compares country objects by countryID
	 *@param object : country object
	 *@return : countryID
	 */
	@Override
	public int compareTo(Country object) {
		Integer country = Integer.parseInt(this.countryID);
		Integer id = Integer.parseInt(object.getCountryID());
		return country.compareTo(id);
	}
	
}
