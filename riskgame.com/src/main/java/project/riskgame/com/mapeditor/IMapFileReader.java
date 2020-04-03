package project.riskgame.com.mapeditor;

/**
 * Interface for implementing adapter mapfile reader.
 * 
 * @author Kshitij
 * @version 1.3
 *
 */
public interface IMapFileReader {

	
	/**
	 * Method for reading the map file loaded by the player.
	 * @param fileName: name of the file
	 * @throws Exception  while loading map file.
	 */
	public void readMapFile(String fileName) throws Exception;

}
