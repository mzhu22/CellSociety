package cellsociety_team08;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class PredatorPrey extends RuleSet {
	
	private static final String SHARK_BREED_TIME = "sharkBreedTime";
	private static final String SHARK_STARVE_TIME = "sharkStarveTime";
	private static final String FISH_BREED_TIME = "fishBreedTime";
	private static int sharkBreedTime, sharkStarveTime, fishBreedTime;

	public PredatorPrey(Map<String, Object> params) {
		super(params);
		
		sharkBreedTime = Integer.parseInt((String) params.get(SHARK_BREED_TIME));
		sharkStarveTime = Integer.parseInt((String) params.get(SHARK_STARVE_TIME));
		fishBreedTime = Integer.parseInt((String) params.get(FISH_BREED_TIME));
		
		myPossibleStates = new State[] {
				new State("Fish", 0, Color.CHARTREUSE, new Object[]{fishBreedTime}), // index 0
				new State("Shark", 1, Color.GREY, new Object[]{sharkBreedTime, sharkStarveTime}) // index 1
		};
		
	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {

		if (patch.isEmpty || patch.flagged)
			return patch;
		
		if (patch.myCell.getState().equals(myPossibleStates[0])) { // FISH
			patch.myCell.getState().setParams(o);
		} else {
			
		}

		return null;
	}

}
