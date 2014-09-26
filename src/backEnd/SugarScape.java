package backEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class SugarScape extends RuleSet {

	private static final String SUGAR_GROW_BACK_RATE = "sugarGrowBackRate";
	private static final String SUGAR_GROW_BACK_INTERVAL = "sugarGrowBackInterval";
	private static final String MAX_SUGAR = "maxSugar";
	private static final String INITIAL_SUGAR = "initialSugar";
	private static final String SUGAR_METABOLISM = "sugarMetabolism";
	private static final String VISION = "vision";
	private static int sugarGrowBackRate, sugarGrowBackInterval, initialSugar, sugarMetabolism, maxSugar, vision;
	private int ticks = 0;
	private Map<Integer, Color> myColorMap;
	private List<Patch> patchesToMove = new ArrayList<Patch>();
	private Random rand = new Random();


	public SugarScape() {
		super();
	}
	@Override
	public Patch[][] update() {
		ticks++;
		if (ticks%sugarGrowBackInterval == 0) growSugar();
		Patch[][] nextGrid = new Patch[myPatches.length][myPatches[0].length];
		
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				nextGrid[i][j] = getNext(myPatches[i][j]);
			}
		}
		
		movePatches(nextGrid);

		patchesToMove.clear();
		
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				nextGrid[i][j].flagged = false;
			}
		}
		
		myPatches = nextGrid;
