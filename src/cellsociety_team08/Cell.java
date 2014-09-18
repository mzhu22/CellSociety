package cellsociety_team08;

import java.util.List;
import java.util.Stack;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	
	public boolean isEmpty = false;
	private List<State> myHistory = new Stack<State>();
	private int[] myLocation; //This is the replacement for patch (I think...)
	private int mySize;
	public Node myNode;
	public Color myColor;
	public Rectangle myRectangle;
	
	public Cell(State s, int[] location) {      /* maybe add this too I'm not sure: int size*/
		
		//This is sortof ugly but you said you hadn't implemented size or 
		double convertedSize = size;
		double doubleX = location[0];
		double doubleY = location[1];
		myRectangle = new Rectangle(doubleX, doubleY, convertedSize, convertedSize);
		myNode = myRectangle;
		//mySize = size;
		
		((Stack<State>) myHistory).push(s);
		myLocation = location;
		
	}
	
	public void remove() {
		isEmpty = true;
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
