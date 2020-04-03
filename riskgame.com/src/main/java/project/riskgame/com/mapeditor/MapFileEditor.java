package project.riskgame.com.mapeditor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import project.riskgame.com.model.Continent;
import project.riskgame.com.model.Country;

/**
 * This class edits map file and performs various operations on it .
 * 
 * @author sakshi
 * @author Kshitij
 * @author Devdutt
 * @author Shubham
 * @author Dhaval
 * @version 1.0
 */
public class MapFileEditor implements IMapFileEditor{


	/**
	 * Instance of MapFileReader class.
	 */
	private MapFileReader mapFileReader = MapFileReader.getInstance();
	/**
	 * Instance of FileWriter created.
	 */
	private FileWriter writer;
	/**
	 * Final classpath of for location of games.
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/";
	/**
	 * Boolean variable to check whether the file is loaded or net. Default set to false.
	 */
	private boolean isFileLoaded = false;
	/**
	 * fileName to be read of String type.
	 */
	private String fileName;
	/**
	 * message to be displayed to the player.
	 */
	private String message = "";
	/**
	 * Instance of MapFileEditor class.
	 */
	private static MapFileEditor instance;
	/**
	 * constructor of MapFileEditor class.
	 */
	private MapFileEditor() {
		
	}
	
	/**
	 * Lazy initialization of Singelton MapFileEditor class.
	 * @return instance of the class.
	 */
public static MapFileEditor getInstance() {
		
		if(instance == null){
            instance = new MapFileEditor();
        }
		
		return instance;
	}
	
	
	
	/**
	 * this method gets the file name
	 * @return String filename
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * this method sets the file name
	 * @param fileName: name of the file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * this method sets the message
	 * 
	 * @param message : message to send
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * this method gets the message value
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * this method check if a file is loaded into map editor or not
	 * 
	 * @return true if the file is loaded else return false
	 */
	public boolean isFileLoaded() {
		return isFileLoaded;
	}

	/**
	 * this method sets isFileLoaded
	 * 
	 * @param isFileLoaded : true if file is loaded else false
	 */
	public void setFileLoaded(boolean isFileLoaded) {
		this.isFileLoaded = isFileLoaded;
		
	}

	/**
	 * This method will remove the adjacent country
	 * @param countryName : name of the country
	 * @param adjacentCountryName : name of the adjacent country
	 */
	private void removeNeighbour(String countryName, String adjacentCountryName) {
		
		Map<String, List<String>> map = mapFileReader.getMap();
		
		String countryID = mapFileReader.getCountryByName(countryName).getCountryID();
		String adjacentCountryID = mapFileReader.getCountryByName(adjacentCountryName).getCountryID();
		
		if(map.containsKey(countryID)) {
			map.get(countryID).remove(adjacentCountryID);
		}
		
		if(map.containsKey(adjacentCountryID)) {
			map.get(adjacentCountryID).remove(countryID);
		}
		
		/* update the adjacent country list whenever there is edge added/removed between two countries */
		mapFileReader.updateCountryList(mapFileReader.getCountryList());
		System.out.println("Successfully removed neighbour "+countryName+" from "+adjacentCountryName);
	}


	/**
	 * This method will add the adjacent country
	 * @param countryName : name of the country
	 * @param adjacentCountryName : name of the adjacent country
	 */
	private void addNeighbour(String countryName, String adjacentCountryName) {
		
		Map<String, List<String>> map = mapFileReader.getMap();
		String countryID = mapFileReader.getCountryByName(countryName).getCountryID();
		String adjacentcountryID = mapFileReader.getCountryByName(adjacentCountryName).getCountryID();
		
		if(map.containsKey(countryID)) {
			map.get(countryID).add(adjacentcountryID);
		}else {
			ArrayList<String> al = new ArrayList<>();
			al.add(adjacentcountryID);
			map.put(countryID, al);
		}
		
		if(map.containsKey(adjacentcountryID)) {
			map.get(adjacentcountryID).add(countryID);
		}else {
			ArrayList<String> al = new ArrayList<>();
			al.add(countryID);
			map.put(adjacentcountryID, al);
		}
		
		/* update the adjacent country list whenever there is edge added/removed between two countries */
		mapFileReader.updateCountryList(mapFileReader.getCountryList());
		System.out.println("Successfully added neighbour "+countryName+" to "+adjacentCountryName);
	}

