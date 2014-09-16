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
				// move somewhere else...
			}
		} else { // Agent B
			if (currSat >= myMinSatB) {
				setSatisfied(currCell);
				return currCell.getState();
			} else {
				
			}
		}
		
		
		return null;
	}
	
	private double getSatisfaction(Cell[][] neighborhood) {
		
		Cell currCell = neighborhood[1][1];
		double newSat = 0;
		
		// Do calculation...
		
		return newSat;
	}
	
	private void setSatisfied(Cell c) {
		c.getState().setParams(new Object[]{true});
	}

	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation(); // TO DO
	}

}
