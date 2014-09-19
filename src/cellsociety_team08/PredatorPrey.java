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

		sharkBreedTime = Integer
				.parseInt((String) params.get(SHARK_BREED_TIME));
		sharkStarveTime = Integer.parseInt((String) params
				.get(SHARK_STARVE_TIME));
		fishBreedTime = Integer.parseInt((String) params.get(FISH_BREED_TIME));

		myPossibleStates = new State[] {
				new State("Fish", 0, Color.CHARTREUSE,
						new Object[] { fishBreedTime }), // index 0
				new State("Shark", 1, Color.GREY, new Object[] {
						sharkBreedTime, sharkStarveTime }) // index 1
		};

	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {

		if (patch.isEmpty || patch.flagged)
			return patch;

		if (patch.myCell.getState().equals(myPossibleStates[0])) { // FISH
			checkBreed(patch, neighborhood);
			move(patch, neighborhood);
		} else { // SHARK

			int starveTime = ((int) patch.myCell.getState().myParams[1]);
			if (starveTime == 0) {
				patch.clear();
				return patch;
			}
			starveTime--;
			checkBreed(patch, neighborhood);
			if (!checkForFishAndMove(patch, neighborhood))
				move(patch, neighborhood);

		}

		return null;
	}

	private boolean checkForFishAndMove(Patch patch, List<Patch> neighborhood) {

		for (Patch p : neighborhood) {
			if (!p.isEmpty && !p.flagged && p.getCell().getState().myIndex==0) {
				eatFish(patch, p);
				return true;
			}
		}
		return false;
	}
	
	private void eatFish(Patch shark, Patch fish) {
		fish.myCell = shark.myCell;
		fish.flagged = true;
		shark.clear();
	}

	private void checkBreed(Patch patch, List<Patch> neighborhood) {
		int breedTime = ((int) patch.myCell.getState().myParams[0]);
		if (breedTime == 0) {
			breed(patch, neighborhood);
			if (patch.getCell().getState().myIndex == 0)
				patch.myCell.getState().myParams[0] = fishBreedTime;
			else
				patch.myCell.getState().myParams[0] = sharkBreedTime;
		}
		breedTime--;
		patch.myCell.getState().myParams[0] = breedTime;
	}

	private void move(Patch patch, List<Patch> neighborhood) {
		for (Patch p : neighborhood) {
			if (p.isEmpty && !p.flagged) {
				p.myCell = patch.myCell;
				patch.clear();
				p.flagged = true;
			}
		}
	}

	private void breed(Patch patch, List<Patch> neighborhood) {
		for (Patch p : neighborhood) {
			if (p.isEmpty && !p.flagged) {
				p.myCell = new Cell(
						myPossibleStates[patch.getCell().getState().myIndex]);
				p.flagged = true;
				return;
			}
		}
	}

}
