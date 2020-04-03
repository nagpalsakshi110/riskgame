package project.riskgame.com.mapeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;

/**
 * This class reads the map file
 * 
 * @author Dhaval
 * @author Kshitij
 * @author Shubham
 * @version 1.0
 */
public class MapFileReader implements IMapFileReader {

	/**
	 * Instance of Mapfile reader class. 
	 *
	 */
	private static MapFileReader instance; 
	/**
	 * Location of the map file from where it has t be loaded.
	 *
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/";
	/**
	 * SCanner initialized for taking user input.
	 *
	 */
	private Scanner sc;
	/**
	 * list of countries.
	 *
	 */
	private List<Country> countryList = new ArrayList<>();
	/**
	 *list of countries.
	 *
	 */
	private List<Continent> continentList = new ArrayList<>();
	/**
	 * Hashmap to save the mapfile with countries and neighbouring countries.
	 *
	 */
	private Map<String,List<String>> map = new LinkedHashMap<>();
	
	/**
	 * constructor of MapFileReader Class
	 */
	private MapFileReader(){	
	}
	

	
	/**
	 * This method returns countryList
	 * @return list of Country
	 */
	public List<Country> getCountryList() {
		return countryList;
	}

	/**
	 * This method set countryList
	 * @param countryList : list of Country
	 */
	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	/**
	 * This method returns continentList
	 * @return list of Continent 
	 */
	public List<Continent> getContinentList() {
		return continentList;
	}

	
	/**
	 * This method sets continentList
	 * @param continentList : list of Continent
	 */
	public void setContinentList(List<Continent> continentList) {
		this.continentList = continentList;
	}

	/**
	 * This method returns map
	 * @return map map file is returned
	 */
	public Map<String, List<String>> getMap() {
		return map;
	}