	/**
	 * This method will either call add country or remove country
	 * @param command : Input entered by the user
	 */
	public void editCountry(String command) {

		String[] strings = command.split("\\s+");

		if ("-add".equals(strings[1])) {
			String countryName = strings[2];
			String continentName = strings[3];
			addCountry(countryName, continentName);
			System.out.println("Successflly added "+countryName+" to country list");
		} else if ("-remove".equals(strings[1])) {
			String countryName = strings[2];
			removeCountry(countryName);
			System.out.println("Successflly removed "+countryName+" from country list");
		}

	}

	
	/**
	 * This method will remove the country from the map
	 * @param countryName : name of the country to be removed
	 */
	private void removeCountry(String countryName) {
		
		Country country = mapFileReader.getCountryByName(countryName);
		
		String continentID = country.getContinentID();
		Continent continent = mapFileReader.getContinentByID(continentID);
		
		continent.getListofCountires().remove(country.getCountryID());
		int numberOfCountries = continent.getNumberOfCountries();
		continent.setNumberOfCountries(numberOfCountries-1);
		
		/* 
		remove continent if the country is the last country in that continent 
		
		if(continent.getListofCountires().isEmpty()) {
			removeContinent(continent.getName());
		}
		*/
		
		mapFileReader.getCountryList().remove(country);
		Map<String, List<String>> map = mapFileReader.getMap();
		
		String countryID = country.getCountryID();
		
		List<String> list = map.get(countryID);
		
		for (String key : list) {
			map.get(key).remove(countryID);
		}
		
		map.remove(countryID);
		
		/* update the adjacent country list whenever there is change in map */
		mapFileReader.updateCountryList(mapFileReader.getCountryList());
		System.out.println(countryName+" successfully removed from Map");
	}


	
	/**
	 * This method will add country in the map
	 * @param countryName : name of the country to be added in the map
	 * @param continentName : name of the continent in which country is to be added
	 */
	private void addCountry(String countryName, String continentName) {

		Country country = new Country();
		String countryID;
		if (mapFileReader.getCountryList().isEmpty()) {
			countryID = "1";
		} else {
			Country max = Collections.max(mapFileReader.getCountryList());
			int maxCountryID = Integer.parseInt(max.getCountryID());
			countryID = String.valueOf(maxCountryID + 1);
		}
		
		country.setCountryID(countryID);
		country.setName(countryName);
		
		Continent continent = mapFileReader.getContinentByName(continentName);
		country.setContinentID(continent.getContinentID());
		
		mapFileReader.getCountryList().add(country);
		
		int numberOfCountries = continent.getNumberOfCountries();
		continent.setNumberOfCountries(numberOfCountries + 1);
		
		Map<String, List<String>> map = mapFileReader.getMap();
		
		/* add country to the map with empty list */
		map.put(countryID, new ArrayList<String>());
		
		/* update the adjacent country list whenever there is change in map */
		mapFileReader.updateCountryList(mapFileReader.getCountryList());
		
		
		mapFileReader.updateContinentList(mapFileReader.getCountryList(), mapFileReader.getContinentList());
		System.out.println(countryName+" successfully added to Map");
	}

	/**
	 * This method will either call addContinent or removeContinent
	 * @param command : Input entered by the user
	 */
	public void editContinent(String command) {

		String[] strings = command.split("\\s+");

		if ("-add".equals(strings[1])) {
			String continentName = strings[2];
			String controlValue = strings[3];
			addContinent(continentName, controlValue);
			System.out.println("successfully added "+ continentName+ " to the List of continents.");
			
		}else if("-remove".equals(strings[1])) {
			String continentName = strings[2];
			removeContinent(continentName);
			System.out.println("successfully removed  "+ continentName+ " from the  List of continents.");
		}
		
	}

	
	/**
	 * This method will remove continent from the map
	 * @param continentName : Name of the continent to be removed
	 */
	private void removeContinent(String continentName) {
		
		Continent continent = mapFileReader.getContinentByName(continentName);

		/* Created new Array list to avoid ConcurrentModificationException */
		List<String> listofCountiresIDs = new ArrayList<>(continent.getListofCountires());

		for (String countryID : listofCountiresIDs) {

			Country country = mapFileReader.getCountryByID(countryID);
			String countryName = country.getName();
			removeCountry(countryName);

		}
		mapFileReader.getContinentList().remove(continent);
		System.out.println("successfully removed  "+ continentName+ " from Map");
	}
	

