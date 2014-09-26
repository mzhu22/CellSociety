package backEnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class PredatorPrey extends RuleSet {

	private static final String SHARK_BREED_TIME = "sharkBreedTime";
	private static final String SHARK_STARVE_TIME = "sharkStarveTime";
	private static final String FISH_BREED_TIME = "fishBreedTime";
	private static int sharkBreedTime, sharkStarveTime, fishBreedTime;
	
	private Random rand = new Random();

	private List<Patch> patchesToMove = new ArrayList<Patch>();

	public PredatorPrey() {
		super();
	}

	@Override
	public void setParams(Map<String, Object> params) {
		super.setParams(params);
		sharkBreedTime = Integer
				.parseInt((String) params.get(SHARK_BREED_TIME));
		sharkStarveTime = Integer.parseInt((String) params
				.get(SHARK_STARVE_TIME));
		fishBreedTime = Integer.parseInt((String) params.get(FISH_BREED_TIME));

		myPossibleStates = new State[] {
				new State("Fish", 0, Color.CHARTREUSE,
						new Object[] { fishBreedTime }), // index 0
				new State("Shark", 1, Color.GREY, new Object[] {
						sharkBreedTime, sharkStarveTime }) // index 1
		};
	}

	@Override
	public Patch[][] update() {
		patchesToMove.clear();
		Patch[][] nextGrid = makeNextGrid();
		myPatches = nextGrid;
		moveOrAddCells();
		patchesToMove.clear();
		breedIfNecessary();
		moveOrAddCells();
		clearAllFlags();
		return myPatches;
	}

	private Patch[][] makeNextGrid() {
		Patch[][] nextGrid = new Patch[myPatches.length][myPatches[0].length];
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches.length; j++) {
				nextGrid[i][j] = getNext(myPatches[i][j]);
			}
		}
		return nextGrid;
	}

	private void breedIfNecessary() {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches.length; j++) {
				List<Patch> neighbors = getNeighbors(myPatches[i][j]);
				checkBreed(myPatches[i][j], neighbors);
			}
		}
	}

	private void clearAllFlags() {
		for (int i = 0; i < myPatches.length; i++) {
			for (int j = 0; j < myPatches[0].length; j++) {
				myPatches[i][j].flagged = false;
			}
		}
	}
	
	

	@Override
	public Patch getNext(Patch patch) {
		
		List<Patch> neighbors = getDirectNeighbors(patch);

		if (patch.isEmpty || patch.flagged || patch.myCell == null
				|| patch.getMyCellState() == null) {
			return patch;
		}

		if (patch.myCellStateEquals(myPossibleStates[0])) { // FISH
			
			moveAdj(patch, neighbors);
			return patch;
			
		} else { // SHARK

			// See if I have starved
			if (checkStarve(patch)) return patch;

			// Still alive!
			if (!checkForFishAndMove(patch, neighbors))
				moveAdj(patch, neighbors);
		}

		return patch;
	}

	private boolean checkStarve(Patch patch) {
		
		State s = patch.getCell().getState();
		s.myParams[1] = (int)s.myParams[1] - 1;
		patch.setMyCellState(s);
		
		if ((int) patch.getMyCellState().myParams[1] == 0) {
			patch.clear();
			patch.flag();
			return true;
		}
		
		return false;
	}

	private void checkBreed(Patch patch, List<Patch> neighborhood) {

		if (patch.isEmpty || patch.flagged || patch.myCell == null
				|| patch.getMyCellState() == null)
			return;

		// decrement breed time
		patch.getMyCellState().myParams[0] = ((int) patch.getMyCellState().myParams[0]) - 1;
		int breedTime = ((int) patch.getMyCellState().myParams[0]);

		if (breedTime == 0) {
			breed(patch, neighborhood);

			// Reset breed timers
			if (patch.getCell().getState().myIndex == 0)
				patch.getMyCellState().myParams[0] = fishBreedTime;
			else
				patch.getMyCellState().myParams[0] = sharkBreedTime;
		}
	}

	private List<Patch> findEmpties(List<Patch> neighborhood) {

		List<Patch> empties = new ArrayList<Patch>();
		for (Patch p : neighborhood) {
			if (p.isEmpty && !p.flagged) {
				empties.add(p);
			}
		}
		return empties;
	}

	private void moveAdj(Patch patch, List<Patch> neighborhood) {

		List<Patch> empties = findEmpties(neighborhood);
		if (empties.isEmpty())
			return;
		Patch newLocation = empties.get(rand.nextInt(empties.size()));

		addCell(newLocation, patch.getCell());
		patch.clear();
		patch.flag();
		newLocation.flagged = true;

	}

	private void addCell(Patch patch, Cell cell) {
		Patch p = new Patch(patch);
		p.myCell = cell;
		patchesToMove.add(p);
	}

	private void moveOrAddCells() {
		for (Patch p : patchesToMove) {
			myPatches[p.myRow][p.myCol] = p;
		}
	}

	private boolean checkForFishAndMove(Patch patch, List<Patch> neighborhood) {

		List<Patch> fishFood = getPossibleFish(neighborhood);
		if (fishFood.isEmpty())
			return false;

		// Eat a fish at random
		eatFish(patch, fishFood.get(rand.nextInt(fishFood.size())));

		return true;
	}

	private List<Patch> getPossibleFish(List<Patch> neighborhood) {

		List<Patch> fish = new ArrayList<Patch>();
		for (Patch p : neighborhood) {
			if (!p.isEmpty && !p.flagged && p.myCell != null
					&& p.getCell().getState().myIndex == 0) {
				fish.add(p);
			}
		}
		return fish;
	}

	private void eatFish(Patch shark, Patch fish) {
		
		// Reset starve timer
		State s = shark.getMyCellState();
		s.myParams[1] = sharkStarveTime;
		shark.myCell.setState(s);
		
		fish.myCell = shark.myCell;
		patchesToMove.add(fish);
		fish.flagged = true;
		shark.clear();
		shark.flag();
	}

	private void breed(Patch patch, List<Patch> neighborhood) {

		List<Patch> empties = findEmpties(neighborhood);
		if (empties.isEmpty())
			return;

		Patch newLocation = empties.get(rand.nextInt(empties.size()));

		addCell(newLocation, new Cell(myPossibleStates[patch.getCell()
				.getState().myIndex]));
		newLocation.flag();

	}

}
