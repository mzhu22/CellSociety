package cellsociety_team08;


public class SpreadingFire extends RuleSet {
	
	private static final String SPREADING_FIRE = "Spreading of Fire";
	
	private static final State[] possibleStates = new State[] {
		new State("Empty", 0), // index 0
		new State("Tree", 1), // index 1
		new State("Burning", 2) // index 2
	};

	public SpreadingFire() {
		super(SPREADING_FIRE, possibleStates);
	}

	@Override
	public State getState(Cell[][] neighborhood) {
		
		State currState = neighborhood[1][1].getState();
		Cell north = neighborhood[0][1];
		Cell south = neighborhood[2][1];
		Cell west = neighborhood[1][0];
		Cell east = neighborhood[1][2];
		
		// If it's already burning or empty, it will be empty on the next iteration
		if (!currState.equals(possibleStates[1])) return possibleStates[0];
		
		if (isBurning(north) || isBurning(south) || isBurning(west) || isBurning(east)) return possibleStates[2];
		
		return possibleStates[1];
	}
	
	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation();
	}
	
	private boolean isBurning(Cell cell) {
		if (cell == null) return false;
		return (possibleStates[2].equals(cell.getState()));
	}

}
