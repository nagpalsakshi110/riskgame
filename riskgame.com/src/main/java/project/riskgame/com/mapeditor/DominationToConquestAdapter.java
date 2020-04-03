package project.riskgame.com.mapeditor;

/**
 * Adapter class for map from domination to conquest.
 * 
 * @author sakshi
 * @version 1.3
 *
 */
public class DominationToConquestAdapter implements IMapFileEditor, IMapFileReader {

	/**
	 * Object of MapFileReader singelton class using its getInstance method.
	 *
	 */
	private MapFileReader mapFileReader = MapFileReader.getInstance();

	/**
	 * Object of MapFileEditor singelton class using its getInstance method.
	 *
	 */
	private MapFileEditor mapFileEditor = MapFileEditor.getInstance();

	/**
	 * Method for saving the map in domination format.
	 *
	 */
	@Override
	public void saveMap(String fileName) throws Exception {
		mapFileEditor.saveConquestMap(fileName);

	}

	/**
	 * Method for reading the map in conquest format.
	 *
	 */
	@Override
	public void readMapFile(String fileName) throws Exception {
		mapFileReader.readMapFileConquest(fileName);

	}

}
