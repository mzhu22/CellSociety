package cellsociety_team08;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javafx.scene.paint.Color;

public class Segregation extends RuleSet {

	private static final String SEGREGATION = "Segregation";
	private static final String MIN_SAT_A = "minSatA";
	private static final String MIN_SAT_B = "minSatB";

	private static float myMinSatA, myMinSatB;
	
	private Queue<Cell> toBeMovedHeap;

	public Segregation(Map<String, Object> params) {
		super(params);
		
		myPossibleStates = new State[] {
				new State("Agent A", 0, Color.BLUE, new Object[]{false}), // index 0
				new State("Agent B", 1, Color.RED, new Object[]{false}) // index 1
		};

		myDescription = SEGREGATION;
		if(params.get(MIN_SAT_A)!=null && params.get(MIN_SAT_B)!=null){
			myMinSatA = Float.parseFloat((String) params.get(MIN_SAT_A));
			myMinSatB = Float.parseFloat((String) params.get(MIN_SAT_B));
		}
		
		toBeMovedHeap = new LinkedList<>();
	}
	
	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {
		// If the current cell is satisfied with its neighbors, return its current state!
		if (patch.myCell == null)
			return patch;

		float currSat = getSatisfaction(patch, neighborhood);

		if(currSat<myMinSatA){
			moveCell(patch.getCell());
			patch.clear();			
		}
		return patch;
	}
	
	private void moveCell(Cell toBeMoved){
		Random gridCoord = new Random();
		boolean placed = false;
		while(!placed){
			int row = gridCoord.nextInt(myPatches.length);
			int col = gridCoord.nextInt(myPatches[0].length);
			if(!myPatches[row][col].containsCell()){
				myPatches[row][col].fill(toBeMoved);
				placed = true;
			}
		}
		
	}

	private float getSatisfaction(Patch patch, List<Patch> neighborhood) {

		float newSat = 0;
		float total = 0;

		for (Patch p : neighborhood) {
			if (p.containsCell()) {
				if (p.myCell.getState().equals(patch.myCell.getState())) {
					newSat++;
				}
				total++;
			}
		}

		return newSat/total;
	}
}
