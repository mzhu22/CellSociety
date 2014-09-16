package cellsociety_team08;

public class PredatorPrey extends RuleSet {
	
	private static final String PREDATOR_PREY = "Predator Prey";
	
	private static final State[] possibleStates = new State[] {
		new State("Water", 0), // index 0
		new State("Fish", 1), // index 1
		new State("Shark", 2) // index 2
	};
	
	public PredatorPrey() {
		super(PREDATOR_PREY, possibleStates);
	}

	@Override
	public State getState(Cell[][] neighborhood) {
		
		State currState = neighborhood[1][1].getState();
		Cell north = neighborhood[0][1];
		Cell south = neighborhood[2][1];
		Cell west = neighborhood[1][0];
		Cell east = neighborhood[1][2];
		
		return null;
	}
	
	@Override
	public int[] getLocation(Cell[][] neighboorhood) {
		return null;
	}

}
