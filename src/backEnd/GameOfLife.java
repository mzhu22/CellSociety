package backEnd;

import java.util.List;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";

	// No extra parameters!

	public GameOfLife() {
		super();

		myDescription = GAME_OF_LIFE;
		myPossibleStates = new State[] { new State("Alive", 0, Color.BLACK,
				null) };
	}

	@Override
	public Patch getNext(Patch patch) {
		List<Patch> neighborhood = getNeighbors(patch);
		Patch newPatch = new Patch(patch);

		int liveNeighbors = countLiveNeighbors(0, neighborhood);

		if (patch.containsCell() && (liveNeighbors < 2 || liveNeighbors > 3)) {
			newPatch.clear();
		}

		if (!patch.containsCell() && liveNeighbors == 3) {
			newPatch.fill(new Cell(myPossibleStates[0]));
		}
		
		return newPatch;
	}
	
	public int countLiveNeighbors(int liveOnes, List<Patch> theNeighborhood) {
		for (Patch p : theNeighborhood) {
			if (p.containsCell())
				liveOnes++;
		}
		return liveOnes;
	}

}
