package cellsociety_team08;

import java.util.List;
import java.util.Map;


public abstract class  RuleSet {
	
	private String myDescription;
	public static State[] myPossibleStates;
	public static Map<String, Object> myParams;
	
	public RuleSet(String description, State[] possibleStates, Map<String,Object> params) {
		myDescription = description;
		myPossibleStates = possibleStates;
		myParams = params;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract Patch getNext(Patch curr, List<Patch> neighborhood);
	
	public void move(Patch patch, List<Patch> emptyPatches) {
		return;
	}
	
	public void addPatch(List<Patch> emptyPatches) {
		
	}
		
}