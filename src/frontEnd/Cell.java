package frontEnd;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Cell {

	
	Random rand = new Random();
	public int sugar = rand.nextInt(25)+1;
	public int sugarMetabolism = rand.nextInt(5);
	public int vision = rand.nextInt(5) + 1;
	
	private List<State> myHistory = new Stack<State>();
	//public double homeDesire = 0;
	//public double foodDesire = 0;
	public boolean hasFood = false;

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
