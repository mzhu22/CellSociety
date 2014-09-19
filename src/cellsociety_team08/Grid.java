package cellsociety_team08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Grid {

	private Map<String, RuleSet> myImplementedRulesets;

	private static int myRows, myCols;
	private Patch[][] myPatches, nextPatches;
	private RuleSet myRuleSet;

	public Grid(String type, Map<String, Object> parametersMap, int rows,
			int cols, String[][] grid) {
		makeMyPossibleRules(parametersMap);

		myRuleSet = myImplementedRulesets.get(type);
		myRows = rows;
		myCols = cols;

		myPatches = new Patch[myRows][myCols];
		nextPatches = new Patch[myRows][myCols];

		initialize(grid);
	}

	/**
	 * Use this method to fill up myPossibleRules with all implemented
	 * simulation rulesets. These rulesets then accessed by the constructor and
	 * put into myRuleSet.
	 * 
	 * 9/19 12:40 am: Currently sketchy implementation. All implemented RuleSets
	 * are created with the single parametersMap input. While this puts
	 * parameters of a single simulation into all RuleSets (e.g., spawnRate into
	 * SpreadingFire), this should never cause an error because: i. No RuleSets
	 * other than the one specified by type should be called ii. If i. isn't
	 * true, then calling on a parameter that doesn't exist would return null,
	 * hopefully program just errors out then.
	 */
	private void makeMyPossibleRules(Map<String, Object> parametersMap) {
		myImplementedRulesets = new HashMap<>();
		// We need to implement passing parameters for the rulesets via the XML
		// file
		myImplementedRulesets.put("SpreadingFire", new SpreadingFire(
				parametersMap));
		// myImplementedRulesets.put("Segregation", new
		// Segregation(parametersMap));
		// myImplementedRulesets.put("PredatorPrey", new
		// PredatorPrey(parametersMap)); // Still needs work
	}

	public void initialize(String[][] grid) {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j] = myRuleSet.initializePatch(i, j, grid[i][j]);
			}
		}
	}

	public Patch[][] update() {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				List<Patch> neighborhood = getNeighborhood(myPatches[i][j]);
				nextPatches[i][j] = myRuleSet.getNext(myPatches[i][j],
						neighborhood);
			}
		}

		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j].flagged = false;
			}
		}

		System.out.println("a");
		myPatches = nextPatches.clone();
		return myPatches;
	}

	public List<Patch> getNeighborhood(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		for (int i = row - 1; i < row + 2; i++) {
			for (int j = col - 1; j < col + 2; j++) {
				if (!isOutside(i, j) && !(i == row && j == col)) {
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
