package backEnd;

import java.util.ArrayList;
import java.util.List;

public class SugarScape extends RuleSet {
	
	public int sugarGrowBackRate = 1;
	
	@Override
	public Patch getNext(Patch curr) {
		int row = curr.myRow;
		int col = curr.myCol;
		List<Patch> neighbors = getNeighbors(curr);
		List<Patch> equalNeighbors = new ArrayList<Patch>();
		Patch nextPatch = new Patch(row, col, false);
		
		if (curr.isEmpty && curr.sugarLevel < curr.maxSugar) { //grow the sugar back
			curr.sugarLevel = curr.sugarLevel + sugarGrowBackRate;
			return curr;
		}
		
		int mostSugar = 0;
		for (Patch patch: neighbors) {
			if (patch.sugarLevel >= mostSugar) {
				nextPatch = patch;
				mostSugar = patch.sugarLevel;
			}
		}
		for (Patch patch: neighbors) {
			if (patch.sugarLevel == nextPatch.sugarLevel) {
				equalNeighbors.add(patch);
			}
		}
		if (equalNeighbors.size() > 1) {
			nextPatch = getClosestNeighbor(curr, equalNeighbors);
		}
		nextPatch.sugarLevel = nextPatch.sugarLevel -nextPatch.myCell.sugarMetabolism;
		return nextPatch;
	}
	
	public Patch getClosestNeighbor(Patch patch, List<Patch> neighbors) {
		int smallestDifference = 100000;
		Patch retPatch = new Patch(0, 0, true);
		
		for (Patch p: neighbors) {
			if (p.myCol == patch.myCol) {
				int difference = p.myRow - patch.myRow;
				if (difference < 0) {
					difference = difference * (-1);
				}
				if (difference < smallestDifference) {
					smallestDifference = difference;
					retPatch = p;
					p.clear();
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
					p.clear();
				}
			}
		}
		return retPatch;
		
	}

	
	@Override
	public List<Patch> getNeighbors(Patch p) {
		return getDirectNeighbors(p);
	}
	
	@Override 
	public List<Patch> getDirectNeighbors(Patch p) {  //don't have a get EXTENDED direct neighbors for hexagonal yet
		
		List<Patch> ret = new ArrayList<Patch>();
		int row = p.myRow;
		int col = p.myCol;
		int vision = p.myCell.vision;
		
		switch (gridShape) {	
		case ("Hexagonal"):
			break;
		case ("Triangular"):
			for (int i = 1; i < vision+1; i++) {
				if (!isOutside(row, col - i))
					ret.add(myPatches[row][col - i]);
				if (!isOutside(row, col + i))
					ret.add(myPatches[row][col + i]);
				if (!isOutside(row - i, col) && col % 2 == 0)
					ret.add(myPatches[row - i][col]);
				if (!isOutside(row + i, col) && col % 2 == 1)
					ret.add(myPatches[row + i][col]);
			}
			break;
		default:
			for (int i = 1; i < vision+1; i++) {
				if (!isOutside(row, col - i))
					ret.add(myPatches[row][col - i]);
				if (!isOutside(row, col + i))
					ret.add(myPatches[row][col + i]);
				if (!isOutside(row - i, col))
					ret.add(myPatches[row - i][col]);
				if (!isOutside(row + i, col))
					ret.add(myPatches[row + i][col]);
			}
			break;
		}
		return ret;
	}
}
