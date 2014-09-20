package cellsociety_team08;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";
	// No extra parameters!

	public GameOfLife(Map<String, Object> params) {
		super(params);
		myDescription = GAME_OF_LIFE;
		myPossibleStates = new State[] { new State(
				"Alive", 0, Color.BLACK, null) };

	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {
		
		List<Patch> directNeighbors = new ArrayList<Patch>();
		for (Patch p : neighborhood) {
			if (p.myRow == patch.myRow || p.myCol == patch.myCol && p.containsCell()) {
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
				patch.fill(new Cell(myPossibleStates[0]));
			}
		}

		return patch;

	}

}
