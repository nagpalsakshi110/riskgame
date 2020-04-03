package project.riskgame.com.mapeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;
import project.riskgame.com.model.Player;

/**
 * checks whether map is connected or not and other validations
 * 
 * @author Devudtt
 * @author Kshitj
 * @author sakshi
 * @author shubham
 * @author Dhaval
 * @version 1.0
 */
public class MapValidator {

	/**
	 * Instance of MapFileReader class using its getInstance method.
	 */
	private static MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * Arraylist that stores the visited countries list.
	 */
	private static List<String> visitedList = new ArrayList<String>();
	/**
	 * Arraylist that stores the list of all the possible paths from a country.
	 */
	private static List<ArrayList<String>> listOfAllPathFromSourceToDestination = new ArrayList<ArrayList<String>>();
	/**
	 * Arraylist of countries that are can be reached from a class in same continent.
	 */
	private static List<String> localPathList = new ArrayList<String>();
	
	/**
	 * This method will check whether the map is connected or not
	 * 
	 * @param map : a graph of all the countries and adjacent countries
	 * @return true if map is connected else return false
	 */
	public static boolean validateMap(Map<String, List<String>> map) {

		ArrayList<String> isVisited = new ArrayList<String>();
		int component = 0;
		for (Entry<String, List<String>> entry : map.entrySet()) {

			String country = entry.getKey();

			if (!isVisited.contains(country)) {
				dfsTraversal(country, map, isVisited);
				component++;
			}
		}

		component = component + (mapFileReader.getCountryList().size() - isVisited.size());

		if (component == 1 && !isAnyContinentEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * This method is used for DFS Traversal
	 * @param country : country id
	 * @param map : a graph of all the countries and adjacent countries
	 * @param isVisited : list of visited countries
	 */
	private static void dfsTraversal(String country, Map<String, List<String>> map, ArrayList<String> isVisited) {
		isVisited.add(country);
		List<String> adjacentCountries = map.get(country);
		for (String adjacentCountry : adjacentCountries) {
			if (!isVisited.contains(adjacentCountry)) {
				dfsTraversal(adjacentCountry,map,isVisited);
			}
		}						
	}

	
	/**
	 * This method checks whether the country is already exists or not
	 * @param countryName : country name
	 * @return true if country exists else return false
	 */
	public static boolean isCountryExists(String countryName) {
		Country country = mapFileReader.getCountryByName(countryName);
		if (country != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method Checks whether continent already exists in the map or not
	 * 
	 * @param continentName : continent name
	 * @return : true or false
	 */
	public static boolean isContinentExists(String continentName) {

		List<Continent> listofContinent = mapFileReader.getContinentList();

		for (Continent continent : listofContinent) {

			if (continent.getName().equals(continentName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This method will check whether control value is valid or not
	 * 
	 * @param controlValue : control value of the continent
	 * @return : true or false
	 */
	public static boolean isControlValueValid(String controlValue) {
		
		if (Integer.parseInt(controlValue) > 0) {
			return true;
		}

		return false;
	}
	
	/**
	 * This method will check if the parameters of command editneighbor are valid or not
	 * @param countryName name of the country
	 * @param neighborName name of the neighbor country
	 * @return true or false
	 */
	public static boolean isNeighbour(String countryName, String neighborName) {
		
		if(isCountryExists(countryName) && isCountryExists(neighborName) && !countryName.equals(neighborName)) {
			
			if(isEdgeExist(countryName,neighborName)) {
				return true;
			}
				
		}
		return false;
	}
	
	
	/**
	 * This method checks if there is an edge between countryName and neighborName exists
	 * 
	 * @param countryName : name of the country 
	 * @param neighborName : name of the neighbor country
	 * @return true if there is an edge ; otherwise return false
	 */
	public static boolean isEdgeExist(String countryName, String neighborName) {
		
		Map<String, List<String>> map = mapFileReader.getMap();
		String countryID = mapFileReader.getCountryByName(countryName).getCountryID();
		String neighborID = mapFileReader.getCountryByName(neighborName).getCountryID();

		if(map.get(countryID) !=null && map.get(countryID).contains(neighborID)) {
			return true;
		}
		
		return false;
	}

	/**
	 * This method checks if any path exists from a specific country to other countries.
	 * 
	 * @param player: name of the player that owns the country
	 * @param fromCountryName : name of the country from which we want to validate the path.
	 * @param toCountryName : name of country we want to check if a path exists.
	 * @return true if there path exists ; otherwise return false
	 */
	public static boolean isPathExists(Player player, String fromCountryName, String toCountryName) {
		
		if(isNeighbour(fromCountryName, toCountryName)) {
			return true;
		}
        
		try {
		getAllPathFromSourceToDestination(player,mapFileReader.getCountryByName(fromCountryName).getCountryID(), mapFileReader.getCountryByName(toCountryName).getCountryID());
		}
		catch(Exception e) {
			
		}
		boolean isPathOwnedByPlayer = checkPathOwnedByPlayer(player);
		return isPathOwnedByPlayer;
	}
	
	/**
	 * This method checks if the path is owned by the player.
	 * 
	 * @param player: name of the player to validate its ownership.
	 * @return true if there ownership exists ; otherwise return false
	 */
	private static boolean checkPathOwnedByPlayer(Player player) {
		for(ArrayList<String> path : listOfAllPathFromSourceToDestination) {
			if(player.getCountriesOwned().containsAll(path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks all the paths possible from a specific country.
	 * 
	 * @param player: name of the player to validate its ownership.
	 * @param source: source country to check the possible paths.
	 * @param destination: destination country we want to validate the path to.
	 * @throws Exception while implementing the method.
	 */
	private static void getAllPathFromSourceToDestination(Player player, String source, String destination) throws Exception  {
		visitedList.add(source);
		
		if(source.equals(destination)) {
			//System.out.println(localPathList);
			listOfAllPathFromSourceToDestination.add(new ArrayList<String>(localPathList));
			visitedList.remove(source);
			throw new Exception();
			//return;
		}
		
		List<String> adjacentNodeList = mapFileReader.getMap().get(source);
		
		for (String node : adjacentNodeList) {
			
			if(!visitedList.contains(node) && player.getCountriesOwned().contains(node)) {
				localPathList.add(node);
				getAllPathFromSourceToDestination(player,node, destination);
				localPathList.remove(node);
			}
			
		}
		
		visitedList.remove(source);
		
	}
	
	
	/**
	 * This method checks whether any of the continent of map is empty or not
	 * @return : true if any continent is empty else return false
	 */
	public static boolean isAnyContinentEmpty() {

		for (Continent continent : mapFileReader.getContinentList()) {

			if (continent.getListofCountires().size() == 0) {
				return true;
			}
		}
		return false;
	}
	
}
