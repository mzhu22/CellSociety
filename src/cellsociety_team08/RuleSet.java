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
	
	public State getDefaultState(){
		return myPossibleStates[0];
	}
	
	//TODO: Tester code. Remove later
	public State getFire(){
		return myPossibleStates[1];
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract Patch getNext(Patch curr, List<Patch> neighborhood);
	
	public void move(Patch curr, List<Patch> emptyPatches) {
		return;
	}
	
	public void addPatch(Patch from, Patch to) {
		return;
	}
		
}