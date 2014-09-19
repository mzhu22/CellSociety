package cellsociety_team08;

public class Patch {
	
	public boolean isEmpty;
	public boolean flagged;
	public int myRow, myCol;
	public static int[] myDimensions;
	public Cell myCell;
	
	public Patch(int row, int col, boolean empty) {
		myRow = row;
		myCol = col;
		isEmpty = empty;
		flagged = false;
	}
	public Cell getCell(){
		return myCell;
	}
	
	public void clear() {
		isEmpty = true;
		myCell = null;
//		myCell.remove();
		flagged = false;
	}
	
	public void fill(Cell cell) {
		myCell = cell;
		isEmpty = false;
	}
	
	public boolean containsCell() {
		return (isEmpty = false);
	}
}
