/*
 * This entire file is part of my masterpiece
 * @MIKE ZHU
 */

package backEnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";

	public GameOfLife(List<ArrayList<Patch>> grid, Map<String, Object> params, Map<String, Color> colorParams) {
		super(grid, params, colorParams);

		myDescription = GAME_OF_LIFE;
		myPossibleStates = new String[] {"Alive"};
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
			newPatch.getCell().changeColor(myColorParams);
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