	/**
	 * This method sets the map
	 * @param map map file which is read
	 */
	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}

	/**
	 * This method returns the instance of MapFileReader class
	 * 
	 * @return instance of MapFileReader
	 */
	public static MapFileReader getInstance() {
		
		if(instance == null){
            instance = new MapFileReader();
        }
		
		return instance;
	}
	
	
	/**
	 * This method reads map file from resource folder
	 * 
	 * @param fileName : name of the file
	 * @throws Exception on readMapFile
	 */
	@Override
	public void readMapFile(String fileName) throws Exception {

		System.out.println("started reading map file at -- " + CLASSPATH + fileName);

		String path = CLASSPATH + fileName;
		File file = new File(path);
		sc = new Scanner(file);

		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.equals("[continents]")) {
				readContinents();
			}
			if (nextLine.equals("[countries]")) {
				readCountries();
			}
			if (nextLine.equals("[borders]")) {
				readBorders();
			}
		}

		updateCountryList(countryList);
		updateContinentList(countryList, continentList);

		sc.close();
		System.out.println("successfully read map file " + fileName);
	}
	
	
	
	/**
	 * Method for reading the mapfile of conquest format.
	 * @param fileName: name of the file
	 * @throws Exception while execution
	 */
	public void readMapFileConquest(String fileName) throws Exception {

		System.out.println("started reading map file at -- " + CLASSPATH + fileName);

		String path = CLASSPATH + fileName;
		File file = new File(path);
		sc = new Scanner(file);

		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if (nextLine.equals("[Continents]")) {
				readContinentsConquest();
			}
			if (nextLine.equals("[Territories]")) {
				readCountriesConquest();
			}
			
		}
		beginToAddAdjacentConquestCountries(fileName);
		sc.close();
		System.out.println("successfully read map file " + fileName);
	}
	
	
	/**
	 * This method updates list of countries in continent object
	 * 
	 * @param countryList : list of countries
	 * @param continentList : list of continents
	 */
	public void updateContinentList(List<Country> countryList, List<Continent> continentList) {

		
		for (Continent continent : continentList) {
			
			List<String> list = new ArrayList<>();
			
			for (Country country : countryList) {
				if(country.getContinentID().equals(continent.getContinentID())) {
					list.add(country.getCountryID());
				}
			}
			continent.setListofCountires(list);
			continent.setNumberOfCountries(list.size());
		}
		
	}
  
 
	/**
	 * This method updates adjacent countries in country object
	 * 
	 * @param countryList : list of countries
	 */
	public void updateCountryList(List<Country> countryList) {
		
		for (Country country : countryList) {
			country.setAdjacentCountries(map.get(country.getCountryID()));
		}	
		
	}
	

	/**
	 * This method read borders of country and add adjacent countries to it
	 */
	private void readBorders() {

		while(sc.hasNextLine()){
			String[] data = sc.nextLine().trim().split("\\s+");
			addAdjacentCountries(data);
		}
		
	}

	
	
	/**
	 * This method creates graph for the country with the adjacent countries
	 * 
	 * @param data : line from map file
	 */
	private void addAdjacentCountries(String[] data) {

		String countryID = data[0];
		ArrayList<String> listOfAdjacentCountries = getListOfAdjacentCountries(data);
		if(map.containsKey(countryID)) {
			map.get(countryID).addAll(listOfAdjacentCountries);
		}else {
			map.put(countryID, listOfAdjacentCountries);
		}
		
	}

	/**
	 * This method will add the adjacent countries into a list of a file to be updated..
	 * 
	 * @param fileName
	 */
	private void beginToAddAdjacentConquestCountries(String fileName) {

		String path = CLASSPATH + fileName;
		File file = new File(path);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();

			if (nextLine.equals("[Territories]")) {


				for (int i = 0; i < continentList.size(); i++) {

					nextLine = sc.nextLine().trim();
					while (!nextLine.isEmpty()) {

						String[] data = nextLine.split(",");
						addAdjacentCountriesOfConquestMap(data);
						nextLine = sc.nextLine().trim();
					}
					

				
				}
				
			}

		}
		updateContinentList(countryList, continentList);
		updateCountryList(countryList);

		sc.close();

	}


	/**
	 * This method will add adjacent countries in conquest format.
	 * 
	 * @param data
	 */
	private void addAdjacentCountriesOfConquestMap(String[] data) {
		String countryID = getCountryByName(data[0]).getCountryID();
		ArrayList<String> listOfAdjacentCountries;

		if (map.containsKey(countryID)) {
			listOfAdjacentCountries = (ArrayList<String>) map.get(countryID);
			for (int i = 4; i < data.length; i++) {
				String adjCountryID = getCountryByName(data[i]).getCountryID();
				listOfAdjacentCountries.add(adjCountryID);
			}

		} else {
			listOfAdjacentCountries = new ArrayList<String>();
			for (int i = 4; i < data.length; i++) {
				String adjCountryID = getCountryByName(data[i]).getCountryID();
				listOfAdjacentCountries.add(adjCountryID);
			}
			map.put(countryID, listOfAdjacentCountries);
		}

	}

	/**
	 * This method updates adjacent countries list
	 * 
	 * @param data : line from map file
	 * @return list of adjacent countries
	 */
	private ArrayList<String> getListOfAdjacentCountries(String[] data) {

		ArrayList<String> listOfAdjacentCountries = new ArrayList<>();
		for (int i = 1; i < data.length; i++) {
			listOfAdjacentCountries.add(data[i]);
		}
		return listOfAdjacentCountries;
	}

	
	
	/**
	 * This method reads country from map file
	 */
	private void readCountries() {
		String nextline = sc.nextLine().trim();
		while (!nextline.isEmpty()) {

			String[] data = nextline.split("\\s+");
			addCountryData(data);
			nextline = sc.nextLine().trim();
		}
		
	}
	
	/**
	 * This ,ethod will read the countries from the file.
	 */
	private void readCountriesConquest() {
		String nextline;
		int countryID = 0;
		for (Continent continent : continentList) {
			nextline = sc.nextLine().trim();
			while (!nextline.isEmpty()) {
				countryID++;
				String[] data = nextline.split(",");
				addCountryDataConquest(data, continent.getContinentID(), countryID);
				nextline = sc.nextLine().trim();
			}
			
		}
		
		
	}

	
	/**
	 * This method adds country to countryList
	 * 
	 * @param data : line from map file
	 */
	private void addCountryData(String[] data) {
	
		Country country = new Country();
		country.setCountryID(data[0]);
		country.setName(data[1]);
		country.setContinentID(data[2]);
		countryList.add(country);
		
	}

	
	/**
	 * This method will add the country data into conquest file.
	 * 
	 * @param data
	 * @param continentID
	 * @param countryID
	 */
	private void addCountryDataConquest(String[] data, String continentID, int countryID) {
		
		Country country = new Country();
		country.setName(data[0]);
		country.setContinentID(continentID);
		country.setCountryID(String.valueOf(countryID));
		countryList.add(country);
		
	}
	
	
	/**
	 * This method reads continent from map file
	 */
	private void readContinents() {
		
		String nextline = sc.nextLine().trim();
		int continentID = 1;
		while(!nextline.isEmpty()) {
			
			String[] data = nextline.split("\\s+");
			
			addContinentData(data, continentID);
			continentID++;
			nextline = sc.nextLine().trim();
		}
		
	}

	
	/**
	 * This method be responsible for reading the continents of conquest file.
	 */
	private void readContinentsConquest() {
		
		String nextline = sc.nextLine().trim();
		int continentID = 1;
		while(!nextline.isEmpty()) {
			
			String[] data = nextline.split("=");
			
			addContinentData(data, continentID);
			continentID++;
			nextline = sc.nextLine().trim();
		}
		
	}

	
	/**
	 * This method adds continent to continentList
	 * 
	 * @param data : line from map file
	 * @param continentID : id of the continent
	 */
	private void addContinentData(String[] data,int continentID) {

		String continentName = data[0];
		int controlValue = Integer.parseInt(data[1]);
		
		Continent continent = new Continent();
		continent.setContinentID(String.valueOf(continentID));
		continent.setName(continentName);
		continent.setControlValue(controlValue);
		
		continentList.add(continent);
		
	}
	
	
	
	/**
	 * This method returns the continent object from continent name
	 * 
	 * @param continentName : name of the continent
	 * @return continent object for the mentioned continent
	 */
	public Continent getContinentByName(String continentName){
		
		for (Continent continent : continentList) {
			
			if(continent.getName().equals(continentName)) {
				return continent;
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * This method returns the continent object from continent id
	 * 
	 * @param continentID : id of the continent
	 * @return Continent object for given id
	 */
	public Continent getContinentByID(String continentID) {

		for (Continent continent : continentList) {

			if (continent.getContinentID().equals(continentID)) {
				return continent;
			}
		}

		return null;
	}
	
	
	
	/**
	 * This method returns the country object from country name
	 * 
	 * @param countryName : name of the country
	 * @return Country object for given id
	 */
	public Country getCountryByName(String countryName) {

		for (Country country : countryList) {

			if (country.getName().equals(countryName)) {
				return country;
			}
		}

		return null;
	}
	
	
	
	/**
	 * This method returns the country object from country ID
	 * 
	 * @param countryID : id of the country
	 * @return Country object for given id
	 */
	public Country getCountryByID(String countryID) {

		for (Country country : countryList) {

			if (country.getCountryID().equals(countryID)) {
				return country;
			}
		}

		return null;
	}
	
	/**
	 * Setter for instance.
	 * 
	 * @param mapFileReader: MapFileReader object
	 */
	public static void setInstance(MapFileReader mapFileReader) {
		instance = mapFileReader;
	}
	
	/**
	 * this method will clear all the data that is loaded from the map file
	 */
	public void clearAll() {
		countryList.clear();
		continentList.clear();
		map.clear();
	}

	
}
