package cellsociety_team08;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Grid {

	/*
	 * Update: 9/16 11am, Brian Bolze: Went ahead and implemented these to
	 * classes because there was so much dependence on my RuleSet and State
	 * implementation.
	 * 
	 * Not sure exactly how to initialize all of this because it all kinda
	 * depends on how we do the XML stuff. I also don't have an initialize
	 * method, I just do it in the constructor. Feel free to change that
	 * accordingly.
	 * 
	 * I also have not implemented the actual shapes/nodes for the cells. I also
	 * do not have color or size of the cells stored ANYWHERE yet. Let me know
	 * what you want to do about this.
	 */
	private Map<String, RuleSet> myImplementedRulesets;

	private static int myRows, myCols, myHeight, myWidth;
	private Cell[][] myCells;
	private Patch[][] myPatches;
	private static RuleSet myRuleSet;

	/**
	 * Use this method to fill up myPossibleRules with all implemented
	 * simulation rulesets. These rulesets then accessed by the constructor and
	 * put into myRuleSet.
	 */
	private void makeMyPossibleRules() {
		myImplementedRulesets = new HashMap<>();
		// We need to implement passing parameters for the rulesets via the XML
		// file
		myImplementedRulesets.put("SpreadingFire", new SpreadingFire(
				new Object[] { (float) 0.9 }));
		myImplementedRulesets.put("Segregation", new Segregation(new Object[] {
				0.3, 0.4 }));
		myImplementedRulesets.put("PredatorPrey", new PredatorPrey(
				new Object[] { 5 })); // Still needs work
	}

	public Grid(String type, int rows, int cols, int height, int width) {
		
		makeMyPossibleRules();

		myRuleSet = myImplementedRulesets.get(type);
		myRows = rows;
		myCols = cols;
		myHeight = height;
		myWidth = width;
		
		myCells = new Cell[myRows][myCols];
		myPatches = new Patch[myRows][myCols];
	}

	public void initialize(RuleSet rules, int size, State state) {
		
		if (rules instanceof Segregation) {
			initializeSegregation(rules, state);
		}
		
		if (rules instanceof SpreadingFire) {
			initializeSpreadingFire(rules, state);
		}
		
		if (rules instanceof PredatorPrey) {
			initializePredatorPrey(rules, state);
		}
		
		if (rules instanceof GameOfLife) {
			initializeGameOfLife(rules, state);
		}
	}
	
	public void initializePatches() {
		for (int i = 0; i < myCells.length; i++) {
			for (int j = 0; j < myCells[0].length; j++) {
				
				int[] location = {i,j};
				int[] dimensions = {myHeight/myRows, myWidth/myCols};
				myPatches[i][j] = new Patch(dimensions, location, true);
			}
		}
	}
	
	
	public void initializeSegregation(RuleSet rules, State state) {
		Random rand = new Random();
		initializePatches();
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				int[] location = {i,j};
				int[] dimensions = {myHeight/myRows, myWidth/myCols};
				if ((rand.nextInt(100) + 1) > 66) {
					myCells[i][j] = new Cell(state, location, dimensions); //placeholder here not sure what to do about size
					myPatches[i][j].fill(myCells[i][j]);
				}
				if (((rand.nextInt(100) + 1) < 66) && ((rand.nextInt(100) + 1) > 33)) {
					myCells[i][j] = new Cell(state, location, dimensions); //5 is a placeholder here not sure what to do about size
					myPatches[i][j].fill(myCells[i][j]);
				}
			}
		}
	}
	

	public void initializeSpreadingFire(RuleSet rules, State state) {
		
	}
	public void initializePredatorPrey(RuleSet rules, State state) {
		initializeSegregation(rules, state); //is there a significant difference?
	}
	public void initializeGameOfLife(RuleSet rules, State state) {
		
	}
	

	public void update() {
		for (int i = 0; i < myCells.length; i++) {
			for (int j = 0; j < myCells[0].length; j++) {
				Cell[][] neighborhood = getNeighborhood(myCells[i][j]);
				myCells[i][j].setState(myRuleSet.getState(neighborhood));
				myCells[i][j].setLocation(myRuleSet.getLocation(neighborhood));
			}
		}
	}

	public Cell[][] getNeighborhood(Cell c) {

		Cell[][] ret = new Cell[3][3];

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isOutside(i, j)) {
					ret[i][j] = null;
				} else {
					ret[i][j] = myCells[i][j];
				}
			}
		}

		return ret;
	}

	public boolean isOutside(int row, int col) {
		return (row >= myRows || row < 0 || col >= myCols || col < 0);
	}

}
