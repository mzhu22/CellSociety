package cellsociety_team08;

import java.util.List;
import java.util.Stack;

import javafx.scene.shape.Rectangle;

public class Cell {
	
	private List<State> myHistory = new Stack<State>();
	public Rectangle myRectangle;
	
	public Cell(State s, int[] dimensions) {      /* maybe add this too I'm not sure: int size*/
		
		((Stack<State>) myHistory).push(s);
		myRectangle = new Rectangle(dimensions[0], dimensions[1]);
		myRectangle.setFill(s.getColor());
		
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
	
	public void setState(State s) {
		if (s == null) { //empty...
			remove();
		}
		((Stack<State>) myHistory).push(s);
		myRectangle.setFill(s.getColor());	
	}

}
