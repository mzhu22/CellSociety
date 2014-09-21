package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class  RuleSet {

	protected String myDescription;
	protected State[] myPossibleStates;
	protected Map<String,Object> myParams;

	protected Patch[][] myPatches;

	public RuleSet() {
	}
	
	public void setParams(Map<String,Object> params) {
		myParams = params;
	}
	
	public void addGrid(Patch[][] grid){
		myPatches = grid;
	}

	public State getDefaultState(){
		return myPossibleStates[0];
	}

	public Patch initializePatch(int row, int column, String s){
		Patch patch;
		if(".".equals(s)){
			patch = new Patch(row, column, true);
		}
		else{
			int choice = Integer.parseInt(s);
			patch = new Patch(row, column, false);
			patch.fill(new Cell(myPossibleStates[choice]));
		}
		return patch;
	}

	public String getDescription() {
		return myDescription;
	}

	public Patch[][] update(){
		Patch[][] nextGrid = new Patch[myPatches.length][myPatches[0].length];
		for(int i=0; i<myPatches.length; i++){
			for(int j=0; j<myPatches.length; j++){
				Patch updatedPatch = getNext(myPatches[i][j]);

				nextGrid[i][j] = updatedPatch;
			}
		}
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				nextGrid[i][j].flagged = false;
			}
		}
		myPatches = nextGrid;
		return nextGrid;
	}

	public List<Patch> getNeighbors(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		switch ((String) myParams.get("gridShape")) {
		
			case ("Hexagonal"):
				for (int i = row; i < row + 2; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches[i][j]);
						}
					}
				}
				break;
		
			default : //Rectangular OR Triangular
				for (int i = row - 1; i < row + 2; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches[i][j]);
						}
					}
				}
				break;
		}

		return ret;
	}
	
	public List<Patch> getDirectNeighbors(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		switch ((String) myParams.get("gridShape")) {
		
			case ("Hexagonal"):
				for (int i = row; i < row + 2; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches[i][j]);
						}
					}
				}
				break;
				
			case ("Triangular"):
				if (!isOutside(row, col-1)) ret.add(myPatches[row][col-1]);
				if (!isOutside(row, col+1)) ret.add(myPatches[row][col+1]);
				if (!isOutside(row-1, col) && col%2 == 0) ret.add(myPatches[row-1][col]);
				if (!isOutside(row+1, col) && col%2 == 1) ret.add(myPatches[row+1][col]);
				break;
		
			default : //Rectangular
				if (!isOutside(row, col-1)) ret.add(myPatches[row][col-1]);
				if (!isOutside(row, col+1)) ret.add(myPatches[row][col+1]);
				if (!isOutside(row-1, col)) ret.add(myPatches[row-1][col]);
				if (!isOutside(row+1, col)) ret.add(myPatches[row+1][col]);
				break;
		}

		return ret;
	}

	public boolean isOutside(int row, int col) {
		int rows = myPatches.length;
		int cols = myPatches[0].length;
		return (row >= rows || row < 0 || col >= cols || col < 0);
	}

	public abstract Patch getNext(Patch curr);
	
	/**
	 * Used to move cells around for those CA that need it. Not required by many
	 */
}