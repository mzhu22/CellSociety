package cellsociety_team08;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class State extends Rectangle {

	private double myWidth;
	private double myHeight;
	private Color myColor;

	public String myName;

	/*
	 * Bounds is the maximum size the shape can fit into For a rectangle, this
	 * would simply coorespond to its length and width For a Circle, this would
	 * indicate its diameter
	 */

	public State(String name, Color color) {
		myName = name;
		myColor = color;
		
		super.setFill(myColor);
	}
	
	public State(String name, Color color, int width, int height, int cols, int rows) {
		myWidth = (double) width / cols;
		myHeight = (double) height / rows;
		myColor = color;
		myName = name;

		super.setWidth(myWidth);
		super.setHeight(myHeight);
		super.setFill(myColor);
	}

	public void setColor(Color color) {
		myColor = color;
		super.setFill(myColor);
	}

	public Color getColor() {
		return myColor;
	}

	public boolean equals(State other) {
		return (myName.equals(other.myName));
	}

}
