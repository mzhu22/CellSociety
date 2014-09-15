package cellsociety_team08;


public abstract class RuleSet {
	
	private String myDescription;
	public static State[] myPossibleStates;
	
	public RuleSet(String description, State[] possibleStates) {
		myDescription = description;
		myPossibleStates = possibleStates;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract State getNext(Site[][] neighborhood);
	
}