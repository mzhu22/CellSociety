package cellsociety_team08;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class Segregation extends RuleSet {

	private static final String SEGREGATION = "Segregation";
	private static final String MIN_SAT_A = "minSatA";
	private static final String MIN_SAT_B = "minSatB";

	private static float myMinSatA, myMinSatB;

	public Segregation(Map<String, Object> params) {
		
		super(params);
		
		myPossibleStates = new State[] {
				new State("Agent A", 0, Color.BLUE, new Object[]{false}), // index 0
				new State("Agent B", 1, Color.RED, new Object[]{false}) // index 1
		};

		myDescription = SEGREGATION;

		myMinSatA = Float.parseFloat((String) myParams.get(MIN_SAT_A));
		myMinSatB = Float.parseFloat((String) myParams.get(MIN_SAT_B));
	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {

		// If the current cell is satisfied with its neighbors, return its
		// current state!
		if (patch.myCell == null || patch.isEmpty || isSatisfied(patch) || patch.flagged)
			return patch;

		float currSat = getSatisfaction(patch, neighborhood);

		// check for empty patches!!!!!!!
		if ((patch.myCell.getState().myIndex == 0 && currSat >= myMinSatA)
				|| (patch.myCell.getState().myIndex == 1 && currSat >= myMinSatB)) {
			return satisfiedPatch(patch);
		} else {
			move(patch, neighborhood);
			return notSatisfiedPatch(patch);
		}
	}

	/*
	 * public List<Patch> getAvaliableNeighbors(List<Patch> neighborhood) {
	 * List<Patch> availableNeighbors = new ArrayList<Patch>(); for (Patch
	 * patch: neighborhood) { if (patch.isEmpty) {
	 * availableNeighbors.add(patch); } } return availableNeighbors; }
	 */

	public void move(Patch patch, List<Patch> neighborhood) {

		if (neighborhood.size() > 0) {
			for (Patch p : neighborhood) {
				if (p.isEmpty && !p.flagged) {
					p.myCell = patch.myCell;
					patch.clear();
					p.flagged = true;
					return;
				}
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
		Cell c = p.myCell;
		State s = c.getState();
		boolean b = (boolean) s.getParams()[0];
		return b;
	}

	public int[] getNewLocation(Cell c) {
		/*
		 * Not sure how to implement this yet. May have to work with Justin to
		 * do this
		 */
		return null;
	}

}
