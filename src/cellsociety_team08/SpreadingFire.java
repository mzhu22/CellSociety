package cellsociety_team08;

import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;


public class SpreadingFire extends RuleSet {
	
	private static final String SPREADING_FIRE = "Spreading of Fire";
	private static final State[] possibleStates = new State[] {
		new State("Tree", 0, Color.GREEN, null), // index 0
		new State("Burning", 1, Color.ORANGERED, null) // index 1
	};	
	private static float probCatch;

	public SpreadingFire(Object[] params) {
		super(SPREADING_FIRE, possibleStates, params);
		probCatch = (float) params[0];
	}

	@Override
	public Patch getNext(Patch curr, List<Patch> neighborhood) {
		
		// If it's already burning, it will be empty on the next iteration
		if (curr.myCell.getState().equals(possibleStates[1])) {
			curr.isEmpty = true;
			return curr;
		}

		for (Patch p : neighborhood) {
			if ((p.myRow == curr.myRow || p.myCol == curr.myCol) && isBurning(p.myCell)) {
				Random rand = new Random();
				float randFloat = rand.nextFloat();
				if (randFloat <= probCatch) {
					curr.myCell.setState(possibleStates[1]);
				} else {
					curr.myCell.setState(possibleStates[0]);
				}
			}
		}
			
		return curr;
	}
	
	private boolean isBurning(Cell cell) {
		if (cell == null) return false;
		return (possibleStates[1].equals(cell.getState()));
	}

}
