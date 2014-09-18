package cellsociety_team08;

public class Patch {
	
	private boolean isEmpty;
	private State myState;
	private static RuleSet myRules;
	public static int[] myLocation;
	private static int[] myDimensions;
	
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
		cell.myLocation = Patch.myLocation;
	}
	
	public boolean containsCell() {
		return (isEmpty = false);
	}
	

}
