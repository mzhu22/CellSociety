package cellsociety_team08;

public class Patch {
	
	private boolean isEmpty;
	private State myState;
	private static RuleSet myRules;
	private static int[] myLocation;
	private static int mySize;
	
	public Patch(int[] location, int size, boolean empty) {
		isEmpty = empty;
		myLocation = location;
		mySize = size;
	}
	
	public void clear() {
		isEmpty = true;
	}
	
	public void fill(Cell cell) {
		isEmpty = false;
		cell.myLocation = this.myLocation
	}
	
	public boolean containsCell() {
		return (isEmpty = false);
	}
	

}
