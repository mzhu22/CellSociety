package cellsociety_team08;

import java.util.List;


public abstract class  RuleSet {
	
	private String myDescription;
	public static State[] myPossibleStates;
	public static Object[] myParams;
	
	public RuleSet(String description, State[] possibleStates, Object[] params) {
		myDescription = description;
		myPossibleStates = possibleStates;
		myParams = params;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract Patch getNext(Patch curr, List<Patch> neighborhood);
		
}