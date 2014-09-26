package backEnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class RuleSet {

	protected static final String GRID_SHAPE = "gridShape";
	protected static final String GRID_TYPE = "gridType";

	protected String myDescription;
	protected State[] myPossibleStates;
	protected Map<String, Object> myParams;
	protected String gridShape;
	protected String gridType;

	private Random gridCoord = new Random();

	protected Patch[][] myPatches;

	public RuleSet() {
	}

	public void setParams(Map<String, Object> params) {
		myParams = params;
		if (myParams.containsKey(GRID_SHAPE)) {
			gridShape = (String) myParams.get(GRID_SHAPE);
		}
		if (myParams.containsKey(GRID_TYPE)) {
			gridType = (String) myParams.get(GRID_TYPE);
		}
	}

	public void addGrid(Patch[][] grid) {
		myPatches = grid;
	}

	public State getDefaultState() {
		return myPossibleStates[0];
	}

	public Patch initializeRandom(int row, int column) {
		Random rand = new Random();
		Patch patch = new Patch(row, column, true);

		// Generates a number from -1 to myPossibleStates.length. If -1, make an
		// empty patch. If => 0, make a cell
		int randomState = rand.nextInt(myPossibleStates.length + 1) - 1;

		if (randomState > -1) {
			patch.fill(new Cell(myPossibleStates[randomState]));
		}
		return patch;
	}

	public Patch initializePatch(int row, int column, String s) {
		Patch patch;
		if (".".equals(s)) {
			patch = new Patch(row, column, true);
		} else {
			int choice = Integer.parseInt(s);
			patch = new Patch(row, column, false);
			patch.fill(new Cell(myPossibleStates[choice]));
		}
		return patch;
	}

	public String getDescription() {
		return myDescription;
	}

	public Patch[][] update() {
		Patch[][] nextGrid = new Patch[myPatches.length][myPatches[0].length];
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				nextGrid[i][j] = getNext(myPatches[i][j]);
			}
		}
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				nextGrid[i][j].flagged = false;
			}
		}
		myPatches = nextGrid;
		return nextGrid;
	}

	protected void moveCell(Cell toBeMoved) {
		boolean placed = false;
		while (!placed) {
			int row = gridCoord.nextInt(myPatches.length);
			int col = gridCoord.nextInt(myPatches[0].length);
			if (!myPatches[row][col].containsCell()) {
				myPatches[row][col].fill(toBeMoved);
				placed = true;
			}
		}
	}

	/*
	 * @TODO : Re-factor this to hide the gridType cases
	 */
	public List<Patch> getNeighbors(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		switch (gridShape) {

		case ("Hexagonal"):
			if ((col + row) % 2 == 1) {
				for (int i = row; i < row + 2; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches[i][j]);
						}
					}
				}
			} else {
				for (int i = row - 1; i < row + 1; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches[i][j]);
						}
					}
				}
			}
			break;

		default: // Rectangular OR Triangular
			for (int i = row - 1; i < row + 2; i++) {
				for (int j = col - 1; j < col + 2; j++) {
					if (i == row && j == col)
						continue;
					if (!isOutside(i, j))
						ret.add(myPatches[i][j]);
					if ("Toroidal".equals(gridType) && isOutside(i,j)) {
						int addRow = row;
						int addCol = col;
						if (i > row)
							addRow = 0;
						if (i < 0)
							addRow = myPatches.length - 1;
						if (j > col)
							addCol = 0;
						if (j < 0)
							addCol = myPatches[0].length - 1;
						ret.add(myPatches[addRow][addCol]);
					}
				}
			}
			break;
		}

		return ret;
	}

	public List<Patch> getDirectNeighbors(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;
		switch ((String) myParams.get("gridShape")) {

		case ("Hexagonal"):
			return getNeighbors(p); // Same!

		case ("Triangular"):
			if (!isOutside(row, col - 1))
				ret.add(myPatches[row][col - 1]);
			if (!isOutside(row, col + 1))
				ret.add(myPatches[row][col + 1]);
			if (!isOutside(row - 1, col) && col % 2 == 0)
				ret.add(myPatches[row - 1][col]);
			if (!isOutside(row + 1, col) && col % 2 == 1)
				ret.add(myPatches[row + 1][col]);
			if ("Toroidal".equals(gridType)) ret = addDirectWrapArounds(ret,row,col);
			break;

		default: // Rectangular
			if (!isOutside(row, col - 1))
				ret.add(myPatches[row][col - 1]);
			if (!isOutside(row, col + 1))
				ret.add(myPatches[row][col + 1]);
			if (!isOutside(row - 1, col))
				ret.add(myPatches[row - 1][col]);
			if (!isOutside(row + 1, col))
				ret.add(myPatches[row + 1][col]);
			if ("Toroidal".equals(gridType))
				ret = addDirectWrapArounds(ret, row, col);
			break;
		}

		return ret;
	}

	protected boolean isOutside(int row, int col) {
		int rows = myPatches.length;
		int cols = myPatches[0].length;
		return (row >= rows || row < 0 || col >= cols || col < 0);
	}

	private List<Patch> addDirectWrapArounds(List<Patch> list, int row, int col) {
		int rows = myPatches.length;
		int cols = myPatches[0].length;
		if (row+1 >= rows && !(gridShape.equals("Triangular") && col % 2 == 1)) list.add(myPatches[0][col]);
		if (row-1 < 0 && !(gridShape.equals("Triangular") && col % 2 == 0)) list.add(myPatches[rows-1][col]);
		if (col+1 >= cols) list.add(myPatches[row][0]);
		if (col-1 < 0) list.add(myPatches[row][cols - 1]);
		return list;
	}

	public abstract Patch getNext(Patch curr);

	/**
	 * Used to move cells around for those CA that need it. Not required by many
	 */
}