package cellsociety_team08;


public class State {

	public String myName;
	public int myIndex;
	private Object[] myParams;

	/*
	 * Bounds is the maximum size the shape can fit into For a rectangle, this
	 * would simply coorespond to its length and width For a Circle, this would
	 * indicate its diameter
	 */

	public State(String name, int index, Object[] params) {
		myName = name;
		myIndex = index;
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

}
