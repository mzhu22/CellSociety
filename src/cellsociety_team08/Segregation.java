package cellsociety_team08;

public class Segregation extends RuleSet {

	private static final String SEGREGATION = "Segregation";

	private static double myMinSatA, myMinSatB;

	/*
	 * States accessed by this class will have the a parameter for isSatisfied
	 * which will be a boolean value
	 */
	private static final State[] possibleStates = new State[] {
			new State("Agent A", 0, null), // index 0
			new State("Agent B", 1, null) // index 1
	};

	public Segregation(Object[] params) {
		super(SEGREGATION, possibleStates, params);
		myMinSatA = (double) params[0];
		myMinSatB = (double) params[1];
	}

	@Override
	public State getState(Cell[][] neighborhood) {

		Cell currCell = neighborhood[1][1];

		// If the current cell is satisfied with its neighbors, return its
		// current state!
		if (isSatisfied(currCell))
			return currCell.getState();

		double currSat = getSatisfaction(neighborhood);

		if ((currCell.getState().myIndex == 0 && currSat >= myMinSatA)
				|| (currCell.getState().myIndex == 1 && currSat >= myMinSatB)) {
			return satisfiedCell(currCell);
		} else {
			currCell.setLocation(getNewLocation(currCell));
		}

		return null;
	}

	private double getSatisfaction(Cell[][] neighborhood) {

		Cell currCell = neighborhood[1][1];
		double newSat = 0;
		double total = 0;

		for (int i = 0; i < neighborhood.length; i++) {
			for (int j = 0; j < neighborhood[0].length; j++) {
				if (neighborhood[i][j] != null && !neighborhood[i][j].isEmpty) {
					if (neighborhood[i][j].getState().equals(
							currCell.getState())) {
						newSat++;
					}
					total++;
				}
			}
		}

		newSat = newSat / total;

		return newSat;
	}

	private State satisfiedCell(Cell c) {
		State s = c.getState();
		s.setParams(new Object[] { true });
		return s;
	}

	private boolean isSatisfied(Cell c) {
		return (boolean) c.getState().getParams()[0];
	}

	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation(); // TO DO
	}

	public int[] getNewLocation(Cell c) {
		/*
		 * Not sure how to implement this yet. May have to work with Justin to
		 * do this
		 */
		return null;
	}

}
