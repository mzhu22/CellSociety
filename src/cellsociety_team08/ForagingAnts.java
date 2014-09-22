package cellsociety_team08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

public class ForagingAnts extends RuleSet{
	
	private static int xPos;
	private static int yPos;
	private static Patch HOME_BASE;// = new Patch(xPos, yPos, false);
	private List<String> orientationList = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
	private String myOrientation;
	private double myHomeDesire;
	private double myFoodDesire;
	private int antLimit = 10;
	private State[] myPossibleStates = new State[] {
			new State("Home", 0, Color.GREEN, null), // index 0
			new State("Ant", 1, Color.ORANGERED, null), // index 1
			new State("Food", 2, Color.GREEN, null) // index 2
	};
	
			
	public ForagingAnts(String orientation, double homeDesire, double foodDesire, int x, int y) {
		myOrientation = orientation;
		myHomeDesire = homeDesire;
		myFoodDesire = foodDesire;
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
	
	public List<Patch> getForwardNeighbors(Patch patch) {
		List<Patch> neighbors = getNeighbors(patch);
		List<Patch> forwardNeighbors = new ArrayList<Patch>();

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
		
		return null;
	}
	
	
	

}
