package project.riskgame.com.mapeditor;

/**
 * Interface class for implementing adapter for Map file editor.
 *
 *@author Shubham	
 *@version 1.3
 *
 */
public interface IMapFileEditor {

	
	/**
	 * Method implementing the savMap on the map loaded.
	 * @param fileName name of the file
	 * @throws Exception  while saving the map file.
	 */
	public void saveMap(String fileName) throws Exception;
}
