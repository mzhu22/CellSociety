// This entire file is part of my masterpiece.
// Justin Carrao
package backEnd;
import static org.junit.Assert.*;
import org.junit.Test;

public class RuleSetTester {
	
	
	public RuleSetTester() {
	}
	
	@Test
	public void testSpreadingFireIsBurning() {
		SpreadingFire fire = new SpreadingFire();
		Patch patch = new Patch(1,1,false);
		patch.flagged = true;
		patch.isEmpty = true;
		assertEquals(true, fire.isBurning(patch));
		patch.flagged = false;
		patch.myCell = null;
		assertEquals(false, fire.isBurning(patch));
		fire.myPossibleStates[1] = patch.getCell().getState();
		assertEquals(true, fire.isBurning(patch));
	}
	
	@Test
	public void testSugarScapeGetSugarLevel() {
		SugarScape sugar = new SugarScape();
		Patch patch = new Patch(1,1,false);
		patch.myCell = new Cell(sugar.myPossibleStates[1]);
		int x = (int)patch.myCell.getState().myParams[0];
		assertEquals(x, sugar.getSugarLevel(patch));
	}
	
	@Test
	public void testSugarScapeIsSugar() {
		SugarScape sugar = new SugarScape();
		Patch patch = new Patch(1,1,false);
		patch.myCell = new Cell(sugar.myPossibleStates[1]);
		patch.getCell().getState().myIndex = 1;
		assertEquals(true, sugar.isSugar(patch));
	}
	
	
	

}
