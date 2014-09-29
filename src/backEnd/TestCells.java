/*
 * This entire file is part of my masterpiece
 * @MIKE ZHU
 */

package backEnd;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

import org.junit.Test;

public class TestCells {
	Cell myCell = new Cell();
	
	@Test
	public void testThatCellsAreBlackByDefault() {
		assertEquals("Cells that haven't called changeColor are BLACK", Color.BLACK, myCell.getColor());
	}
	
	@Test
	public void testCellsCanReadMapAndChangeColor() {
		Map<String, Color> testColorMap = new HashMap<String, Color>();
		testColorMap.put("Alive", Color.BLUE);
		testColorMap.put("Dead", Color.RED);
		
		myCell.setState("Alive");
		myCell.changeColor(testColorMap);
		assertEquals("Alive cell should be blue", Color.BLUE, myCell.getColor());
		
		myCell.setState("Dead");
		myCell.changeColor(testColorMap);
		assertEquals("Alive cell should be blue", Color.RED, myCell.getColor());
	}
	
	@Test
	public void testDefaultRuleSetState(){
		Map<String, Color> testColorMap = new HashMap<String, Color>();
		testColorMap.put("Alive", Color.BLUE);
		testColorMap.put("Dead", Color.RED);
		
		ArrayList<ArrayList<Patch>> testGrid = new ArrayList<ArrayList<Patch>>();
		ArrayList<Patch> row = new ArrayList<>();
		row.add(new Patch(0,0)); row.add(new Patch(0,1));
		
		RuleSet testConway = new GameOfLife(testGrid, new HashMap<String, Object>(), testColorMap);
		assertEquals(0, testConway.getNext(new Patch(0, 0)));
	}
}
