package cellsociety_team08;

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
	
	public CASettings(String type, int row, int column, String[][] grid){
		myType = type;
		myGrid = grid;
		myRows = row;
		myColumns = column;
	}
	
	public String getType(){
		return myType;
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

