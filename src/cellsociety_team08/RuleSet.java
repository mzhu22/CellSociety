package cellsociety_team08;


public abstract class  RuleSet {
	
	private String myDescription;
	public static State[] myPossibleStates;
	
	public RuleSet(String description, State[] possibleStates) {
		myDescription = description;
		myPossibleStates = possibleStates;
	}
	
	public String getDescription(Object[] params) {
		return myDescription;
	}
	
	public abstract State getState(Cell[][] neighborhood);
	
	public abstract int[] getLocation(Cell[][] neighborhood);
	
}