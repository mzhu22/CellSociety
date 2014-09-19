package cellsociety_team08;

public class Patch {
	
	public boolean isEmpty;
	public boolean flagged;
	public int myRow, myCol;
	public static int[] myDimensions;
	public Cell myCell;
	
	public Patch(int[] dimensions, int row, int col, boolean empty) {
		myDimensions = dimensions;
		myRow = row;
		myCol = col;
		isEmpty = empty;
		flagged = false;
	}
	
	public void clear() {
		isEmpty = true;
		myCell.remove();
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
