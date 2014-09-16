package cellsociety_team08;


public class State {

	public String myName;
	public int myIndex;

	/*
	 * Bounds is the maximum size the shape can fit into For a rectangle, this
	 * would simply coorespond to its length and width For a Circle, this would
	 * indicate its diameter
	 */

	public State(String name, int index) {
		myName = name;
		myIndex = index;
	}

	public boolean equals(State other) {
		return (myName.equals(other.myName));
	}

}
