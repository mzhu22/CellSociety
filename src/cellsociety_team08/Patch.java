
public class Patch {
	
	private boolean isEmpty;
	private State myState;
	private static RuleSet myRules;
	private static int[] myLocation;
	private static int mySize;
	
	public Patch(State state, RuleSet rules, int[] location, int size, boolean empty) {
		isEmpty = empty;
		myState = state;
		myRules = rules;
		myLocation = location;
		mySize = size;
	}
	
	public void empty() {
		isEmpty = true;
	}
	
	public void fill() {
		isEmpty = false;
	}
	

}
