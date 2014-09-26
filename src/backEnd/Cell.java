package backEnd;

import java.util.List;
import java.util.Stack;

import javafx.scene.paint.Color;

public class Cell {

	protected List<State> myHistory = new Stack<State>(); // TODO : History not needed...

	public Cell(State s) { /* maybe add this too I'm not sure: int size */

		((Stack<State>) myHistory).push(s);

	}
	
	public Color getColor() {
		State state = getState();
		//if (state == null) System.out.println("Tried to get color from NULL cell");
		return state.myColor;
	}

	public void remove() {
		myHistory.clear();
	}

	public State getState() {
		return ((Stack<State>) myHistory).peek();
	}

	public void setState(State s) {
		if (s == null) { // empty...
			remove();
		}
		((Stack<State>) myHistory).push(s);
	}

}
