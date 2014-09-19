package cellsociety_team08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Grid {

	private Map<String, RuleSet> myImplementedRulesets;

	private static int myRows, myCols, myHeight, myWidth;
	private Patch[][] myPatches, nextPatches;
	private static RuleSet myRuleSet;


	public Grid(String type, int rows, int cols, int height, int width) {
		
		makeMyPossibleRules();

		myRuleSet = myImplementedRulesets.get(type);
		myRows = rows;
		myCols = cols;
		myHeight = height;
		myWidth = width;
		
		myPatches = new Patch[myRows][myCols];
		nextPatches = new Patch[myRows][myCols];
	}
	
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

	public void initialize(RuleSet rules, State state) {
		
		initializePatches();
		
		if (rules instanceof Segregation) {
			initializeSegregation();
		}
		
		if (rules instanceof SpreadingFire) {
			initializeSpreadingFire(rules, state);
		}
		
		if (rules instanceof GameOfLife) {
			initializeGameOfLife(rules, state);
		}
		
	}
	
	public void initializePatches() {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {	
				int[] dimensions = {myHeight/myRows, myWidth/myCols};
				myPatches[i][j] = new Patch(dimensions, i, j, true);
			}
		}
	}
	
	
	public void initializeSegregation() {
		Random rand = new Random();
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				int[] dimensions = {myHeight/myRows, myWidth/myCols};
				if ((rand.nextInt(100) + 1) > 66) { // Read from XML file later
					myPatches[i][j].fill(new Cell(myRuleSet.myPossibleStates[0], dimensions));
				}
				if (((rand.nextInt(100) + 1) < 66) && ((rand.nextInt(100) + 1) > 33)) {
					myPatches[i][j].fill(new Cell(myRuleSet.myPossibleStates[1], dimensions));
				}
			}
		}
	}
	

	public void initializeSpreadingFire(RuleSet rules, State state) {
		
	}
	public void initializePredatorPrey(RuleSet rules, State state) {
		//initializeSegregation(rules, state); //is there a significant difference?
	}
	public void initializeGameOfLife(RuleSet rules, State state) {
		
	}
	

	public Patch[][] update() {
		
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				List<Patch> neighborhood = getNeighborhood(myPatches[i][j]);
				nextPatches[i][j] = myRuleSet.getNext(myPatches[i][j], neighborhood);
			}
		}
		
		myPatches = nextPatches.clone();
		return myPatches;
		
	}

	public List<Patch> getNeighborhood(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		for (int i = row-1; i < row + 2; i++) {
			for (int j = col-1; j < col+2; j++) {
				if (!isOutside(i, j) && !(i==row && j==col)) {
					ret.add(myPatches[i][j]);
				} 
			}
		}

		return ret;
	}

	public boolean isOutside(int row, int col) {
		return (row >= myRows || row < 0 || col >= myCols || col < 0);
	}

}
