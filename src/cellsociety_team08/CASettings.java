package cellsociety_team08;

import java.util.Map;

/**
 * CASettings is a data structure that holds all info read from the XML file, including grid size, simulation type, 
 * and parameters. CASettings serves to encapsulate this data and pass it from XMLReader to the CALoop, where it 
 * is accessed and used to construct the visualization. 
 * 
 * Though CASettings is a passive data object, its existence allows XML file-reading to be put into its own
 * specialized class. 
 *
 * @author Mike Zhu
 *
 */
public class CASettings {
	private String myType;
	private int myRows;
	private int myColumns;
	private String[][] myGrid;
	private Map<String, Object> myParameters;
	
	public CASettings(String type, Map<String, Object> parametersMap, int row, int column, String[][] grid){
		myParameters = parametersMap;
		myType = type;
		myGrid = grid;
		myRows = row;
		myColumns = column;
	}
	
	public String getType(){
		return myType;
	}
	
	public Map<String, Object> getParameters(){
		return myParameters;
	}
	
	public int getRows(){
		return myRows;
	}

	public int getColumns(){
		return myColumns;
	}
	
	public String[][] getGrid(){
		return myGrid;
	}
}

