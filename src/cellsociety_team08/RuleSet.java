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
	
	public Patch initializePatch(int row, int column, String s){
		Patch patch;
		int choice = Integer.parseInt(s);
		if(choice == 9){
			patch = new Patch(row, column, true);
		}
		else{
			patch = new Patch(row, column, false);
			patch.fill(new Cell(myPossibleStates[choice]));
		}
		return patch;
	}
	
	//TODO: Tester code. Remove later
	public State getFire(){
		return myPossibleStates[1];
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public abstract Patch getNext(Patch curr, List<Patch> neighborhood);
		
}