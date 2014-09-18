package cellsociety_team08;

import java.util.List;
import java.util.Stack;

import javafx.scene.shape.Rectangle;

public class Cell {
	
	private List<State> myHistory = new Stack<State>();
	public Patch myPatch; //This is the replacement for patch (I think...)
	public int[] myDimensions;
	public Rectangle myRectangle;
	
	public Cell(State s, Patch patch) {      /* maybe add this too I'm not sure: int size*/
		
		((Stack<State>) myHistory).push(s);
		myPatch = patch;
		myDimensions = myPatch.myDimensions;
		myRectangle = new Rectangle(myDimensions[0],myDimensions[1]);
		myRectangle.setFill(s.myColor);
	}
	
	public void remove() {
		myHistory.clear();
		myRectangle.setVisible(false);
	}
	
	public State getState() {
		return ((Stack<State>) myHistory).peek();
	}
	
	public List<State> getHistory() {
		return myHistory;
	}
	
	public void setLocation(int[] location) {
		// Needs work!!!
		myPatch.myLocation = location;
	}
	
	public void setState(State s) {
		if (s == null) { //empty...
			remove();
			
		}
		((Stack<State>) myHistory).push(s);
		myRectangle.setFill(s.myColor);
		
	}
	
	public int[] getLocation() {
		return myPatch.myLocation;
	}

}
