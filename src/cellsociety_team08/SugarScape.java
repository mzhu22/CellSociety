package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;

public class SugarScape extends RuleSet {
	
	public int sugarGrowBackRate = 1;
	
	@Override
	public Patch getNext(Patch curr) {
		
		if (curr.isEmpty && curr.sugarLevel < curr.maxSugar) {
			curr.sugarLevel = curr.sugarLevel + sugarGrowBackRate;
		}
		
		
		
		return null;
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
