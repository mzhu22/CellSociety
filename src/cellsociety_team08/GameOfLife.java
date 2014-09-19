package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";
	private static final State[] possibleStates = new State[] { new State(
			"Alive", 0, Color.GREEN, null) };
	// No extra parameters!

	public GameOfLife(Map<String, Object> params) {
		super(params);
	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {
		
		List<Patch> directNeighbors = new ArrayList<Patch>();
		for (Patch p : neighborhood) {
			if (p.myRow == patch.myRow || p.myCol == patch.myCol) {
				directNeighbors.add(p);
			}
		}
		
		if (patch.containsCell()) {
			if (directNeighbors.size() < 2 || directNeighbors.size() > 3) {
				patch.clear();
			}
		}

		if (!patch.containsCell()) {
			if (directNeighbors.size() == 3) {
				patch.fill(new Cell(possibleStates[0]));
			}
		}

		return patch;

	}

}
