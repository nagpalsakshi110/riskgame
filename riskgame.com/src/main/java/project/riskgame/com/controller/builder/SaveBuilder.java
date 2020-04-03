package project.riskgame.com.controller.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The builder class that implements the save operation when a player saves the game in the middle.
 * 
 * @author Shubham
 * @version 1.3
 *
 */
public class SaveBuilder extends GamePlayControllerBuilder {

	/**
	 *Final variable classpath to set the path for location where files are saved. 
	 *
	 */
	private static final String CLASSPATH = "src/main/java/project/riskgame/com/resources/savedgames/";
	/**
	 * Instance of ObjectMapper class to write the file saved by player.
	 *
	 */
	private ObjectMapper mapper;
	/**
	 *  Directory name 
	 *
	 */
	private String directoryName;
	/**
	 * Object of OutputStream.
	 */
	private OutputStream outputStream;
	/**
	 *  Object of JsonGenerator.
	 *
	 */
	private JsonGenerator jsonGenerator;
	
	
	/**
	 * constructor of SavBuilder class
	 * @throws Exception while saving the file. 
	 *
	 */
	public SaveBuilder() throws Exception {
		mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter();
	}
	


	/**
	 * method for saving an already loaded map.
	 * 
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadMapFileReader() throws IOException {
		
		String fileName =  directoryName + "/MapFileReader.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator, getGamePlayController().getMapFileReader());
		closeStream();
		
	}


	/**
	 * method for saving an already loaded map and its player information and its phase.
	 * 
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadPlayerController() throws Exception {
		
		String fileName =  directoryName + "/PlayerController.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().getPlayerController());
		closeStream();
		
	}

	/**
	 *method for saving the start up phase state of current game.
	 *
	 *@throws Exception while implementation.
	 */
	@Override
	public void saveLoadStartupPhase() throws Exception {
		
		String fileName =  directoryName + "/StartupPhase.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().getStartupPhase());
		closeStream();
		
	}

	/**
	 *method for saving the information of the current player
	 *
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadCurrentPlayer() throws IOException {
		
		String fileName =  directoryName + "/CurrentPlayer.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().getCurrentPlayer());
		closeStream();
	}



	/**
	 * method for saving the loaded cards of the game.
	 * 
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadCards() throws IOException {
		
		String fileName =  directoryName + "/LoadCards.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().getCards());
		closeStream();
	}


	/**
	 * method for saving the size of the armies owned by the attacking country
	 * 
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadAttackerCountrySize() throws IOException {
		
		String fileName =  directoryName + "/AttackerCountrySize.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().getAttackerCountrySize());
		closeStream();
		
	}

	/**
	 * method for saving the state of exchange mode.
	 * 
	 *@throws IOException while implementation.
	 */
	@Override
	public void saveLoadIsExchangeOpen() throws IOException {
		
		String fileName =  directoryName + "/IsExchangeOpen.json";
		jsonGenerator = getJsonGenerator(fileName);
		mapper.writeValue(jsonGenerator,getGamePlayController().isExchangeOpen());
		closeStream();
		
	}

	/**
	 *method for setting the name of directory
	 *
	 *@param directoryName: name of the directory 
	 *@throws Exception while implementation.
	 */
	@Override
	public void setDirectoryName(String directoryName) throws Exception {
		
		this.directoryName = CLASSPATH + directoryName;
		File directory = new File(this.directoryName);
		directory.mkdirs();
		
	}
	
	/**
	 * Getter method for getting the file by mapping it with the Input Stream
	 *
	 *@param fileName: name of the file to be mapped. 
	 *@throws IOException while implementation.
	 *@return jsonGenerator.
	 */
	private JsonGenerator getJsonGenerator(String fileName) throws IOException {
		
		File file = new File(fileName);
		outputStream = new FileOutputStream(file);
		jsonGenerator = mapper.getFactory().createGenerator(outputStream);
		return jsonGenerator;
		
	}
	
	/**
	 *method for closing the stream.
	 *
	 *@throws IOException while implementation.
	 */
	private void closeStream() throws IOException {
		jsonGenerator.close();
		outputStream.close();
	}

	/**
	 * method for displaying that the file was saved successfully.
	 */
	@Override
	public void printMessage() {
		System.out.println("Successfully saved the game");
	}
	

}
