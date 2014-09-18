package cellsociety_team08;

import java.util.List;
import java.util.Stack;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	
	private List<State> myHistory = new Stack<State>();
	public Patch myPatch; //This is the replacement for patch (I think...)
	public int[] myDimensions;
	public Node myNode;
	public Color myColor;
	public Rectangle myRectangle;
	
	public Cell(State s, Patch patch) {      /* maybe add this too I'm not sure: int size*/
		
		((Stack<State>) myHistory).push(s);
		myPatch = patch;
		myDimensions = myPatch.myDimensions;
		myRectangle = new Rectangle(myDimensions[0],myDimensions[1]);
		myRectangle.setFill(myColor);
	}
	
	public void remove() {
		myHistory.clear();
	}
	
	//new method
	public void setColor(Color color) {
		myRectangle.setFill(color);
		myColor = color;
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
