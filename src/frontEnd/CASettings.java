package frontEnd;

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
	
	private static final String GRID_SHAPE = "gridShape";
	
	protected String myGridShape = "Rectangular"; //Default
	private String myType;
	private Integer myRows;
	private Integer myColumns;
	private String[][] myGrid;
	protected static Map<String, Object> myParameters;
	
	public CASettings(String type, Map<String, Object> parametersMap, Integer row, Integer column, String[][] grid){
		myParameters = parametersMap;
		myType = type;
		myGrid = grid;
		myRows = row;
		myColumns = column;
		
		if(myParameters.containsKey("gridShape")){
			myGridShape = (String) myParameters.get(GRID_SHAPE);
		}
		
	}
	
	public String getType(){
		return myType;
	}
	
	public Map<String, Object> getParameters(){
		return myParameters;
	}
	
	public Integer getRows(){
		return myRows;
	}

	public Integer getColumns(){
		return myColumns;
	}
	
	public String[][] getGrid(){
		return myGrid;
	}
}

