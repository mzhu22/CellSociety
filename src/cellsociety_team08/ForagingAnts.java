package cellsociety_team08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class ForagingAnts extends RuleSet{
	
	private static int xPos;
	private static int yPos;
	private static Patch HOME_BASE; // = new Patch(xPos, yPos, false);
	private List<String> orientationList = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
	private String myOrientation;
	private int antLimit = 10;
	private int maxFoodPheromones = 10;
	private int maxHomePheromones = 10;
	private State[] myPossibleStates = new State[] {
			new State("Home", 0, Color.BLUE, null), // index 0
			new State("Ant", 1, Color.RED, null), // index 1
			new State("Food", 2, Color.GREEN, null) // index 2
	};
	
			
	public ForagingAnts(String orientation, int x, int y) {
		myOrientation = orientation;
		xPos = x;
		yPos = y;
		HOME_BASE = new Patch(xPos, yPos, false);
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
	
	public List<Patch> getForwardNeighbors(Patch patch) {
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
		// TODO Auto-generated method stub
		List<Patch> forwardNeighbors = getForwardNeighbors(patch);
		List<Patch> backNeighbors = getBackNeighbors(patch);
		Patch nextPatch = patch;
		double highPheromones = 0;
		
		if (patch.myCell.getState() == myPossibleStates[0] || patch.myCell.getState() == myPossibleStates[2]) { //home base doesn't do anything except depreciate pheromone values
			patch.foodPheromoneLevel--;
			patch.homePheromoneLevel--;
			return patch;
		}
		
		if (!patch.myCell.hasFood ) {
			//patch.myCell.foodDesire = 10;
			if (forwardNeighbors.size() > 0) {
				for (Patch p: forwardNeighbors) {
					if (p.foodPheromoneLevel > highPheromones) {
						highPheromones = p.foodPheromoneLevel;
						nextPatch = p;
					}
				}
				Random rand = new Random();
				if (nextPatch == patch || rand.nextInt(3) == 0) { //if we didn't find a new patch
					nextPatch = forwardNeighbors.get(rand.nextInt(forwardNeighbors.size()));
				}
			}
			else {
				for (Patch p: backNeighbors) {
					if (p.foodPheromoneLevel > highPheromones) {
						highPheromones = p.foodPheromoneLevel;
						nextPatch = p;
					}
				}
				
				Random rand = new Random();
				if (nextPatch == patch || rand.nextInt(3) == 0) { //if we didn't find a new patch
					nextPatch = backNeighbors.get(rand.nextInt(backNeighbors.size()));
				}
			}
			patch.homePheromoneLevel = 10;
		}
		
		else { // cell has food
			if (forwardNeighbors.size() > 0) {
				for (Patch p: forwardNeighbors) {
					if (p.homePheromoneLevel > highPheromones) {
						highPheromones = p.homePheromoneLevel;
						nextPatch = p;
					}
				}
				Random rand = new Random();
				if (nextPatch == patch || rand.nextInt(3) == 0) { //if we didn't find a new patch 
					nextPatch = forwardNeighbors.get(rand.nextInt(forwardNeighbors.size()));
				}
			}
			for (Patch p: backNeighbors) {
				if (p.homePheromoneLevel > highPheromones) {
					highPheromones = p.homePheromoneLevel;
					nextPatch = p;
				}
			}
			Random rand = new Random();
			if (nextPatch == patch || rand.nextInt(3) == 0) { //if we didn't find a new patch
				nextPatch = backNeighbors.get(rand.nextInt(backNeighbors.size()));
			}
			patch.foodPheromoneLevel = 10;
		}
		
		nextPatch.myCell = patch.myCell;
		
		if (nextPatch.myCell.getState().myIndex == 2) { //pick up food
			nextPatch.myCell.hasFood = true;
		}
		
		if (nextPatch.myRow == HOME_BASE.myRow && nextPatch.myCol == HOME_BASE.myCol) { //drop food off at home base
			nextPatch.myCell.hasFood = false;
		}
		
		patch.clear();
		return nextPatch;
	}

}
