package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class Segregation extends RuleSet {

	private static final String SEGREGATION = "Segregation";
	private static final String MIN_SAT_A = "minSatA";
	private static final String MIN_SAT_B = "minSatB";

	private static double myMinSatA, myMinSatB;

	/*
	 * States accessed by this class will have the a parameter for isSatisfied
	 * which will be a boolean value
	 */
	private static final State[] possibleStates = new State[] {
			new State("Agent A", 0, Color.BLUE, null), // index 0
			new State("Agent B", 1, Color.RED, null) // index 1
	};

	public Segregation(Map<String, Object> params) {
		super(params);
		
		myDescription = SEGREGATION;
		
		//myMinSatA = Float.parseFloat((String) params.get(MIN_SAT_A));
		//myMinSatB = Float.parseFloat((String) params.get(MIN_SAT_B));
		
		myMinSatA = (double) myParams.get(MIN_SAT_A);
		myMinSatB = (double) myParams.get(MIN_SAT_B);
	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {

		// If the current cell is satisfied with its neighbors, return its
		// current state!
		if (isSatisfied(patch) || patch.flagged)
			return patch;

		double currSat = getSatisfaction(patch, neighborhood);
		
		// check for empty patches!!!!!!!
		if ((patch.myCell.getState().myIndex == 0 && currSat >= myMinSatA)
				|| (patch.myCell.getState().myIndex == 1 && currSat >= myMinSatB)) {
			return satisfiedPatch(patch);
		} else {
			move(patch, neighborhood);
			return notSatisfiedPatch(patch);
		}
	}
	
	/*public List<Patch> getAvaliableNeighbors(List<Patch> neighborhood) {
		List<Patch> availableNeighbors = new ArrayList<Patch>();
		for (Patch patch: neighborhood) {
			if (patch.isEmpty) {
				availableNeighbors.add(patch);
			}
		}
		return availableNeighbors;
	}*/
	
	public void move(Patch patch, List<Patch> neighborhood) {
		
		if (neighborhood.size() > 0) {
			int patchesLeft = 1;
			for(Patch p: neighborhood) {
				if (patchesLeft == 1) { //only move once
					p.myCell = patch.myCell;
					patch.clear();
					p.flagged = true;
					return;
				}		
			}
		}
	}

	private double getSatisfaction(Patch patch, List<Patch> neighborhood) {

		double newSat = 0;
		double total = 0;

		for (Patch p : neighborhood) {
			if (p.containsCell()) {
				if (p.myCell.getState().equals(patch.myCell.getState())) {
					newSat++;
				}
				total++;
			}
		}
		newSat = newSat / total;

		return newSat;
	}

	private Patch satisfiedPatch(Patch p) {
		State s = p.myCell.getState();
		s.setParams(new Object[] { true });
		p.myCell.setState(s);
		return p;
	}
	
	private Patch notSatisfiedPatch(Patch p) {
		State s = p.myCell.getState();
		s.setParams(new Object[] { false });
		p.myCell.setState(s);
		return p;
	}

	private boolean isSatisfied(Patch p) {
		return (boolean) p.myCell.getState().getParams()[0];
	}

	public int[] getNewLocation(Cell c) {
		/*
		 * Not sure how to implement this yet. May have to work with Justin to
		 * do this
		 */
		return null;
	}

}
