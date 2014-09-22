package cellsociety_team08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForagingAnts extends RuleSet{
	
	private static int xPos;
	private static int yPos;
	private static final Patch HOME_BASE = new Patch(xPos, yPos, false);
	private List<String> orientationList = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
	private String myOrientation;
	private double myHomeDesire;
	private double myFoodDesire;
	private Patch[][] myGrid;
	private int antLimit = 10;
	
			
	public ForagingAnts(String orientation, double homeDesire, double foodDesire, Patch[][] grid) {
		myOrientation = orientation;
		myHomeDesire = homeDesire;
		myFoodDesire = foodDesire;
		
	}
	
	public int getNumCells(Patch patch) {
		int neighborCount = patch.numCells;
		return neighborCount;
	}
	
	public int getNumNeighbors(List<Patch> neighborhood) {
		int numNeighbors = 0;
		for (Patch patch: neighborhood) {
			numNeighbors += patch.numCells;
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
			if (patch.numCells > antLimit) {
				forwardNeighbors.remove(patch);
			}
		}
		return forwardNeighbors;
	}

	@Override
	public Patch getNext(Patch curr) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