	/**
	 * This method will add continent in the map
	 * @param continentName :  name of the country to be added
	 * @param controlValue : control value of the continent to be added
	 */
	private void addContinent(String continentName,String controlValue ) {
		
		Continent continent = new Continent();
		int newContinentID;
		if (mapFileReader.getContinentList().isEmpty()) {
			newContinentID = 1;
		} else {
			Continent maxContinent = Collections.max(mapFileReader.getContinentList());
			int maxContinentID = Integer.parseInt(maxContinent.getContinentID());
			newContinentID = maxContinentID + 1;
		}
		
		continent.setContinentID(String.valueOf(newContinentID));
		continent.setName(continentName);
		
		int controlvalue = Integer.parseInt(controlValue);
		continent.setControlValue(controlvalue);
		
		mapFileReader.getContinentList().add(continent);
		
		mapFileReader.updateContinentList(mapFileReader.getCountryList(), mapFileReader.getContinentList());
		System.out.println("successfully added  "+ continentName+ " to the Map");
	}
	
	
	/**
	 * This method checks the classpath and opens the file if it exists else create a new file at the same path
	 *  
	 * @param fileName : name of the file
	 * @throws Exception on editMap()
	 */
	public void editMap(String fileName) throws Exception {

		/* check if file already exist */
		String path = CLASSPATH + fileName;
		File file = new File(path);
		setFileName(fileName);
		IMapFileReader dominationToConquestAdapter = new DominationToConquestAdapter();

		if (file.exists()) {

			/* if yes file is now loaded */
			if (fileName.endsWith(".map")) {
				mapFileReader.readMapFile(fileName);
			} else {
				dominationToConquestAdapter.readMapFile(fileName);
			}

			setFileLoaded(true);

		} else {

			/* if no create new file and load the file */
			System.out.println("created new file " + fileName + " at " + CLASSPATH);
			file.createNewFile();
			setFileLoaded(true);

		}

	}
	
	
	/**
	 * This method will save the map in a text file
	 * @param fileName: name of the file
	 * @throws Exception on saveMap()
	 */
	@Override
	public void saveMap(String fileName) throws Exception {

		System.out.println("started saving map file at -- " + CLASSPATH + fileName);

		String path = CLASSPATH + fileName;
		File file = new File(path);

		writer = new FileWriter(file);
		writeHeaders(fileName);
		writer.write("\n");
		writer.write("\n");

		writeContinents();
		writeCountries();
		writeBorders();

		writer.close();
		mapFileReader.clearAll();
		setFileLoaded(false);
		setFileName(null);
		
		System.out.println("Map has been saved Successfully");

	}
	
	
	/**
	 * This method will save the map in conquest format.
	 * 
	 * @param fileName: name of the file
	 * @throws Exception throws exception while saving the file
	 */
	public void saveConquestMap(String fileName) throws Exception {

		System.out.println("started saving Conquest map file at -- " + CLASSPATH + fileName);

		String path = CLASSPATH + fileName;
		File file = new File(path);

		writer = new FileWriter(file);
		writeConquestHeaders(fileName);
		writer.write("\n");
		writer.write("\n");

		writeConquestContinents();
		writeConquestCountries();

		writer.close();
		mapFileReader.clearAll();
		setFileLoaded(false);
		setFileName(null);
		
		System.out.println("Map has been saved Successfully");

	}

	
	
	/**
	 * this method writes the file on the disk
	 * @param fileName: name of the file
	 * @throws Exception on writeHeaders() 
	 */
	private void writeHeaders(String fileName) throws Exception {

		writer.write("name ");
		writer.write(fileName);
		writer.write("\n");
		writer.write("\n");
		writer.write("[files]");
	}

