package cellsociety_team08;

import java.util.Arrays;
import java.util.List;

public class ForagingAnts {
	
	private static int xPos;
	private static int yPos;
	private static final Patch HOME_BASE = new Patch(xPos, yPos, false);
	private List<String> orientationList = Arrays.asList("N", "NE", "E", "SE", "S", "SW", "W", "NW");
	private String myOrientation;
	private double myHomeDesire;
	private double myFoodDesire;
	private Patch[][] myGrid;
	
			
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
	
	
	

}
