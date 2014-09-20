package cellsociety_team08;

import java.util.List;
import java.util.Map;


public abstract class  RuleSet {
	
	protected String myDescription;
	protected State[] myPossibleStates;
	
	public RuleSet(Map<String,Object> params) {
	}
	
	public State getDefaultState(){
		return myPossibleStates[0];
	}
	
	public Patch initializePatch(int row, int column, String s){
		Patch patch;
		if(".".equals(s)){
			patch = new Patch(row, column, true);
		}
		else{
			int choice = Integer.parseInt(s);
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