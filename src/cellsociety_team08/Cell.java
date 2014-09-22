package cellsociety_team08;

import java.util.List;
import java.util.Stack;

public class Cell {

	private List<State> myHistory = new Stack<State>();
	private double homeDesire = 0;
	private double foodDesire = 0;
	

	public Cell(State s) { /* maybe add this too I'm not sure: int size */

		((Stack<State>) myHistory).push(s);

	}

	public void remove() {
		myHistory.clear();
	}

	public State getState() {
		return ((Stack<State>) myHistory).peek();
	}

	public List<State> getHistory() {
		return myHistory;
	}

	public void setState(State s) {
		if (s == null) { // empty...
			remove();
		}
		((Stack<State>) myHistory).push(s);
	}

}
