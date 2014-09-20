package cellsociety_team08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {

	private Map<String, RuleSet> myImplementedRulesets;

	private static int myRows, myCols;
	private Patch[][] myPatches, nextPatches;
	public RuleSet myRuleSet;

	public Grid(String type, Map<String, Object> parametersMap, int rows,
			int cols, String[][] grid) {

		myRows = rows;
		myCols = cols;

		myPatches = new Patch[myRows][myCols];
		nextPatches = new Patch[myRows][myCols];

		makeMyPossibleRules(parametersMap);
		myRuleSet = myImplementedRulesets.get(type);

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
	private void makeMyPossibleRules(Map<String, Object> parametersMap) {
		myImplementedRulesets = new HashMap<>();
		
		myImplementedRulesets.put("SpreadingFire", new SpreadingFire(parametersMap));
		myImplementedRulesets.put("GameOfLife", new GameOfLife(parametersMap));
	}

	public void initialize(String[][] grid) {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j] = myRuleSet.initializePatch(i, j, grid[i][j]);
			}
		}
	}
}
