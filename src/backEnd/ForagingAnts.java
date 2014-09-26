package backEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class ForagingAnts extends RuleSet{
	
	private static int xPos;
	private static int yPos;
	private static Patch HOME_BASE; // = new Patch(xPos, yPos, false);
	private String myOrientation;
	private int antLimit = 10;
	private HashMap<Patch, Integer> homePheromoneLevels = new HashMap<Patch, Integer>();
	private HashMap<Patch, Integer> foodPheromoneLevels = new HashMap<Patch, Integer>();

	private State[] myPossibleStates = new State[] {
			new State("Home", 0, Color.BLUE, null), // index 0
			new State("Ant", 1, Color.RED, null), // index 1
			new State("Food", 2, Color.GREEN, null), // index 2
			new State("Empty", 3, Color.WHITE, null)
	};
	
			
	public ForagingAnts(String orientation, int x, int y) {
		myOrientation = orientation;
		xPos = x;
		yPos = y;
		HOME_BASE = new Patch(xPos, yPos, false);
		setPheromoneLevels(HOME_BASE, 10, 0);
	}
	
	public void setPheromoneLevels(Patch patch, int homePheromone, int foodPheromone) {
		homePheromoneLevels.put(patch, homePheromone);
		foodPheromoneLevels.put(patch, foodPheromone);
	}
	
	public void decrementPheromoneLevels(Patch patch) {
		homePheromoneLevels.put(patch, homePheromoneLevels.get(patch) - 1);
		foodPheromoneLevels.put(patch, foodPheromoneLevels.get(patch) - 1);
	}
	
	public int getNumCells(Patch patch) {
		int neighborCount = patch.numCells;
		return neighborCount;
	}
	
	public int getNumNeighbors(List<Patch> neighborhood) {
		int numNeighbors = 0;
		for (Patch patch: neighborhood) {
			numNeighbors += getNumCells(patch);
		}
		return numNeighbors;
	}
	
	public List<Patch> getBackNeighbors(Patch patch) {
		List<Patch> backNeighbors = getNeighbors(patch);
		List<Patch> forwardNeighbors = getForwardNeighbors(patch);
		for (Patch p: backNeighbors) {
			if (forwardNeighbors.contains(p)) backNeighbors.remove(p);
		}
		return backNeighbors;
	}
	
	public List<Patch> getForwardNeighbors(Patch patch) { //will need to implement myOrientation as a class i guess because of what he said in class
		List<Patch> neighbors = getNeighbors(patch);
		List<Patch> forwardNeighbors = new ArrayList<Patch>();

		// Tons of repeated code here. Could definitely shorten this with some if statements.
		switch (myOrientation) {
		case "N": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row + 1][col]);
				forwardNeighbors.add(myPatches[row + 1][col + 1]);
				forwardNeighbors.add(myPatches[row + 1][col - 1]);
			}
		case "NE": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row + 1][col + 1]);
				forwardNeighbors.add(myPatches[row][col + 1]);
				forwardNeighbors.add(myPatches[row + 1][col]);
			}
		case "E": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row][col + 1]);
				forwardNeighbors.add(myPatches[row + 1][col + 1]);
				forwardNeighbors.add(myPatches[row - 1][col + 1]);
			}
		case "SE": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row - 1][col + 1]);
				forwardNeighbors.add(myPatches[row + 1][col]);
				forwardNeighbors.add(myPatches[row - 1][col]);
			}
		case "S": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row - 1][col]);
				forwardNeighbors.add(myPatches[row - 1][col + 1]);
				forwardNeighbors.add(myPatches[row - 1][col - 1]);
			}
		case "SW": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row - 1][col - 1]);
				forwardNeighbors.add(myPatches[row - 1][col]);
				forwardNeighbors.add(myPatches[row][col - 1]);
			}
		case "W": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row][col - 1]);
				forwardNeighbors.add(myPatches[row + 1][col - 1]);
				forwardNeighbors.add(myPatches[row - 1][col - 1]);
			}
		case "NW": 
			for (Patch p: neighbors) {
				int row = p.myRow;
				int col = p.myCol;
				forwardNeighbors.add(myPatches[row][col - 1]); //W
				forwardNeighbors.add(myPatches[row + 1][col]); //N
				forwardNeighbors.add(myPatches[row + 1][col - 1]); //NW
			}
		default: break;
		}
		forwardNeighbors = getViableForwardNeighbors(forwardNeighbors);
		return forwardNeighbors;
	}
	
	public List<Patch> getViableForwardNeighbors(List<Patch> forwardNeighbors) {
		for (Patch patch: forwardNeighbors) {
			if (getNumCells(patch) > antLimit) {
				forwardNeighbors.remove(patch);
			}
		}
		return forwardNeighbors;
	}

	@Override
	public Patch getNext(Patch patch) {
		
		Patch nextPatch = patch;
		Random rand = new Random();
		List<Patch> forwardNeighbors = getForwardNeighbors(patch);
		List<Patch> backNeighbors = getBackNeighbors(patch);
		
		if (patch.myCell.getState().equals(myPossibleStates[0])) {
			return patch;
		}
		else if (patch.myCell.getState().equals(myPossibleStates[2])) {
			decrementPheromoneLevels(patch);
		}
		
		else {
			int maxHomePheromones = 0;
			int maxFoodPheromones = 0;
			if (patch.myCell.hasFood) { //we're heading home
				if (forwardNeighbors.size() > 0) {
					for (Patch p: forwardNeighbors) {
						if (!homePheromoneLevels.containsKey(p)) {
							homePheromoneLevels.put(p, 0);
						}
						if (homePheromoneLevels.get(p) > maxHomePheromones) {
							maxHomePheromones = homePheromoneLevels.get(p);
							nextPatch = p;
						}
					}
				}
				else {
					if (backNeighbors.size() > 0) {
						for (Patch p: backNeighbors) {
							if (!homePheromoneLevels.containsKey(p)) {
								homePheromoneLevels.put(p, 0);
							}
							if (homePheromoneLevels.get(p) > maxHomePheromones) {
								maxHomePheromones = homePheromoneLevels.get(p);
								nextPatch = p;
							}
						}
					} 
				}
				if (nextPatch == patch) { //if we didn't find a forward neighbor with high homePhers, pick random
					nextPatch = forwardNeighbors.get(rand.nextInt(forwardNeighbors.size()));
				} 
				foodPheromoneLevels.replace(patch, foodPheromoneLevels.get(patch), 10);
			}
			if (!patch.myCell.hasFood) { //exploring for food
				if (forwardNeighbors.size() > 0) {
					for (Patch p: forwardNeighbors) {
						if (!foodPheromoneLevels.containsKey(p)) {
							foodPheromoneLevels.put(p, 0);
						}
						if (foodPheromoneLevels.get(p) > maxFoodPheromones) {
							maxFoodPheromones = foodPheromoneLevels.get(p);
							nextPatch = p;
						}
					}
				}
				else {
					for (Patch p: backNeighbors) {
						if (!foodPheromoneLevels.containsKey(p)) {
							foodPheromoneLevels.put(p, 0);
						}
						if (foodPheromoneLevels.get(p) > maxFoodPheromones) {
							maxFoodPheromones = foodPheromoneLevels.get(p);
							nextPatch = p;
						}
					}
				}
				if (nextPatch == patch) { //if we didn't find a forward neighbor with high Pheromones, pick random
					nextPatch = backNeighbors.get(rand.nextInt(backNeighbors.size()));
				}
				homePheromoneLevels.replace(patch, homePheromoneLevels.get(patch), 10);
			}
		}
		if (nextPatch.equals(HOME_BASE) && nextPatch.myCell.hasFood) { //leave food at base and go back
			nextPatch.myCell.hasFood = false;
		}
		return nextPatch;
	}
		
}