//		randomizeSugar();
//		testRandomness();
		return myPatches;
	}

	@Override
	public void setParams(Map<String, Object> params) {
		super.setParams(params);
		sugarGrowBackInterval = Integer.parseInt((String) params
				.get(SUGAR_GROW_BACK_INTERVAL));
		sugarGrowBackRate = Integer.parseInt((String) params
				.get(SUGAR_GROW_BACK_RATE));
		maxSugar = Integer.parseInt((String) params.get(MAX_SUGAR));
		initialSugar = Integer.parseInt((String) params.get(INITIAL_SUGAR));
		sugarMetabolism = Integer.parseInt((String) params.get(SUGAR_METABOLISM));
		vision = Integer.parseInt((String) params.get(VISION));
		
		setupColorMap();

		myPossibleStates = new State[] {
				new State("Agent", 0, Color.ORANGERED, new Object[] { initialSugar }),
				new State("Sugar", 1, myColorMap.get(maxSugar), new Object[] { maxSugar }),
		};
		// Right now all sugar patches are initiated with maxSugar
	}
	
	private void growSugar() {
		for (int row=0; row<myPatches.length; row++) {
			for (int col=0; col<myPatches[0].length; col++) {
				if (isSugar(myPatches[row][col])) {
					int count = (int)myPatches[row][col].myCell.getState().myParams[0];
					if (count < maxSugar - sugarGrowBackRate) count += sugarGrowBackRate;
					if (count < maxSugar && count > maxSugar - sugarGrowBackRate) count = maxSugar;
					setSugarLevel(myPatches[row][col], count);
					myPatches[row][col].myCell.getState().myColor = myColorMap.get(count);
				}
			}
		}
	}

	@Override
	public Patch getNext(Patch curr) {
		
		if (curr.isEmpty || curr.flagged || isSugar(curr)) return curr;
		
		List<Patch> neighbors = getNeighbors(curr);
		List<Patch> equalNeighbors = new ArrayList<Patch>();
		Patch nextPatch = new Patch(curr);

		int mostSugar = 0;
		for (Patch patch : neighbors) {
			if (getSugarLevel(patch) >= mostSugar) {
				nextPatch = patch;
				mostSugar = getSugarLevel(patch);
			}
		}
		
		for (Patch patch : neighbors) {
			if (getSugarLevel(patch) == getSugarLevel(nextPatch)) {
				equalNeighbors.add(patch);
			}
		}
		
		if (equalNeighbors.size() > 1) {
			nextPatch = getClosestNeighbor(curr, equalNeighbors);
		}
		
		clearPatch(curr);
		moveAgent(nextPatch);
		subtractMetabolism(nextPatch);
		
		addLater(nextPatch);
		
		return curr;
	}
	
	private boolean isSugar(Patch p) {
		return p.getCell().getState().myIndex == 1;
	}
	
	private int getSugarLevel(Patch p) {	
		return (int)p.myCell.getState().myParams[0];
	}
	
	private void setSugarLevel(Patch patch, int sugarLevel) {
		patch.myCell.getState().myParams = new Object[]{sugarLevel};
		patch.myCell.getState().myColor = myColorMap.get(sugarLevel);
	}
	
	private Patch clearPatch(Patch p){
		Patch ret = new Patch(p.myRow, p.myCol, false);
		ret.fill(new Cell(myPossibleStates[1])); //Make it sugar
		setSugarLevel(ret, 0);
		ret.flag();
		return ret;
	}
	
	private Patch moveAgent(Patch nextPatch) {
		Patch ret = new Patch(nextPatch.myRow, nextPatch.myCol, false);
		ret.fill(new Cell(myPossibleStates[0])); //Make it an agent
		setSugarLevel(ret, getSugarLevel(nextPatch));
		ret.flag();
		return ret;
	}
	
	private void subtractMetabolism(Patch nextPatch){
		int currLevel = getSugarLevel(nextPatch);
		currLevel -= sugarMetabolism;
		setSugarLevel(nextPatch, currLevel);
	}
	
	private void setupColorMap(){
		myColorMap = new HashMap<Integer, Color>();
		for (int i=0; i<=maxSugar; i++) {
			int rgbBVal = (maxSugar-i)*255/maxSugar;
			Color color = Color.rgb(255, 255, rgbBVal);
			myColorMap.put(i, color);
		}
	}
	
	private void addLater(Patch p) {
		patchesToMove.add(p);
	}
	
	private void movePatches(Patch[][] grid) {
		for (Patch p : patchesToMove) {
			grid[p.myRow][p.myCol] = p;
		}
	}
	
	public Patch getClosestNeighbor(Patch patch, List<Patch> neighbors) {
		int smallestDifference = Integer.MAX_VALUE;
		Patch retPatch = new Patch(0, 0, true);

		for (Patch p : neighbors) {
			if (p.myCol == patch.myCol) {
				int difference = p.myRow - patch.myRow;
				if (difference < 0) {
					difference = difference * (-1);
				}
				if (difference < smallestDifference) {
					smallestDifference = difference;
					retPatch = p;
				}
			}
			if (p.myRow == patch.myRow) {
				int difference = p.myCol - patch.myCol;
				if (difference < 0) {
					difference = difference * (-1);
				}
				if (difference < smallestDifference) {
					smallestDifference = difference;
					retPatch = p;
				}
			}
		}
		return retPatch;

	}

	@Override
	public List<Patch> getNeighbors(Patch p) { 

		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;

		switch (gridShape) {
		
		case ("Triangular"):
			// Currently looks past other agents for sugar
			for (int i = 1; i < vision + 1; i++) {
				if (!isOutside(row, col - i) && isSugar(myPatches[row][col-i]))
					ret.add(myPatches[row][col - i]);
				if (!isOutside(row, col + i) && isSugar(myPatches[row][col+i]))
					ret.add(myPatches[row][col + i]);
				if (!isOutside(row - i, col) && col % 2 == 0 && isSugar(myPatches[row-i][col]))
					ret.add(myPatches[row - i][col]);
				if (!isOutside(row + i, col) && col % 2 == 1 && isSugar(myPatches[row+i][col]))
					ret.add(myPatches[row + i][col]);
			}
			break;
		default: //Rectangular or Hexagonal
			for (int i = 1; i < vision + 1; i++) {
				if (!isOutside(row, col - i) && isSugar(myPatches[row][col-i]))
					ret.add(myPatches[row][col - i]);
				if (!isOutside(row, col + i) && isSugar(myPatches[row][col+i]))
					ret.add(myPatches[row][col + i]);
				if (!isOutside(row - i, col) && isSugar(myPatches[row-i][col]))
					ret.add(myPatches[row - i][col]);
				if (!isOutside(row + i, col) && isSugar(myPatches[row+i][col]))
					ret.add(myPatches[row + i][col]);
			}
			break;
		}
		return ret;
	}
	
	private void randomizeSugar() {
		for (int row=0; row<myPatches.length; row++) {
			for (int col=0; col<myPatches[0].length; col++) {
				// WHY DOESN'T THIS WORK!!!!!!??!?!?!????!??!???!?
				if (isSugar(myPatches[row][col])) {
					int count = rand.nextInt(maxSugar + 1);
					setSugarLevel(myPatches[row][col], count);
				}
			}
		}
	}
	
	// WHY DOESN'T THIS WORK!!!!!!??!?!?!????!??!???!?
	private void testRandomness() {
		Color c = myPatches[0][0].myCell.getColor();
		int count=0;
		for (int row=0; row<myPatches.length; row++) {
			for (int col=0; col<myPatches[0].length; col++) {
				if (isSugar(myPatches[row][col])) {
					if (myPatches[row][col].myCell.getColor().equals(c)){
						count++;
					} else {
						System.out.println(Integer.toString(row)+","+Integer.toString(col));
					}
				}
			}
		}
	}
}
