package cellsociety_team08;

import java.util.List;
import java.util.Stack;

public class Cell {
	
	public boolean isEmpty = false;
	private List<State> myHistory = new Stack<State>();
	private int[] myLocation; //This is the replacement for patch (I think...)
	
	public Cell(State s, int[] location) {
		((Stack<State>) myHistory).push(s); //Can I just make this add???
		myLocation = location;
	}
	
	public void remove() {
		isEmpty = true;
		myHistory.clear();
	}
	
	public State getState() {
		return ((Stack<State>) myHistory).peek();
	}
	
	public List<State> getHistory() {
		return myHistory;
	}
	
	public void setLocation(int[] location) {
		myLocation = location;
	}
	
	public void setState(State s) {
		if (s == null) remove();
		((Stack<State>) myHistory).push(s);
	}
	
	public int[] getLocation() {
		return myLocation;
	}

}
