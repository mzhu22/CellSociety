package backEnd;

import javafx.scene.shape.Rectangle;

public class Patch extends Rectangle{
	
	public boolean isEmpty;
	public boolean flagged;
	public int myRow, myCol;
	public Cell myCell;
	public State myState;
	public int numCells;
	
	
	// TODO : Make these parameters within their specific ruleset - Brian Bolze

	public int maxSugar = 25;
	public int sugarLevel = maxSugar;
	
	public Patch(int row, int col, boolean empty) {
		myRow = row;
		myCol = col;
		isEmpty = empty;
		flagged = false;
	}
	
	/**
	 * Cloner constructor. Creates an exact copy of input Patch
	 * @param original = Patch to be cloned
	 */
	public Patch(Patch original){
		myRow = original.myRow;
		myCol = original.myCol;
		myCell = original.getCell();
		flagged = false;
	}
	
	public void changeColor(){
		setFill(myCell.getState().myColor);
	}
	
	public Cell getCell(){
		return myCell;
	}
	
	public void clear() {
		isEmpty = true;
		myCell = null;
		flagged = false;
	}
	
	public void flag() {
		flagged = true;
	}
	
	public void fill(Cell cell) {
		myCell = cell;
		isEmpty = false;
	}
	
	public boolean containsCell() {
		return (myCell!=null);
	}
	
	public boolean myCellStateEquals(State state) {
		return this.myCell.getState().equals(state);
	}
	
	public void setMyCellState(State state) {
		this.myCell.setState(state);
	}
	
	public State getMyCellState() {
		return this.myCell.getState();
	}
}
