package cellsociety_team08;

import java.util.HashMap;
import java.util.Map;

public class Grid {

	private Map<String, RuleSet> myImplementedRulesets;

	private static int myRows, myCols;
	private Patch[][] myPatches;
	public RuleSet myRuleSet;

	public Grid(String type, Map<String, Object> map, int rows,
			int cols, String[][] grid) {

		myRows = rows;
		myCols = cols;

		myPatches = new Patch[myRows][myCols];

		makeMyPossibleRules();
		myRuleSet = myImplementedRulesets.get(type);
		myRuleSet.setParams(map);

		initialize(grid);
		myRuleSet.addGrid(myPatches);
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
	private void makeMyPossibleRules() {
		myImplementedRulesets = new HashMap<>();

		myImplementedRulesets.put("PredatorPrey", new PredatorPrey());
		myImplementedRulesets.put("SpreadingFire", new SpreadingFire());
		myImplementedRulesets.put("GameOfLife", new GameOfLife());
		myImplementedRulesets.put("Segregation", new Segregation());

	}

	public void initialize(String[][] grid) {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j] = myRuleSet.initializePatch(i, j, grid[i][j]);
				//myPatches[i][j] = myRuleSet.initializeRandom(i, j);

			}
		}
	}
}
