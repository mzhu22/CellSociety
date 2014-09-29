/*
 * This entire file is part of my masterpiece
 * @MIKE ZHU
 */

package backEnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public abstract class RuleSet {

	protected static final String GRID_SHAPE = "gridShape";
	protected static final String GRID_TYPE = "gridType";

	protected String myDescription;
	protected String[] myPossibleStates;
	protected Map<String, Object> myParams;
	protected Map<String, Color> myColorParams;
	protected String gridShape = "Rectangular"; //Default
	protected String gridType = "Finite"; //Default

	private Random gridCoord = new Random();

	protected List<ArrayList<Patch>> myPatches;

	/**
	 * Initialize a RuleSet using grid, 
	 * @param grid == initial state of the grid
	 * @param params == parameters for the sim (e.g., breed time)
	 * @param colorParams == map of state-to-color used to update GUI representation of cells
	 */
	public RuleSet(List<ArrayList<Patch>> grid, Map<String, Object> params, Map<String, Color> colorParams) {
		myPatches = grid;
		myParams = params;
		myColorParams = colorParams;
		if (myParams.containsKey(GRID_SHAPE)) {
			gridShape = (String) myParams.get(GRID_SHAPE);
		}
		if (myParams.containsKey(GRID_TYPE)) {
			gridType = (String) myParams.get(GRID_TYPE);
		}
	}
	
	/**
	 * getNext be implemented by all subclasses
	 * @param curr = current Patch
	 * @return
	 */
	public abstract Patch getNext(Patch curr);

	public String getDefaultState() {
		return myPossibleStates[0];
	}

	/**
	 * Initializes a random initial state for the grid rather than reading the XML-specified initial state
	 * @param row == row of Patch to be created
	 * @param column == column of Patch to be created
	 * @return
	 */
	public Patch initializeRandom(int row, int column) {
		Random rand = new Random();
		Patch patch = new Patch(row, column);

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
			patch = new Patch(row, column);
		} else {
			int choice = Integer.parseInt(s);
			patch = new Patch(row, column);
			patch.fill(new Cell(myPossibleStates[choice]));
		}
		return patch;
	}

	/**
	 * Updates the grid by calling on getNext() 
	 * @return
	 */
	public List<ArrayList<Patch>> update() {
		List<ArrayList<Patch>> nextGrid = new ArrayList<ArrayList<Patch>>();
		for (int i = 0; i < myPatches.size(); i++) {
			for (int j = 0; j < myPatches.get(i).size(); j++) {
				List<Patch> oldRow = myPatches.get(i);
				List<Patch> newRow = nextGrid.get(i); 
				newRow.add(j, getNext(oldRow.get(j)));
			}
		}
		myPatches = nextGrid;
		return nextGrid;
	}
	
	/**
	 * Used in those sims that require cell movement (predator-prey, segregation, etc.)
	 * @param toBeMoved
	 */
	protected void moveCell(Cell toBeMoved) {
		boolean placed = false;
		while (!placed) {
			int row = gridCoord.nextInt(myPatches.size());
			int col = gridCoord.nextInt(myPatches.get(0).size());
			if (!myPatches.get(row).get(col).containsCell()) {
				myPatches.get(row).get(col).fill(toBeMoved);
				placed = true;
			}
		}
	}

	/**
	 * Gets all surrounding neighbors of a patch. Different implementation for hex vs. rectangular and triangular shapes
	 * @param p == current patch
	 * @return
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
							ret.add(myPatches.get(i).get(j));
						}
					}
				}
			} else {
				for (int i = row - 1; i < row + 1; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (!isOutside(i, j) && !(i == row && j == col)) {
							ret.add(myPatches.get(i).get(j));
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
						ret.add(myPatches.get(i).get(j));
					else if ("Toroidal".equals(gridType) && isOutside(i,j)) {
						ret = addWrapArounds(ret,row,col);
					}
				}
			}
			break;
		}

		return ret;
	}

	/**
	 * Returns only those patches directly adjacent to the current (up, down, left, right) in rectangular
	 * @param p
	 * @return
	 */
	public List<Patch> getDirectNeighbors(Patch p) {

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;
		
		switch (gridType) {

		case ("Hexagonal"):
			return getNeighbors(p); // Same!

		case ("Triangular"):
			if (!isOutside(row, col - 1))
				ret.add(myPatches.get(row).get(col - 1));
			if (!isOutside(row, col + 1))
				ret.add(myPatches.get(row).get(col + 1));
			if (!isOutside(row - 1, col) && col % 2 == 0)
				ret.add(myPatches.get(row - 1).get(col));
			if (!isOutside(row + 1, col) && col % 2 == 1)
				ret.add(myPatches.get(row + 1).get(col));
			if ("Toroidal".equals(gridType)) ret = addDirectWrapArounds(ret,row,col);
			break;

		default: // Rectangular
			if (!isOutside(row, col - 1))
				ret.add(myPatches.get(row).get(col - 1));
			if (!isOutside(row, col + 1))
				ret.add(myPatches.get(row).get(col + 1));
			if (!isOutside(row - 1, col))
				ret.add(myPatches.get(row - 1).get(col));
			if (!isOutside(row + 1, col))
				ret.add(myPatches.get(row + 1).get(col));
			if ("Toroidal".equals(gridType))
				ret = addDirectWrapArounds(ret, row, col);
			break;
		}

		return ret;
	}

	protected boolean isOutside(int row, int col) {
		int rows = myPatches.size();
		int cols = myPatches.get(row).size();
		return (row >= rows || row < 0 || col >= cols || col < 0);
	}

	/**
	 * Special getNeighbors for a toroidal grid
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	private List<Patch> addDirectWrapArounds(List<Patch> list, int row, int col) {
		int rows = myPatches.size();
		int cols = myPatches.get(0).size();
		if (row+1 >= rows && !(gridShape.equals("Triangular") && col % 2 == 1)) list.add(myPatches.get(0).get(col));
		if (row-1 < 0 && !(gridShape.equals("Triangular") && col % 2 == 0)) list.add(myPatches.get(rows-1).get(col));
		if (col+1 >= cols) list.add(myPatches.get(row).get(0));
		if (col-1 < 0) list.add(myPatches.get(row).get(col-1));
		return list;
	}
	
	/**
	 * Special getDirectNeighbors for a toroidal grid
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	private List<Patch> addWrapArounds(List<Patch> list, int row, int col) {
		int numRows = myPatches.size();
		int numCols = myPatches.get(0).size();
		if (row >= numRows) row = 0;
		if (row < 0) row = numRows-1;
		if (col >= numCols) col = 0;
		if (col < 0) col = numCols-1;
		list.add(myPatches.get(row).get(col));
		return list;
	}
}