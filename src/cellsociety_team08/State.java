package cellsociety_team08;

import javafx.scene.paint.Color;


public class State {

	public static String myName;
	public int myIndex;
	private Color myColor;
	private Object[] myParams;

	/*
	 * Bounds is the maximum size the shape can fit into For a rectangle, this
	 * would simply coorespond to its length and width For a Circle, this would
	 * indicate its diameter
	 */

	public State(String name, int index, Color color, Object[] params) {
		myName = name;
		myIndex = index;
		setColor(color);
		myParams = params;
	}

	public boolean equals(State other) {
		return (myIndex == other.myIndex);
	}
	
	public Object[] getParams() {
		return myParams;
	}
	
	public void setParams(Object[] o) {
		myParams = o;
	}

	public Color getColor() {
		return myColor;
	}

	public void setColor(Color myColor) {
		this.myColor = myColor;
	}

}
