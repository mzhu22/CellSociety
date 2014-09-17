package cellsociety_team08;

public class Segregation extends RuleSet {
	
	private static final String SEGREGATION = "Segregation";
	
	private static double myMinSatA, myMinSatB;
	
	private static final State[] possibleStates = new State[] {
		new State("Agent A", 0, null), // index 0
		new State("Agent B", 1, null) // index 1
	};
	
	public Segregation(Object[] params) {
		super(SEGREGATION, possibleStates);
		myMinSatA = (double)params[0];
		myMinSatB = (double)params[1];
	}

	@Override
	public State getState(Cell[][] neighborhood) {
		
		Cell currCell = neighborhood[1][1];
		
		double currSat = getSatisfaction(neighborhood);
			
		if (currCell.getState().myIndex == 1) { // Agent A
			if (currSat >= myMinSatA) {
				setSatisfied(currCell);
				return currCell.getState();
			} else {
				currCell.setLocation(getNewLocation(currCell));
			}
		} else { // Agent B
			if (currSat >= myMinSatB) {
				setSatisfied(currCell);
				return currCell.getState();
			} else {
				currCell.setLocation(getNewLocation(currCell));
			}
		}
		
		
		return null;
	}
	
	private double getSatisfaction(Cell[][] neighborhood) {
		
		Cell currCell = neighborhood[1][1];
		double newSat = 0;
		double total = 0;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (neighborhood[i][j] != null && !neighborhood[i][j].isEmpty) {
					if (neighborhood[i][j].getState().equals(currCell.getState())) {
						newSat++;
					}
					total++;
				}
			}
		}
		
		newSat = newSat/total;
		
		return newSat;
	}
	
	private void setSatisfied(Cell c) {
		c.getState().setParams(new Object[]{true});
	}
	
	private boolean isSatisfied(Cell c) {
		return (boolean) c.getState().getParams()[0];
	}

	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation(); // TO DO
	}
	
	public int[] getNewLocation(Cell c) {
		// Gunna be tuff...
		// May need a method in Grid for this
		return null;
	}

}
