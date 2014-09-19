package cellsociety_team08;

import java.util.List;
import java.util.Map;


public abstract class  RuleSet {
	
	protected String myDescription;
	protected static State[] myPossibleStates;
	protected static Map<String, Object> myParams;
	
	public RuleSet(Map<String,Object> params) {
		myParams = params;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract Patch getNext(Patch curr, List<Patch> neighborhood);
		
}