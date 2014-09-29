package backEnd;

public class Patch{
	
	public boolean flagged;
	public int myRow, myCol;
	public Cell myCell;
	public int numCells;
		
	public Patch(int row, int col) {
		myRow = row;
		myCol = col;
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
	
	public Cell getCell(){
		return myCell;
	}
	
	public void clear() {
		myCell = null;
		flagged = false;
	}
	
	public void flag() {
		flagged = true;
	}
	
	public void fill(Cell cell) {
		myCell = cell;
	}
	
	public boolean containsCell() {
		return (myCell!=null);
	}
}
