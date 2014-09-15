package cellsociety_team08;

import javafx.scene.paint.Color;

public class SpreadingFire extends RuleSet {
	
	private static final String SPREADING_FIRE = "Spreading of Fire";
	
	private static final State[] possibleStates = new State[] {
		new State("Empty", Color.YELLOW), // index 0
		new State("Tree", Color.GREEN), // index 1
		new State("Burning", Color.FIREBRICK) // index 2
	};

	public SpreadingFire() {
		super(SPREADING_FIRE, possibleStates);
	}

	@Override
	public State getNext(Site[][] neighborhood) {
		
		State currState = neighborhood[1][1].getState();
		Site north = neighborhood[1][0];
		Site south = neighborhood[1][2];
		Site west = neighborhood[0][1];
		Site east = neighborhood[2][1];
		
		// If it's already burning or empty, it will be empty on the next iteration
		if (!currState.equals(possibleStates[1])) return possibleStates[0];
		
		if (isBurning(north) || isBurning(south) || isBurning(west) || isBurning(east)) return possibleStates[2];
		
		return possibleStates[1];
	}
	
	private boolean isBurning(Site cell) {
		if (cell == null) return false;
		return (possibleStates[2].equals(cell.getState()));
	}

}
