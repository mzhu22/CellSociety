package backEnd;

import javafx.scene.paint.Color;


public class State {
	
	/*
	 * TODO : This class doesn't have any real behavior!! - Brian Bolze
	 */

	protected String myName;
	protected Integer myIndex;
	protected Object[] myParams;
	public Color myColor;

	public State(String name, int index, Color color, Object[] params) {
		myName = name;
		myIndex = index;
		setColor(color);
		myParams = params;
	}
	public Integer getIndex(){
		return myIndex;
	}

	public boolean equals(State other) {
		return (myIndex == other.myIndex);
	}
	
	public void setParams(Object[] obj) {
		myParams = obj;
	}

	public void setColor(Color myColor) {
		this.myColor = myColor;
	}

}
