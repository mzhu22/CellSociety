package cellsociety_team08;

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

	private static int myHeight, myWidth, myRows, myCols, mySize;
	private Cell[][] myCells;
	private static RuleSet myRuleSet;

	public Grid(RuleSet ruleSet, int rows, int cols, int height, int width, int size) {
		myRuleSet = ruleSet;
		myRows = rows;
		myCols = cols;
		myHeight = height;
		myWidth = width;

		myCells = new Cell[myRows][myCols];

	}
	
	public void initialize(RuleSet rules, int[] size) {
		
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
