package cellsociety_team08;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;

public class SpreadingFire extends RuleSet {
	
	private static final String SPREADING_FIRE = "Spreading of Fire";
	private static final String PROB_CATCH = "probCatch";
	
	private static float probCatch;

	public SpreadingFire(Map<String, Object> params) {
		super(params);

		myDescription = SPREADING_FIRE;
		//TODO: Fix this stupid casting and parsing craziness
		probCatch = Float.parseFloat((String) params.get(PROB_CATCH));

		myPossibleStates = new State[] {
				new State("Tree", 0, Color.GREEN, null), // index 0
				new State("Burning", 1, Color.ORANGERED, null) // index 1
		};	
	}

	@Override
	public Patch getNext(Patch curr, List<Patch> neighborhood) {
		// If it's already empty, do nothing		
		if (curr.isEmpty){
			return curr;
		}
		
		for (Patch p : neighborhood) {
			if ((p.myRow == curr.myRow || p.myCol == curr.myCol) && isBurning(p)) {
				System.out.println(neighborhood.size());

				Random rand = new Random();
				float randFloat = rand.nextFloat();
				if (randFloat <= probCatch) {
					curr.myCell.setState(myPossibleStates[1]);
				} else {
					curr.myCell.setState(myPossibleStates[0]);
				}
			}
		}	

		// If it's already burning, it will be empty on the next iteration
		if (curr.myCell.getState().equals(myPossibleStates[1])) {
			curr.clear();
		}
		
		return curr;
	}
	
	private boolean isBurning(Patch p) {
		if(p.getCell()==null){
			return false;
		}
		//TODO: CHECK WHY THIS COMPARISON DOESNT FUCKING WORK
		return (myPossibleStates[1].myName.equals(p.getCell().getState().myName));
	}

}
