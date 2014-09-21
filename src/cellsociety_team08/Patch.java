package cellsociety_team08;

public class Patch {
	
	public boolean isEmpty;
	public boolean flagged;
	public int myRow, myCol;
	public Cell myCell;
	
	public Patch(int row, int col, boolean empty) {
		myRow = row;
		myCol = col;
		isEmpty = empty;
		flagged = false;
	}
	
	public Patch(Patch original){
		myRow = original.myRow;
		myCol = original.myCol;
		myCell = original.getCell();
		flagged = false;
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
}
