package cellsociety_team08;

public class Patch {
	
	public boolean isEmpty;
	public static int[] myLocation;
	public static int[] myDimensions;
	
	public Patch(int[] dimensions, int[] location, boolean empty) {
		myDimensions = dimensions;
		myLocation = location;
		isEmpty = empty;

	}
	
	public void clear() {
		isEmpty = true;
	}
	
	public void fill(Cell cell) {
		isEmpty = false;
	}
	
	public boolean containsCell() {
		return (isEmpty = false);
	}
	

}
