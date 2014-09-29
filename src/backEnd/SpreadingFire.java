package backEnd;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class SpreadingFire extends RuleSet {

	private static final String SPREADING_FIRE = "Spreading of Fire";
	private static final String PROB_CATCH = "probCatch";
	private static float probCatch;
	
	private Random rand = new Random();

	public SpreadingFire() {
		super();
		myPossibleStates = new State[] {
				new State("Tree", 0, Color.GREEN, null), // index 0
				new State("Burning", 1, Color.ORANGERED, null) // index 1
		};

		myDescription = SPREADING_FIRE;
		// TODO: Fix this stupid casting and parsing craziness
	}
	
	@Override
	public void setParams(Map<String, Object> params) {
		super.setParams(params);
		if(myParams.containsKey(PROB_CATCH)){
			probCatch = Float.parseFloat((String) myParams.get(PROB_CATCH));
		}
	}

	@Override
	public Patch getNext(Patch curr) {
		
		List<Patch> neighbors = getDirectNeighbors(curr);
		
		// If it's already empty, do nothing
		if (curr.isEmpty || curr.myCell == null
				|| curr.myCell.getState() == null) {
			return curr;
		}

		// New patch with empty cell
		Patch ret = new Patch(curr.myRow, curr.myCol, true);
		// If it's already empty, do nothing		
		if (curr.isEmpty){
			return curr;
		}

		// If it's already burning, it will be empty on the next iteration
		if (curr.myCell.getState().equals(myPossibleStates[1])) {
			ret.flagged = true;
			return ret;
		}

		
		// If it's already burning, it will be empty on the next iteration
		for (Patch p : neighbors) {
			if ((p.myRow == curr.myRow || p.myCol == curr.myCol)
					&& isBurning(p)) {
				float randFloat = rand.nextFloat();
				if (randFloat <= probCatch) {
					// Start burning!!!
					ret.fill(new Cell(myPossibleStates[1]));
					ret.flagged = true;
					return ret;
				}
			}
		}
		ret.fill(curr.myCell);
		return ret;
	}

	public boolean isBurning(Patch p) {
		// If it just burnt down...
		if (p.flagged && p.isEmpty) return true;
		
		if (p.getCell() == null) {
			return false;
		}
		return (myPossibleStates[1].equals(p.getCell().getState()) && !p.flagged);
	}

}
