package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";
	// No extra parameters!

	public GameOfLife() {
		super();

		myDescription = GAME_OF_LIFE;
		myPossibleStates = new State[] { new State(
				"Alive", 0, Color.BLACK, null) };
	}
	
	@Override
	public Patch getNext(Patch patch) {
		List<Patch> neighborhood = getNeighbors(patch);
		Patch newPatch = new Patch(patch);
		
		List<Patch> directNeighbors = new ArrayList<Patch>();
		for (Patch p : neighborhood) {
			if (p.containsCell()) {
				directNeighbors.add(p);
			}
		}
		
		if (patch.containsCell()) {
			if (directNeighbors.size() < 2 || directNeighbors.size() > 3) {
				newPatch.clear();
			}
		}

		if (!patch.containsCell()) {
			if (directNeighbors.size() == 3) {
				newPatch.fill(new Cell(myPossibleStates[0]));
			}	
		}

		return newPatch;
	}

}
