/*
 * This entire file is part of my masterpiece
 * @MIKE ZHU
 */

package backEnd;

import java.util.Map;

import javafx.scene.paint.Color;

public class Cell {
	
	private static final Color DEFAULT_COLOR = Color.BLACK;

	protected String myState;
	protected Color myColor;
	
	public Cell(){
		myState = null;
		myColor = DEFAULT_COLOR; 
	}
	public Cell(String state) { 
		myState = state;
		myColor = DEFAULT_COLOR; 
	}
	
	/**
	 * Changes Cell color based on state and state-to-color mapping specified in XML file
	 * @param colorParams = specific map of state-to-color
	 */
	public void changeColor(Map<String, Color> colorParams){
		myColor = colorParams.get(myState);
	}
	
	public Color getColor(){
		return myColor;
	}
	
	public void setState(String state){
		myState = state;
	}

}
