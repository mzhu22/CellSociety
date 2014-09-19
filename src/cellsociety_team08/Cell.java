package cellsociety_team08;

import java.util.List;
import java.util.Stack;

import javafx.scene.shape.Rectangle;

public class Cell {
	
	private List<State> myHistory = new Stack<State>();
	public Rectangle myRectangle;
	
	public Cell(State s) {      /* maybe add this too I'm not sure: int size*/
		
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
		if (s == null) { //empty...
			remove();
		}
		((Stack<State>) myHistory).push(s);
	}

}
