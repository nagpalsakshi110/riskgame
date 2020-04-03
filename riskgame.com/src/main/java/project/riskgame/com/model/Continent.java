package project.riskgame.com.model;

import java.util.List;

/**
 *model information about continent
 *
 *@author Devdutt
 *@version 1.0
 */
public class Continent implements Comparable<Continent> {

	/**
	 * continentId variable.
	 */
	private String continentID;
	/**
	 * name variable.
	 */
	private String name;
	/**
	 * control value variable for storing control value of continent.
	 */
	private int controlValue;
	
	/**
	 * variable to store numberOfCountries.
	 */
	private int numberOfCountries;
	
	/**
	 * list for countriesIds.
	 */
	private List<String> listofCountiresIDs;
	
	
	/**
	 * this method gets the continent ID
	 * @return continent ID 
	 */
	public String getContinentID() {
		return continentID;
	}
	/**
	 * this method sets the continent ID
	 * @param continentID : ID of the continent
	 */
	public void setContinentID(String continentID) {
		this.continentID = continentID;
	}
	/**
	 * this method gets the continentName
	 * @return continent name
	 */
	public String getName() {
		return name;
	}
	/**
	 * this method sets the continent name
	 * @param name : Name of the continent
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * this method gets the controlValue of the continent
	 * @return control value
	 */
	public int getControlValue() {
		return controlValue;
	}
	/**
	 * this method sets the controlValue of the continent
	 * @param controlValue : control value of the continent
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	/**
	 * this method gets the numberOfCountries
	 * @return no. of countries
	 */
	public int getNumberOfCountries() {
		return numberOfCountries;
	}
	/**
	 * this method sets the numberOfCountries
	 * @param numberOfCountries : no. of countries the continent has
	 */
	public void setNumberOfCountries(int numberOfCountries) {
		this.numberOfCountries = numberOfCountries;
	}
	/**
	 * this method gets the list of countries IDs
	 * @return list of countries IDs
	 */
	public List<String> getListofCountires() {
		return listofCountiresIDs;
	}
	/**
	 *  this method sets the list of countries IDs
	 * @param listofCountiresIDs : list of countries that resides in the continent
	 */
	public void setListofCountires(List<String> listofCountiresIDs) {
		this.listofCountiresIDs = listofCountiresIDs;
	}
	
	/**
	 *This method display continent object
	 *@return : String that contains information about continent object
	 */
	@Override
	public String toString() {
		return "\nContinent [continentID=" + continentID + ", name=" + name + ", controlValue=" + controlValue
				+ ", numberOfCountries=" + numberOfCountries + ", listofCountires=" + listofCountiresIDs + "]\n";
	}
	
	/**
	 *This method compares continent objects by continentID
	 *@param object : continent object
	 *@return continentID
	 */
	@Override
	public int compareTo(Continent object) {
		Integer country = Integer.parseInt(this.continentID);
		Integer id = Integer.parseInt(object.getContinentID());
		return country.compareTo(id);
	}
	
	
	
}
