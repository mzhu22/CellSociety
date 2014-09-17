package cellsociety_team08;

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

