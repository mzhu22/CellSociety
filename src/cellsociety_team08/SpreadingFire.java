package cellsociety_team08;


public class SpreadingFire extends RuleSet {
	
	private static final String SPREADING_FIRE = "Spreading of Fire";
	
	private static final State[] possibleStates = new State[] {
		new State("Tree", 0, null), // index 1
		new State("Burning", 1, null) // index 2
	};

	public SpreadingFire() {
		super(SPREADING_FIRE, possibleStates);
	}

	@Override
	public State getState(Cell[][] neighborhood) {
		
		Cell curr = neighborhood[1][1];
		Cell north = neighborhood[0][1];
		Cell south = neighborhood[2][1];
		Cell west = neighborhood[1][0];
		Cell east = neighborhood[1][2];
		
		// If it's already burning, it will be empty on the next iteration
		if (curr.getState().equals(possibleStates[1])) {
			curr.isEmpty = true;
			return null;
		}
		
		if (isBurning(north) || isBurning(south) || isBurning(west) || isBurning(east)) return possibleStates[1];
		
		return possibleStates[0];
	}
	
	private boolean isBurning(Cell cell) {
		if (cell == null) return false;
		return (possibleStates[1].equals(cell.getState()));
	}
	
	@Override
	public int[] getLocation(Cell[][] neighborhood) {
		return neighborhood[1][1].getLocation();
	}

}