	/**
	 * This method will write the header in conquest format.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void writeConquestHeaders(String fileName) throws Exception {

		writer.write("name ");
		writer.write(fileName);
		writer.write("\n");
		writer.write("\n");
		writer.write("[Map]");
	}
	
	
	/**
	 * This method will write continents in the file
	 * @throws Exception on writeContinents()
	 */
	private void writeContinents() throws Exception {
		
		writer.write("[continents]");
		writer.write("\n");
		List<Continent> listofContinents = mapFileReader.getContinentList();
		
		for (Continent continent : listofContinents) {
			writer.write(continent.getName());
			writer.write(" ");
			writer.write(Integer.toString(continent.getControlValue()));
			writer.write("\n");
		}

	}
	
/**
 * This method will write the continents in file in conquest format.
 * 
 * @throws Exception
 */
private void writeConquestContinents() throws Exception {
		
		writer.write("[Continents]");
		writer.write("\n");
		List<Continent> listofContinents = mapFileReader.getContinentList();
		
		for (Continent continent : listofContinents) {
			writer.write(continent.getName());
			writer.write("=");
			writer.write(Integer.toString(continent.getControlValue()));
			writer.write("\n");
		}

	}

	/**
	 * This method will write countries in the file
	 * @throws Exception on writeCountries()
	 */
	private void writeCountries() throws Exception {
		
		writer.write("\n");
		writer.write("[countries]");
		writer.write("\n");
		List<Country> listofCountries = mapFileReader.getCountryList();
		
		for (Country country : listofCountries) {

			int index = listofCountries.indexOf(country);
			writer.write(Integer.toString(++index));
			writer.write(" ");
			writer.write(country.getName());
			writer.write(" ");
			writer.write(country.getContinentID());
			writer.write(" ");
			writer.write("0");
			writer.write(" ");
			writer.write("0");
			writer.write("\n");

		}
	}
	
	/**
	 * This method will write countries in file in conquest format.
	 * 
	 * @throws Exception
	 */
	private void writeConquestCountries() throws Exception {

		writer.write("\n");
		writer.write("[Territories]");
		writer.write("\n");
		List<Continent> listofContinents = mapFileReader.getContinentList();

		for (Continent continent : listofContinents) {
			for (String countryID : continent.getListofCountires()) {
				Country country = mapFileReader.getCountryByID(countryID);
				writer.write(country.getName());
				writer.write(",,,");
				String continentName = mapFileReader.getContinentByID(country.getContinentID()).getName();
				writer.write(continentName);

				List<String> adjacentCountriesIDs = country.getAdjacentCountries();
				for (String adjCountryID : adjacentCountriesIDs) {
					writer.write(",");
					writer.write(mapFileReader.getCountryByID(adjCountryID).getName());
				}
				writer.write("\n");
			}

			writer.write("\n");
		}

	}
	
	/**
	 * This method will write adjacent countries in the file
	 * @throws Exception on writeBorders()
	 */
	private void writeBorders() throws Exception {
		
		writer.write("\n");
		writer.write("[borders]");
		writer.write("\n");

		List<Country> listofCountries = mapFileReader.getCountryList();
		
		for (Country country : listofCountries) {

			writer.write(country.getCountryID());
			writer.write(" ");
			List<String> listofadjacentCountries = country.getAdjacentCountries();

			int size = listofadjacentCountries.size();
			for (String adjacentCountry : listofadjacentCountries) {
				writer.write(adjacentCountry);
				if(size>1)
				writer.write(" ");
				size--;
			}

			writer.write("\n");

		}
	}
	
	
	
	/**
	 * This method executes the commands typed in the map editor screen
	 * 
	 * @param commands: Command to mapEditor
	 * @return the message
	 */
	public String executeCommands(List<String> commands)  {
		
		/* set the message to blank before each execute command */
		setMessage("");
		
		for (String command : commands) {
			try {
				executeCommand(command);
			} catch (Exception e) {
				System.out.println("Error occured : "+e.getClass().getName()+" - "+e.getMessage());
			}
		}
		
		return getMessage();
	}

	
	
	/**
	 * this method is used to execute command by calling the commands specific methods
	 * @param command :command to edit map file
	 * @throws Exception on executeCommand()
	 */
	private void executeCommand(String command) throws Exception {
		
		String[] commands = command.split("\\s+");
		/* set the message to blank before each execute command */
		setMessage("");
		
		if("editcontinent".equals(commands[0]) && isFileLoaded()) {
			
			if ("-add".equals(commands[1])) {
				String continentName = commands[2];
				String controlValue = commands[3];
				
				if(!MapValidator.isContinentExists(continentName)
						&& MapValidator.isControlValueValid(controlValue)) {
					addContinent(continentName, controlValue);
				}else {
					setMessage("Invalid Command -- "+ command);
				}
				
			} else if ("-remove".equals(commands[1])) {

				String continentName = commands[2];

				if (MapValidator.isContinentExists(continentName)) {
					removeContinent(continentName);
				}else {
					setMessage("Invalid Command -- " + command);
				}
				
			}
			
		} else if ("editcountry".equals(commands[0]) && isFileLoaded()) {

			if ("-add".equals(commands[1])) {
				
				String countryName = commands[2];
				String continentName = commands[3];

				if (MapValidator.isContinentExists(continentName) && !MapValidator.isCountryExists(countryName)
						&& !countryName.equals(continentName)) {
					addCountry(countryName, continentName);
				} else {
					setMessage("Invalid Command -- " + command);
				}

			} else if ("-remove".equals(commands[1])) {
				
				String countryName = commands[2];
				
				if (MapValidator.isCountryExists(countryName)) {
					removeCountry(countryName);
				} else {
					setMessage("Invalid Command -- " + command);
				}

			}

		}else if("editneighbor".equals(commands[0]) && isFileLoaded()) {
			
			String countryName = commands[2];
			String neighborName = commands[3];
			
			if("-add".equals(commands[1])) {
				
				if(MapValidator.isCountryExists(countryName)
						&& MapValidator.isCountryExists(neighborName)
						&& !countryName.equals(neighborName)
						&& !MapValidator.isEdgeExist(countryName,neighborName)) {
					addNeighbour(countryName, neighborName);
				}else {
					setMessage("Invalid Command -- " + command);
				}
				
			} else if("-remove".equals(commands[1])){
				
				if(MapValidator.isNeighbour(countryName, neighborName)) {
					removeNeighbour(countryName, neighborName);
				}else {
					setMessage("Invalid Command -- " + command);
				}
				
			}
			
		}else if("showmap".equals(commands[0]) && isFileLoaded()) {
			
			System.out.println("Start showing map file ------------------------------------------ ");
			
			System.out.println(mapFileReader.getContinentList());
			System.out.println(mapFileReader.getCountryList());
			System.out.println("");
			System.out.println(mapFileReader.getMap());
			System.out.println("");
			
			System.out.println("Map File Displayed successfully ----------------------------------");
			
		} else if ("savemap".equals(commands[0]) && commands[1].equals(getFileName())) {

			if (MapValidator.validateMap(mapFileReader.getMap())) {
				String filename = commands[1];
				
				if (filename.equals(getFileName())) {

					if (fileName.endsWith(".map")) {
						saveMap(filename);
					} else {
						IMapFileEditor dominationToConquestAdapter = new DominationToConquestAdapter();
						dominationToConquestAdapter.saveMap(fileName);
					}
				} else {
					System.out.println("INVALID Filename");
					setMessage("INVALID Filename");
				}

			} else {
				System.out.println("INVALID MAP");
				setMessage("INVALID MAP");
			}

		}else if("editmap".equals(commands[0]) && !isFileLoaded()) {
			
			String filename = commands[1];
			editMap(filename);
			
		}else if("validatemap".equals(commands[0]) && isFileLoaded()) {
			
			if(MapValidator.validateMap(mapFileReader.getMap())) { 
				System.out.println("Map is Valid"); 
			}else {
				System.out.println("INVALID MAP");
				setMessage("INVALID MAP");
			}
			
		}else{
			System.out.println("invalid command");
			setMessage("Invalid Command -- " + command);
		}
		
	}

	
}

